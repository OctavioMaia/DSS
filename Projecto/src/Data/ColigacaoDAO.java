package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.Coligacao;
import Business.Partido;

public class ColigacaoDAO implements Map<Integer, Coligacao> {
	
	//Tabela coligaçao
	private static String TabColName= "Coligacoes";
	private static String TabColID = "id";
	private static String TabColNome = "nome";
	private static String TabColSig = "sigla";
	private static String TabColSimb = "simbolo";
	private static String TabColRem = "removido";
	//tabela partidos_Coligaoa
	private static String TabColPartName= "Partido_pertence_Coligacao";
	private static String TabColPartPartId= "idPartido";
	private static String TabColPartColId= "idColigacao";
	
	
	
	public ColigacaoDAO() {}

	
	private int size_aux(Connection c) throws SQLException{
		int ret =0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) From "+ TabColName);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			ret=rs.getInt(1);
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	@Override
	public int size() {
		Connection cn = null;
		int i = 0;
		try {
			cn = Connector.newConnection(true);
			i = this.size_aux(cn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		return i;
	}

	@Override
	public boolean isEmpty() {
		return this.size()==0;
	}

	
	private boolean containsKey_aux(Integer key,Connection c)throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM "+TabColName+" WHERE "+TabColID+" = ?");
		ps.setInt(1,key);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret = true;
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	@Override
	public boolean containsKey(Object key) {
		Connection cn = null;
		boolean b = false;
		try {
			cn = Connector.newConnection(true);
			b =this.containsKey_aux((Integer)key, cn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		
		return b;
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((Coligacao)value).getId());
	}

	
	protected Coligacao remove_aux(Integer key,Connection c) throws SQLException{
		Coligacao ret =this.get_aux(key,c);
		if(ret!=null){
			PreparedStatement ps = c.prepareStatement("UPDATE "+ TabColName + 
					" SET " + TabColRem + "=true"
					+ " WHERE " + TabColID + "=?");
			ps.setInt(1, key);
			ps.execute();
			ps.close();
			ret.setRemovido(true);
		}
		return ret;
	}
	
	
	private Coligacao get_aux(Integer key,Connection c) throws SQLException{
		Coligacao col = null;
		PreparedStatement ps  = c.prepareStatement("SELECT * FROM " + TabColName +" WHERE " + TabColID + "=?");
		ps.setInt(1, key);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			PartidosDAO pdao = new PartidosDAO();
			int id=key;
			String sigla=rs.getString(TabColSig);
			String nome=rs.getString(TabColNome);
			String simbolo=rs.getString(TabColSimb);
			boolean removido=rs.getBoolean(TabColRem);
			//ir buscat os paridos 
			Set<Partido>  partidos = new HashSet<>();
			PreparedStatement ps1 = c.prepareStatement("SELECT " + TabColPartPartId + " FROM " + TabColPartName +" WHERE " + TabColPartColId + "=?");
			ps1.setInt(1,key);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()){
				partidos.add(pdao.get(rs1.getInt(TabColPartPartId)));
			}
			col = new Coligacao(id, sigla, nome, simbolo, partidos, removido);		
		}
				
		return col;	
	}
	
	@Override
	public Coligacao get(Object key) {
		Connection c = null;
		Coligacao ret =null;
		try{
			c=Connector.newConnection(true);
			ret = this.get_aux((Integer)key, c);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		return ret;
	}

	private boolean partidoInColigacao(Integer col, Integer part,Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps  = c.prepareStatement("SELECT * FROM " + TabColPartName + " WHERE "
				+ TabColPartColId + "=? AND " + TabColPartPartId +" =?");
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret=true;
		}
		return ret;
	}
	
	private void addPartidos(Integer col,Iterator<Partido> i, Connection c) throws SQLException{
		PartidosDAO pdao = new PartidosDAO();
		PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabColPartName +
				" (,"+TabColPartColId+","+TabColPartPartId+") "
				+ "VALUES "
				+ "(?,?)");
		ps.setInt(1, col);
		while (i.hasNext()) {
			Partido p =i.next();
			pdao.put_aux(p.getId(), p,c);
			if(!this.partidoInColigacao(col, p.getId(), c)){
				ps.setInt(2,p.getId());
				ps.execute();
			}
			
		}
	}
	
	private Coligacao put_aux(Integer key, Coligacao value,Connection c) throws SQLException{
		Coligacao ret = this.get_aux(key, c);
		if(ret!=null){//Existe UPDATE
			PreparedStatement ps = c.prepareStatement("UPDATE " +TabColName 
					+ " SET " + TabColNome + "=?,"+TabColSig+"=?,"+TabColSimb+"=?,"+TabColRem+"=? "
					+ " WHERE "+ TabColID + "=?");
			ps.setString(1,value.getNome());
			ps.setString(2,value.getSigla());
			ps.setString(3,value.getSimbolo());
			ps.setBoolean(4,value.isRemovido());
			ps.setInt(1,key);
			ps.execute();
			ps.close();
		}else{//Isereir
			PreparedStatement ps = c.prepareStatement("INSERT INTO " +TabColName 
					+ " (" + TabColNome + ","+TabColSig+","+TabColSimb+","+TabColRem+","+ TabColID+")"
					+ " VALUES "
					+ "(?,?,?,?,?)");
			ps.setString(1,value.getNome());
			ps.setString(2,value.getSigla());
			ps.setString(3,value.getSimbolo());
			ps.setBoolean(4,value.isRemovido());
			ps.setInt(1,key);
			ps.execute();
			ps.close();	
		}
		//Meter partidos
		this.addPartidos(key,value.getPartidos().iterator(),c);
		
		return ret;
	}
	
	@Override
	public Coligacao put(Integer key, Coligacao value) {
		Coligacao ret = null;
		Connection c = null;
		try {
			c=Connector.newConnection(false);
			ret = put_aux(key, value, c);
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
		}catch (Exception e){
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
	public Coligacao remove(Object key) {
		Coligacao ret = null;
		Connection c =null;
		try{
			c = Connector.newConnection(false);
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
	public void putAll(Map<? extends Integer, ? extends Coligacao> m) {
		throw new RuntimeException("Nao Implemantado");

	}

	private void clear_aux(Connection c)throws SQLException{
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while (i.hasNext()) {
			this.remove_aux(i.next(),c);		
		}	
	}
	
	@Override
	public void clear() {
		Connection c =null;
		try{
			c = Connector.newConnection(false);
			this.clear_aux(c);
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
		}finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

	}

	private Set<Integer> keySet_aux(Connection c)  throws SQLException{
		TreeSet<Integer> ret = new TreeSet<>();
		PreparedStatement ps = c.prepareStatement("SELECT " + TabColID + " FROM " + TabColName);
		ResultSet rs  = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(TabColID));
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	@Override
	public Set<Integer> keySet() {
		Connection cn = null;
		Set<Integer> ret = new TreeSet<>();
		try {
			cn = Connector.newConnection(true);
			ret =this.keySet_aux(cn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		
		return ret;
	}

	private Collection<Coligacao> values_aux(Connection c) throws SQLException{
		Collection<Coligacao> ret  = new ArrayList<>();
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while (i.hasNext()) {
			ret.add(this.get_aux(i.next(),c));	
		}
		return ret;
	}
	
	@Override
	public Collection<Coligacao> values() {
		Connection cn = null;
		Collection<Coligacao> ret = new ArrayList<>();
		try {
			cn = Connector.newConnection(true);
			ret =this.values_aux(cn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		return ret;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Coligacao>> entrySet() {
		throw new RuntimeException("Nao Implemantado");
	}

}
