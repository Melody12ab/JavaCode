package com.lee.mysql.object.tree;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import com.lee.mysql.exception.ConnectionException;
import com.lee.mysql.exception.QueryException;
import com.lee.mysql.object.list.ProcedureData;
import com.lee.mysql.object.list.TableData;
import com.lee.mysql.object.list.ViewData;
import com.lee.mysql.util.ImageUtil;
import com.lee.mysql.util.MySQLUtil;


/**
 * ���������е����ݿ����
 * @author yangenxiong
 *
 */
public class Database extends ConnectionNode {

	//���ݿ�����
	private String databaseName;
	//���ݿ������ķ���������
	private ServerConnection serverConnection;
	
	//MySQL�в�ѯ��ʱ��TABLE_NAME�ֶ�
	public final static String TABLE_NAME = "TABLE_NAME";
		
	public Database(String databaseName, ServerConnection serverConnection) {
		this.databaseName = databaseName;
		this.serverConnection = serverConnection;
	}
	
	//������������Ӷ���
	public Connection connect() {
		//����Ѿ����ӣ� �򷵻�
		if (super.connection != null) return super.connection;
		//�������ݿ�����
		try {
			super.connection = this.serverConnection.createConnection(this.databaseName);
		} catch (Exception e) {
			throw new ConnectionException("�������ݿ����Ӵ���" + this.databaseName);
		}
		return super.connection;
	}
	
	private Statement stmt;
	
	public Statement getStatement() throws Exception {
		if (this.stmt == null) {
			this.stmt = super.connection.createStatement();
		}
		return this.stmt;
	}
	
	/**
	 * �������ݿ�����б�����ݼ�
	 * @return
	 */
	public ResultSet getTablesResultSet() throws Exception {
		Statement stmt = getStatement();
		//ȥϵͳ��information_schema��ѯ
		String sql = "SELECT TABLE_NAME FROM " +
		"information_schema.TABLES sc WHERE (sc.TABLE_TYPE='" + MySQLUtil.TABLE_TYPE +
		"' OR sc.TABLE_TYPE='" + MySQLUtil.SYSTEM_VIEW_TYPE + "') AND sc.TABLE_SCHEMA='" + this.databaseName + "'";
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	/**
	 * �������ֵõ���
	 * @return
	 */
	public TableData getTableByName(String tableName) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT TABLE_NAME FROM information_schema.TABLES sc")
			.append(" WHERE sc.TABLE_SCHEMA='" + this.databaseName + "'")
			.append(" AND sc.TABLE_NAME='" + tableName + "'");
			Statement stmt = getStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			TableData table = null;
			if (rs.next()) {
				table = new TableData(this);
				table.setName(tableName);
			}
			rs.close();
			return table;
		} catch (Exception e) {
			throw new QueryException("��ȡ�����" + e.getMessage());
		}
	}
	
	/**
	 * ����ȫ���ı�
	 * @param db
	 * @return
	 */
	public List<TableData> getTables() {
		try {
			List<TableData> result = new ArrayList<TableData>();
			ResultSet rs = getTablesResultSet();
			while (rs.next()) {
				TableData td = new TableData(this);
				td.setName(rs.getString(TABLE_NAME));
				result.add(td);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("���ұ����" + this.getDatabaseName());
		}
	}
	
	/**
	 * �������ݿ�
	 */
	public void create() {
		try {
			Statement stmt = this.serverConnection.getStatement();
			stmt.execute("create database " + this.databaseName);
		} catch (Exception e) {
			throw new ConnectionException("�������ݿ����" + this.databaseName);
		}
	}
	
	/**
	 * ɾ��һ�����ݿ�
	 */
	public void remove() {
		try {
			Statement stmt = this.serverConnection.getStatement();
			stmt.execute("drop database " + this.databaseName);
		} catch (Exception e) {
			throw new ConnectionException("ɾ�����ݿ����" + this.databaseName);
		}
	}
	
	/**
	 * �������ݿ������еĴ洢����
	 * @return
	 * @throws Exception
	 */
	private ResultSet getProceduresResultSet() throws Exception {
		Statement stmt = getStatement();
		String sql = "SELECT * FROM mysql.proc pc WHERE pc.db='" + this.databaseName + "' " +
				"AND pc.type='" + MySQLUtil.PROCEDURE_TYPE + "'";
		return stmt.executeQuery(sql);
	}
	
	/**
	 * �������ݿ������еĺ��������ݼ�
	 * @return
	 * @throws Exception
	 */
	private ResultSet getFunctionsResultSet() throws Exception {
		Statement stmt = getStatement();
		String sql = "SELECT * FROM mysql.proc pc WHERE pc.db='" + this.databaseName + "' " +
				"AND pc.type='" + MySQLUtil.FUNCTION_TYPE + "'";
		return stmt.executeQuery(sql);
	}
	
	/**
	 * ����������ݿ�����д洢���̺ͺ���
	 * @return
	 */
	public List<ProcedureData> getProcedures() {
		List<ProcedureData> result = new ArrayList<ProcedureData>();
		try {
			//�õ�������ResultSet
			ResultSet functionRs = getFunctionsResultSet();
			List<ProcedureData> functionList = getProcedureData(this, functionRs, 
					MySQLUtil.FUNCTION_TYPE);
			//�õ��洢���̵�ResultSet
			ResultSet procedureRs = getProceduresResultSet();
			List<ProcedureData> procedureList = getProcedureData(this, procedureRs, 
					MySQLUtil.PROCEDURE_TYPE);
			result.addAll(functionList);
			result.addAll(procedureList);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("����(�洢����/����)����" + getDatabaseName());
		}
	}
	
	/**
	 * ��ResultSet������ݲ���type���ͷ�װ����Ӧ��ProcedureData
	 * @param rs
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private List<ProcedureData> getProcedureData(Database db, 
			ResultSet rs, String type) throws Exception {
		List<ProcedureData> result = new ArrayList<ProcedureData>();
		//����ResultSet
		while (rs.next()) {
			String content = rs.getString("body");
			String params = rs.getString("param_list");
			String returnString = rs.getString("returns");
			//�������ͷ�װProcedureData
			ProcedureData data = new ProcedureData(db, type, content);
			//���ò����ͷ���ֵ
			data.setArg(params);
			data.setReturnString(returnString);
			//��ã��洢����/����������
			data.setName(rs.getString("name"));
			result.add(data);
		}
		rs.close();
		return result;
	}
	
	/**
	 * ����������ݿ����������ͼ
	 * @return
	 */
	public List<ViewData> getViews() {
		try {
			List<ViewData> result = new ArrayList<ViewData>();
			ResultSet rs = getViewsResultSet();
			while (rs.next()) {
				String content = rs.getString("VIEW_DEFINITION");
				ViewData td = new ViewData(this, content);
				td.setName(rs.getString(TABLE_NAME));
				result.add(td);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("������ͼ����" + getDatabaseName());
		}
	}
	
	/**
	 * ��������Ϊ�����е���ͼ
	 * @return
	 * @throws Exception
	 */
	private ResultSet getViewsResultSet() throws Exception {
		Statement stmt = getStatement();
		//��information_schema���ݿ��е�VIEWS���ѯ
		String sql = "SELECT * FROM information_schema.VIEWS sc WHERE " +
				"sc.TABLE_SCHEMA='" + this.databaseName + "'";
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public ServerConnection getServerConnection() {
		return serverConnection;
	}

	public void setServerConnection(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}

	
	public Icon getIcon() {
		if (this.connection == null) return ImageUtil.DATABASE_CLOSE;
		return ImageUtil.DATABASE_OPEN;
	}
	
	
	public String toString() {
		return this.databaseName;
	}
	
}
