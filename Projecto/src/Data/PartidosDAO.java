/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Partido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.sql.*;

/**
 *
 * @author ruifreitas
 * @author jms 04_12_2015
 * 
 */
public class PartidosDAO implements Map<Integer,Partido>{
	private Connector c;
	
    public PartidosDAO(Connector c) throws Exception{
        this.c =c;
    }
	
    
    @Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = this.c.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select  count(*) FROM Partidos");
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

	@Override
	public boolean containsKey(Object id) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn = c.newConnection();
        	PreparedStatement ps = conn.prepareStatement(" Select  EXISTS (SELECT id FROM Partidos " +
                " WHERE id = ?)");
        	ps.setInt(1,(Integer) id);
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
	public boolean containsValue(Object value) {
		return this.containsKey(((Partido)value).getId());
	}

	@Override
	public Partido get(Object key) {
		Partido partido  = null;
        Connection conn = null;
        
        try{
        	conn=this.c.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Partidos WHERE Id = ?");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               partido = new Partido (rs.getInt("id"),rs.getString("sigla"),rs.getString("nome"),rs.getString("simbolo"));
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
        return partido;
	}

	@Override
	public Partido put(Integer key, Partido value) {
		Connection conn=null;
		Partido partido = this.remove(key);
    	try{
    		conn = c.newConnection();
    		PreparedStatement ps = conn.prepareStatement("insert into Partidos " +
                    "(id,nome,simbolo,sigla) " +
                    "value " +
                    "(?,?,?,?)");
    		ps.setString(2, value.getNome());
            ps.setInt(1, key);
            ps.setString(3, value.getSimbolo());
            ps.setString(4, value.getSigla());
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
    	return partido;
	}

	@Override
	public Partido remove(Object key) {
		Connection conn  = null;
    	Partido partido =null;
    	try{
    		conn = this.c.newConnection();
    	    partido  = this.get(key); 
    	    PreparedStatement ps = conn.prepareStatement("DELETE FROM Partidos where id= ?");
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
       return partido;  
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Partido> m) {
		throw new RuntimeException("Funcao nao implementada");
		
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = c.newConnection();
    		Statement s = conn.createStatement();
    		s.executeUpdate("DELETE FROM Partidos");
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
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try{
        	conn=this.c.newConnection();
        	Statement s = conn.createStatement();
            String querie = " Select id FROM Partidos";
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
	public Collection<Partido> values() {
		ArrayList <Partido> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Partido>> entrySet() {
		throw new RuntimeException("Funcao nao implementada");
	}

    
    
}
