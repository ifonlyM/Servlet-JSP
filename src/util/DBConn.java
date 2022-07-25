package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","board", "1234");
//			connection = DriverManager.getConnection("jdbc:oracle:thin:@human.lepelaka.net:1521:xe","USER10", "USER10");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@db.ifonlygaram.net:1521:xe","awsBoard", "awsBoard");
//			System.out.println("getConnection complete!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void main(String[] args) {
		getConnection();
	}
}
