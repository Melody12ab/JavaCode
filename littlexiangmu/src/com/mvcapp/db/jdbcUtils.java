package com.mvcapp.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * jdbc操作工具类
 * @author melody
 *
 */
public class jdbcUtils {
	
	public static void releaseConnection(Connection connection){
		try {
			if(connection!=null){
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static DataSource dataSource=null;
	//数据源只能被创建一次
	static {
		dataSource=new ComboPooledDataSource("mvcapp");
	}
	
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}
