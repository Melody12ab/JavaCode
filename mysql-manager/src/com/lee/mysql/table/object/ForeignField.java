package com.lee.mysql.table.object;

/**
 * ���е�һ�����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ForeignField {

	//Լ��������
	private String constraintName;
	
	//��Լ�����ֶΣ����ݸ��ֶο����ҳ���������������ڵı�
	private Field field;
	
	//������ֶΣ����Ը��ݴ������ҳ��ù�ϵ�е������
	private Field referenceField;
	
	//����ɾ������
	private String onDelete;
	
	//�������²���
	private String onUpdate;
	
	//Լ���������
	private String referenceTableName;
	
	//Լ���ֶε�����
	private String referenceFieldName;
	
	//�ֶε�uuid
	private String uuid;

	public ForeignField(String constraintName, Field field, Field referenceField) {
		this.field = field;
		this.referenceField = referenceField;
		this.constraintName = constraintName;
	}
	
	public ForeignField(String constraintName, Field field, 
			String referenceTableName, String referenceFieldName) {
		this.constraintName = constraintName;
		this.field = field;
		this.referenceTableName = referenceTableName;
		this.referenceFieldName = referenceFieldName;
	}
	
	public ForeignField() {
		
	}

	public String getReferenceFieldName() {
		return referenceFieldName;
	}

	public void setReferenceFieldName(String referenceFieldName) {
		this.referenceFieldName = referenceFieldName;
	}

	public String getReferenceTableName() {
		return referenceTableName;
	}

	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Field getReferenceField() {
		return referenceField;
	}

	public void setReferenceField(Field referenceField) {
		this.referenceField = referenceField;
	}

	public String getOnDelete() {
		return onDelete;
	}

	public void setOnDelete(String onDelete) {
		this.onDelete = onDelete;
	}

	public String getOnUpdate() {
		return onUpdate;
	}

	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
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
		ForeignField f = (ForeignField)obj;
		if (this.uuid.equals(f.getUuid())) {
			return true;
		}
		return false;
	}
	
}
