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

import Business.CirculoInfo;
import Business.Circulo;

public class CirculoInfoDAO implements Map<Integer,CirculoInfo>{
	private int idEleicao;
	//Tabela CirculoInfo
	private static String Tabname ="Circulo_has_EleicaoAR";
	private static String Mandatos = "mandatos";
	private static String Circulo = "idCirculo";
	private static String Eleicao = "idEleicao";
	//Tabela Circulos
	private static String TabCirculos = "Circulos";
	private static String CirculoId = "idCirculo";
	private static String CirculoNome = "nome";
	private static String CirculoTotEleitores = "totEleitores";
	
	public CirculoInfoDAO(int idEleicao){
		this.idEleicao = idEleicao;
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = Connector.newConnection(false);
    		PreparedStatement psClear = conn.prepareStatement("DELETE FROM " +Tabname + " WHERE "+Eleicao+" = "+this.idEleicao );
        	psClear.executeUpdate();
        	psClear.close();
        	for(Integer circulo: this.keySet()){
        		new ListaARDAO(this.idEleicao,circulo).clear();
        	}
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
				e.printStackTrace();
			}
		} 	
	}

	@Override
	public boolean containsKey(Object key) {
		Connection conn = null;
		boolean b =false;
        try{
        	conn = Connector.newConnection(true);
        	PreparedStatement ps = conn.prepareStatement(" SELECT  EXISTS (SELECT * FROM "+ Tabname +
        			" WHERE "+Circulo+" = ? AND "+Eleicao+" = "+this.idEleicao+")");
        	ps.setInt(1,(Integer)key);
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()){
        		b = (rs.getInt(1)!=0);
        	}
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
	public boolean containsValue(Object arg0) {
		// Apenas verifica a chave
		return this.containsKey(((CirculoInfo)arg0).getCirculo().getId());
	}

	@Override
	public CirculoInfo get(Object key) {
		CirculoInfo ci  = null;
		Circulo circulo = null;
        Connection conn = null; 
        try{
        	conn=Connector.newConnection(true);
        	PreparedStatement psCirculo = conn.prepareStatement("Select * FROM "+TabCirculos+
        			" WHERE "+CirculoId+" = ?");
        	psCirculo.setInt(1,(Integer)key);
        	ResultSet rsCirculo = psCirculo.executeQuery();
        	if(rsCirculo.next()){
        		circulo = new Circulo(rsCirculo.getInt(CirculoId),
        				rsCirculo.getString(CirculoNome),rsCirculo.getInt(CirculoTotEleitores));
        	}
        	rsCirculo.close();
        	psCirculo.close();
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+Tabname+
        			" WHERE "+Eleicao+" = "+this.idEleicao+" AND "+Circulo+" = ?");
        	ps.setInt(1,(Integer)key);
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()){
        		ci = new CirculoInfo(rs.getInt(Eleicao),circulo,rs.getInt(Mandatos));
        	}
        	rs.close();
        	ps.close();
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
        return ci;
	}

	@Override
	public boolean isEmpty() {
		return this.size()==0;
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = null;
        Connection conn = null;
        try{
        	conn=Connector.newConnection(true);
        	ret = new TreeSet<Integer>();
        	PreparedStatement ps  = conn.prepareStatement("SELECT " +Circulo + " FROM " + Tabname
        			+ " WHERE "+Eleicao+" = "+this.idEleicao);
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()){
        		int num = rs.getInt(Circulo);
        		ret.add(num);
        	}        
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
	public CirculoInfo put(Integer key, CirculoInfo value) {
		Connection conn=null;
    	CirculoInfo cinfo = null;
    	try{
    		conn = Connector.newConnection(false);
    		cinfo = this.get(key);
        	if(cinfo==null){//novo registo
        		PreparedStatement ps = conn.prepareStatement("INSERT INTO "+ Tabname +
                        " ("+Eleicao+","+Circulo+","+Mandatos+") " +
                        "values " +
                        "(?,?,?)");
        		ps.setInt(1, this.idEleicao);
        		ps.setInt(2, key);
        		ps.setInt(3, value.getMandatos());
        		ps.execute();
        		ps.close();
        	}else{//registo existente
        		PreparedStatement ps = conn.prepareStatement("UPDATE "+ Tabname + 
        				" SET "+Mandatos+" = ? WHERE "+Circulo+" = ? AND "+Eleicao+" = "+this.idEleicao);
        		ps.setInt(1, value.getMandatos());
        		ps.setInt(2, key);
        		ps.execute();
        		ps.close();
        	}
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
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
    	return cinfo;
	}

	@Override
	public CirculoInfo remove(Object key) {
		Connection conn  = null;
		CirculoInfo cinfo = null;
		try{
    		conn = Connector.newConnection(false);
    		cinfo = this.get(key);
        	if(cinfo!=null){
        		PreparedStatement ps = conn.prepareStatement("DELETE FROM " + Tabname + " WHERE " +
        				Circulo  + " = ? AND "+Eleicao+" = "+this.idEleicao);
        		ps.setInt(1, (int)key);
        		ps.executeUpdate();
        		ps.close();
        		new ListaARDAO(this.idEleicao,(Integer)key).clear();
        	}
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
    	}
		finally{
    		try {
				conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
				throw new RuntimeException(e2.getMessage());
			}
    	}
       return cinfo;  
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(true); 
    		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM " + Tabname);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) ret = rs.getInt(1);
    		rs.close();
    		ps.close();
		}catch(Exception e){{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
        return ret;  
	}

	@Override
	public Collection<CirculoInfo> values() {
		Connection c = null;
    	ArrayList <CirculoInfo> ret  = new ArrayList<>();
    	try{
    		c = Connector.newConnection(true);
    		Set<Integer> keys = this.keySet();
    		Iterator<Integer> i  = keys.iterator();
            while (i.hasNext()){
                ret.add(this.get(i.next()));
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

	
/*
 * 	Nao implementado
 */
	@Override
	public void putAll(Map<? extends Integer, ? extends CirculoInfo> arg0) {
		throw new RuntimeException("Funçao não implementada");		
	}

	@Override
	public Set<java.util.Map.Entry<Integer, CirculoInfo>> entrySet() {
		throw new RuntimeException("Funçao não implementada");
	}
	
}
