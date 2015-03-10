package com.lee.mysql.table.object;

import com.lee.mysql.object.list.TableData;

/**
 * ���е�һ���ֶ�
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Field {

	//�ֶ���
	private String fieldName;
	
	//�ֶ�����
	private String type;
	
	//����գ�Ĭ������Ϊ��
	private boolean allowNull = true;
	
	//�Ƿ�������������Ϊtrue������Ϊfalse
	private boolean isPrimaryKey = false;
	
	//Ĭ��ֵ
	private String defaultValue;
	
	//�Ƿ��Զ�����
	private boolean autoIncrement = false;
	
	//���ֶ������ı�
	private TableData table;
	
	//��ʶ����ֶε�uuid
	private String uuid;
	
	public Field(String fieldName, String type, boolean allowNull,
			boolean isPrimaryKey, String defaultValue, boolean autoIncrement) {
		this.fieldName = fieldName;
		this.type = type;
		this.allowNull = allowNull;
		this.isPrimaryKey = isPrimaryKey;
		this.defaultValue = defaultValue;
		this.autoIncrement = autoIncrement;
	}
	
	public Field(String fieldName, TableData table) {
		this.fieldName = fieldName;
		this.table = table;
	}
	
	public Field() {
		
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public TableData getTable() {
		return table;
	}

	public void setTable(TableData table) {
		this.table = table;
	}

	
	public String toString() {
		return this.fieldName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public boolean uuidEquals(Object obj) {
		if (this.uuid == null) {
			return equals(obj);
		}
		Field f = (Field)obj;
		if (this.uuid.equals(f.getUuid())) {
			return true;
		}
		return false;
	}
	
}
