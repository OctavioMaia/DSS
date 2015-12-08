package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Business.ResultadoCirculoPR;

public class ResultadoCirculoPRDAO implements Map<Integer,ResultadoCirculoPR>{
	
	private int idEleicao;
	private int volta;
	
	public ResultadoCirculoPRDAO(int idEleicao, int volta){
		this.idEleicao = idEleicao;
		this.volta = volta;
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
                    "value " +
                    "(?,?,?,?,?,?)");
			psRGeral.setInt(1,(Integer)key);
			psRGeral.setInt(2,value.getNulos());
			psRGeral.setInt(3,value.getBrancos());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends ResultadoCirculoPR> m) {
		// TODO Auto-generated method stub
		
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
	public Collection<ResultadoCirculoPR> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, ResultadoCirculoPR>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	