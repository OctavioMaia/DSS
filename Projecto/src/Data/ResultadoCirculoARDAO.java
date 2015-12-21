package Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import Business.ResultadoCirculoAR;

public class ResultadoCirculoARDAO implements Map<Integer,ResultadoCirculoAR>{
	private int idEleicao;
	
	public ResultadoCirculoARDAO(int idEleicao){
		this.idEleicao = idEleicao;
	}
	protected void clear_aux(Connection c)throws SQLException{
		this.clear();
		
	}
	
	/*
	 * Implementar interface...
	 */

}
