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

import com.sun.org.glassfish.external.statistics.annotations.Reset;



/**
 *
 * @author jms 04_12_2015
 */
public class EleitoresDAO implements Map<Integer,Eleitor>{
	private static String Tabname ="Eleitores";
	private static String EleitoresEleicao = "Eleitor_vota_Eleicao";
	private static String EleitoresEleicaoEleit = "nrIdEleitor";
	private static String EleitID = "nrID";
	private static String Eleitpin = "pin";
	private static String Eleinome = "nome";
	private static String elitCirc = "idCirculo";
    public EleitoresDAO(){
    }

    
    private void clear_aux(Connection c) throws SQLException{
    	PreparedStatement psVota = c.prepareStatement("DELETE FROM " +EleitoresEleicao );
    	psVota.executeQuery();
    	psVota.close();
    	PreparedStatement psEleit = c.prepareStatement("DELETE FROM " +Tabname );
    	psEleit.executeQuery();
    	psEleit.close();
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
    	PreparedStatement ps = c.prepareStatement(" SELECT  EXISTS (SELECT " +EleitID + " FROM "+ Tabname +
                " WHERE "+EleitID +" = ?)");
    	ps.setInt(1,key);
    	ResultSet rs = ps.executeQuery();
    	if(rs.next()){
    		b = (rs.getInt(1)!=0);
    	}
    	return b;
    }
    
    @Override
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
    
    @Override
    public boolean containsValue(Object value){
        return this.containsKey(((Eleitor)value).getnIdent());
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

    
    private Eleitor remove_aux(Integer key,Connection c) throws SQLException{
    	Eleitor e =null;
    	e = this.get_aux(key);
    	if(e!=null){
    		PreparedStatement psVota = c.prepareStatement("DELETE FROM " + EleitoresEleicao + " WHERE " +
    				EleitoresEleicaoEleit  + " = ?");
    		psVota.setInt(1, key);
    		psVota.executeQuery();
    		psVota.close();
    		PreparedStatement psEleit = c.prepareStatement("DELETE FROM " + Tabname + " WHERE " +
    				EleitID  + " = ?");
    		psEleit.setInt(1, key);
    		psEleit.executeQuery();
    		psEleit.close();	
    	}
    	return e;
    }
   
    
    @Override
    public Eleitor remove(Object key){
    	Connection conn  = null;
    	Eleitor e =null;
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

    ///// Aqui a altear
    public Set<Integer> keySet(){
        Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try{
        	conn=Connector.newConnection();
        	Statement s = conn.createStatement();
            String querie = " Select nrID FROM Eleitores";
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
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Eleitores WHERE nrID = ?");
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
    	Eleitor el = this.get(key);
    	try{
    		conn = Connector.newConnection();
    		if(el==null){//eleitor nao existe
    			PreparedStatement ps = conn.prepareStatement("insert into Eleitores " +
                    "(nrID,nome,pin,idCirculo) " +
                    "value " +
                    "(?,?,?,?)");
    			ps.setInt(1, key);
    			ps.setString(2, value.getNome());
    			ps.setString(3, value.getPin());
    			ps.setInt(4, value.getCirculo());
    			ps.execute();
    			ps.close();
    		}else{
    			PreparedStatement ps = conn.prepareStatement("UPDATE Eleitores " + "SET nome=?,pin=?,idCirculo=? " +
                        "WHERE nrID = ? ");
        			ps.setInt(4, key);
        			ps.setString(1, value.getNome());
        			ps.setString(2, value.getPin());
        			ps.setInt(3, value.getCirculo());
        			ps.execute();
        			ps.close();
    		}
    		conn.commit();
    	}catch(Exception e){
    		try {
    			e.printStackTrace();
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
