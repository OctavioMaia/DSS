/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Business.CandidatoAR;
import Business.Partido;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.sql.*;

/**
 *
 * @author ruifreitas
 * @author jms 21_12_2015
 * 
 */
public class PartidosDAO implements Map<Integer,Partido>{
	
	//Tabela de Partidos
	private static String TabPartName = "Partidos";
	private static String TabPartID = "Partidos";
	private static String TabPartNome = "nome";
	private static String TabPartSimb = "simbolo";
	private static String TabPartSigl = "sigla";
	//Tabela de CandidatosAR
	private static String TabCandid = "CandidatosPR";
	private static String TabCandidID = "bi";
	private static String TabCandidProf = "prof";
	private static String TabCandidNasc = "dataNasc";
	private static String TabCandidResid = "residencia";
	private static String TabCandidNat = "naturalidade";
	private static String TabCandidNome = "nome";
	private static String TabCandidTipo = "tipo";
	private static String TabCandidPart = "idPartido";

	
    public PartidosDAO(){
    }
	
    
    private int size_aux(Connection c) throws SQLException{
    	int ret =0;
    	PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM "+TabPartName);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret = rs.getInt(1);
		}
		rs.close();
		ps.close();
    	return ret;
    }
    
    
    @Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(true); 
    		ret= this.size_aux(conn);
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
	public boolean isEmpty() {
		return this.size()==0;
	}

	private boolean containsKey_aux(Integer key,Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement(" SELECT EXISTS (SELECT "+TabPartID+" FROM " + TabPartName+ 
                " WHERE id = ?)");
		ps.setInt(1,key);
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()){
    		ret = (rs.getInt(1)!=0);
    	}
    	rs.close();
    	ps.close();
    	return ret;
	}
	
	@Override
	public boolean containsKey(Object key) {
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
	        	throw new RuntimeException(e.getMessage());
			}
        }
        return b;
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((Partido)value).getId());
	}

/*	
	private Collection<CandidatoAR> getCandLista(Integer key,Connection c) throws SQLException{
		ArrayList<CandidatoAR> ret  = new ArrayList<>();
		PreparedStatement ps  =c.prepareStatement("SELECT "+TabCandidID+" FROM "+TabCandid+ " WHERE " + TabCandidPart +"=?");
		ps.setInt(1, key);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			CandidatoAR cn = this.getCandAR(rs.getInt(TabCandidID), c);
			ret.add(cn);
		}
		return ret;
	}
	
	private CandidatoAR getCandAR(Integer key,Connection c) throws SQLException{
		CandidatoAR cad = null;
		PreparedStatement ps = c.prepareStatement("SLEECT * FROM "+TabCandid+ " WHERE " + TabCandidID +"=?");
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			String nome = rs.getString(TabCandidNome);
			int bi = rs.getInt(TabCandidID);
			String prof = rs.getString(TabCandidProf);
			Calendar dataNasc = new GregorianCalendar();
			dataNasc.setTimeInMillis(rs.getDate(TabCandidNasc).getTime());
			String residencia = rs.getString(TabCandidResid);
			String naturalidade = rs.getString(TabCandidNat);
			String partido = rs.getString(TabCandidPart);
			Character tipo = rs.getString(TabCandidTipo).charAt(0);
			cad = new CandidatoAR(nome, bi, prof, dataNasc, residencia, naturalidade, partido, tipo);
		}
		return cad;
	}*/
	private Partido get_aux(Integer key,Connection c) throws SQLException{
		Partido p =null;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " + TabPartName + " WHERE " + TabPartID+ "=?");
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			String sigla = rs.getString(TabPartSigl);
			String nome = rs.getString(TabPartNome);
			String simbolo = rs.getString(TabPartSimb);
			p = new Partido(key, sigla, nome, simbolo);
		}
		rs.close();
		ps.close();
		return p;
	}
	
	
	@Override
	public Partido get(Object key) {
		Partido partido  = null;
        Connection conn = null;
        
        try{
        	conn=Connector.newConnection(true);
        	partido = this.get_aux((Integer)key, conn);
        	
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
        return partido;
	}

	
	private Partido put_aux(Integer key,Partido value, Connection c) throws SQLException{
		Partido p = this.get_aux(key, c);
		if(p!=null){//update
			PreparedStatement ps = c.prepareStatement("UPDATE " + TabPartName+ 
                    " SET "+TabPartNome+"=?,"+TabPartSimb+"=?,"+TabPartSigl+"=?," +
                    " WHERE " +TabPartID +"=?");
			ps.setInt(1,key);
			ps.setString(2,value.getNome());
			ps.setString(3,value.getSimbolo());
			ps.setString(4,value.getSigla());
			ps.execute();
			ps.close();
			
		}else{//novo
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabPartName+ 
                    " ("+TabPartID+","+TabPartNome+","+TabPartSimb+","+TabPartSigl+") " +
                    " value " +
                    " (?,?,?,?)");
			ps.setInt(1,key);
			ps.setString(2,value.getNome());
			ps.setString(3,value.getSimbolo());
			ps.setString(4,value.getSigla());
			ps.execute();
			ps.close();
		}
		return ret
	}
	
	@Override
	public Partido put(Integer key, Partido value) {
		Connection conn=null;
		Partido partido = this.remove(key);
    	try{
    		conn = Connector.newConnection();
    		PreparedStatement ps = conn.prepareStatement("insert into Partidos " +
                    "(id,nome,simbolo,sigla) " +
                    "value " +
                    "(?,?,?,?)");
    		ps.setString(2, value.getNome());
            ps.setInt(1, key);
            ps.setString(3, value.getSimbolo());
            ps.setString(4, value.getSigla());
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
    	return partido;
	}

	@Override
	public Partido remove(Object key) {
		Connection conn  = null;
    	Partido partido =null;
    	try{
    		conn = Connector.newConnection();
    	    partido  = this.get(key); 
    	    PreparedStatement ps = conn.prepareStatement("DELETE FROM Partidos where id= ?");
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
       return partido;  
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Partido> m) {
		throw new RuntimeException("Funcao nao implementada");
		
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = Connector.newConnection();
    		Statement s = conn.createStatement();
    		s.executeUpdate("DELETE FROM Partidos");
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
            String querie = " Select id FROM Partidos";
            ResultSet rs = s.executeQuery(querie);
            while(rs.next())
               ret.add(rs.getInt("id"));
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
	public Collection<Partido> values() {
		ArrayList <Partido> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Partido>> entrySet() {
		throw new RuntimeException("Funcao nao implementada");
	}

    
    
}
