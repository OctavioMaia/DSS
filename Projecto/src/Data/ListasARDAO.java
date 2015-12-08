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
import Business.Votavel;

public class ListasARDAO implements Map<Integer,Lista>{

	public ListasARDAO(){
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select count(*) FROM ListasAR");
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
	
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn = Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement("Select EXISTS (SELECT id FROM ListasAR WHERE id = ?)");
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
	public void clear() {
		Connection conn = null;
    	try{
    		conn = Connector.newConnection();
    		Statement s = conn.createStatement();
    		s.executeUpdate("DELETE FROM ListasAR");
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
	public boolean containsValue(Object value) {
		return this.containsKey(((Lista)value).getID());
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Lista>> entrySet() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try{
        	conn=Connector.newConnection();
        	Statement s = conn.createStatement();
            String querie = "Select id FROM ListasAR";
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
		// TODO Auto-generated method stub
		
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

	@Override
	public Collection<Lista> values() {
		ArrayList <Lista> ret  = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        Iterator<Integer> i  = keys.iterator();
        while (i.hasNext()){
            ret.add(this.get((int) i.next()));
        }
        return ret;
	}
	
	
	
}
