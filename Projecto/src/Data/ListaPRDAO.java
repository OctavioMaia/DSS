package Data;

import java.sql.Connection;
import java.sql.Date;
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

import Business.Candidato;
import Business.Lista;
import Business.Votavel;
import java.sql.SQLException;

public class ListaPRDAO implements Map<Integer,Lista> {
	
	public ListaPRDAO (){
		}
	
	public int size() {
		int ret = 0;
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("Select count(*) FROM ListasPR");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) ret = rs.getInt(1);
			rs.close();
			ps.close();
			conn.commit();
			}
		catch(Exception e){
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
	
	public boolean isEmpty() {
		return (this.size()==0);
	}
	
	public boolean containsKey(Object key) {
		boolean b = false;
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("Select EXISTS (SELECT ID FROM ListasPR WHERE idListaPR=?)");
			ps.setInt(1,(Integer) key);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) b = (rs.getInt(1)!=0);
			rs.close();
			ps.close();
			conn.commit();
			}
		catch(Exception e){
        	try {
				conn.rollback();
			} catch (SQLException e1) {	
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	throw new RuntimeException(e.getMessage());
        }
		finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return b;
	}
	
	public void clear() {
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM ListasPR");
			s.close();
			conn.commit();
		}
		catch(Exception e){
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
	
	public Lista get(Object key){
		if (!this.containsKey(key)) return null;
		
		
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT ")
		}
		
	}
	
	
	
	}
		
		
	


