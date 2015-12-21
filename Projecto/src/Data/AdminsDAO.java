package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.Admin;
import Business.Eleitor;

public class AdminsDAO {

	private static String idAdmin = "idAdmin";
	private static String Tabname = "Admin";
	private static String pin = "pin";
	
	public AdminsDAO(){}
	
	private void clear_aux(Connection c) throws SQLException{
    	PreparedStatement psDeleteAdmin = c.prepareStatement("DELETE FROM " +Tabname );
    	psDeleteAdmin.executeQuery();
    	psDeleteAdmin.close();
    }
	
	public void clear(){
    	
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(false);
    		this.clear_aux(conn);
    		conn.commit();
    	}catch(SQLException e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    	}catch(Exception e){
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
	
	private boolean containsKey_aux(Integer key,Connection c) throws SQLException{
    	boolean b=false;
    	PreparedStatement ps = c.prepareStatement(" SELECT  EXISTS (SELECT " +idAdmin + " FROM "+ Tabname +
                " WHERE "+idAdmin +" = ?)");
    	ps.setInt(1,key);
    	ResultSet rs = ps.executeQuery();
    	if(rs.next()){
    		b = (rs.getInt(1)!=0);
    	}
    	return b;
    }
	
	public boolean containsKey(Object key){
        boolean b=false;
        Connection conn = null;
        try{
        	conn = Connector.newConnection(true);
        	b = this.containsKey_aux((Integer)key, conn);
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException(e.getMessage());
        }finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return b;
    }
	
	public boolean containsValue(Object value){
        return this.containsKey(((Admin)value).getIdAdmin());
    }
	
	public boolean isEmpty(){
        return this.size()==0;
    }
	
	
	private int size_aux(Connection c) throws SQLException{
    	int ret=0;
    	PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + Tabname);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) ret = rs.getInt(1);
		rs.close();
		ps.close();
    	return ret;
    }
	
	public int size(){
    	int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(true); 
    		ret = this.size_aux(conn);
    	}catch(Exception e){{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
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
	
	
	private Admin remove_aux(Integer key,Connection c) throws SQLException{
    	Admin e =null;
    	e = this.get_aux(key,c);
    	if(e!=null){
    		PreparedStatement psAdmin = c.prepareStatement("DELETE FROM " + Tabname + " WHERE " +
    				idAdmin  + " = ?");
    		psAdmin.setInt(1, key);
    		psAdmin.executeQuery();
    		psAdmin.close();	
    	}
    	return e;
    }
	
	public Admin remove(Object key){
    	Connection conn  = null;
    	Admin e =null;
    	try{
    		conn = Connector.newConnection(false);
    	    e  = this.remove_aux((Integer)key, conn);
    	    conn.commit();
    	}catch(SQLException ex){
    		try{
    			conn.rollback();
    		}catch(Exception ex1){
    			ex1.printStackTrace();
    			throw new RuntimeException(ex1.getMessage());
    		}
    	}
    	catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
				throw new RuntimeException(e2.getMessage());
			}
    	}
       return e;  
    }
	
	
	private Admin get_aux(Integer key,Connection c) throws SQLException{
    	Admin e =null;
    	PreparedStatement ps = c.prepareStatement("SELECT * FROM "+Tabname+" WHERE "+idAdmin+" = ?");
    	ps.setInt(1,key);
    	ResultSet rs = ps.executeQuery();
    	if(rs.next()){
    		e =new Admin(rs.getInt(idAdmin),rs.getString(pin));
    	}
    	rs.close();
    	ps.close();
    	return e;
    }

	public Admin get(Object key){
        Admin el  = null;
        Connection conn = null;
        
        try{
        	conn=Connector.newConnection(true);
        	el = this.get_aux((Integer)key, conn);
        }catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
        return el;
     
    }

	
	private Set<Integer> keySet_aux(Connection c) throws SQLException{
    	Set<Integer> ret  =new TreeSet<Integer>();
    	PreparedStatement ps  = c.prepareStatement("SELECT " +idAdmin + " FROM " + Tabname );
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()){
    		int num = rs.getInt(idAdmin);
    		ret.add(num);
    	}
    	
    	return ret;
    	
    }

	public Set<Integer> keySet(){
        Set<Integer> ret = null;
        Connection conn = null;
        try{
        	conn=Connector.newConnection(true);
        	ret = this.keySet_aux(conn);
        }catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
        return ret;
    }

	
	public Collection<Admin> values(){
    	Connection c = null;
    	ArrayList <Admin> ret  = new ArrayList<>();
    	try{
    		c = Connector.newConnection(true);
    		Set<Integer> keys = this.keySet_aux(c);
    		Iterator<Integer> i  = keys.iterator();
            while (i.hasNext()){
                ret.add(this.get_aux((Integer)i.next(), c));
            }
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new RuntimeException(e.getMessage());
    	}finally {
    		try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

        return ret;
    }

	
	private Admin put_aux(Integer key, Admin value, Connection c) throws SQLException{
    	Admin e = this.get_aux(key, c);
    	if(e==null){//novo registo
    		PreparedStatement ps = c.prepareStatement("INSERT INTO "+ Tabname +
                    "("+idAdmin+","+pin+")" +
                    "value " +
                    "(?,?)");
    		ps.setInt(1, key);
    		ps.setString(2,value.getPin());
    		ps.execute();
    		ps.close();
    	}else{//registo existente
    		PreparedStatement ps = c.prepareStatement("UPDATE "+ Tabname + 
    				" SET "+idAdmin+" = ?, "+pin +" = ? " +
                    " WHERE "+idAdmin+" = ? ");
    		ps.setInt(1, key);
    		ps.setString(2, value.getPin());
    		ps.setInt(3, key);
    		ps.execute();
    		ps.close();
    	}
    	return e;
    }

	public Admin put(Integer key,  Admin value){
    	Connection conn=null;
    	Admin el = null;
    	try{
    		conn = Connector.newConnection(false);
    		el = this.put_aux(key, value, conn);
    		conn.commit();
    	}catch(Exception e){
    		try {
    			conn.rollback();
    			e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
    	return el;
    }
	
	
	public void putAll(Map<? extends Integer, ? extends Eleitor> m) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Funcao nao implementada");
		
	}

	public Set<java.util.Map.Entry<Integer, Eleitor>> entrySet() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Funcao nao implementada");
		//return null;
	}

}
