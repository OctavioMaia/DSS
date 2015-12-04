/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.Eleitor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author joaosilva
 */
public class EleitoresDAO implements Map<Integer,Eleitor>{
    private Connector c;
    public EleitoresDAO(Connector c) throws Exception{
        this.c =c;
    }

    public void clear(){
    	Connection conn = null;
    	try{
    		conn = c.newConnection();
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
    public boolean containsKey(Object bi){
        boolean b=false;
        Connection conn = null;
        try{
        	conn = c.newConnection();
        	PreparedStatement ps = conn.prepareStatement(" Select  EXISTS (SELECT bi FROM Eleitores " +
                " WHERE bi = ?)");
        	ps.setInt(1,(Integer) bi);
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
    public boolean containsValue(Object e){
        return this.containsKey(((Eleitor)e).getnIdent());
    }
    
    public boolean isEmpty(){
        return this.size()==0;
    }
    
    public int size(){
    	int ret=0;
    	Connection conn = null;
    	try{
    		conn = this.c.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select  count(*) FROM Eleitores");
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
    public Eleitor remove(Object bi){
    	Connection conn  = null;
    	Eleitor e =null;
    	try{
    		conn = this.c.newConnection();
    	    e  = this.get(bi); 
    	    PreparedStatement ps = conn.prepareStatement("DELETE FROM Eleitores where bi= ?");
    	    ps.setInt(1,(Integer) bi);
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
        	conn=this.c.newConnection();
        	Statement s = conn.createStatement();
            String querie = " Select Bi FROM Eleitores;";
            ResultSet rs = s.executeQuery(querie);
            while(rs.next())
               ret.add(rs.getInt("Bi"));
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
    public Eleitor get(Object bi){
        Eleitor el  = null;
        Connection conn = null;
        
        try{
        	conn=this.c.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Eleitores WHERE Bi = ?");
        	ps.setInt(1, (Integer)bi);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               el = new Eleitor (rs.getString(2),rs.getInt(4),rs.getInt(1),rs.getInt(3));
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
    
//    public Collection<Eleitor> values(){
//        return this.map.values();
//    }
    
    public Collection<Eleitor> values(){
        ArrayList <Eleitor> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
    }
  
    
    public Eleitor put(Integer bi,  Eleitor ep){
    	Connection conn=null;
    	Eleitor el = this.remove(bi);
    	try{
    		conn = c.newConnection();
    		PreparedStatement ps = conn.prepareStatement("insert into Eleitores " +
                    "(BI,Nome,PIN,CirculoID) " +
                    "value " +
                    "(?,?,?,?)");
    		ps.setString(2, ep.getNome());
            ps.setInt(1, ep.getnIdent());
            ps.setInt(3, ep.getPin());
            ps.setInt(4, ep.getCirculo());
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
