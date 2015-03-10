package com.mvcapp.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mvcapp.db.jdbcUtils;

public class jdbcUtilsTest {

	@Test
	public void testGetConnection() throws SQLException {
		Connection connection=jdbcUtils.getConnection();
		System.out.println(connection);
	}
}
