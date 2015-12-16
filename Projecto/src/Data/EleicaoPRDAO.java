package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import Business.EleicaoPR;


public class EleicaoPRDAO implements Map<Integer,EleicaoPR>{

	public EleicaoPRDAO() {
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select count(*) FROM EleicoesPR");
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
	public boolean isEmpty() {
		return this.size()==0;
	}

	@Override
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn =Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement(" Select  EXISTS (SELECT idEleicao FROM EleicoesPR " +
                " WHERE idEleicao = ?)");
        	ps.setInt(1,(Integer) key);
        	ResultSet rs = ps.executeQuery();
        	if (rs.next()) b = (rs.getInt(1)!=0);
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
	public boolean containsValue(Object value) {
		return this.containsKey(((EleicaoPR)value).getIdEleicao());
	}

	@Override
	public EleicaoPR get(Object key) {
		EleicaoPR eleic = null;
		Connection conn = null;
		if(!this.containsKey(key)) return eleic;
        
        try{
        	conn=Connector.newConnection();
        	PreparedStatement psEleicaoPR = conn.prepareStatement("SELECT * FROM EleicoesPR WHERE idEleicao = ?");
        	PreparedStatement psEleicao = conn.prepareStatement("SELECT * FROM Eleicoes WHERE idEleicao = ?");
        	PreparedStatement psEleiteleic = conn.prepareStatement("SELECT * FROM Eleitor_vota_Eleicao WHERE idEleicao = ?");
        	psEleicaoPR.setInt(1,(Integer)key );
        	psEleicao.setInt(1,(Integer)key);
        	psEleiteleic.setInt(1, (Integer)key);
        	ResultSet rsEleicao = psEleicao.executeQuery();
        	ResultSet rsEleicaoPR = psEleicaoPR.executeQuery();
        	ResultSet rsEleiteleic = psEleiteleic.executeQuery();
        	Set<Integer> vota = new HashSet<Integer>();
        	Set<Integer> vota2 = new HashSet<Integer>();
        	while(rsEleiteleic.next()){
        		int num = rsEleiteleic.getInt("nrIdEleitor");
        		int volta = rsEleiteleic.getInt("volta");
        		if(volta ==1){
        			vota.add(num);
        		}else{
        			vota2.add(num);
        		}
        	}
        	if(rsEleicao.next() && rsEleicaoPR.next()){
        		eleic = new EleicaoPR(rsEleicao.getInt("idEleicao"), rsEleicao.getDate("data"), rsEleicao.getInt("estado"), rsEleicao.getBoolean("permitidoVotar"), vota,vota2, rsEleicaoPR.getBoolean("volta2"), 
        				rsEleicaoPR.getDate("data2"), null, null);
        	}	
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
