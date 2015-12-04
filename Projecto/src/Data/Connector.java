package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	private String url;
	private String username;
	private String password;
	public Connector(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Connection newConnection() throws SQLException{
		Connection conn = DriverManager.getConnection(this.url,this.username,this.password);
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        return conn;
	}


	public int teste;
}
