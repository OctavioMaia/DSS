package Data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.Candidato;
import Business.Lista;
import Business.ListaPR;

public class ListaPRDAO implements Map<Integer,ListaPR> {
	private int idEleicao;
	private ResultadoCirculoPRDAO v1;
	private ResultadoCirculoPRDAO v2;
	//Tabela de Lista 
	private static String TabLista ="listasPR";
	private static String TabId ="idlistaPR";
	private static String Volta2 ="volta2";
	private static String Ordem1 ="ordem1";
	private static String Ordem2 ="ordem2";
	private static String Eleicao ="idEleicao";
	private static String Candidato ="biCandidato";
	//Tabela do candidadtoPR
	private static String TabCandid = "CandidatosPR";
	private static String TabCandidID = "bi";
	private static String Prof = "prof";
	private static String Nasc = "dataNasc";
	private static String Resid = "residencia";
	private static String Nat = "naturalidade";
	private static String Nome = "nome";
	private static String Foto = "foto";
	//Tabela ResultadoLista
	private static String TabRes = "Resultado_ListaPR";
	//private static String IdCirculo = "idCirculo";
	private static String IdListaPR = "idlistaPR";
	//private static String Votos = "NrVotos";
	private static String IdEleic = "idEleicao";
	//private static String Volta = "volta";

	
	public ListaPRDAO (int idEleicao){
		this.idEleicao=idEleicao;
		this.v1 = new ResultadoCirculoPRDAO(this.idEleicao, 1);
		this.v2 = new ResultadoCirculoPRDAO(this.idEleicao, 2);
	}
	
	
	private int size_aux(Connection c) throws SQLException{
		int ret=0;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM "+TabLista+" WHERE "+Eleicao+" = ?");
		ps.setInt(1, this.idEleicao);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ret++;
		}
		rs.close();
		ps.close();
		return ret;
	}

	@Override
	public int size() {
		int ret = 0;
		Connection conn = null;
		try{
			conn = Connector.newConnection(true);
			ret = this.size_aux(conn);
			}
		catch(Exception e){
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
		return (this.size()==0);
	}
	
	
	private boolean containsKey_aux(Integer key,Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement("SELECT "+TabId+" FROM "
				+TabLista +" WHERE "+TabId+"=? AND "+Eleicao+"=?");
		ps.setInt(1,key);
		ps.setInt(2, this.idEleicao);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			ret = true;
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	@Override
	public boolean containsKey(Object key) {
		boolean b = false;
		Connection conn = null;
		try{
			conn = Connector.newConnection(true);
			b = this.containsKey_aux((Integer)key, conn);
		}
		catch(Exception e){
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
        return b;
	}
	
	protected void clear_aux(Connection c)throws SQLException{
		//Limpar Resultados das lsiats desta eleicao
		this.v1.clear_aux(c);
		this.v2.clear_aux(c);
		Iterator<Integer> i = this.keySet_aux(c).iterator();//Todas as listas da eleicao
		//EliminaTodas as listas Desta eleicao
		//eliminar o candidato de todas as listas
		while(i.hasNext()){
			this.remove_aux(i.next(), c);
		}
	}
	
	@Override
	public void clear() {
		Connection conn = null;
		try{
			conn = Connector.newConnection(false);
			this.clear_aux(conn);
			conn.commit();
		}
		catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
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
	
	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((ListaPR)value).getIdEleicao());
	}
	
	private boolean containsCand(Integer key, Connection c) throws SQLException{
		boolean ret =false;
		PreparedStatement psexi = c.prepareStatement("SELECT EXISTS (SELECT "+TabCandidID+
				" FROM " + TabCandid+
				" WHERE "+TabCandidID+"=?)");
		psexi.setInt(1, key);
		ResultSet rsExis = psexi.executeQuery();
		if(rsExis.next()){
			ret=rsExis.getInt(1)==1;
		}
		rsExis.close();
		psexi.close();
		return ret;
	}
	
	private Candidato get_candid(Integer key, Connection c) throws SQLException{
		Candidato ret = null;
		if(this.containsCand(key, c)){
			PreparedStatement psCand = c.prepareStatement("SELECT * FROM "+TabCandid+" WHERE "
					+TabCandidID+" = ?");
			psCand.setInt(1, key);
			ResultSet rsc = psCand.executeQuery();
			if(rsc.next()){
				GregorianCalendar n = new GregorianCalendar();
				n.setTime(rsc.getDate(Nasc));
				ret = new Candidato(rsc.getString(Nome),key, rsc.getString(Prof), 
						n, rsc.getString(Resid),
						rsc.getString(Nat),rsc.getString(Foto));
			}
			rsc.close();
			psCand.close();
		}
		
		return ret;
	}
	

	private Candidato insereCand(Candidato c, Connection conn) throws SQLException{
		int key  =c.getBi();
		Candidato ret = this.get_candid(key, conn);
		if(ret!=null){
			PreparedStatement candUP = conn.prepareStatement("UPDATE " + TabCandid
					+ " SET "+Prof+"=?,"+Nasc+"=?,"+Resid+"=?,"+Nat+"=?,"+Nome+"=?," + Foto + "=?"
					+ " WHERE "+TabCandidID+"=?");
			candUP.setString(1,c.getProf());
			candUP.setDate(2, new Date(c.getDataNasc().getTimeInMillis()));
			candUP.setString(3, c.getResidencia());
			candUP.setString(4, c.getNaturalidade());
			candUP.setString(5, c.getNome());
			candUP.setInt(7, c.getBi());
			candUP.setString(6, c.getFoto());
			candUP.executeUpdate();
			candUP.close();
		}else{
			PreparedStatement candIN = conn.prepareStatement("INSERT INTO " + TabCandid
					+ " ("+Prof+","+Nasc+","+Resid+","+Nat+","+Nome+","+TabCandidID+","+Foto+") "
					+ " VALUES "
					+ " (?,?,?,?,?,?,?)");
			candIN.setString(1,c.getProf());
			candIN.setDate(2, new Date(c.getDataNasc().getTimeInMillis()));
			candIN.setString(3, c.getResidencia());
			candIN.setString(4, c.getNaturalidade());
			candIN.setString(5, c.getNome());
			candIN.setInt(6, c.getBi());
			candIN.setString(7, c.getFoto());
			candIN.executeUpdate();
			candIN.close();
		}
		return ret;
	}
	
	
	protected ListaPR get_aux(Integer key, Connection c) throws SQLException{
		
		ListaPR ret = null;
		if(this.containsKey_aux(key, c)){
			PreparedStatement ps = c.prepareStatement("SELECT * FROM "+TabLista+" WHERE "
				+ TabId+" = ? AND "
				+ Eleicao+" = ? ");
			ps.setInt(1, key);
			ps.setInt(2, this.idEleicao);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				int candKey = rs.getInt(Candidato);
				//ir buscar o candidadto para a lista
				Candidato cand = this.get_candid(candKey, c);
				//Criar a lista
				ret = new ListaPR(this.idEleicao, key, rs.getBoolean(Volta2),
						rs.getInt(Ordem1), rs.getInt(Ordem2), cand);	
			}
		}
		return ret;
	}
	
	@Override
	public ListaPR get(Object key){
		Connection conn = null;
		ListaPR ret = null;
		try{
			conn = Connector.newConnection(true);
			ret = this.get_aux((Integer)key, conn);

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
		return ret;
		
	}

	@Override
	public ListaPR put(Integer key, ListaPR value){
		Connection c = null;
		ListaPR ret = null;
		try{
			c=Connector.newConnection(false);
			ret = this.put_aux(key, value, c);
			c.commit();
		}catch(SQLException e){
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		return ret;
	}
	
	public ListaPR put_aux(Integer key, ListaPR value,Connection c) throws SQLException{
		ListaPR ret = this.get_aux(key,c);
			//Inserir o candidadato
		this.insereCand(value.getCandidato(),c);
			//Candidadto atulaizado na BD
			if(ret!=null){
				PreparedStatement psupLista = c.prepareStatement("UPDATE " + TabLista
						+ " SET "+Volta2+" = ?, "+Ordem2+" = ?, "+Ordem1+" = ?, "+Candidato+" = ?"
						+ " WHERE "+TabId+" = ? AND "+Eleicao+" = ?");
				psupLista.setBoolean(1, value.getVolta2());
				psupLista.setInt(2, value.ordem2());
				psupLista.setInt(3, value.ordem1());
				psupLista.setInt(4, value.getCandidato().getBi());
				psupLista.setInt(5, key);
				psupLista.setInt(6, this.idEleicao);
				psupLista.execute();
				psupLista.close();
				//Caso exista update feito
			}else{
				PreparedStatement psupLista = c.prepareStatement("INSERT INTO " + TabLista
						+ " ("+Volta2+","+Ordem2+","+Ordem1+","+Candidato+","+TabId+","+Eleicao+")"
						+ " VALUES "
						+ " (?,?,?,?,?,?)");
				psupLista.setBoolean(1, value.getVolta2());
				psupLista.setInt(2, value.ordem2());
				psupLista.setInt(3, value.ordem1());
				psupLista.setInt(4, value.getCandidato().getBi());
				psupLista.setInt(5, key);
				psupLista.setInt(6, this.idEleicao);
				psupLista.execute();
				psupLista.close();
			}
		return ret;
	}

	private void removeCand(Integer key,Connection c) throws SQLException{
		PreparedStatement ps = c.prepareStatement("DELETE FROM "+TabCandid
				+ " WHERE " + TabCandidID + "= ?");
		ps.setInt(1, key);
		ps.execute();
		ps.close();
	}
	
	private ListaPR remove_aux(Integer key, Connection c) throws SQLException{
		ListaPR ret = this.get_aux(key,c);
		if(ret!=null){
			//Remover resultados
			PreparedStatement psRes = c.prepareStatement("DELETE FROM "+TabRes+" WHERE "
					+ IdEleic+" =? AND "
					+ IdListaPR+"= ?");
			psRes.setInt(1,this.idEleicao);
			psRes.setInt(2,key);
			psRes.executeUpdate();
			psRes.close();
			//Remover Lista
			PreparedStatement ps = c.prepareStatement("DELETE FROM "+TabLista+" WHERE "
					+ Eleicao+ "= ? AND "
					+ TabId+ " = ?");
			ps.setInt(1, this.idEleicao);
			ps.setInt(2,key);
			ps.executeUpdate();
			ps.close();
			//Remover o candidadto?
			this.removeCand(ret.getCandidato().getBi(), c);
			
		}
		
		return ret;
	}
	
	@Override
	public ListaPR remove(Object key) {
		ListaPR ret = null;
		Connection conn =null;
		try{
			conn=Connector.newConnection(false);
			ret = this.remove_aux((Integer)key, conn);
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
		return ret;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends ListaPR> m) {
		throw new RuntimeException("Nao Implementado");	
	}

	
	private Set<Integer> keySet_aux(Connection c) throws SQLException{
		Set<Integer> ret = new TreeSet<>();
		PreparedStatement ps = c.prepareStatement("SELECT "+TabId+" FROM "+TabLista+" WHERE "
				+ Eleicao+" = ?");
		ps.setInt(1,this.idEleicao);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(TabId));
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<>();
		Connection conn= null;
		try{
			conn=Connector.newConnection(true);
			ret = this.keySet_aux(conn);
		}catch(Exception e){
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
		return ret;
	}

	protected Collection<ListaPR> values_aux(Connection c) throws SQLException{
		ArrayList<ListaPR> ret = new ArrayList<>();
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while(i.hasNext()){
			ret.add(this.get_aux(i.next(),c));
		}
		return ret;
	}
	
	@Override
	public Collection<ListaPR> values() {
		Collection<ListaPR> ret = new ArrayList<>();
		Connection c = null;
		try{
			c = Connector.newConnection(true);
			ret = this.values_aux(c);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
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
	public Set<java.util.Map.Entry<Integer, ListaPR>> entrySet() {
		throw new RuntimeException("Nao Implementado");
	}
}
		
		
	


