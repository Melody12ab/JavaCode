package com.lee.mysql.object.tree;

import java.util.List;

import javax.swing.Icon;

import com.lee.mysql.object.ViewObject;
import com.lee.mysql.object.list.TableData;
import com.lee.mysql.util.ImageUtil;


public class TableNode implements ViewObject {

	//���������ݿ�ڵ�
	private Database database;
	
	public TableNode(Database database) {
		this.database = database;
	}
	
	
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return ImageUtil.TABLE_TREE_ICON;
	}

	
	public String toString() {
		// TODO Auto-generated method stub
		return "��";
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}
