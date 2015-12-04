package Data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.sql.*;

import Business.Circulo;

public class CirculoDAO implements Map<Integer,Circulo>{
	private Connector c;
	
	
	public CirculoDAO(Connector c) {
		this.c = c;
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = this.c.newConnection(); 
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
        	conn = c.newConnection();
        	PreparedStatement ps = conn.prepareStatement(" Select  EXISTS (SELECT idCirculo FROM Circulos " +
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Circulo put(Integer key, Circulo value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Circulo remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Circulo> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Circulo> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Circulo>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
