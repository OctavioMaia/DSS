package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import Business.CirculoInfo;

public class CirculoInfoDAO implements Map<Integer,CirculoInfo>{
	private int idEleicao;
	private static String Tabname ="Circulo_has_EleicaoAR";
	private static String Mandatos = "mandatos";
	private static String Circulo = "idCirculo";
	private static String Eleicao = "idEleicao";
	private static String TabEleicoes = "EleicoesAR";
	private static String TabCirculos = "Circulos";
	
	public CirculoInfoDAO(int idEleicao){
		this.idEleicao = idEleicao;
	}

	@Override
	public void clear() {
		Connection conn = null;
    	try{
    		conn = Connector.newConnection(false);
    		PreparedStatement psClear = conn.prepareStatement("DELETE FROM " +Tabname + " WHERE "+Eleicao+" = "+this.idEleicao );
        	psClear.executeQuery();
        	psClear.close();    		
    		conn.commit();
    	}catch(SQLException e){
    		try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    	}catch(Exception e){
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
	public boolean containsKey(Object key) {
		Connection conn = null;
		boolean b =false;
        try{
        	conn = Connector.newConnection(true);
        	PreparedStatement ps = conn.prepareStatement(" SELECT  EXISTS (SELECT * FROM "+ Tabname +
        			" WHERE "+Circulo+" = ? AND "+Eleicao+" = "+this.idEleicao+")");
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
	public boolean containsValue(Object arg0) {
		// Apenas verifica a chave
		// TODO return this.containsKey((CirculoInfo)arg0.);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<java.util.Map.Entry<Integer, CirculoInfo>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public CirculoInfo get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<Integer> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public CirculoInfo put(Integer arg0, CirculoInfo arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends Integer, ? extends CirculoInfo> arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public CirculoInfo remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<CirculoInfo> values() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
