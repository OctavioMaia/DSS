package Data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Business.CandidatoAR;
import Business.Circulo;
import Business.Coligacao;
import Business.Lista;
import Business.Partido;
import Business.Votavel;

public class ListaARDAO implements Map<Integer,Lista>{
	private int Eleicao;
	private int circulo;
	//Tabela das Listas
	private static String TabName ="listasAR";
	private static String TabListID = "id";
	private static String TabListSimb = "simbolo";
	private static String TabListSig = "sigla";
	private static String TabListNome = "nome";
	private static String TabListIDCirc = "idCirculo";
	private static String TabListIDElei = "idEleicao";
	private static String TabListPart =  "idPartido";
	private static String TabListCol = "idColigacao";
	private static String TabListOrd = "ordem";
	//Tabela de CandidatosAR
	private static String TabCandid = "candidatosAR";
	private static String TabCandidID = "bi";
	private static String TabCandidProf = "prof";
	private static String TabCandidNasc = "dataNasc";
	private static String TabCandidResid = "residencia";
	private static String TabCandidNat = "naturalidade";
	private static String TabCandidNome = "nome";
	private static String TabCandidPart = "idPartido";
	private static String TabCandidTipo = "tipo";
	//TabelaCandidatosLista
	private static String TabCanListName = "candidatoar_has_listasar";
	private static String TabCanListIDCand = "biCandidatoAR";
	private static String TabCanListTipo = "tipo";
	private static String TabCanListOrd = "Numordem";
	private static String TabCanListIDList  = "idLista";
	private static String TabCanListIDElei = "idEleicao";
	private static String TabCanListIDCirc = "idCirculo";

	
	
		
	
	public ListaARDAO(int eleicao, int circulo) {
		this.Eleicao = eleicao;
		this.circulo = circulo;
	}
	
	protected void clear_aux(Connection c)throws SQLException{
		Iterator<Integer> i = this.keySet_aux(c).iterator();
		while (i.hasNext()) {
			this.remove_aux(i.next(),c);	
		}	
	}
	
	private int size_aux(Connection c) throws SQLException{
		int ret =0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + TabName
				+ " WHERE " + TabListIDCirc + "=? AND " + TabListIDElei + "=?");
		ps.setInt(1, this.circulo);
		ps.setInt(2, this.Eleicao);
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
		PreparedStatement ps = c.prepareStatement("Select EXISTS "
				+ "(SELECT "+TabListID+" FROM ListasAR WHERE "
				+TabListID +"= ? AND "+TabListIDCirc+"=? AND "+TabListIDElei+" =?)");
		ps.setInt(1, key);
		ps.setInt(2, this.circulo);
		ps.setInt(3, this.Eleicao);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret=rs.getBoolean(1);
		}
		return ret;
		
	}

	@Override
	public boolean containsKey(Object key) {
		boolean b=false;
        Connection conn = null;
        try{
        	conn = Connector.newConnection(true);
        	b=this.containsKey_aux((Integer)key, conn);
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

	@Override
	public boolean containsValue(Object value) {
		return this.containsKey(((Lista)value).getID());
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Lista>> entrySet() {
		throw new RuntimeException("Nao implementado");
	}

	
	private boolean existCand(Integer candBi, Connection c) throws SQLException{
		boolean ret = false;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " +  TabCandid + 
				" WHERE " + TabCandidID + "=?");
		ps.setInt(1,candBi);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			ret=true;
		}
		rs.close();
		ps.close();
		return ret;
	}
	
	private CandidatoAR getCand(Integer candBi,char tipo,int ordem,Connection c)throws SQLException{
		CandidatoAR ret = null;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " +  TabCandid + 
				" WHERE " + TabCandidID + "=?");
		if(this.existCand(candBi,c)){
			ps.setInt(1,candBi);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				PartidosDAO p = new PartidosDAO();
				String nome = rs.getString(TabCandidNome);
				int bi = rs.getInt(TabCandidID);
				String prof = rs.getString(TabCandidProf);
				GregorianCalendar dataNasc = new GregorianCalendar();
				dataNasc.setTimeInMillis(rs.getDate(TabCandidNasc).getTime());
				String residencia = rs.getString(TabCandidResid);
				String naturalidade = rs.getString(TabCandidNat);
				int partId = rs.getInt(TabCandidPart);
				Partido partido = p.get_aux(partId,c);
				ret = new CandidatoAR(nome, bi, prof, dataNasc, residencia, naturalidade, partido, tipo, ordem);
			}
			rs.close();
		}
		
		ps.close();
		return ret;
	}
	
	protected Lista get_aux(Integer key,Connection c) throws SQLException{
		Lista l =null;
		//Ids de todos os candidadtos daquela lista
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " + TabCanListName +
				" WHERE "+ TabCanListIDCirc + "=? AND " + TabCanListIDElei+"=? AND " 
				+ TabCanListIDList +"=?" );
		ps.setInt(1, this.circulo);
		ps.setInt(2, this.Eleicao);
		ps.setInt(3, key);
		ResultSet rs = ps.executeQuery();
		ArrayList<CandidatoAR> cands  =new ArrayList<>();
		while(rs.next()){
			int idCand  =rs.getInt(TabCanListIDCand);
			int nOrd = rs.getInt(TabCanListOrd);
			char tip = rs.getString(TabCanListTipo).charAt(0);
			cands.add(this.getCand(idCand, tip, nOrd, c));	
		}
		rs.close();
		ps.close();
		//ja tem os candiadatos todos
		//Irbuscar a lista
		PreparedStatement psL = c.prepareStatement("SELECT * FROM " + TabName 
				+ " WHERE " + TabListID+ "=? AND "+TabListIDElei+" =? ");
		//TabListIDCirc = "idCirculo";
		//private static String TabListIDElei = "idEleicao";
		psL.setInt(1, key);
		psL.setInt(2, this.Eleicao);
		ResultSet rsL = psL.executeQuery();
		if(rsL.next()){
			Votavel mandante = null;
			CirculoDAO cdao = new CirculoDAO();
			Circulo ci  = cdao.get_aux(this.circulo, c);
			String sigla = rsL.getString(TabListSig);
			String nome = rsL.getString(TabListNome);
			String simbolo = rsL.getString(TabListSimb);
			// irbuscar o votavel
			//Ver aqui por causa de um deles ser NULL
			//Pde dar muita merada
			Object partido = rsL.getObject(TabListPart);
			Object coligacao = rsL.getObject(TabListCol);
			Object mandanteid =partido;
			int ordem = rsL.getInt(TabListOrd);
			if(mandanteid==null){
				mandanteid =coligacao;
				ColigacaoDAO coldao = new ColigacaoDAO();
				mandante  = coldao.get_aux((Integer)mandanteid,c);
			}else{
				PartidosDAO pdao = new PartidosDAO();
				mandante = pdao.get_aux((Integer)mandanteid, c);
				
			}
			
			l = new Lista(key, ci, ordem, sigla, nome, simbolo, mandante, cands);
			
		}
		rsL.close();
		psL.close();
		return l;
	}
	
	@Override
	public Lista get(Object key) {
		Lista l  = null;
		Connection c=null;
		try{
			c = Connector.newConnection(true);
			l = this.get_aux((Integer)key, c);
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
		return l;
	}

	private Set<Integer> keySet_aux(Connection c) throws SQLException{
		Set<Integer> ret = new TreeSet<Integer>();
		PreparedStatement ps  = c.prepareStatement("SELECT "+TabListID+" FROM " +TabName
				+ " WHERE " + TabListIDCirc + "=? AND "+ TabListIDElei + "=?");
		ps.setInt(1, this.circulo);
		ps.setInt(2, this.Eleicao);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ret.add(rs.getInt(TabListID));
		}
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

	
	private boolean listaHasCand(Integer Cand, Integer Lista, Connection c) throws SQLException{
		boolean ret =false;
		PreparedStatement ps = c.prepareStatement("SELECT * FROM " + TabCanListName +
				" WHERE " + TabCanListIDCand+ "=? AND " + TabCanListIDList + "=? AND "+  TabCanListIDElei
				+ " =? AND "+TabCanListIDCirc +"=?");
		ps.setInt(1, Cand);
		ps.setInt(2, Lista);
		ps.setInt(3, this.Eleicao);
		ps.setInt(4, this.circulo);
		ResultSet rs =ps.executeQuery();
		if(rs.next()){
			ret=true;
		}
		rs.close();
		ps.close();
		return ret;
	}

	
	private void putCand(CandidatoAR ca, Integer listaId, Connection c) throws SQLException{
		if(this.existCand(ca.getBi(), c)){
			PreparedStatement ps = c.prepareStatement("UPDATE " + TabCandid+
					" SET "+TabCandidProf+"=?,"+TabCandidNasc+"=?,"+TabCandidResid+"=?,"+TabCandidNat+"=?,"
					+TabCandidNome+"=?,"+TabCandidPart+"=? "
					+" WHERE " +TabCandidID+"=?" );
			ps.setString(1, ca.getProf());
			ps.setDate(2, new Date(ca.getDataNasc().getTimeInMillis()));
			ps.setString(3, ca.getResidencia());
			ps.setString(4,ca.getNaturalidade());
			ps.setString(5, ca.getNome());
			ps.setInt(6, ca.getPartido().getId());
			ps.setInt(7, ca.getBi());	
			ps.execute();
			ps.close();
		}else{
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabCandid+
					" ("+TabCandidProf+","+TabCandidNasc+","+TabCandidResid+","+TabCandidNat+","
					+TabCandidNome+","+TabCandidPart+","+TabCandidID+","+TabCandidTipo+")"
					+ " VALUES "
					+ "(?,?,?,?,?,?,?,?)" );
			ps.setString(1, ca.getProf());
			ps.setDate(2, new Date(ca.getDataNasc().getTimeInMillis()));
			ps.setString(3, ca.getResidencia());
			ps.setString(4,ca.getNaturalidade());
			ps.setString(5, ca.getNome());
			ps.setInt(6, ca.getPartido().getId());
			ps.setInt(7, ca.getBi());	
			ps.setString(8, "n");
			ps.execute();
			ps.close();
		}
		//Candidatos inseridos
		//Inserir na tabela de ligaçao
		if(this.listaHasCand(ca.getBi(),listaId,c)){//Upadet
			PreparedStatement ps = c.prepareStatement("UPDATE " + TabCanListName
					+" SET "+TabCanListTipo+"=?,"+TabCanListOrd+"=?"
					+" WHERE " + TabCanListIDCand + "=? AND "+TabCanListIDList+"=? AND "
					+ TabCanListIDCirc + "=? AND " + TabCanListIDElei + "=?" );
			String s ="";
			s = s+ca.getTipo();
			ps.setString(1, s);
			ps.setInt(2, ca.getOrdem());
			ps.setInt(3, ca.getBi());
			ps.setInt(4, listaId);
			ps.setInt(5, this.circulo);
			ps.setInt(6, this.Eleicao);
			ps.execute();
			ps.close();
		}else{//Inserir
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabCanListName
					+"("+TabCanListTipo+","+TabCanListOrd+"," + TabCanListIDCand + ","+TabCanListIDList+
					","+ TabCanListIDCirc + "," + TabCanListIDElei + ")"
					+ " VALUES "
					+ "(?,?,?,?,?,?)" );
			String s ="";
			s = s+ca.getTipo();
			ps.setString(1, s);
			ps.setInt(2, ca.getOrdem());
			ps.setInt(3, ca.getBi());
			ps.setInt(4, listaId);
			ps.setInt(5, this.circulo);
			ps.setInt(6, this.Eleicao);
			ps.execute();
			ps.close();
		}
		
	}

	private void insereCands(ArrayList<CandidatoAR> cands, Integer listaId,Connection c) throws SQLException{
		for (CandidatoAR candidatoAR : cands) {
			this.putCand(candidatoAR,listaId,c);
		}
	}
	
	protected Lista put_aux(Integer key,Lista value, Connection c) throws SQLException{
		Lista l = this.get_aux(key, c);
		ArrayList<CandidatoAR> cands = value.getCandidatos();
		if(l==null){//nova inserir
			PreparedStatement ps = c.prepareStatement("INSERT INTO " + TabName +
					"("+TabListID+","+TabListSimb+","+TabListSig+","+TabListNome+","+
					TabListIDCirc+","+TabListIDElei+","+TabListOrd+","+TabListPart+","+TabListCol+")"+
					" VALUES "
					+ "(?,?,?,?,?,?,?,?,?)");
			ps.setInt(1,key);
			ps.setString(2,value.getSimbolo());
			ps.setString(3,value.getSigla());
			ps.setString(4,value.getNome());
			ps.setInt(5,this.circulo);
			ps.setInt(6,this.Eleicao);
			ps.setInt(7,value.getOrdem());
			Votavel v = value.getMandante();
			Integer idPartido = null;
			Integer idColig = null;
			if(v instanceof Partido){
				idPartido = ((Partido)v).getId();
			}else{
				idColig = ((Coligacao)v).getId();
			}
			ps.setObject(8,idPartido);
			ps.setObject(9,idColig);
			ps.executeUpdate();
			ps.close();
		}else{
			PreparedStatement ps = c.prepareStatement("UPDATE " + TabName +
					" SET "+TabListSimb+"=?,"+TabListSig+"=?,"+TabListNome+"=?,"+TabListOrd+"=?,"+
					TabListPart+"=?,"+TabListCol+"=?"+
					" WHERE "+ TabListID + "=? AND " + TabListIDCirc + "=? AND " + TabListIDElei +"=?"); 

			ps.setInt(7,key);
			ps.setString(1,value.getSimbolo());
			ps.setString(2,value.getSigla());
			ps.setString(3,value.getNome());
			ps.setInt(8,this.circulo);
			ps.setInt(9,this.Eleicao);
			ps.setInt(4,value.getOrdem());
			Votavel v = value.getMandante();
			Integer idPartido = null;
			Integer idColig = null;
			if(v instanceof Partido){
				idPartido = ((Partido)v).getId();
			}else{
				idColig = ((Coligacao)v).getId();
			}
			ps.setObject(5,idPartido);
			ps.setObject(6,idColig);
			ps.executeUpdate();
			ps.close();
		}
		this.insereCands(cands,key,c);
		return l;
	}
	
	@Override
	public Lista put(Integer key, Lista value) {
		Lista ret = null;
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

	@Override
	public void putAll(Map<? extends Integer, ? extends Lista> m) {
		throw new RuntimeException("Nao implementado");
		
	}

	

	
	protected Lista remove_aux(Integer key, Connection c) throws SQLException{
		Lista l = this.get_aux(key, c);
		if(l!=null){//existe é para remover
			//Rmove Ligacoes de Candidatos e Listas
			PreparedStatement ps = c.prepareStatement("DELETE FROM " + TabCanListName
					+ " WHERE " + TabCanListIDList + " =? AND "+ TabCanListIDElei + "=? AND" 
					+ TabCanListIDCirc +"=?" );
			ps.setInt(1, key);
			ps.setInt(2, this.Eleicao);
			ps.setInt(3, this.circulo);
			ps.execute();
			ps.close();
			//REMOVER A lista
			PreparedStatement ps2 = c.prepareStatement("DELETE FROM " + TabName
					+ " WHERE " + TabListID + " =? AND "+ TabListIDElei + "=? AND " 
					+ TabListIDCirc +"=?" );
			ps2.setInt(1, key);
			ps2.setInt(2, this.Eleicao);
			ps2.setInt(3, this.circulo);
			ps2.execute();
			ps2.close();
			
		}
		return l;
	}
	@Override
	public Lista remove(Object key) {
		Lista ret = null;
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

	private Collection<Lista> values_aux(Connection c) throws SQLException{
		ArrayList <Lista> ret  = new ArrayList<>();
		 Iterator<Integer> i = this.keySet_aux(c).iterator();
        while (i.hasNext()){
            ret.add(this.get_aux(i.next(),c));
        }
        return ret;
	}
	
	@Override
	public Collection<Lista> values() {
		Collection<Lista> ret = new ArrayList<>();
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
	
	
	
}
