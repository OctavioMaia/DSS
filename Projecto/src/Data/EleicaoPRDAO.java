package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Business.Boletim;
import Business.Candidato;
import Business.Circulo;
import Business.EleicaoPR;
import Business.ResultadoPR;
import Business.ListaPR;

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
        	PreparedStatement psEleiteleic = conn.prepareStatement("SELECT nrIdEleitor FROM Eleitor_vota_Eleicao WHERE idEleicao = ?");
        	psEleicaoPR.setInt(1,(Integer)key );
        	psEleicao.setInt(1,(Integer)key);
        	psEleiteleic.setInt(1, (Integer)key);
        	ResultSet rsEleicao = psEleicao.executeQuery();
        	ResultSet rsEleicaoPR = psEleicaoPR.executeQuery();
        	ResultSet rsEleiteleic = psEleiteleic.executeQuery();
        	Set<Integer> vota = new HashSet<Integer>();
        	while(rsEleiteleic.next()){
        		vota.add(rsEleiteleic.getInt("nrIdEleitor"));
        	}
        	if(rsEleicao.next() && rsEleicaoPR.next()){
        		eleic = new EleicaoPR(rsEleicao.getInt("idEleicao"), rsEleicao.getDate("data"), rsEleicao.getInt("estado"), rsEleicao.getBoolean("permitidoVotar"), vota, rsEleicaoPR.getBoolean("volta2"), 
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

	@Override
	public EleicaoPR put(Integer key, EleicaoPR value) {
		EleicaoPR ret =null;
		ret = this.get(key);
		Connection conn =null;
		try{
			conn = Connector.newConnection();
			//inserir Eleicao
			if(ret==null){//nao existe na BD
				
			}else{//existe na BD Update
				
			}
			//inserir votantes
			Iterator<Integer> votantes = value.getVotantes().iterator();
			PreparedStatement psvota = conn.prepareStatement("INSERT INTRO Eleitor_vota_Eleicao"
					+ "VALUES"
					+ "(nrIdEleitor,idEleicao)");
			while(votantes.hasNext()){
				int eleit = votantes.next();
			}
			
			
		}
		
		return ret;
	}

	@Override
	public EleicaoPR remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends EleicaoPR> m) {
		throw new RuntimeException("Nao Implementada");
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<EleicaoPR> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, EleicaoPR>> entrySet() {
		throw new RuntimeException("Nao Implementada");
	}
	
}
