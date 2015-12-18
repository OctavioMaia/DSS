package Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.sql.*;

import Business.Circulo;

public class CirculoDAO implements Map<Integer,Circulo>{
	
	public CirculoDAO() {
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection();
    		PreparedStatement ps = conn.prepareStatement("Select count(*) FROM Circulos");
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
        	conn = Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select  EXISTS (SELECT idCirculo FROM Circulos " +
                " WHERE idCirculo = ?)");
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
	public boolean containsValue(Object value) {
		return this.containsKey(((Circulo)value).getId());
	}

	@Override
	public Circulo get(Object key) {
		Circulo circulo  = null;
        Connection conn = null;
        
        try{
        	conn=Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Circulos WHERE idCirculo = ?");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               circulo = new Circulo (rs.getInt("idCirculo"),rs.getString("nome"),rs.getInt("totEleitores"));
            }
            rs.close();
            ps.close();
            conn.commit();
        }catch(Exception e){
    		try {
				conn.rollback();
				e.printStackTrace();
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
        return circulo;
	}

	@Override
	public Circulo put(Integer key, Circulo value) {
		Connection conn=null;
		Circulo circulo = this.get(key);
    	try{
    		conn = Connector.newConnection();
    		if(circulo==null){
    			PreparedStatement ps = conn.prepareStatement("insert into Circulos " +
                    "(idCirculo,nome,totEleitores) " +
                    "value " +
                    "(?,?,?)");
    			ps.setString(2, value.getNome());
    			ps.setInt(1, key);
    			ps.setInt(3, value.getTotEleitores());
    			ps.execute();
    			ps.close();
    		}else{
    			//UPDATE table_name
    			//SET column1=value1,column2=value2,...
    			//		WHERE some_column=some_value;
    			PreparedStatement ps = conn.prepareStatement("UPDATE Circulos " +
                        "SET nome = ?,totEleitores = ? " +
                        "WHERE idCirculo = ? "); 
                    //    "(?,?,?)");
        		ps.setString(1, value.getNome());
        		ps.setInt(3, key);
        		ps.setInt(2, value.getTotEleitores());
        		ps.execute();
        		ps.close();
    			
    		}
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
    	return circulo;
	}

	@Override
	public Circulo remove(Object key) {
		Connection conn  = null;
    	Circulo circulo =null;
    	try{
    		conn = Connector.newConnection();
    	    circulo  = this.get(key); 
    	    PreparedStatement ps = conn.prepareStatement("DELETE FROM Circulos where idCirculo= ?");
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
       return circulo;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Circulo> m) {
		throw new RuntimeException("Funcao nao implementada");
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = Connector.newConnection();
    		Statement s = conn.createStatement();
    		s.executeUpdate("DELETE FROM Circulos");
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
        	conn=Connector.newConnection();
        	Statement s = conn.createStatement();
            String querie = " Select idCirculo FROM Circulos";
            ResultSet rs = s.executeQuery(querie);
            while(rs.next())
               ret.add(rs.getInt("idCirculo"));
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
	public Collection<Circulo> values() {
		ArrayList <Circulo> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Circulo>> entrySet() {
		throw new RuntimeException("Funcao nao implementada");
	}

}
