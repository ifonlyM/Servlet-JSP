package util;


public class DBConnTests {
	public static void main(String[] args) {
		testGetConneciton();
	}
	public static void testGetConneciton() {
		System.out.println(DBConn.getConnection());
	}
}
