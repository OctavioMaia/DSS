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
import Business.ResultadoCirculoAR;
import Business.Votavel;

public class ListaARDAO implements Map<Integer,Lista>{
	private int Eleicao;
	private int circulo;
	//Tabela das Listas
	private static String TabName ="ListasAR";
	private static String TabListID = "id";
	private static String TabListSimb = "simbolo";
	private static String TabListSig = "sigla";
	private static String TabListNome = "nome";
	private static String TabListIDCirc = "idCirculo";
	private static String TabListIDElei = "idEleicao";
	private static String TabListPart =  "idPartido";
	private static String TabListCol = "idColigacao";
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

	
	
		
	protected Lista get_aux(Integer key,Connection c) throws SQLException{
		return this.get(key);
	}
	public ListaARDAO(int eleicao, int circulo) {
		this.Eleicao = eleicao;
		this.circulo = circulo;
	}
	
	protected void clear_aux(Connection c)throws SQLException{
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while (i.hasNext()) {
			this.remove_aux(i.next(),c);	
		}	
	}
	
	private int size_aux(Connection c) throws SQLException{
		int ret =0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + TabName
				+ " WHERE " + TabListIDCirc + "=?, AND " + TabListIDElei + "=?");
		ps.setInt(1, this.circulo);
		ps.setInt(2, this.Eleicao);
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
    		ret = this.size_aux(conn);
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
		PreparedStatement ps = c.prepareStatement("Select EXISTS "
				+ "(SELECT "+TabListID+" FROM ListasAR WHERE "
				+TabListID +"= ? AND "+TabListIDCirc+"=? AND "+TabListIDElei+" =?)");
		ps.setInt(1, key);
		ps.setInt(2, this.circulo);
		ps.setInt(3, this.Eleicao);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret=rs.getBoolean(1);
		}
		return ret;
		
	}

	@Override
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn = Connector.newConnection(true);
        	b=this.containsKey_aux((Integer)key, conn);
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
    	}catch(Exception e){
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}finally {
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((Lista)value).getID());
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Lista>> entrySet() {
		throw new RuntimeException("Nao implementado");
	}

	@Override
	public Lista get(Object key) {
		Lista l  = null;
        Connection conn = null;
        ArrayList<Candidato> r = new ArrayList<>();
        try{
        	conn=Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select * FROM CandidatosAR WHERE bi IN (SELECT CandidatoAR_bi FROM CandidatoAR_has_ListasAR) WHERE ListasAR_id IN (Select id FROM ListaAR WHERE id = ? ) ");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
            	/*Est� mal esta merda*/
            	
            	r.add(new Candidato(rs.getString("nome"), rs.getInt("bi"), rs.getString("prof"), rs.getDate("dataNasc"), rs.getString("residencia"), rs.getString("naturalidade")));
            }
            l = new Lista(rs.getInt("id"),rs.getString("sigla"),rs.getString("nome"),rs.getString("simbolo"),(Votavel) rs.getObject("mandante"), r);
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
        return l;
	}

	private Set<Integer> keySet_aux(Connection c) throws SQLException{
		Set<Integer> ret = new TreeSet<Integer>();
		PreparedStatement ps  = c.prepareStatement("SELECT "+TabListID+" FROM " +TabName
				+ " WHERE " + TabListIDCirc + "=?, "+ TabListIDElei + "=?");
		ps.setInt(1, this.circulo);
		ps.setInt(2, this.Eleicao);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(TabListID));
		}
		return ret;
	}
	
	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try {
			conn = Connector.newConnection(true);
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
	public Lista put(Integer key, Lista value) {
		Connection conn=null;
		Lista l = this.remove(key);
		ArrayList<Candidato> r=null;
    	try{
    		conn = Connector.newConnection();
    		PreparedStatement ps1 = conn.prepareStatement("INSERT INTO ListasAR (id,ordem,sigla,nome,simbolo) value (?,?,?,?,?)");
            ps1.setInt(1, key);
            ps1.setInt(2, value.getOrdem());
            ps1.setString(3, value.getSigla());
            ps1.setString(4, value.getNome());
            ps1.setString(5, value.getSimbolo());
            ps1.execute();
            ps1.close();
            
            r = value.getCandidatos();
            Iterator<Candidato> i  = r.iterator();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO CandidatoAR_has_ListasAR (CandidatoAR_bi,ListasAR_id) value (?,?)");
            ps2.setInt(2, key);
            PreparedStatement ps3 = conn.prepareStatement("INSERT INTO CandidatosAR (bi,dataNasc,naturalidade,residencia,prof) value (?,?,?,?,?)");
            
            while(i.hasNext()){
            	Candidato c = i.next();
	            ps3.setInt(1, c.getBi());
	            ps3.setDate(2, (Date) c.getDataNasc());
	            ps3.setString(3, c.getNaturalidade());
	            ps3.setString(4, c.getResidencia());
	            ps3.setString(5, c.getProf());
	            ps3.execute();
	            ps2.setInt(1, c.getBi());
	            ps2.execute();
            }
            
            ps2.close();
            ps3.close();
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
    	return l;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Lista> m) {
		throw new RuntimeException("Nao implementado");
		
	}

	protected Lista remove_aux(Integer key, Connection c) throws SQLException{
		return null;
		// TODO
	}
	
	@Override
	public Lista remove(Object key) {
		Connection conn = null;
    	Lista l = null;
    	try{
    		conn = Connector.newConnection();
    	    l  = this.get(key); 
    	    PreparedStatement ps1 = conn.prepareStatement("DELETE FROM CandidatoAR_has_ListasAR WHERE ListasAR_id = ?");
    	    PreparedStatement ps2 = conn.prepareStatement("DELETE FROM ListasAR WHERE id = ?");
    	    ps1.setInt(1,(Integer)key);
    	    ps1.execute();
    	    ps2.setInt(1,(Integer)key);
    	    ps2.execute();
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
       return l;
	}

	private Collection<Lista> values_aux(Connection c) throws SQLException{
		ArrayList <Lista> ret  = new ArrayList<>();
		 Iterator<Integer> i = this.keySet_aux(c).iterator();
        while (i.hasNext()){
            ret.add(this.get_aux(i.next(),c));
        }
        return ret;
	}
	
	@Override
	public Collection<Lista> values() {
		Collection<Lista> ret = new ArrayList<>();
		Connection c = null;
		try{
			c = Connector.newConnection(true);
			ret= this.values_aux(c);	
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
	
	
	
}
