package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

	private void InsertEleitorVota(Integer key,EleicaoPR value,Connection conn) throws SQLException{
		//inserir votantes volta1
		PreparedStatement psisnsert = conn.prepareStatement("INSERT INTRO Eleitor_vota_Eleicao"
				+ "VALUES"
				+ "(nrIdeleitor,idEleicao,volta)"
				+ "(?,?,?)");
		psisnsert.setInt(3,1);
		psisnsert.setInt(2,key);
		Iterator<Integer> iv1 = value.getVotantes().iterator();
		while(iv1.hasNext()){
			int idv = iv1.next();
			psisnsert.setInt(1, idv);
			psisnsert.executeQuery();
		}
		
		//inserir votantes volta2
		psisnsert.setInt(3,2);
		Iterator<Integer> iv2 = value.getVotantes2().iterator();
		while(iv2.hasNext()){
			int idv = iv2.next();
			psisnsert.setInt(1, idv);
			psisnsert.executeQuery();
		}
		psisnsert.close();
	}
	
	
	@Override
	public EleicaoPR put(Integer key, EleicaoPR value) {
		EleicaoPR ret= this.get(key);
		Connection conn =null;
		try{
			conn = Connector.newConnection();
			//inserir Eleicao
			if(ret==null){//nao existe na BD
				PreparedStatement psElei = conn.prepareStatement("INSERT INTO Eleicoes"
						+ "VALUES"
						+ "(idEleicao,estado,data,permitirVotar)"
						+ "(?,?,?,?)");
				psElei.setInt(1, key);
				psElei.setInt(2, value.getEstado());
				psElei.setDate(3, (java.sql.Date) value.getData()); //atencao aqui
				psElei.setBoolean(5, value.isPermitirVotar());
				psElei.close();
				PreparedStatement pseicaoPR = conn.prepareStatement("INSERT INTO EleicoesPR"
						+ "VALUES"
						+ "(idEleicao,volta2,data2)"
						+ "(?,?,?)");
				pseicaoPR.setInt(1, key);
				pseicaoPR.setBoolean(2, value.isVolta2());
				pseicaoPR.setDate(3, (java.sql.Date) value.getData2());
				pseicaoPR.close();
			}else{//existe na BD Update

				//Update Eleicao
				PreparedStatement eleicUpdate =conn.prepareStatement("UPDATE Eleicoes"
						+ "SET estado=?,data=?,permitirVotar=?"
						+ "WHERE idEleicao=?");
				eleicUpdate.setInt(4,key);
				eleicUpdate.setInt(1,value.getEstado());
				eleicUpdate.setDate(2,(java.sql.Date) value.getData());
				eleicUpdate.setBoolean(3, value.isPermitirVotar());
				eleicUpdate.execute();
				eleicUpdate.close();
				//Update EleicaoPR
				PreparedStatement eleicUpdatePR = conn.prepareStatement("UPDATE EleicoesPR"
						+ "VALUES"
						+ "volta2 = ? ,data2 = ?)"
						+ "WHERE idEleicao= ?");
				eleicUpdatePR.setInt(3, key);
				eleicUpdatePR.setBoolean(2, value.isVolta2());
				eleicUpdatePR.setDate(1, (java.sql.Date) value.getData2());
				eleicUpdatePR.close();
				//remover todos os votantes esistentes da eleicao 
				PreparedStatement p = conn.prepareStatement("DELETE FROM Eleitor_vota_Eleicao WHERE idEleicao=?");
				p.setInt(1, key);
				p.executeQuery();
				p.close();				
			}
			//inserir votantes
			InsertEleitorVota(key,value,conn);
			conn.commit();
			
		}catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return ret=null;
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
	public EleicaoPR remove(Object key) {
		EleicaoPR ret = this.get(key);
		if(ret==null) return null; 
		ret.getVoltaR1().clear();
		ret.getVoltaR2().clear();
		ret.getListas().clear();
		Connection conn =null;
		try{
			conn= Connector.newConnection();
			PreparedStatement psReleit = conn.prepareStatement("DELETE FROM Eleitor_vota_Eleicao"
					+ "WHERE idEleicao = ?");
			psReleit.setInt(1, (Integer)key);
			psReleit.executeQuery();
			psReleit.close();
			PreparedStatement psReleicaoPRt = conn.prepareStatement("DELETE FROM EleicoesPR"
					+ "WHERE idEleicao = ?");
			psReleicaoPRt.setInt(1, (Integer)key);
			psReleicaoPRt.executeQuery();
			psReleicaoPRt.close();
			PreparedStatement psReleicao = conn.prepareStatement("DELETE FROM Eleicoes"
					+ "WHERE idEleicao = ?");
			psReleicao.setInt(1, (Integer)key);
			psReleicao.executeQuery();
			psReleicao.close();
			conn.commit();	
		}catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		ret=null; //ver isto
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
	public void putAll(Map<? extends Integer, ? extends EleicaoPR> m) {
		throw new RuntimeException("Nao Implementada");
		
	}

	@Override
	public void clear() {
		Set<Integer> ks = this.keySet();
		if(ks==null || ks.isEmpty()) return;
		Iterator<Integer> i = ks.iterator();
		while(i.hasNext()){
			this.remove(i.next());
		}
		
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret  = new TreeSet<>();
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT idEleicao FROM EleicoesPR");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ret.add(rs.getInt("idEleicao"));
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
    		ret=new TreeSet<>(); //ver isto
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
	public Collection<EleicaoPR> values() {
		ArrayList<EleicaoPR> ret = new ArrayList<>();
		Set<Integer> keys = this.keySet();
		if(keys==null || keys.isEmpty()) return ret;
		Iterator<Integer> i = keys.iterator();
		while(i.hasNext()){
			ret.add(this.get(i.next()));
		}
		return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, EleicaoPR>> entrySet() {
		throw new RuntimeException("Nao Implementada");
	}
	
}
