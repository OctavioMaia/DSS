package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import Business.Boletim;
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
		Date data = null;
		Date data2 = null;
		ResultadoPR voltaR1=null;
		ResultadoPR voltaR2=null;
		Boletim boletim1=null;
		Boletim boletim2=null;
		int estado =0;
		int idEleicao =0;
		boolean volta2 = false;
		if(!this.containsKey(key)) return eleic;
        
        try{
        	conn=Connector.newConnection();
        	PreparedStatement psListas = conn.prepareStatement("SELECT * FROM listasPR WHERE idEleicao = ?");
        	PreparedStatement psCandidato = conn.prepareStatement("SELECT * FROM Candidatos WHERE bi = ?");
        	psListas.setInt(1,(Integer)key);
        	ResultSet rsListas = psListas.executeQuery();
        	while(rsListas.next()){
        		int candit = rsListas.getInt("Candidato_bi");
        		psCandidato.setInt(1, candit);
        		
        	}
        	PreparedStatement ps = conn.prepareStatement("Select * FROM Circulos WHERE Id = ?");
        	ps.setInt(1, (Integer)key);
        	ResultSet rs = ps.executeQuery();
            while(rs.next()){
               circulo = new Circulo (rs.getInt("idCirculo"),rs.getString("nome"),rs.getInt("totEleitores"));
            }
            
            eleic= new EleicaoPR(data, estado, idEleicao, volta2, data2, voltaR1, voltaR2, boletim1, boletim2, listas);
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
		
		return eleic;
	}

	@Override
	public EleicaoPR put(Integer key, EleicaoPR value) {
		// TODO Auto-generated method stub
		return null;
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
