package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.CirculoInfo;
import Business.EleicaoAR;

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
	

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
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

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public EleicaoAR get(Object key) {
		// TODO Auto-generated method stub
		return null;
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

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public EleicaoAR remove(Object key) {
		// TODO Auto-generated method stub
		return null;
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
