/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Eleitor;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



/**
 *
 * @author jms 04_12_2015
 */
public class EleitoresDAO implements Map<Integer,Eleitor>{
    public EleitoresDAO(){
    }

    public void clear(){
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection();
    		Statement s = conn.createStatement();
    		s.executeUpdate("DELETE FROM Eleitores");
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
    public boolean containsKey(Object key){
        boolean b=false;
        Connection conn = null;
        try{
        	conn = Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement(" Select  EXISTS (SELECT nrID FROM Eleitores " +
                " WHERE nrID = ?)");
        	ps.setInt(1,(Integer) key);
        	ResultSet rs = ps.executeQuery();
        	while(rs.next())
        		b = (rs.getInt(1)!=0);
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
    public boolean containsValue(Object value){
        return this.containsKey(((Eleitor)value).getnIdent());
    }
    
    public boolean isEmpty(){
        return this.size()==0;
    }
    
    public int size(){
    	int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select count(*) FROM Eleitores");
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
    public Eleitor remove(Object key){
    	Connection conn  = null;
    	Eleitor e =null;
    	try{
    		conn = Connector.newConnection();
    	    e  = this.get(key); 
    	    PreparedStatement ps = conn.prepareStatement("DELETE FROM Eleitores where nrID= ?");
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
       return e;  
    }

    
    public Set<Integer> keySet(){
        Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try{
        	conn=Connector.newConnection();
        	Statement s = conn.createStatement();
            String querie = " Select nrId FROM Eleitores";
            ResultSet rs = s.executeQuery(querie);
            while(rs.next())
               ret.add(rs.getInt("nrID"));
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
    public Eleitor get(Object key){
        Eleitor el  = null;
        Connection conn = null;
        
        try{
        	conn=Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Eleitores WHERE nrId = ?");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               el = new Eleitor (rs.getString("nome"),rs.getInt("idCirculo"),rs.getInt("nrID"),rs.getString("pin"));
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
    
    
    public Collection<Eleitor> values(){
        ArrayList <Eleitor> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
    }
  
    
    public Eleitor put(Integer key,  Eleitor value){
    	Connection conn=null;
    	Eleitor el = this.remove(key);
    	try{
    		conn = Connector.newConnection();
    		PreparedStatement ps = conn.prepareStatement("insert into Eleitores " +
                    "(nrId,nome,pin,idCirculo) " +
                    "value " +
                    "(?,?,?,?)");
    		ps.setInt(2, value.getnIdent());
            ps.setString(1, value.getNome());
            ps.setString(3, value.getPin());
            ps.setInt(4, value.getCirculo());
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
    	return el;
    }


	@Override
	public void putAll(Map<? extends Integer, ? extends Eleitor> m) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Funçao não implementada");
		
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Eleitor>> entrySet() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Funçao não implementada");
		//return null;
	}
}
