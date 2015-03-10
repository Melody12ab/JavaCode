package com.lee.mysql.object.tree;

import java.sql.Connection;

import com.lee.mysql.object.ViewObject;

/**
 * ��Ҫ�������ӵĽڵ�ĸ��࣬ Database��ServerConnection
 * @author yangenxiong
 *
 */
public abstract class ConnectionNode implements ViewObject {

	protected Connection connection;
	
	public abstract Connection connect();
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
