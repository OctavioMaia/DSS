package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import Business.EleicaoPR;


public class EleicaoPRDAO implements Map<Integer,EleicaoPR>{
	//Tabela EleicoesPR
	private static String TabnamePR = "EleicoesPR";
	private static String TabId = "idEleicao";
	private static String Volta2 = "volta2";
	private static String Data2 = "data2";
	
	//Tabela eleicoes
	private static String Tabname = "Eleicoes";
	private static String Estado = "estado";
	private static String Data = "data";
	private static String PVot = "permitidoVotar";
	
	//Tabela de votacao
	private static String TabVotName = "Eleitor_vota_Eleicao";
	private static String IdEleit = "nrIdEleitor";
	private static String Volta = "volta";

	public EleicaoPRDAO() {
	}

	
	private int size_aux(Connection c) throws SQLException{
		int ret=0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + TabnamePR);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) ret = rs.getInt(1);
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
    		ret=this.size_aux(conn);
    	}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
		boolean b=false;
		PreparedStatement ps = c.prepareStatement("SELECT EXISTS (SELECT "+TabId+" FROM "+TabnamePR+
                " WHERE "+TabId+" = ?)");
		ps.setInt(1,key);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			b = (rs.getInt(1)!=0);
		}
		rs.close();
		ps.close();
		return b;
	}

	@Override
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn =Connector.newConnection(true);
        	b = this.containsKey_aux((Integer)key, conn);
        }catch(Exception e){
			e.printStackTrace();
        	throw new RuntimeException(e.getMessage());
        }finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
        }
        return b;
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((EleicaoPR)value).getIdEleicao());
	}

	
	private EleicaoPR get_aux(Integer key,Connection c) throws SQLException{
		EleicaoPR eleic = null;
		PreparedStatement psEleicaoPR = c.prepareStatement("SELECT * FROM "+TabnamePR+" WHERE "+TabId+" = ?");
		psEleicaoPR.setInt(1,key);
		PreparedStatement psEleicao = c.prepareStatement("SELECT * FROM "+Tabname+" WHERE "+TabId+" = ?");
		psEleicao.setInt(1,key);
		PreparedStatement psEleiteleic = c.prepareStatement("SELECT * FROM "+TabVotName+" WHERE "+TabId+" = ?");
		psEleiteleic.setInt(1,key);
		//Queries resultados em Memoria
		ResultSet rsEleicao = psEleicao.executeQuery();
    	ResultSet rsEleicaoPR = psEleicaoPR.executeQuery();
    	ResultSet rsEleiteleic = psEleiteleic.executeQuery();
    	Set<Integer> vota1Eleit = new HashSet<Integer>();
    	Set<Integer> vota2Eleit = new HashSet<Integer>();
    	
    	while(rsEleiteleic.next()){
    		int num = rsEleiteleic.getInt(IdEleit);
    		int volta = rsEleiteleic.getInt(Volta);
    		if(volta ==1){
    			vota1Eleit.add(num);
    		}else{
    			vota2Eleit.add(num);
    		}
    	}
    	rsEleiteleic.close();
    	psEleiteleic.close();
    	//Lista de votantes das duas voltas criados
    	//Criar Eleicoes a partir das duas tabelas
    	if(rsEleicao.next() && rsEleicaoPR.next()){
    		CirculoDAO ci = new CirculoDAO();	
    		Calendar d1 = new GregorianCalendar();
    		Calendar d2 = new GregorianCalendar();
    		d1.setTime(rsEleicao.getDate(Data));
    		d2.setTime(rsEleicaoPR.getDate(Data2));
    		eleic = new EleicaoPR(rsEleicao.getInt(TabId), d1,rsEleicao.getInt(Estado), 
    				rsEleicao.getBoolean(PVot),vota1Eleit,ci.values(),vota2Eleit, 
    				rsEleicaoPR.getBoolean(Volta2), d2);
    	}
    	psEleicaoPR.close();
    	psEleicao.close();
    	rsEleicaoPR.close();
    	rsEleicao.close();
		return eleic;
	}
	
	@Override
	public EleicaoPR get(Object key) {
		EleicaoPR eleic = null;
		Connection conn = null;   
        try{
        	conn=Connector.newConnection(true);
        	if(this.containsKey(key)){
    			eleic = this.get_aux((Integer)key, conn);
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
		
		return eleic;
	}

	private void InsertEleitorVota(Integer key,EleicaoPR value,Connection conn,boolean del) throws SQLException{
		if(del){
			//Remover Todos Votantes Existentes
			PreparedStatement ps = conn.prepareStatement("DELETE FROM "+TabVotName+" WHERE "+TabId+" =?");
			ps.setInt(1, key);
			ps.execute();
			ps.close();
			//os que estavam na BD estao removidos
		}
		
		//inserir votantes volta1
		PreparedStatement psisnsert = conn.prepareStatement("INSERT INTO "+ TabVotName +
				 " ("+IdEleit+","+TabId+","+Volta+")"
				+ " VALUES"
				+ " (?,?,?)");
		psisnsert.setInt(3,1);
		psisnsert.setInt(2,key);
		Iterator<Integer> iv1 = value.getVotantes().iterator();
		while(iv1.hasNext()){
			int idv = iv1.next();
			psisnsert.setInt(1, idv);
			psisnsert.execute();
		}
		
		//inserir votantes volta2
		psisnsert.setInt(3,2);
		Iterator<Integer> iv2 = value.getVotantes2().iterator();
		while(iv2.hasNext()){
			int idv = iv2.next();
			psisnsert.setInt(1, idv);
			psisnsert.execute();
		}
		psisnsert.close();
	}
	
	private EleicaoPR put_aux(Integer key, EleicaoPR value,Connection c) throws SQLException{
		EleicaoPR ret= this.get_aux(key,c);
		if(ret==null){//nao existe na BD
			//Insere na Eleicoes
			PreparedStatement psElei = c.prepareStatement("INSERT INTO " + Tabname
					+ " ("+TabId+","+Estado+","+Data+","+PVot+")"
					+ " VALUES "
					+ "(?,?,?,?)");
			psElei.setInt(1, key);
			psElei.setInt(2, value.getEstado());
			psElei.setDate(3, new java.sql.Date(value.getData().getTimeInMillis()));
			psElei.setBoolean(4, value.isPermitirVotar());
			psElei.execute();
			psElei.close();
			//Insere na EleicoesPR
			PreparedStatement pseicaoPR = c.prepareStatement("INSERT INTO " +TabnamePR
					+ "("+TabId+","+Volta2+","+Data2+")"
					+ " VALUES "
					+ "(?,?,?)");
			pseicaoPR.setInt(1, key);
			pseicaoPR.setBoolean(2, value.isVolta2());
			pseicaoPR.setDate(3, new java.sql.Date(value.getData2().getTimeInMillis()));
			pseicaoPR.execute();
			pseicaoPR.close();
			//inserir eleitores delete a false
			InsertEleitorVota(key,value,c,false);
		}else{//existe na BD Update
			//Update Eleicao
			PreparedStatement eleicUpdate =c.prepareStatement("UPDATE "+ Tabname
					+ " SET "+Estado+"=?,"+Data+"=?,"+PVot+"=?"
					+ " WHERE "+TabId+"=?");
			eleicUpdate.setInt(4,key);
			eleicUpdate.setInt(1,value.getEstado());
			eleicUpdate.setDate(2,new java.sql.Date(value.getData().getTimeInMillis()));
			eleicUpdate.setBoolean(3, value.isPermitirVotar());
			eleicUpdate.execute();
			eleicUpdate.close();
			//Update EleicaoPR
			PreparedStatement eleicUpdatePR = c.prepareStatement("UPDATE " + TabnamePR
					+ " SET "
					+ " "+Volta2+" = ? ,"+Data2+" = ?"
					+ " WHERE "+TabId+"= ?");
			eleicUpdatePR.setInt(3, key);
			eleicUpdatePR.setBoolean(1, value.isVolta2());
			eleicUpdatePR.setDate(2,new java.sql.Date(value.getData2().getTimeInMillis()));
			eleicUpdatePR.execute();
			eleicUpdatePR.close();
			//inserir eleitores delete a true
			InsertEleitorVota(key,value,c,true);
		}
		return ret;
	}
	
	@Override
	public EleicaoPR put(Integer key, EleicaoPR value) {
		Connection conn =null;
		EleicaoPR ret = null;
		try{
			conn = Connector.newConnection(false);
			ret = this.put_aux(key, value, conn);
			conn.commit();
			
		}catch(Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
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

	private EleicaoPR remove_aux(Integer key, Connection c) throws SQLException{
		EleicaoPR ret = this.get_aux(key,c);
		if(ret==null) return null;
		//Limpar DAO quem EleicaoTem com a  mesma conneccao
		/*
		(new ResultadoCirculoPRDAO(key, 1)).clear_aux(c);
		(new ResultadoCirculoPRDAO(key, 2)).clear_aux(c);*/
		ret.getVoltaR1().clear_aux(c);
		ret.getVoltaR2().clear_aux(c);
		ret.getListas().clear_aux(c);
		//Daos Limpos
		//Remover Votantes
		PreparedStatement psReleit = c.prepareStatement("DELETE FROM " +TabVotName
				+ " WHERE "+TabId+" = ?");
		psReleit.setInt(1,key);
		psReleit.execute();
		psReleit.close();
		//Remover DasEleicoesPR
		PreparedStatement psReleicaoPRt = c.prepareStatement("DELETE FROM "+ TabnamePR
				+ " WHERE "+TabId+" = ?");
		psReleicaoPRt.setInt(1,key);
		psReleicaoPRt.execute();
		psReleicaoPRt.close();
		//Remover DasEleicoes
		PreparedStatement psReleicao = c.prepareStatement("DELETE FROM " + Tabname
				+ " WHERE "+TabId+" = ?");
		psReleicao.setInt(1,key);
		psReleicao.execute();
		psReleicao.close();
		return ret;
	}

	@Override
	public EleicaoPR remove(Object key) {
		Connection conn =null;
		EleicaoPR ret = null;
		try{
			conn= Connector.newConnection(false);
			ret = this.remove_aux((Integer)key, conn);
			conn.commit();	
		}catch(SQLException e){
    		try {
				conn.rollback();
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
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
	public void putAll(Map<? extends Integer, ? extends EleicaoPR> m) {
		throw new RuntimeException("Nao Implementada");
		
	}

	protected void clear_aux(Connection c) throws SQLException{
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while(i.hasNext()){
			int idelei=i.next();
			this.remove_aux(idelei,c);
		}	
	}
	
	@Override
	public void clear() {
		Connection c = null;
		try {
			c = Connector.newConnection(false);
			this.clear_aux(c);
			c.commit();	
		} catch (SQLException e) {
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
		}finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		
	}

	private Set<Integer> keySet_aux(Connection c) throws SQLException{
		Set<Integer> ret  = new TreeSet<>();
		PreparedStatement ps = c.prepareStatement("SELECT "+TabId+" FROM " +TabnamePR);
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
		Set<Integer> ret  = new TreeSet<>();
		Connection conn = null;
		try{
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
	public Collection<EleicaoPR> values() {
		Connection c  = null;
		ArrayList<EleicaoPR> ret = new ArrayList<>();
		try {
			c = Connector.newConnection(true);
			Iterator<Integer> i = this.keySet_aux(c).iterator();
			while(i.hasNext()){
				ret.add(this.get_aux(i.next(), c));
			}
			
		} catch(Exception e){
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
	public Set<java.util.Map.Entry<Integer, EleicaoPR>> entrySet() {
		throw new RuntimeException("Nao Implementada");
	}
	
}
