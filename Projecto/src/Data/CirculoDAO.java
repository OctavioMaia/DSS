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
	
	private static String Tabname = "circulos";
	private static String idCirculo = "idCirculo";
	private static String nome = "nome";
	private static String totEleitores = "totEleitores";
	
	public CirculoDAO() {
	}

	
	protected Circulo get_aux(Integer key,Connection c) throws SQLException{
		Circulo e =null;
    	PreparedStatement ps = c.prepareStatement("SELECT * FROM "+Tabname+" WHERE "+idCirculo+" = ?");
    	ps.setInt(1,key);
    	ResultSet rs = ps.executeQuery();
    	if(rs.next()){
    		e =new Circulo(rs.getInt(idCirculo), rs.getString(nome), rs.getInt(totEleitores));
    	}
    	rs.close();
    	ps.close();
    	return e;
	}
	
	public Circulo get(Object key){
        Circulo el  = null;
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
	
	@Override
	public boolean isEmpty() {
		return this.size()==0;
	}

	
	private boolean containsKey_aux(Integer key,Connection c) throws SQLException{
    	boolean b=false;
    	PreparedStatement ps = c.prepareStatement(" SELECT  EXISTS (SELECT " +idCirculo + " FROM "+ Tabname +
                " WHERE "+idCirculo +" = ?)");
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

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((Circulo)value).getId());
	}

	
	private Circulo put_aux(Integer key, Circulo value, Connection c) throws SQLException{
    	Circulo e = this.get_aux(key, c);
    	if(e==null){//novo registo
    		PreparedStatement ps = c.prepareStatement("INSERT INTO "+ Tabname +
                    "("+idCirculo+","+nome+","+totEleitores+")" +
                    " value " +
                    "(?,?,?)");
    		ps.setInt(1, key);
    		ps.setString(2, value.getNome());
    		ps.setInt(3, value.getTotEleitores());
    		ps.execute();
    		ps.close();
    	}else{//registo existente
    		PreparedStatement ps = c.prepareStatement("UPDATE "+ Tabname + 
    				" SET "+nome+" = ?, "+totEleitores +" = ?" +
                    " WHERE "+idCirculo+" = ? ");
    		ps.setString(1, value.getNome());
    		ps.setInt(2, value.getTotEleitores());
    		ps.setInt(3, key);
    		ps.execute();
    		ps.close();
    	}
    	return e;
    }
  
	public Circulo put(Integer key, Circulo value){
	    	Connection conn=null;
	    	Circulo el = null;
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
	
	
	private Set<Integer> keySet_aux(Connection c) throws SQLException{
    	Set<Integer> ret  =new TreeSet<Integer>();
    	PreparedStatement ps  = c.prepareStatement("SELECT " +idCirculo + " FROM " + Tabname );
    	ResultSet rs = ps.executeQuery();
    	while(rs.next()){
    		int num = rs.getInt(idCirculo);
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

	
	
	@Override
	

	public void putAll(Map<? extends Integer, ? extends Circulo> m) {
		throw new RuntimeException("Funcao nao implementada");
	}

	
	public Collection<Circulo> values(){
    	Connection c = null;
    	ArrayList <Circulo> ret  = new ArrayList<>();
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

	@Override
	public Set<java.util.Map.Entry<Integer, Circulo>> entrySet() {
		throw new RuntimeException("Funcao nao implementada");
	}
	
	public Circulo remove(Object key){
		throw new RuntimeException("Funcao nao implementada");
	}
	public void clear (){
		throw new RuntimeException("Funcao nao implementada");
	}

}
