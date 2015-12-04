/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Partido;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruifreitas
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
        	//Atençao ver aqui a ordem das Colunas na Base de dados
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Partidos WHERE Id = ?");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               partido = new Partido (rs.getInt(2),rs.getString(4),rs.getString(1),rs.getString(3));
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
        return el;
	}

	@Override
	public Partido put(Integer key, Partido value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partido remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Partido> m) {
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
	public Collection<Partido> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Partido>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

    
    
}
