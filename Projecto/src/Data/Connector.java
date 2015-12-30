package Data;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {
	private static String url="jdbc:mysql://localhost:3306/sge";
	private static String username="root";
	private static String password="";

	public static Connection newConnection(boolean autoCommit) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,username,password);
		conn.setAutoCommit(autoCommit);
        conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        return conn;
	}

}