package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.ResultadoCirculoPR;

public class ResultadoCirculoPRDAO implements Map<Integer,ResultadoCirculoPR>{
	
	private int idEleicao;
	private int volta;
	
	public ResultadoCirculoPRDAO(int idEleicao, int volta){
		this.idEleicao = idEleicao;
		this.volta = volta;
	}
	protected void clear_aux(Connection c)throws SQLException{
		this.clear();
		
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(); 
    		PreparedStatement ps = conn.prepareStatement("Select count(*) FROM Circulo_tem_ResultadoPR WHERE idEleicao = ? AND volta = ?");
    		ps.setInt(1, this.idEleicao);
    		ps.setInt(2, this.volta);
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
        	conn = Connector.newConnection();
        	PreparedStatement ps = conn.prepareStatement(" Select  EXISTS (SELECT * FROM Circulo_tem_ResultadoPR " +
                " WHERE idEleicao = ? and volta = ? and idCirculo = ?)");
        	ps.setInt(1,this.idEleicao);
        	ps.setInt(2,this.volta);
        	ps.setInt(3,(Integer)key );
        	ResultSet rs = ps.executeQuery();
        	while(rs.next())
        		b=  (rs.getInt(1)!=0);
        	rs.close();
        	PreparedStatement ps2 = conn.prepareStatement(" Select  EXISTS (SELECT * FROM Circulo_tem_ListaPR_ResultadoPR " +
                    " WHERE idEleicao = ? and volta = ? and idCirculo = ?)");
        	ps2.setInt(1,this.idEleicao);
            ps2.setInt(2,this.volta);
            ps2.setInt(3,(Integer)key );
            ResultSet rs2 = ps2.executeQuery();
        	while(rs2.next())
        		b= b && (rs.getInt(1)!=0);
        	rs2.close();
        	ps2.close();
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
		return this.containsKey(((ResultadoCirculoPR)value).getIdcirculo());
	}

	@Override
	public ResultadoCirculoPR get(Object key) {
		ResultadoCirculoPR r =null;
		Connection c=null;
		try{
			c = Connector.newConnection();
			PreparedStatement psRLista = c.prepareStatement("SELECT idlistaPR, NrVotos FROM Circulo_tem_ListaPR_ResultadoPR "
					+ "WHERE idEleicao = ? and volta = ? and idCirculo = ?)");
			psRLista.setInt(1, this.idEleicao);
			psRLista.setInt(2, this.volta);
			psRLista.setInt(3, (Integer)key);
			HashMap<Integer,Integer> listasvotos= new HashMap<>();
			ResultSet rs = psRLista.executeQuery();
			while(rs.next()){
				listasvotos.put(rs.getInt("idlistaPR"), rs.getInt("NrVotos"));
			}
			PreparedStatement circuloGeral = c.prepareStatement("SELECT nulos, brancos, totEleitores FROM Circulo_tem_ResultadoPR "
					+ "WHERE idEleicao = ? and volta = ? and idCirculo = ?)");
			circuloGeral.setInt(1, this.idEleicao);
			circuloGeral.setInt(2, this.volta);
			circuloGeral.setInt(3, (Integer)key);
			ResultSet rs2 = psRLista.executeQuery();
			if(rs2.next()){
				r = new ResultadoCirculoPR(rs2.getInt("brancos"), rs2.getInt("nulos"), rs2.getInt("totEleitores"), listasvotos, (Integer)key);
			}
			rs.close();
			rs2.close();
			psRLista.close();
			circuloGeral.close();
			c.commit();
		}catch(Exception e){
			try {
				c.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
			c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return r;
	}

	@Override
	public ResultadoCirculoPR put(Integer key, ResultadoCirculoPR value) {
		ResultadoCirculoPR ret = this.remove(key);
		Connection c = null;
		try{
			c=Connector.newConnection();
			PreparedStatement psRGeral = c.prepareStatement("INSERT INTO Circulo_tem_ResultadoRP"+
                    "(idCirculo,nulos,brancos,totEleitores,idEleicao,volta) " +
                    "values " +
                    "(?,?,?,?,?,?)");
			psRGeral.setInt(1,(Integer)key);
			psRGeral.setInt(2,value.getNulos());
			psRGeral.setInt(3,value.getBrancos());
			psRGeral.setInt(4,value.getTotEleitores());
			psRGeral.setInt(5,this.idEleicao);
			psRGeral.setInt(6,this.volta);
			psRGeral.executeQuery();
			PreparedStatement psRLista = c.prepareStatement("INSERT INTO Circulo_tem_listaPR_ResultadoPR"+
                    "(idCirculo,idlistaPR,NrVotos,idEleicao,volta) " +
                    "values " +
                    "(?,?,?,?,?)");
			psRLista.setInt(1, (Integer)key);
			psRLista.setInt(4, this.idEleicao);
			psRLista.setInt(5, this.volta);
			Map<Integer,Integer> val = value.getValidos();
			Iterator<Integer> i = val.keySet().iterator();
			while(i.hasNext()){
				int lista = i.next();
				int valid = val.get(lista);
				psRLista.setInt(2, lista);
				psRLista.setInt(3, valid);
				psRLista.executeQuery();

			}
			psRGeral.close();
			psRLista.close();
			c.commit();
		}catch(Exception e){
			try {
				c.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
			c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}

	@Override
	public ResultadoCirculoPR remove(Object key) {
		ResultadoCirculoPR ret = this.get(key);
		Connection c = null;
		try{
			c=Connector.newConnection();
			PreparedStatement psRGeral = c.prepareStatement("DELETE FROM Circulo_tem_ResultadoRP"+
                    "WHERE idCirculo = ? AND volta = ? AND idEleicao = ? ");
			psRGeral.setInt(1,(Integer)key);
			psRGeral.setInt(2,this.volta);
			psRGeral.setInt(3,this.idEleicao);
			psRGeral.executeQuery();
			
			PreparedStatement psRLista = c.prepareStatement("DELETE FROM Circulo_tem_listaPR_ResultadoRP"+
                    "WHERE idCirculo = ? AND volta = ? AND idEleicao = ? ");
			psRLista.setInt(1,(Integer)key);
			psRLista.setInt(2,this.volta);
			psRLista.setInt(3,this.idEleicao);
			
			psRLista.executeQuery();
			
			psRGeral.close();
			psRLista.close();
			c.commit();
		}catch(Exception e){
			try {
				c.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
			c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends ResultadoCirculoPR> m) {
		throw new RuntimeException("Nao implementado");
		
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = Connector.newConnection();
    		PreparedStatement ps = conn.prepareStatement("DELETE FROM Circulo_tem_ResultadoPR WHERE idEleicao = ? AND volta = ?");
    		ps.setInt(1, this.idEleicao);
    		ps.setInt(2, this.volta);
    		ps.executeQuery();
    		PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Circulo_tem_listaPR_ResultadoPR WHERE idEleicao = ? AND volta = ?");
    		ps2.setInt(1, this.idEleicao);
    		ps2.setInt(2, this.volta);
    		ps2.executeQuery();
    		ps.close();
    		ps2.close();
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
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try {
			conn = Connector.newConnection();
			PreparedStatement ps = conn.prepareStatement("Select idCirculo FROM Circulo_tem_ResultadoPR WHERE idEleicao = ? AND volta = ?");
			ps.setInt(1, this.idEleicao);
			ps.setInt(2, this.volta);
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
			throw new RuntimeException(e.getMessage());
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
	public Collection<ResultadoCirculoPR> values() {
		ArrayList<ResultadoCirculoPR> ret = new ArrayList<>();
		Iterator<Integer> i = this.keySet().iterator();
		while(i.hasNext()){
			int id = i.next();
			ret.add(this.get(id));
		}
		return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, ResultadoCirculoPR>> entrySet() {
		throw new RuntimeException("Nao implementado");
	}
}
	