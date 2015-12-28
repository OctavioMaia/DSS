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

import Business.Circulo;
import Business.ListaPR;
import Business.ResultadoCirculoPR;


public class ResultadoCirculoPRDAO implements Map<Integer,ResultadoCirculoPR>{
	
	private int idEleicao;
	private int volta;
	//TabelaResultados Globais
	private static String TabGlobal = "Circulo_tem_ResultadoPR";
	private static String IdCirculo = "idCirculo";
	private static String Nulos = "nulos";
	private static String Brancos = "brancos";
	private static String Toteleitores = "totEleitores";
	private static String Eleicao = "idEleicao";
	private static String Volta = "volta";
	//TabelaResultadosLista
	private static String TabLista = "Resultado_ListaPR";
	private static String Lista = "idListaPR";
	private static String Votos = "NrVotos";
	private static String VoltaL = "volta";
	
	public ResultadoCirculoPRDAO(int idEleicao, int volta){
		this.idEleicao = idEleicao;
		this.volta = volta;
	}

	private int size_aux(Connection c)  throws SQLException{
		int ret = 0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM "+TabGlobal+" WHERE "
				+ Eleicao+" = ? AND "+Volta+" = ?");
		ps.setInt(1, this.idEleicao);
		ps.setInt(2, this.volta);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret = rs.getInt(1);
		}
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
    		ret = this.size_aux(conn);
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
	public boolean isEmpty() {
		return this.size()==0;
	}

	
	private boolean containsKey_aux(Integer key,Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement(" SELECT  EXISTS (SELECT * FROM " +TabGlobal +
                " WHERE "+Eleicao+" = ? and "+Volta+" = ? and "+IdCirculo+" = ?)");
        	ps.setInt(1,this.idEleicao);
        	ps.setInt(2,this.volta);
        	ps.setInt(3,key );
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()){
        		ret=  (rs.getInt(1)!=0);
        	}
        	rs.close();
        	ps.close();
        	PreparedStatement ps2 = c.prepareStatement(" SELECT  EXISTS (SELECT * FROM " + TabLista+ 
                    " WHERE "+Eleicao+" = ? and "+VoltaL+" = ? and "+IdCirculo+" = ?)");
        	ps2.setInt(1,this.idEleicao);
            ps2.setInt(2,this.volta);
            ps2.setInt(3,key );
            ResultSet rs2 = ps2.executeQuery();
        	if(rs2.next()){
        		ret= ret && (rs2.getInt(1)!=0);
        	}else{
        		ret = false;
        	}
        	rs2.close();
        	ps2.close();
		return ret;
	}
	
	@Override
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn = Connector.newConnection(true);
        	b = this.containsKey_aux((Integer)key, conn);
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
        return b;
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((ResultadoCirculoPR)value).getCirculo().getId());
	}

	
	private ResultadoCirculoPR get_aux(Integer key,Connection c) throws SQLException{
		ResultadoCirculoPR ret = null;
		ListaPRDAO daoListas = new ListaPRDAO(this.idEleicao);
		CirculoDAO cd = new CirculoDAO();

		PreparedStatement psRLista = c.prepareStatement("SELECT "+Lista+", "+Votos+" FROM " + TabLista
				+ " WHERE "+Eleicao+" = ? and "+Volta+" = ? and "+IdCirculo+" = ?");
		psRLista.setInt(1, this.idEleicao);
		psRLista.setInt(2, this.volta);
		psRLista.setInt(3, key);
		HashMap<ListaPR,Integer> listasvotos= new HashMap<>();
		ResultSet rs = psRLista.executeQuery();
		while(rs.next()){
			//presiso de um comparador de int com listaPR ou iquals
			listasvotos.put(daoListas.get_aux(rs.getInt(Lista),c), rs.getInt(Votos));
		}
		rs.close();
		psRLista.close();
		PreparedStatement circuloGeral = c.prepareStatement("SELECT "+Nulos+", "+Brancos+", " + Toteleitores
				+ " FROM " + TabGlobal
				+ " WHERE "+Eleicao+" = ? and "+Volta+" = ? and "+IdCirculo+" = ?");
		circuloGeral.setInt(1, this.idEleicao);
		circuloGeral.setInt(2, this.volta);
		circuloGeral.setInt(3,key);
		ResultSet rs2 = circuloGeral.executeQuery();
		if(rs2.next()){
			//Ir buscar o circulo
			Circulo ci = cd.get_aux(key,c);
			ret = new ResultadoCirculoPR(ci,rs2.getInt(Brancos), rs2.getInt(Nulos), rs2.getInt(Toteleitores), 
					listasvotos);
		}
		rs2.close();
		circuloGeral.close();
		return ret;
	}
	
	@Override
	public ResultadoCirculoPR get(Object key) {
		ResultadoCirculoPR r =null;
		Connection c=null;
		try{
			c = Connector.newConnection(true);
			r = this.get_aux((Integer)key, c);
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
		return r;
	}


	private boolean listaHasResult(Integer idLista, Integer idCirculo,Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " + TabLista +
				" WHERE " +IdCirculo+ "= ? AND " + Eleicao +" = ? AND " + VoltaL +" = ? AND " + Lista + "= ? ");
		ps.setInt(1, idCirculo);
		ps.setInt(2, this.idEleicao);
		ps.setInt(3, this.volta);
		ps.setInt(4, idLista);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret=true;
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	private void updateListas(Map<ListaPR,Integer> votos, int idCirculo, Connection c) throws SQLException{
		//Update aos resultados das listas
		PreparedStatement psUPLista = c.prepareStatement("UPDATE " + TabLista + 
				" SET " + Votos + "=?" +
			    " WHERE " +IdCirculo+ "= ? AND " + Eleicao +" = ? AND " + VoltaL +" = ? AND " + Lista + "= ? ");
		psUPLista.setInt(2, idCirculo);
		psUPLista.setInt(3, this.idEleicao);
		psUPLista.setInt(4, this.volta);
		//Inserir aos resultados das listas
		PreparedStatement psINSLista = c.prepareStatement("INSERT INTO "+TabLista+
                " ("+IdCirculo+","+Lista+","+Votos+","+Eleicao+","+Volta+") " +
                "values " +
                "(?,?,?,?,?)");
		psINSLista.setInt(1,idCirculo);
		psINSLista.setInt(4, this.idEleicao);
		psINSLista.setInt(5, this.volta);
		Iterator<ListaPR> i = votos.keySet().iterator();
		while(i.hasNext()){
			ListaPR l = i.next();
			if(this.listaHasResult(l.getIdListaPR(),idCirculo,c)){
				psUPLista.setInt(1, votos.get(l));
				psUPLista.setInt(5, l.getIdListaPR());
				psUPLista.execute();
			}else{
				psINSLista.setInt(2, l.getIdListaPR());
				psINSLista.setInt(3, votos.get(l));
				psINSLista.execute();
			}
			
		}
		psINSLista.close();
		psUPLista.close();
	}
	
	protected ResultadoCirculoPR put_aux(Integer key, ResultadoCirculoPR value,Connection c) throws SQLException{
		ResultadoCirculoPR ret = this.get_aux(key,c);
		if(ret!=null){//Existe na BD
			//Update ao geral
			PreparedStatement psGeral = c.prepareStatement("UPDATE " + TabGlobal
					+ " SET "+Nulos+"=? ,"+Brancos+"=? ,"+Toteleitores+"=? "
					+ " WHERE " +IdCirculo+ "= ? AND " + Eleicao +" = ? AND " + Volta +" = ?");
			psGeral.setInt(1,value.getNulos());
			psGeral.setInt(2,value.getBrancos());
			psGeral.setInt(3,value.getTotEleitores());
			
			psGeral.setInt(4,key);
			psGeral.setInt(5,this.idEleicao);
			psGeral.setInt(6,this.volta);
			psGeral.execute();
			psGeral.close();
		}else{//Novo na BD
			//Inserir global
			PreparedStatement psRGeral = c.prepareStatement("INSERT INTO "+ TabGlobal +
                    " ("+IdCirculo+","+Nulos+","+Brancos+","+Toteleitores+","+Eleicao+","+Volta+") " +
                    " values " +
                    "(?,?,?,?,?,?)");
			psRGeral.setInt(1,key);
			psRGeral.setInt(2,value.getNulos());
			psRGeral.setInt(3,value.getBrancos());
			psRGeral.setInt(4,value.getTotEleitores());
			psRGeral.setInt(5,this.idEleicao);
			psRGeral.setInt(6,this.volta);
			psRGeral.execute();
			psRGeral.close();
		}
		this.updateListas(value.getValidos(), key, c);
		return ret;
	}

	
	@Override
	public ResultadoCirculoPR put(Integer key, ResultadoCirculoPR value) {
		ResultadoCirculoPR ret = null;
		Connection c = null;
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
		}catch (Exception e) {
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

	//Esta funçao nao eleimina os registos da DB
	private ResultadoCirculoPR remove_aux(Integer key,Connection c) throws SQLException{
		ResultadoCirculoPR  ret = this.get_aux(key, c);
		if(ret!=null){
			//Remover a global
			PreparedStatement psRGeral = c.prepareStatement("DELETE FROM "+ TabGlobal+
					" WHERE "+IdCirculo+" = ? AND "+Volta+" = ? AND "+Eleicao+" = ? ");
			
			psRGeral.setInt(1,key);
			psRGeral.setInt(2,this.volta);
			psRGeral.setInt(3,this.idEleicao);
			psRGeral.executeUpdate();
			psRGeral.close();
			//RemoverOs resultados das listas
			PreparedStatement psRLista = c.prepareStatement("DELETE FROM "+ TabLista + 
					" WHERE "+IdCirculo+" = ? AND "+Volta+" = ? AND "+Eleicao+" = ? ");
			
			psRLista.setInt(1,key);
			psRLista.setInt(2,this.volta);
			psRLista.setInt(3,this.idEleicao);
			psRLista.executeUpdate();
			psRLista.close();
		}
		return ret;
	}
	
	@Override
	public ResultadoCirculoPR remove(Object key) {
		ResultadoCirculoPR ret = null;
		Connection c = null;
		try{
			c=Connector.newConnection(false);
			ret = this.remove_aux((Integer)key, c);
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
			}catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		return ret;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends ResultadoCirculoPR> m) {
		throw new RuntimeException("Nao implementado");
		
	}

	
	protected void clear_aux(Connection c)throws SQLException{
		Iterator<Integer> i = this.keySet_aux(c).iterator();
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
    	}catch(SQLException e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
    	}catch(Exception e){
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());
    	}finally {
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		} 	
	}

	
	private Set<Integer> keySet_aux(Connection c)throws SQLException{
		Set<Integer> ret = new TreeSet<Integer>();
		PreparedStatement ps = c.prepareStatement("Select "+IdCirculo+" FROM "+TabGlobal+" WHERE "
				+ Eleicao+ " = ? AND "+Volta+" = ?");
		ps.setInt(1, this.idEleicao);
		ps.setInt(2, this.volta);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(IdCirculo));
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	
	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = new TreeSet<Integer>();
        Connection conn = null;
        try {
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

	
	private Collection<ResultadoCirculoPR> values_aux(Connection c) throws SQLException{
		ArrayList<ResultadoCirculoPR> ret = new ArrayList<>();
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while(i.hasNext()){
			int id = i.next();
			ret.add(this.get_aux(id,c));
		}
		return ret;
	}
	
	@Override
	public Collection<ResultadoCirculoPR> values() {
		Collection<ResultadoCirculoPR> ret = new ArrayList<>();
		Connection c = null;
		try{
			c = Connector.newConnection(true);
			ret= this.values_aux(c);	
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

		return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, ResultadoCirculoPR>> entrySet() {
		throw new RuntimeException("Nao implementado");
	}
}
	