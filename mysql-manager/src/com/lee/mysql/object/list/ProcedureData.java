package com.lee.mysql.object.list;

import javax.swing.Icon;

import com.lee.mysql.exception.QueryException;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.util.ImageUtil;
import com.lee.mysql.util.MySQLUtil;


public class ProcedureData extends AbstractData {

	private String type;
	
	private Database database;
	private String content;
	private String arg;
	private String returnString;
	
	public ProcedureData(Database database, String type, String content) {
		this.type = type;
		this.database = database;
		this.content = content;
	}
	
	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public String getReturnString() {
		return returnString;
	}

	public void setReturnString(String returnString) {
		this.returnString = returnString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public void createProcedure() {
		try {
			String sql = MySQLUtil.CREATE_PROCEDURE + this.name + 
			" (" + this.arg + ") " + this.content;
			this.database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("�����洢���̴���" + e.getMessage());
		}
	}
	

	public void updateProcedure() {
		try {
			String dropSQL = MySQLUtil.DROP_PROCEDURE + this.name;
			this.database.getStatement().execute(dropSQL);
			String createSQL = MySQLUtil.CREATE_PROCEDURE + this.name + 
			" (" + this.arg + ") " + this.content;
			this.database.getStatement().execute(createSQL);
		} catch (Exception e) {
			throw new QueryException("�޸Ĵ洢���̴���" + e.getMessage());
		}
	}
	

	public void drop() {
		try {
			String dropSQL = "";
			if (this.type.equals(MySQLUtil.PROCEDURE_TYPE)) {
				dropSQL = MySQLUtil.DROP_PROCEDURE + this.name;
			} else {
				dropSQL = MySQLUtil.DROP_FUNCTION + this.name;
			}
			this.database.getStatement().execute(dropSQL);
		} catch (Exception e) {
			throw new QueryException("�޸Ĵ洢���̴���" + this.name);
		}
	}
	

	public void createFunction() {
		try {
			String sql = MySQLUtil.CREATE_FUNCTION + this.name + 
			" (" + this.arg + ") returns " + this.returnString + " " + this.content;
			this.database.getStatement().execute(sql);
		} catch (Exception e) {
			throw new QueryException("������������" + e.getMessage());
		}
	}
	

	public void updateFunction() {
		try {
			String dropSQL = MySQLUtil.DROP_FUNCTION + this.name;
			this.database.getStatement().execute(dropSQL);
			String createSQL = MySQLUtil.CREATE_FUNCTION + this.name + 
			" (" + this.arg + ") returns " + this.returnString + " \n" + this.content;
			this.database.getStatement().execute(createSQL);
		} catch (Exception e) {
			throw new QueryException("�޸ĺ�������" + e.getMessage());
		}
	}

	public Icon getIcon() {
		if (MySQLUtil.FUNCTION_TYPE.equals(this.type)) return ImageUtil.FUNCTION_DATA_ICON;
		return ImageUtil.PROCEDURE_DATA_ICON;
	}

	public String toString() {
		return this.name;
	}

}
