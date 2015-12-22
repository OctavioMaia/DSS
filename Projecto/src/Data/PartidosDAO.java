/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;


import Business.Partido;
import java.util.ArrayList;
import java.util.Collection;
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
	private static String TabPartID = "id";
	private static String TabPartNome = "nome";
	private static String TabPartSimb = "simbolo";
	private static String TabPartSigl = "sigla";
	private static String TabPartRemov = "removido";
	/*
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
	*/
	
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
                " WHERE "+TabPartID+ "= ?)");
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
	
	protected Partido get_aux(Integer key,Connection c) throws SQLException{
		Partido p =null;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " + TabPartName + " WHERE " + TabPartID+ "=?");
		ps.setInt(1,key);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			String sigla = rs.getString(TabPartSigl);
			String nome = rs.getString(TabPartNome);
			String simbolo = rs.getString(TabPartSimb);
			boolean eleim = rs.getBoolean(TabPartRemov);
			p = new Partido(key, sigla, nome, simbolo,eleim);
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

	
	protected Partido put_aux(Integer key,Partido value, Connection c) throws SQLException{
		Partido p = this.get_aux(key, c);
		if(p!=null){//update
			PreparedStatement ps = c.prepareStatement("UPDATE " +TabPartName+ 
                    " SET "+TabPartID+"=?,"+TabPartNome+"=?,"+TabPartSimb+"=?,"+TabPartSigl+"=?,"+TabPartRemov+"=? "+
                    "WHERE " +TabPartID +"=?");
			ps.setInt(1,key);
			ps.setString(2,value.getNome());
			ps.setString(3,value.getSimbolo());
			ps.setString(4,value.getSigla());
			ps.setBoolean(5,value.isRemovido());
			ps.setInt(6,key);
			ps.execute();
			ps.close();
			
		}else{//novo
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabPartName+ 
                    " ("+TabPartID+","+TabPartNome+","+TabPartSimb+","+TabPartSigl+","+TabPartRemov+")" +
                    " value " +
                    "(?,?,?,?,?)");
			ps.setInt(1,key);
			ps.setString(2,value.getNome());
			ps.setString(3,value.getSimbolo());
			ps.setString(4,value.getSigla());
			ps.setBoolean(5,value.isRemovido());
			ps.execute();
			ps.close();
		}
		return p;
	}
	
	@Override
	public Partido put(Integer key, Partido value) {
		Connection conn=null;
		Partido partido = null;
    	try{
    		conn = Connector.newConnection(false);
    		partido = this.put_aux(key, value, conn);
            conn.commit();
    	}catch(SQLException e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
    	}catch (Exception e){
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}
    	finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
    	return partido;
	}

	private Partido remove_aux(Integer key,Connection c) throws SQLException{
		ColigacaoDAO cdao = new ColigacaoDAO();
		Partido p = this.get_aux((Integer)key, c);
		if(p!=null){
			PreparedStatement ps = c.prepareStatement("UPDATE " + TabPartName+
					" SET "+TabPartRemov+"=true"+" WHERE id=?");
			ps.setInt(1, key);
			ps.executeUpdate();
			ps.close();
			PreparedStatement ps1 = c.prepareStatement("SELECT idColigacao FROM Partido_pertence_Coligacao WHERE idPartido = ?");
			ps1.setInt(1, key);
			ResultSet rs = ps1.executeQuery();
			while(rs.next()){
				cdao.remove_aux(rs.getInt("idColigacao"), c);
			}
			p.setRemovido(true);
			PreparedStatement ps2 = c.prepareStatement("DELETE FROM Partido_pertence_Coligacao WHERE idPartido= ?");
			ps2.setInt(1,key);
			ps2.execute();
			ps2.close();
		}
		return p;
	}
	
	@Override
	public Partido remove(Object key) {
		Connection conn  = null;
    	Partido partido =null;
    	try{
    		conn = Connector.newConnection(false);
    	    partido  = this.remove_aux((Integer)key, conn);
    	    conn.commit();
    	}catch(SQLException e){
    		try {
				conn.rollback();
			} catch (SQLException e1){
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}catch(Exception e ){
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}
    	finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
       return partido;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Partido> m) {
		throw new RuntimeException("Funcao nao implementada");
		
	}

	private void clear_aux(Connection c) throws SQLException{
		Iterator<Integer> kyes = this.keySet_aux(c).iterator();
		while(kyes.hasNext()){
			this.remove_aux(kyes.next(),c);
		}
	}
	
	@Override
	public void clear() {
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
				throw new RuntimeException(e1.getMessage());
			}
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}catch(Exception e){
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}
    	
    	finally {
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		
	}

	private Set<Integer> keySet_aux(Connection c) throws SQLException{
		Set<Integer> ret = new TreeSet<Integer>();
		PreparedStatement ps = c.prepareStatement("SELECT " + TabPartID +" FROM " + TabPartName);
		ResultSet rs  = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(TabPartID));
		}
		return ret;
	}
	
	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
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
	public Collection<Partido> values() {
		ArrayList <Partido> ret  = new ArrayList<>();
		Connection c = null;
		try{
			c = Connector.newConnection(true);
	        Iterator<Integer> i  = this.keySet_aux(c).iterator();
	        while (i.hasNext()){
	            ret.add(this.get_aux(i.next(),c));
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
	public Set<java.util.Map.Entry<Integer, Partido>> entrySet() {
		throw new RuntimeException("Funcao nao implementada");
	}
       
}
