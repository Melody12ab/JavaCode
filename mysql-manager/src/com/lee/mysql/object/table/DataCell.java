package com.lee.mysql.object.table;

/**
 * ���ݱ���еĵ�Ԫ�����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DataCell {

	//�õ�Ԫ�����ڵ���
	private int row;
	
	//�õ�Ԫ�����ڵ���
	private DataColumn column;
	
	//�õ�Ԫ���ֵ
	private String value;
		
	public DataCell(int row, DataColumn column, String value) {
		this.row = row;
		this.column = column;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public DataColumn getColumn() {
		return column;
	}
	
	public void setColumn(DataColumn column) {
		this.column = column;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return this.value;
	}
	
	
}
