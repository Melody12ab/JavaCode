package com.lee.mysql.object.list;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Icon;

import com.lee.mysql.exception.QueryException;
import com.lee.mysql.object.QueryObject;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.util.ImageUtil;
import com.lee.mysql.util.MySQLUtil;

/**
 * �б��е���ͼ��ʾ����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ViewData extends AbstractData implements QueryObject {

	private Database database;
	
	private String content;
	
	public ViewData(Database database, String content) {
		this.database = database;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDataCount() {
		try {
			Statement stmt = this.database.getStatement();
			//�õ�ȫ����¼����SQL
			String sql = "SELECT COUNT(*) FROM " + this.name;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int result = rs.getInt(1);
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("��ѯ��ͼ����" + this.name);
		}
	}

	public ResultSet getDatas(String orderString) {
		try {
			String sql = getQuerySQL(orderString);
			Statement stmt = database.getStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			throw new QueryException("��ѯ��ͼ����" + this.name);
		}
	}
	
	//������ͼ
	public void createView() {
		try {
			//ƴװCREATE VIEW���
			String sql = MySQLUtil.CREATE_VIEW + name + " " + 
			MySQLUtil.AS + " " + content;
			database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("�����ͼ����" + e.getMessage());
		}
	}
	
	//ɾ����ͼ
	public void dropView() {
		try {
			String sql = MySQLUtil.DROP_VIEW + this.name;
			database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("ɾ����ͼ����" + e.getMessage());
		}
	}
	
	//�޸���ͼ
	public void alterView() {
		try {
			String sql = MySQLUtil.ALTER_VIEW + name + " " + MySQLUtil.AS 
				+ " " + content;
			database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("�޸���ͼ����" + e.getMessage());
		}
	}

	public String getQueryName() {
		return this.name;
	}

	public String getQuerySQL(String orderString) {
		StringBuffer sql = new StringBuffer("SELECT * FROM " + this.name);
		if (orderString == null || orderString.trim().equals("")) {
			return sql.toString();
		} else {
			sql.append(" ORDER BY " + orderString);
			return sql.toString();
		}
	}

	public String toString() {
		return this.name;
	}

	public Icon getIcon() {
		// TODO Auto-generated method stub
		return ImageUtil.VIEW_DATA_ICON;
	}
}
