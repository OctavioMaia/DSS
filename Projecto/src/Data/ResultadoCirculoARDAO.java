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
import Business.Lista;
import Business.ResultadoCirculoAR;


public class ResultadoCirculoARDAO implements Map<Integer,ResultadoCirculoAR>{
	private int idEleicao;
	//Tabela do circulo
	private static String TabResGeral = "Circulo_tem_ResultadosAR";
	private static String TabResGeralIDCirc = "idCirculo";
	private static String TabResGeralNulo = "nulos";
	private static String TabResGeralBranc = "brancos";
	private static String TabResGeralTotEleit = "totEleitores";
	private static String TabResGeralIDElei = "idEleicao";
	//Tabela da Lista No circulo
	private static String TabResLista = "ListasAR_has_Circulo";
	private static String TabResListaIDLista = "idListasAR";
	private static String TabResListaIDCirc = "idCirculo";
	private static String TabResListaMandatos = "mandatos";
	private static String TabResListaVotos = "nrVotos";
	private static String TabResListaIDElei = "idEleicao";
	
	
	
	public ResultadoCirculoARDAO(int idEleicao){
		this.idEleicao = idEleicao;
	}
	protected void clear_aux(Connection c)throws SQLException{
		Iterator<Integer>  i = this.keySet_aux(c).iterator();
		while (i.hasNext()) {
			this.remove_aux(i.next(),c);		
		}
	}
	
	private int size_aux(Connection c) throws SQLException{
		int ret  = 0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM "+TabResGeral+" WHERE "
				+ TabResGeralIDElei+" = ?");
		ps.setInt(1, this.idEleicao);
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
		int ret = 0;
		Connection c = null;
		try {
			c = Connector.newConnection(true);
			ret=this.size_aux(c);
		} catch (Exception e) {
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
	public boolean isEmpty() {
		return this.size()==0;
	}
	
	private boolean containsKey_aux(Integer key,Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement(" SELECT  EXISTS (SELECT * FROM " +TabResGeral +
                " WHERE "+TabResGeralIDElei+" = ?  and "+TabResGeralIDCirc+" = ?)");
        	ps.setInt(1,this.idEleicao);
        	ps.setInt(2,key );
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()){
        		ret=  (rs.getInt(1)!=0);
        	}
        	rs.close();
        	ps.close();
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
		return this.containsKey(((ResultadoCirculoAR)value).getCirculo().getId());
	}
	
	private ResultadoCirculoAR get_aux(Integer key, Connection c) throws SQLException{
		ResultadoCirculoAR ret = null;
		ListaARDAO daoListas = new ListaARDAO(this.idEleicao,key);
		CirculoDAO cd = new CirculoDAO();
		//IR buscar as Listas Todas que participaram naquela eleicao naqule circulo e dados delas
		PreparedStatement psLista = c.prepareStatement("SELECT "+TabResListaIDLista+","
		+TabResListaMandatos+ "," +TabResListaVotos +" FROM " + TabResLista
		+" WHERE "+TabResListaIDCirc+" = ? AND "+TabResListaIDElei+" = ?");
		psLista.setInt(2, this.idEleicao);
		psLista.setInt(1, key);
		ResultSet rsLista = psLista.executeQuery();
		HashMap<Lista,Integer> listasvotos= new HashMap<>();
		HashMap<Lista,Integer> mandatos= new HashMap<>();
		while(rsLista.next()){
			int lista = rsLista.getInt(TabResListaIDLista);
			int votos = rsLista.getInt(TabResListaVotos);
			int mandan = rsLista.getInt(TabResListaMandatos);
			Lista l = daoListas.get_aux(lista,c);
			listasvotos.put(l, votos);
			mandatos.put(l, mandan);
		}
		rsLista.close();
		psLista.close();
		//ir buscar dados gerais
		PreparedStatement circuloGeral = c.prepareStatement("SELECT "+TabResGeralNulo+", "+TabResGeralBranc+", "
		+ TabResGeralTotEleit + " FROM " + TabResGeral
		+ " WHERE "+TabResGeralIDElei+" = ? and "+TabResGeralIDCirc+" = ?");
		circuloGeral.setInt(1, this.idEleicao);
		circuloGeral.setInt(2,key);
		ResultSet rs2 = circuloGeral.executeQuery();
		if(rs2.next()){
			//Ir buscar o circulo
			Circulo ci = cd.get_aux(key,c);
			int brancos = rs2.getInt(TabResGeralBranc);
			int nulos= rs2.getInt(TabResGeralNulo);
			int totEleitores = rs2.getInt(TabResGeralTotEleit);
			ret = new ResultadoCirculoAR(ci, totEleitores, brancos, nulos, listasvotos, mandatos);
		}
		rs2.close();
		circuloGeral.close();
		return ret;
	}
	
	@Override
	public ResultadoCirculoAR get(Object key) {
		ResultadoCirculoAR r =null;
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
	
	private boolean existResLista(Integer idLista, Integer idCirculo, Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " + TabResLista+
				" WHERE " + TabResListaIDElei + "=? AND " + TabResListaIDCirc + "=? AND " + 
				TabResListaIDLista +"=?");
		ps.setInt(1, this.idEleicao);
		ps.setInt(2, idCirculo);
		ps.setInt(3, idLista);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret=true;
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	private void insereListasRes(Integer key, ResultadoCirculoAR value,Connection c) throws SQLException{
		Map<Lista,Integer> mandatos = value.getMandatos();
		Map<Lista,Integer> votos = value.getValidos();
		Iterator<Lista> i = mandatos.keySet().iterator();

		PreparedStatement psU = c.prepareStatement("UPDATE " + TabResLista + " "
				+ "SET "+ TabResListaMandatos+"=?,"+TabResListaVotos+"=?, "
				+ "WHERE " + TabResListaIDCirc+"=? AND "+TabResListaIDElei+"=? AND "
				+ TabResListaIDLista +"=?");
		
		psU.setInt(3, key);
		psU.setInt(4, this.idEleicao);
		
		
		PreparedStatement psI = c.prepareStatement("INSERT INTO " + TabResLista + " "
				+ "("+TabResListaIDLista+","+TabResListaIDCirc+","+TabResListaIDElei+","
				+TabResListaMandatos+","+TabResListaVotos+") "
				+ "VALEUS "
				+ "(?,?,?,?,?)");
		psI.setInt(2, key);
		psI.setInt(3, this.idEleicao);
		
		while (i.hasNext()) {
			Lista l =  i.next();
			int idlista =l.getID();
			int vot = votos.get(l);
			int mand = mandatos.get(l);
			if(this.existResLista(idlista,key,c)){
				psU.setInt(5, idlista);
				psU.setInt(1, mand);
				psU.setInt(2, vot);
				psU.execute();
			}else{
				psI.setInt(1, idlista);
				psI.setInt(4, mand);
				psI.setInt(5, vot);
				psI.execute();
			}
		}
		psI.close();
		psU.close();
	}
	//Assmindo que todas as listas estam na BD
	protected ResultadoCirculoAR put_aux(Integer key, ResultadoCirculoAR value,Connection c) throws SQLException{
		ResultadoCirculoAR ret = this.get_aux(key, c);
		if(ret!=null){//Existe na DB
			/*
			 	private static String TabResGeralNulo = "nulos";
				private static String TabResGeralBranc = "brancos";
				private static String TabResGeralTotEleit = "totEleitores";
				private static String TabResGeralIDElei = "idEleicao";
			 */
			PreparedStatement ps = c.prepareStatement("UPDATE " + TabResGeral +
					" SET "+TabResGeralNulo+"=?,"+TabResGeralBranc+"=?,"+TabResGeralTotEleit+"=?"+
					" WHERE " + TabResGeralIDElei + "=? AND " +TabResGeralIDCirc +"=?");
			ps.setInt(1, value.getNulos());
			ps.setInt(2, value.getBrancos());
			ps.setInt(3, value.getTotEleitores());
			ps.setInt(4, this.idEleicao);
			ps.setInt(5, key);
			System.out.println(ps.toString());
			ps.execute();
			ps.close();
		}else{//novo
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabResGeral +
					" ("+TabResGeralNulo+","+TabResGeralBranc+","+TabResGeralTotEleit+","+TabResGeralIDElei + "," +TabResGeralIDCirc +")"
					+ " VALUES "
					+ "(?,?,?,?,?)");
			ps.setInt(1, value.getNulos());
			ps.setInt(2, value.getBrancos());
			ps.setInt(3, value.getTotEleitores());
			ps.setInt(4, this.idEleicao);
			ps.setInt(5, key);
			ps.execute();
			ps.close();
		}
		this.insereListasRes(key,value,c);
		return ret;
	}
	
	@Override
	public ResultadoCirculoAR put(Integer key, ResultadoCirculoAR value) {
		ResultadoCirculoAR ret = null;
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
	
	private void removeRLista(Integer idLista, Integer idCirculo,Connection c) throws SQLException{
		PreparedStatement ps = c.prepareStatement("DELETE FROM " + TabResLista
				+ " WHERE " + TabResListaIDElei + "=? AND " + TabResListaIDCirc + "=? AND " + 
				TabResListaIDLista +"=?");
		ps.setInt(1, this.idEleicao);
		ps.setInt(2, idCirculo);
		ps.setInt(3, idLista);
		ps.executeUpdate();
		ps.close();
	}
	
	private ResultadoCirculoAR remove_aux(Integer key,Connection c) throws SQLException{
		ResultadoCirculoAR ret = this.get_aux(key, c);
		if(ret!=null){//Limpar na BD
			//Remover o resultado de todas as listas para quele circulo
			Iterator<Lista> i =ret.getMandatos().keySet().iterator();
			while(i.hasNext()){
				int idLista = i.next().getID();
				this.removeRLista(idLista, key, c);
			}
			//Remover Globais daquele Circulo
			PreparedStatement ps = c.prepareStatement("DELETE FROM " + TabResGeral
					+ " WHERE " + TabResGeralIDCirc + "=? AND " + TabResGeralIDElei +"=?");
			ps.setInt(1, key);
			ps.setInt(2, this.idEleicao);
			ps.executeUpdate();
			ps.close();	
		}
		return ret;
	}
	
	@Override
	public ResultadoCirculoAR remove(Object key) {
		ResultadoCirculoAR ret = null;
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
	public void putAll(Map<? extends Integer, ? extends ResultadoCirculoAR> m) {
		throw new RuntimeException("Nao implementado");
		
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
	
	private Set<Integer> keySet_aux(Connection c) throws SQLException{
		Set<Integer> ret = new TreeSet<>();
		PreparedStatement ps = c.prepareStatement("SELECT "+TabResGeralIDCirc+" FROM "+TabResGeral+" WHERE "
				+ TabResGeralIDElei+ " = ? ");
		ps.setInt(1, this.idEleicao);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(TabResGeralIDCirc));
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
	
	private Collection<ResultadoCirculoAR> values_aux(Connection c) throws SQLException{
		ArrayList<ResultadoCirculoAR> ret = new ArrayList<>();
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while(i.hasNext()){
			int id = i.next();
			ret.add(this.get_aux(id,c));
		}
		return ret;
	}
	
	@Override
	public Collection<ResultadoCirculoAR> values() {
		Collection<ResultadoCirculoAR> ret = new ArrayList<>();
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
	public Set<java.util.Map.Entry<Integer, ResultadoCirculoAR>> entrySet() {
		throw new RuntimeException("Nao implementado");
	}
	

}
