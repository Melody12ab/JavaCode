package com.lee.mysql.table.object;

/**
 * �ڲ����б��޸ĵ��������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UpdateForeignField {

	//�������µ����
	private ForeignField newForeignField;
	
	//���ݿ��оɵ����
	private ForeignField sourceForeignField;

	public UpdateForeignField(ForeignField sourceForeignField, 
			ForeignField newForeignField) {
		this.newForeignField = newForeignField;
		this.sourceForeignField = sourceForeignField;
	}

	public ForeignField getNewForeignField() {
		return newForeignField;
	}

	public void setNewForeignField(ForeignField newForeignField) {
		this.newForeignField = newForeignField;
	}

	public ForeignField getSourceForeignField() {
		return sourceForeignField;
	}

	public void setSourceForeignField(ForeignField sourceForeignField) {
		this.sourceForeignField = sourceForeignField;
	}
	
	
}
