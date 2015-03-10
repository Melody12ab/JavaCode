package com.melody;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTools {
	/**
	 * 获得数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		String driverClass = null;
		String url = null;
		String user = null;
		String password = null;
		InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream(
				"jdbc.properties");

		Properties p = new Properties();
		p.load(in);
		driverClass = p.getProperty("driver");
		url = p.getProperty("url");
		user = p.getProperty("user");
		password = p.getProperty("password");

		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);

		Driver driver = (Driver) Class.forName(driverClass).newInstance();
		Connection connection = driver.connect(url, info);
		return connection;
	}

	public static void releaseSource(ResultSet rs, Statement statement,
			Connection conncetion) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conncetion != null) {
			try {
				conncetion.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 关闭statement和conncetion
	 * 
	 * @param statement
	 * @param conncetion
	 */
	public static void releaseSource(Statement statement, Connection conncetion) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conncetion != null) {
			try {
				conncetion.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 通用数据库更新的方法：包括INSERT、UPDATE、DELETE
	 * 
	 * @param sql
	 */
	public static void update(String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseSource(statement, connection);
		}
	}
	
	/**
	 * 使用preparement升级update方法
	 * @param sql
	 * @param args
	 */
	public static void update(String sql,Object ... args){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=JDBCTools.getConnection();
			ps=conn.prepareStatement(sql);
			
			for(int i=0;i<args.length;i++){
				ps.setObject(i+1, args[i]);
			}
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
