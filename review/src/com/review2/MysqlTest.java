package com.review2;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MysqlTest {
	private String url;
	private String driver;
	private String user;
	private String pass;
	public void test() throws Exception {
		Properties p=new Properties();
		p.load(new FileInputStream("jdbc.properties"));
		driver=p.getProperty("driver");
		url=p.getProperty("url");
		user=p.getProperty("user");
		pass=p.getProperty("password");
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/servlet", "root", "123456");
			Statement stmt = conn.createStatement();
			ResultSet st = stmt.executeQuery("select * from users");
			while (st.next()) {
				System.out.println(st.getInt(1) + "\t" + st.getString(2) + "\t"
						+ st.getString(3) + "\t" + st.getInt(4) + "\t"
						+ st.getInt(5));
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/	}
}
