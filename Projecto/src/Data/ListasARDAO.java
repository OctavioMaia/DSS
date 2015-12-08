package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.Circulo;
import Business.Lista;
import Business.Votavel;

public class ListasARDAO implements Map<Integer,Lista>{
	private Connector c;

	public ListasARDAO(Connector c){
		this.c = c;
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = this.c.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select count(*) FROM ListasAR");
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) ret = rs.getInt(1);
    		rs.close();
    		ps.close();
    		conn.commit();
    	}catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        return ret; 
	}

	@Override
	public boolean isEmpty() {
		return this.size()==0;
	}
	
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn = c.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select  EXISTS (SELECT id FROM ListasAR WHERE id = ?)");
        	ps.setInt(1,(Integer) key);
        	ResultSet rs = ps.executeQuery();
        	if (rs.next()) b = (rs.getInt(1)!=0);
        	rs.close();
        	ps.close();
        	conn.commit();
        }catch(Exception e){
        	try {
				conn.rollback();
			} catch (SQLException e1) {	
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	throw new RuntimeException(e.getMessage());
        }finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return b;
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = c.newConnection();
    		Statement s = conn.createStatement();
    		s.executeUpdate("DELETE FROM ListasAR");
    		s.close();
    		conn.commit();
    	}catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		throw new RuntimeException(e.getMessage());
    	}
    	finally {
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((Lista)value).getID());
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Lista>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lista get(Object key) {
		Lista l  = null;
        Connection conn = null;
        
        try{
        	conn=this.c.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select * FROM ListaAR WHERE id = ?");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               l = new Lista(rs.getInt("id"),rs.getString("sigla"),rs.getString("nome"),rs.getString("simbolo"),(Votavel) rs.getObject("mandante"));
            }
            rs.close();
            ps.close();
            conn.commit();
        }catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        return l;
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try{
        	conn=this.c.newConnection();
        	Statement s = conn.createStatement();
            String querie = "Select id FROM ListasAR";
            ResultSet rs = s.executeQuery(querie);
            while(rs.next())
               ret.add(rs.getInt("id"));
            rs.close();
            s.close();
            conn.commit();
        }catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        return ret;
	}

	@Override
	public Lista put(Integer key, Lista value) {
		Connection conn=null;
		Lista l = this.remove(key);
    	try{
    		conn = c.newConnection();
    		PreparedStatement ps = conn.prepareStatement("insert into ListasAR " +
                    "(id,sigla,nome,simbolo,mandante) " +
                    "value " +
                    "(?,?,?,?,?)");
            ps.setInt(1, key);
            ps.setString(2, value.getSigla());
            ps.setString(3, value.getNome());
            ps.setString(4, value.getSimbolo());
            ps.setObject(4, (Votavel) value.getMandante());
            ps.execute();
            ps.close();
            conn.commit();
    	}catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return l;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Lista> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Lista remove(Object key) {
		Connection conn  = null;
    	Lista l =null;
    	try{
    		conn = this.c.newConnection();
    	    l  = this.get(key); 
    	    PreparedStatement ps = conn.prepareStatement("DELETE FROM ListasAR where id= ?");
    	    ps.setInt(1,(Integer)key);
    	    ps.execute();
    	    conn.commit();
    	}catch(Exception e2){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    	}
       return l;
	}

	@Override
	public Collection<Lista> values() {
		ArrayList <Lista> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
	}
	
	
	
}
