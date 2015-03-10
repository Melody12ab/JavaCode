package com.lee.mysql.object.list;

import java.sql.ResultSet;
import java.sql.Statement;

import com.lee.mysql.exception.QueryException;
import com.lee.mysql.object.QueryObject;
import com.lee.mysql.object.tree.Database;

/**
 * ִ�в�ѯʱ�����ݶ���
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class QueryData implements QueryObject {

	//�˴β�ѯ��SQL���
	private String sql;
	//��Ӧ�����ݿ����
	private Database database;
	
	public QueryData(Database database, String sql) {
		this.sql = sql;
		this.database = database;
	}

	public int getDataCount() {
		try {
			String countSQL = getSelectCount();
			if (countSQL == null) return 0;
			Statement stmt = this.database.getStatement();
			ResultSet rs = stmt.executeQuery(countSQL);
			rs.next();
			int result = rs.getInt(1);
			rs.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("��ѯ����" + this.sql);
		}
	}
	
	//ִ��SQL
	public void execute() {
		try {
			Statement stmt = this.database.getStatement();
			stmt.execute(this.sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("ִ��SQL����" + e.getMessage());
		}
	}
	
	/**
	 * ����select count���
	 * @return
	 */
	private String getSelectCount() {
		StringBuffer newSQL = new StringBuffer();
		int selectCount = this.sql.toLowerCase().indexOf("select count");
		if (selectCount == -1) {
			int from = this.sql.toLowerCase().indexOf("from");
			if (from == -1) return null;
			newSQL.append("select count(*) " + 
					this.sql.substring(from, this.sql.length()));
		} else {
			newSQL.append(this.sql);
		}
		return newSQL.toString();
	}

	public ResultSet getDatas(String orderString) {
		try {
			String newSQL = getQuerySQL(orderString);
			Statement stmt = this.database.getStatement();
			return stmt.executeQuery(newSQL);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("��ѯ���ݴ���" + this.sql);
		}
	}

	public String getQueryName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQuerySQL(String orderString) {
		if (this.sql.toLowerCase().indexOf("call") == 0) return this.sql;
		StringBuffer buffer = new StringBuffer(this.sql);
		if (orderString == null || orderString.trim().equals("")) {
			return buffer.toString();
		} else {
			buffer.append(" ORDER BY " + orderString);
			return buffer.toString();
		}
	}

}
