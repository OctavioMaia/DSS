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
import Business.EleicaoPR;
import Business.Lista;
import Business.ListaPR;
import Business.Votavel;
import java.sql.SQLException;

public class ListaPRDAO implements Map<Integer,ListaPR> {
	private int idEleicao;
	public ListaPRDAO (int idEleicao){
		this.idEleicao=idEleicao;
	}
	
	public int size() {
		int ret = 0;
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("Select count(*) FROM listasPR WHERE IdEleicao = ?");
			ps.setInt(1, this.idEleicao);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) ret = rs.getInt(1);
			rs.close();
			ps.close();
			conn.commit();
			}
		catch(Exception e){
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
	
	public boolean isEmpty() {
		return (this.size()==0);
	}
	
	public boolean containsKey(Object key) {
		boolean b = false;
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("Select EXISTS (SELECT idlistaPR FROM ListasPR WHERE idListaPR=? AND idEleicao=?)");
			ps.setInt(1,(Integer) key);
			ps.setInt(2, this.idEleicao);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) b = (rs.getInt(1)!=0);
			rs.close();
			ps.close();
			conn.commit();
		}
		catch(Exception e){
        	try {
				conn.rollback();
			} catch (SQLException e1) {	
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	throw new RuntimeException(e.getMessage());
        }
		finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return b;
	}
	
	public void clear() {
		Connection conn = null;
		try{
			conn = Connector.newConnection();
			Statement s = conn.createStatement();
			s.executeUpdate("DELETE FROM ListasPR");
			s.close();
			conn.commit();
		}
		catch(Exception e){
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
	public boolean containsValue(Object value) {
		return this.containsKey(((Lista)value).getID());
	}
	
	public ListaPR get(Object key){
		if (!this.containsKey(key)) return null;
		
		
		Connection conn = null;
		ListaPR ret = null;
		Candidato c = null;
		try{
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM listasPR WHERE"
					+ " idlistaPR = ? AND "
					+ " idEleicao = ? ");
			ps.setInt(1, (Integer)key);
			ps.setInt(2, this.idEleicao);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){//trnho a lista na eleicao
				int idcand = rs.getInt("biCandidato");
				PreparedStatement psCand = conn.prepareStatement("SELECT * FROM CandidatosPR WHERE bi = ?");
				psCand.setInt(1, idcand);
				ResultSet rsc = psCand.executeQuery();
				if(rsc.next()){
					c = new Candidato(rsc.getString("nome"),idcand, rsc.getString("prof"), 
							rsc.getDate("dataNasc"), rsc.getString("residencia"),
							rsc.getString("naturalidade"));
				}
				ret = new ListaPR(idEleicao, (Integer)key, rs.getBoolean("volta2"),
						rs.getInt("ordem1"), rs.getInt("ordem2"), c);
			}
		}catch(Exception e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		ret = null;
    	}
    	finally {
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
	public ListaPR put(Integer key, ListaPR value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListaPR remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends ListaPR> m) {
		throw new RuntimeException("Nao Implementado");	
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<>();
		Connection conn= null;
		try{
			conn=Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT idlistaPR FROM listasPR WHERE idEleicao = ?");
			ps.setInt(1,this.idEleicao);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ret.add(rs.getInt("idlistaPR"));
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
        	ret=new TreeSet<>();
        }
		finally{
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
	public Collection<ListaPR> values() {
		ArrayList<ListaPR> ret = new ArrayList<>();
		Set<Integer> keys = this.keySet();
		if(keys==null || keys.isEmpty()) return ret;
		Iterator<Integer> i = keys.iterator();
		while(i.hasNext()){
			ret.add(this.get(i.next()));
		}
		return ret;
		
	}

	@Override
	public Set<java.util.Map.Entry<Integer, ListaPR>> entrySet() {
		throw new RuntimeException("Nao Implementado");
	}
}
		
		
	


