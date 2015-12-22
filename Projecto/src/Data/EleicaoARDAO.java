package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.Circulo;
import Business.CirculoInfo;
import Business.EleicaoAR;
import Business.EleicaoPR;

public class EleicaoARDAO implements Map<Integer,EleicaoAR>{
	//Tabela Eleições AR
	private static String Tabname = "EleicoesAR";
	private static String Eleicao = "idEleicao";
	private static String Mandatos = "mandatosAssembleia";
	//Tabela Eleições
	private static String TabEleicoes = "Eleicoes";
	private static String Estado = "estado";
	private static String Data = "data";
	private static String PermitirVotar = "permitirVotar";
	//Tabela Eleitores que votaram na eleicao
	private static String TabEleitores = "Eleitor_Vota_Eleicao";
	private static String Eleitor = "nrIdEleitor";
	private static String Volta = "volta";
	

	@Override
	public void clear() {
		Connection c = null;
		try {
			c = Connector.newConnection(false);
			Set<Integer> eleicoes = this.keySet();
			if(eleicoes==null || eleicoes.isEmpty()) return;
			Iterator<Integer> itEleicoes = eleicoes.iterator();
			while(itEleicoes.hasNext()){
				this.remove(itEleicoes.next());
			}				
			c.commit();	
		} catch (SQLException e) {
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
		}finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Override
	public boolean containsKey(Object key) {
		Connection conn = null;
		boolean b = false;
        try{
        	conn = Connector.newConnection(true);
        	PreparedStatement ps = conn.prepareStatement("SELECT  EXISTS (SELECT * FROM "+ Tabname +
        			" WHERE "+Eleicao+" = ?");
        	ps.setInt(1,(Integer)key);
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()){
        		b = (rs.getInt(1)!=0);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	throw new RuntimeException(e.getMessage());
        }finally{
        	try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return b;
	}

	@Override
	public boolean containsValue(Object value) {
		/*
		 * Apenas verifica a chave
		 */
		return this.containsKey( ((EleicaoAR)value).getIdEleicao() );
	}

	@Override
	public EleicaoAR get(Object key) {
		EleicaoAR eleicao = null;
        Connection conn = null; 
        try{
        	if(!this.containsKey(key)) return null;
        	conn=Connector.newConnection(true);
        	int estado = 0;
        	Calendar data = null;
        	boolean permitirVotar = false;
        	HashSet<Integer> eleitores = new HashSet<>();
        	int mandatos = 0;
        	// tabela eleicoes
        	PreparedStatement psEleicao = conn.prepareStatement("Select * FROM "+TabEleicoes+
        			" WHERE "+Eleicao+" = ?");
        	psEleicao.setInt(1,(Integer)key);
        	ResultSet rsEleicao = psEleicao.executeQuery();
        	if(rsEleicao.next()){
        		estado = rsEleicao.getInt(Estado);
        		data = new GregorianCalendar();
        		data.setTime(rsEleicao.getDate(Data));
        		permitirVotar = rsEleicao.getBoolean(PermitirVotar);
        	}
        	rsEleicao.close();
        	psEleicao.close();
        	// conjunto de eleitores
        	PreparedStatement psEleitores = conn.prepareStatement("Select "+Eleitor+" from "+TabEleitores+
        			" where "+Eleicao+" = ?");
        	psEleitores.setInt(1,(Integer)key);
        	ResultSet rsEleitores = psEleitores.executeQuery();
        	while(rsEleitores.next()){
        		eleitores.add(rsEleitores.getInt(Eleitor));
        	}
        	rsEleitores.close();
        	psEleitores.close();
        	// tabela eleicoesAR
        	PreparedStatement psEleicaoAR = conn.prepareStatement("select "+Mandatos+" from "+Tabname
        			+" where "+Eleicao+" = ?");
        	psEleicaoAR.setInt(1, (Integer)key);
        	ResultSet rsEleicaoAR = psEleicaoAR.executeQuery();
        	if(rsEleicaoAR.next()){
        		mandatos = rsEleicaoAR.getInt(Mandatos);
        	}
        	Collection<Circulo> circulos = new CirculoDAO().values();
        	eleicao = new EleicaoAR((Integer)key,data,circulos,estado,permitirVotar,eleitores,mandatos);
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
        return eleicao;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public Set<Integer> keySet() {
		Set<Integer> ret = null;
        Connection conn = null;
        try{
        	conn=Connector.newConnection(true);
        	ret = new TreeSet<Integer>();
        	PreparedStatement ps  = conn.prepareStatement("SELECT " +Eleicao + " FROM " + Tabname + ")");
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()){
        		int num = rs.getInt(Eleicao);
        		ret.add(num);
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
        return ret;
	}

	@Override
	public EleicaoAR put(Integer key, EleicaoAR value) {
		Connection conn=null;
    	EleicaoAR eleicao = null;
    	try{
    		conn = Connector.newConnection(false);
    		eleicao = this.get(key);
        	if(eleicao==null){//novo registo
        		PreparedStatement psEleicao = conn.prepareStatement("INSERT INTO "+TabEleicoes+
        				" ("+Eleicao+","+Estado+","+Data+","+PermitirVotar+") values " + "(?,?,?,?)");
    			psEleicao.setInt(1, key);
    			psEleicao.setInt(2, value.getEstado());
    			psEleicao.setDate(3,new java.sql.Date(value.getData().getTimeInMillis()));
    			psEleicao.setBoolean(4, value.isPermitirVotar());
    			psEleicao.execute();
    			psEleicao.close();
        		PreparedStatement psEleicaoAR = conn.prepareStatement("INSERT INTO "+ Tabname +
                        " ("+Eleicao+","+Mandatos+") " +
                        "values " +
                        "(?,?)");
        		psEleicaoAR.setInt(1, key);
        		psEleicaoAR.setInt(2, value.getMandatosAssembleia());
        		psEleicaoAR.execute();
        		psEleicaoAR.close();
    			PreparedStatement psEleitores = conn.prepareStatement("Insert into "+TabEleitores
    					+" ("+Eleitor+","+Eleicao+","+Volta+") values " + "(?,?,0)");
    			psEleitores.setInt(2, key);
        		for(int eleitor: value.getVotantes()){
        			psEleitores.setInt(1, eleitor);
        			psEleitores.execute();
        			psEleitores.close();
        		}
        	}else{//registo existente
        		PreparedStatement psEleicao = conn.prepareStatement("UPDATE "+ TabEleicoes + 
        				" SET "+Estado+" = ?,"+Data+" = ?,"+PermitirVotar+" = ? WHERE "+Eleicao+" = ?");
        		psEleicao.setInt(4, key);
        		psEleicao.setInt(1, value.getEstado());
        		psEleicao.setDate(2, new java.sql.Date(value.getData().getTimeInMillis()));
        		psEleicao.setBoolean(3, value.isPermitirVotar());
        		psEleicao.execute();
        		psEleicao.close();
        		PreparedStatement psEleicaoAR = conn.prepareStatement("UPDATE "+ Tabname + 
        				" SET "+Mandatos+" = ? WHERE "+Eleicao+" = ?");
        		psEleicaoAR.setInt(1, value.getMandatosAssembleia());
        		psEleicaoAR.setInt(2, key);
        		psEleicaoAR.execute();
        		psEleicaoAR.close();
        		//Remover eleitores antigos
    			PreparedStatement psRemoveEleitor = conn.prepareStatement("DELETE FROM "+TabEleitores+" WHERE "+Eleicao+" =?");
    			psRemoveEleitor.setInt(1, key);
    			psRemoveEleitor.execute();
    			psRemoveEleitor.close();
    			//Inserir novos eleitores
        		PreparedStatement psInsertEleitor = conn.prepareStatement("INSERT INTRO "+ TabEleitores +
        				 " VALUES ("+Eleitor+","+Eleicao+","+Volta+")"
        				+ "(?,?,0)");
        		psInsertEleitor.setInt(2,key);
        		Iterator<Integer> eleitores = value.getVotantes().iterator();
        		while(eleitores.hasNext()){
        			int eleitor = eleitores.next();
        			psInsertEleitor.setInt(1, eleitor);
        			psInsertEleitor.executeQuery();
        		}
        	}
    		conn.commit();
    	}catch(Exception e){
    		try {
    			conn.rollback();
    			e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
			}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
    	}
    	return eleicao;
	}

	@Override
	public EleicaoAR remove(Object key) {
		Connection conn =null;
		EleicaoAR ret = null;
		try{
			conn= Connector.newConnection(false);
			ret = this.get(key);
			if(ret == null) return null;
			new CirculoInfoDAO((Integer)key).clear();
			new ResultadoCirculoARDAO((Integer)key).clear();
			//Remover Votantes
			PreparedStatement psEleitores = conn.prepareStatement("DELETE FROM " +TabEleitores
					+ " WHERE "+Eleicao+" = ?");
			psEleitores.setInt(1,(Integer)key);
			psEleitores.execute();
			psEleitores.close();
			//Remover DasEleicoesAR
			PreparedStatement psEleicoesAR = conn.prepareStatement("DELETE FROM "+ Tabname
					+ " WHERE "+Eleicao+" = ?");
			psEleicoesAR.setInt(1,(Integer)key);
			psEleicoesAR.execute();
			psEleicoesAR.close();
			//Remover DasEleicoes
			PreparedStatement psEleicoes = conn.prepareStatement("DELETE FROM " + Tabname
					+ " WHERE "+Eleicao+" = ?");
			psEleicoes.setInt(1,(Integer)key);
			psEleicoes.execute();
			psEleicoes.close();
			conn.commit();	
		}catch(SQLException e){
    		try {
				conn.rollback();
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1.getMessage());
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
		return ret;
	}

	@Override
	public int size() {
		int ret=0;
    	Connection conn = null;
    	try{
    		conn = Connector.newConnection(true); 
    		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM " + Tabname);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) ret = rs.getInt(1);
    		rs.close();
    		ps.close();
		}catch(Exception e){{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
    	}finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
        return ret;  
	}

	@Override
	public Collection<EleicaoAR> values() {
		Connection c = null;
    	ArrayList <EleicaoAR> ret  = new ArrayList<>();
    	try{
    		c = Connector.newConnection(true);
    		Set<Integer> keys = this.keySet();
    		Iterator<Integer> i  = keys.iterator();
            while (i.hasNext()){
                ret.add(this.get(i.next()));
            }
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
	
// Metodos nao implementados
	
	@Override
	public Set<java.util.Map.Entry<Integer, EleicaoAR>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void putAll(Map<? extends Integer, ? extends EleicaoAR> m) {
		// TODO Auto-generated method stub
	}
}
