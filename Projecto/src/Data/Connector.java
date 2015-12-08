package Data;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
	private static  String url="url";
	private static String username="usrname";
	private static String password="password";

	public static Connection newConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,username,password);
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        return conn;
	}

}
