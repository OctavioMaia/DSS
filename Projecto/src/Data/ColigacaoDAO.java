package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import Business.Coligacao;

public class ColigacaoDAO implements Map<Integer, Coligacao> {

	public ColigacaoDAO() {}

	@Override
	public int size() {
		Connection cn = null;
		int i = 0;
		try {
			cn = con.newConnection();
			PreparedStatement ps = cn.prepareStatement("Select id From Coligacao");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				i++;
			}
			rs.close();
			ps.close();
			cn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	@Override
	public boolean isEmpty() {
		Connection cn=null;
		try {
			cn = con.newConnection();
			if (this.size()==0){
				return true;
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		Connection cn = null;
		boolean b = false;
		try {
			cn = con.newConnection();
			PreparedStatement ps = cn.prepareStatement("Select * From Coligacao where id = ?");
			ps.setString(1,key.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				b = true;
			}
			rs.close();
			ps.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cn.close();
		}
		
		return b;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Coligacao get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coligacao put(Integer key, Coligacao value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coligacao remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Coligacao> m) {
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
	public Collection<Coligacao> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Coligacao>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
