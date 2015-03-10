package com.lee.mysql.object.tree;

import javax.swing.Icon;

import com.lee.mysql.object.ViewObject;
import com.lee.mysql.util.ImageUtil;


/**
 * �洢���̽ڵ�
 * @author yangenxiong
 *
 */
public class ProcedureNode implements ViewObject {

	private Database database;
	
	public ProcedureNode(Database database) {
		this.database = database;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	
	public Icon getIcon() {
		return ImageUtil.PROCEDURE_TREE_ICON;
	}

	
	public String toString() {
		return "�洢����";
	}
	
	
}
