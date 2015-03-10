package com.lee.mysql.object.list;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import com.lee.mysql.exception.QueryException;
import com.lee.mysql.object.QueryObject;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.table.object.Field;
import com.lee.mysql.table.object.ForeignField;
import com.lee.mysql.table.object.UpdateField;
import com.lee.mysql.table.object.UpdateForeignField;
import com.lee.mysql.util.ImageUtil;

/**
 * �б��еı����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TableData extends AbstractData implements QueryObject {

	//����������
	private final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
	
	//FOREIGN KEY�ַ���
	private final static String FOREIGN_KEY = "FOREIGN KEY";
	
	private final static String CONSTRAINT = "CONSTRAINT";
	
	//REFERENCES�ַ���
	private final static String REFERENCES = "REFERENCES";
	
	//PRIMARY KEY�ַ���
	private final static String PRIMARY_KEY = "PRIMARY KEY";
	
	//DEFAULT�ַ���
	private final static String DEFAULT = "DEFAULT";
	
	//AUTO_INCREMENT�ַ���
	private final static String AUTO_INCREMENT = "AUTO_INCREMENT";
	
	//NOT NULL�ַ���
	private final static String NOT_NULL = "NOT NULL";
	
	public final static String ON_DELETE = "ON DELETE";
	
	public final static String ON_UPDATE = "ON UPDATE";
	
	//�ñ����������ݿ�
	private Database database;
		
	public TableData(Database database) {
		this.database = database;
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
	/**
	 * ��ñ��е�����
	 * @return
	 */
	public ResultSet getDatas(String orderString) {
		try {
			Statement stmt = database.getStatement();
			String sql = getQuerySQL(orderString);
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			throw new QueryException("��ѯ�������쳣��" + this.name);
		}
	}

	/**
	 * ���ظñ��������
	 * @return
	 */
	public int getDataCount() {
		try {
			Statement stmt = database.getStatement();
			//�õ�ȫ����¼����SQL
			String sql = "SELECT COUNT(*) FROM " + this.name;
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int result = rs.getInt(1);
			rs.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("��ѯ�������쳣��" + this.name);
		}
	}
	
	public String getQueryName() {
		return this.name;
	}

	//���ر��ѯ���ݵ�SQL
	public String getQuerySQL(String orderString) {
		StringBuffer sql = new StringBuffer("SELECT * FROM " + this.name);
		if (orderString == null || orderString.trim().equals("")) {
			return sql.toString();
		} else {
			sql.append(" ORDER BY " + orderString);
			return sql.toString();
		}
	}
	
	/**
	 * ���ظñ�������ֶ�
	 * @return
	 */
	public List<Field> readFields() {
		try {
			List<Field> result = new ArrayList<Field>();
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(getFieldSQL());
			while (rs.next()) {
				//�õ��ֶεĸ�������
				String fieldName = rs.getString("COLUMN_NAME");
				String type = rs.getString("COLUMN_TYPE");
				boolean allowNull = rs.getBoolean("IS_NULLABLE");
				boolean isPrimaryKey = isPrimaryKey(rs.getString("COLUMN_KEY"));
				String defaultValue = rs.getString("COLUMN_DEFAULT");
				boolean autoIncrement = isAutoIncrement(rs.getString("EXTRA"));
				//����Field����
				Field field = new Field(fieldName, type, allowNull, 
						isPrimaryKey, defaultValue, autoIncrement);
				//�����ֶ������ı�
				field.setTable(this);
				result.add(field);
			}
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("��ѯ���ֶ��쳣��" + e.getMessage());
		}
	}
	
	/**
	 * ɾ��һ�������Ӧ�ı�
	 */
	public void dropTable() {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("DROP TABLE IF EXISTS " + this.name);
			Statement stmt = database.getStatement();
			stmt.execute(sql.toString());
		} catch (Exception e) {
			throw new QueryException("ɾ�����쳣��" + e.getMessage());
		}
	}
	
	/**
	 * ���ظñ����������ֶ�
	 * @return
	 */
	public List<ForeignField> getForeignFields(List<Field> fields) {
		try {
			StringBuffer sql = new StringBuffer();
			//ȥϵͳ��KEY_COLUMN_USAGE��
			sql.append("SELECT * FROM information_schema.KEY_COLUMN_USAGE sc")
			.append(" WHERE sc.TABLE_SCHEMA='" + this.database.getDatabaseName() + "'")
			.append(" AND sc.TABLE_NAME='" + this.name + "'")
			.append(" AND sc.REFERENCED_COLUMN_NAME <> '' ORDER BY sc.COLUMN_NAME");
			System.out.println(sql.toString());
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			List<ForeignField> result = new ArrayList<ForeignField>();
			while (rs.next()) {
				//�õ�Field���󣬴�ResultSet�еõ�COLUMN_NAME����ȥ����������ֶ�����
				Field field = getForeignField(fields, rs.getString("COLUMN_NAME"));
				//�õ�Լ�������ƣ�������Ψһ
				String constraintName = rs.getString("CONSTRAINT_NAME");
				String referenceTableName = rs.getString("REFERENCED_TABLE_NAME");
				String referenceFieldName = rs.getString("REFERENCED_COLUMN_NAME");
				result.add(new ForeignField(constraintName, field, 
						referenceTableName, referenceFieldName));
			}
			rs.close();
			//������ÿ��ForeignField��Լ���ֶ�
			setReferenceField(result);
			//����ON DELETE��ON UPDATE��ֵ
			setOnValue(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("��ѯ���ֶ��쳣��" + e.getMessage());
		}
	}
	
	/**
	 * ���ü����е�����ֶε�ON DELETE��ON UPDATE��ֵ
	 * @param foreignFields
	 */
	private void setOnValue(List<ForeignField> foreignFields) {
		//�õ��������SQL���壬�ٶ�����ַ������з����������õ�ֵ
		String createTableSQL = getCreateSQL();
		for (ForeignField field : foreignFields) {
			String onDelete = getOnDeleteValue(createTableSQL, field);
			String onUpdate = getOnUpdateValue(createTableSQL, field);
			field.setOnDelete(onDelete);
			field.setOnUpdate(onUpdate);
		}
	}
	
	/**
	 * �����ֶ����Ʋ����ֶ�
	 * @param name
	 * @return
	 */
	public Field getFieldByName(String columnName) {
		try {
			//ȥϵͳ��COLUMNS�в���
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM information_schema.COLUMNS sc")
			.append(" WHERE sc.TABLE_NAME='" + this.name + "'")
			.append(" AND sc.COLUMN_NAME='" + columnName + "'");
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			//����Ҳ������ֶΣ��򷵻�null
			Field field = null;
			if (rs.next()) field = new Field(columnName, this);
			rs.close();
			return field;
		} catch (Exception e) {
			throw new QueryException("��ѯ�ֶ��쳣��" + e.getMessage());
		}
	}
	
	/**
	 * ����ForeignField�����Լ���ֶ�
	 * @param foreignFields
	 */
	private void setReferenceField(List<ForeignField> foreignFields) {
		for(ForeignField f : foreignFields) {
			//�ҵ�Լ���ı�
			TableData referenceTable = this.database.getTableByName(f.getReferenceTableName());
			//�ҵ���Ӧ�ĸñ���Լ�����ֶ�
			if (referenceTable != null) {
				Field referenceField = referenceTable.getFieldByName(f.getReferenceFieldName());
				f.setReferenceField(referenceField);
			}
		}
	}
	
	/**
	 * �ӱ����е��ֶ��в�������ΪcolumnName���ֶ�
	 * @param fields
	 * @param columnName
	 * @return
	 */
	private Field getForeignField(List<Field> fields, String columnName) {
		for (Field f : fields) {
			if (f.getFieldName().equals(columnName)) return f;
		}
		return null;
	}
	
	/**
	 * ���ش����ñ��SQL
	 * @return
	 */
	private String getCreateSQL() {
		try {
			String sql = "SHOW CREATE TABLE " + this.name;
			Statement stmt = database.getStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String result = "";
			while (rs.next()) result = rs.getString("Create Table");
			rs.close();
			return result;
		} catch (Exception e) {
			throw new QueryException("��ȡ�������SQL����" + e.getMessage());
		}
	}
	
	/**
	 * ����ON DELETE����ON UPDATE��ֵ
	 * @param createSQL
	 * @param foreignField
	 * @param on
	 * @return
	 */
	private String getOnValue(String createSQL, ForeignField foreignField, 
			String on) {
		String constraintName = foreignField.getConstraintName();
		//�Զ��Ž���ָ�
		String[] temp = createSQL.split(",");
		for (int i = 0; i < temp.length; i++) {
			String tempString = temp[i];
			//�������������ַ���������д���
			if (tempString.indexOf("CONSTRAINT `" + constraintName + "`") != -1) {
				//�������ON DELETE����ON UPDATE������д�������ON DELETE����ON UPDATE��ֵ
				if (tempString.indexOf(on) != -1) {
					//�õ�ON DELETE����ON UPDAT��λ��
					int onIndex = tempString.indexOf(on) + on.length() + 1;
					String value = tempString.substring(onIndex, onIndex + 7);
					if (value.indexOf("NO ACTI") != -1) return "NO ACTION";
					else if (value.indexOf("RESTRIC") != -1)return "RESTRICT";
					else if (value.indexOf("CASCADE") != -1) return "CASCADE";
					else if (value.indexOf("SET NUL") != -1)return "SET NULL";
				}
			}
		}
		return null;
	}
	
	/*
	 * �õ������ON UPDATEֵ��������MySQL5.0
	 */
	private String getOnUpdateValue(String createSQL, ForeignField foreignField) {
		return getOnValue(createSQL, foreignField, ON_UPDATE);
	}
	
	/*
	 * �õ������ON DELETEֵ��������MySQL5.0
	 */
	private String getOnDeleteValue(String createSQL, ForeignField foreignField) {
		return getOnValue(createSQL, foreignField, ON_DELETE);
	}
	
	/*
	 * �ж�һ���ֶ�ֵ�Ƿ�Ϊ����
	 */
	private boolean isPrimaryKey(String value) {
		if ("PRI".equals(value)) return true;
		return false;
	}
	
	/*
	 * �ж�һ���ֶ�ֵ�Ƿ�Ϊ�Զ�����
	 */
	private boolean isAutoIncrement(String value) {
		if ("auto_increment".equals(value)) return true;
		return false;
	}
	
	/*
	 * ���ز�ѯ�����ֶε�SQL���
	 */
	private String getFieldSQL() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM information_schema.COLUMNS sc")
		.append(" WHERE sc.TABLE_SCHEMA='")
		.append(this.database.getDatabaseName() + "' ")
		.append(" AND sc.TABLE_NAME='")
		.append(this.name + "' ")
		.append("ORDER BY sc.ORDINAL_POSITION");
		return sql.toString();
	}
	
	public Icon getIcon() {
		return ImageUtil.TABLE_DATA_ICON;
	}

	public String toString() {
		return this.name;
	}
	
	/**
	 * ����һ����
	 * @param fields ��������ֶ�
	 * @param foreignFields ����ֶ�
	 */
	public void addTable(List<Field> fields, List<ForeignField> foreignFields) {
		try {
			String createSQL = getTableSQL(this.getName(), fields, 
					foreignFields, CREATE_TABLE);
			System.out.println(createSQL);
			Statement stmt = database.getStatement();
			stmt.execute(createSQL);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("�½������" + e.getMessage());
		}
	}
	
	/**
	 * �޸�һ����
	 * @param addFields ��Ҫ��ӵ��ֶ�
	 * @param updateFields �޸ĵ��ֶ�
	 * @param dropFields ɾ�����ֶ�
	 * @param addFF ��ӵ����
	 * @param updateFF �޸ĵ����
	 * @param dropFF ɾ�������
	 */
	public void updateTable(List<Field> addFields, List<UpdateField> updateFields, 
			List<Field> dropFields, List<ForeignField> addFF, 
			List<UpdateForeignField> updateFF, List<ForeignField> dropFF) {
		//�õ�����ֶε�SQL
		List<String> addFieldSQL = getAlterAddFieldSQL(addFields);
		//�õ��޸��ֶε�SQL
		List<String> updateFieldSQL = getAlterUpdateFieldSQL(updateFields);
		//�õ�ɾ���ֶε�SQL
		List<String> dropFieldSQL = getAlterDropFieldSQL(dropFields);
		//�õ���������SQL
		List<String> addFFSQL = getAlterAddForeignFieldSQL(addFF);
		//�õ��޸������SQL
		List<String> updateFFSQL = getAlterUpdateForeignFieldSQL(updateFF);
		//�õ�ɾ�������SQL
		List<String> dropFFSQL = getAlterDropForeignFieldSQL(dropFF);
		try {
			Statement stmt = database.getStatement();
			for (String s : addFieldSQL) stmt.addBatch(s);
			for (String s : updateFieldSQL) stmt.addBatch(s);
			for (String s : dropFieldSQL) stmt.addBatch(s);
			for (String s : addFFSQL) stmt.addBatch(s);
			for (String s : updateFFSQL) stmt.addBatch(s);
			for (String s : dropFFSQL) stmt.addBatch(s);
			stmt.executeBatch();
		} catch (Exception e) {
			throw new QueryException("���ı����" + e.getMessage());
		}
	}
	
	//����ȫ��ɾ�������SQL���
	private List<String> getAlterDropForeignFieldSQL(List<ForeignField> dropFF) {
		List<String> result = new ArrayList<String>();
		for (ForeignField ff : dropFF) {
			String sql = getDropForeignKeySQL(this.name, ff);
			result.add(sql);
		}
		return result;
	}
	
	//����ɾ�������SQL
	private String getDropForeignKeySQL(String tableName, ForeignField ff) {
		StringBuffer sql = new StringBuffer();
		sql.append("ALTER TABLE " + tableName)
		.append(" DROP FOREIGN KEY " + ff.getConstraintName());
		return sql.toString();
	}
	
	//����ȫ���޸������SQL��䣬�޸������Ҫ��ɾ���ٴ���
	private List<String> getAlterUpdateForeignFieldSQL(List<UpdateForeignField> updateFF) {
		List<String> result = new ArrayList<String>();
		for (UpdateForeignField uff : updateFF) {
			ForeignField source = uff.getSourceForeignField();
			ForeignField newField = uff.getNewForeignField();
			//���ɾ�������SQL
			String dropSQL = getDropForeignKeySQL(this.name, source);
			//��ô��������SQL
			String newSQL = getAddForeignKeySQL(newField, this.name);
			result.add(dropSQL);
			result.add(newSQL);
		}
		return result;
	}
	
	//����ȫ����������ALTER TABLE ADD FOIEIGN KEY���
	private List<String> getAlterAddForeignFieldSQL(List<ForeignField> addFF) {
		//��Ϊ����Լ���ֶμ�������
		List<String> result = getAddReferenceFieldIndex(addFF);
		for (ForeignField ff : addFF) {
			String sql = getAddForeignKeySQL(ff, this.name);
			result.add(sql.toString());
		}
		return result;
	}
	
	//���ش������Լ����SQL���
	private String getAddForeignKeySQL(ForeignField ff, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("ALTER TABLE " + tableName)
		.append(" ADD FOREIGN KEY")
		.append(" (`" + ff.getField().getFieldName() + "`)")
		.append(" REFERENCES `" + ff.getReferenceField().getTable().getName() + "`")
		.append(" (`" + ff.getReferenceField().getFieldName() + "`)");
		if (ff.getOnDelete() != null) sql.append(" ON DELETE " + ff.getOnDelete());
		if (ff.getOnUpdate() != null) sql.append(" ON UPDATE " + ff.getOnUpdate());
		return sql.toString();
	}
	
	//ΪԼ���ֶμ�����
	private List<String> getAddReferenceFieldIndex(List<ForeignField> addFF) {
		List<String> result = new ArrayList<String>();
		for (ForeignField ff : addFF) {
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + ff.getReferenceField().getTable().getName())
			.append(" ADD INDEX")
			.append(" (`" + ff.getReferenceField().getFieldName() + "`)");
			result.add(sql.toString());
		}
		return result;
	}
	
	//����ȫ��ɾ���ֶε�SQL���
	private List<String> getAlterDropFieldSQL(List<Field> dropFields) {
		List<String> result = new ArrayList<String>();
		for (Field f : dropFields) {
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + this.name + " DROP COLUMN")
			.append(" " + f.getFieldName());
			result.add(sql.toString());
		}
		return result;
	}
	
	//����ȫ����ALTER TABLE CHANGE�ֶ����
	private List<String> getAlterUpdateFieldSQL(List<UpdateField> updateFields) {
		List<String> result = new ArrayList<String>();
		for (UpdateField f : updateFields) {
			//�õ����ݿ��е��ֶ�
			Field source = f.getSourceField();
			//�õ��޸ĺ���ֶ�
			Field newField = f.getNewField();
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + this.name + " CHANGE")
			.append(" " + source.getFieldName() + " " + newField.getFieldName())
			.append(" " + newField.getType());
			if (!newField.isAllowNull()) sql.append(" NOT NULL");
			if (newField.getDefaultValue() != null) {
				sql.append(" DEFAULT '" + newField.getDefaultValue() + "'");
			}
			if (newField.isAutoIncrement()) sql.append(" AUTO_INCREMENT");
			if (!source.isPrimaryKey() && newField.isPrimaryKey()) {
				sql.append(", ADD PRIMARY KEY (" + newField.getFieldName() + ")");
			}
			result.add(sql.toString());
		}
		return result;
	}
	
	//����ȫ����ALTER TABLE ADD�ֶ����
	private List<String> getAlterAddFieldSQL(List<Field> addFields) {
		List<String> result = new ArrayList<String>();
		for (Field f : addFields) {
			StringBuffer sql = new StringBuffer();
			sql.append("ALTER TABLE " + this.name)
			.append(" ADD " + f.getFieldName())
			.append(" " + f.getType());
			if (!f.isAllowNull()) sql.append(" NOT NULL");
			if (f.getDefaultValue() != null) {
				sql.append(" DEFAULT '" + f.getDefaultValue() + "'");
			}
			if (f.isAutoIncrement()) sql.append(" AUTO_INCREMENT");
			if (f.isPrimaryKey()) {
				sql.append(", ADD PRIMARY KEY (" + f.getFieldName() + ")");
			}
			result.add(sql.toString());
		}
		return result;
	}
	
	/**
	 * ��ȡ����һ�����SQL���
	 * @param table
	 * @param fields
	 * @param foreignFields
	 * @param command ���������޸ģ�create table or alter table��
	 * @return
	 */
	private String getTableSQL(String tableName, 
			List<Field> fields, List<ForeignField> foreignFields, String command) {
		StringBuffer sql = new StringBuffer(command + "`");
		sql.append(tableName + "` (");
		createField(sql, fields);
		createForeignFields(sql, foreignFields);
		createPrimary(sql, fields);
		sql.append(")");
		return cutLastComma(sql.toString()) + ")";
	}
	
	//ȥ�����Ķ���
	private String cutLastComma(String sql) {
		if (sql.lastIndexOf(",") == -1) return sql;
		int last = sql.lastIndexOf(",");
		return sql.substring(0, last);
	}
	
	//��������SQL
	private void createPrimary(StringBuffer sql, List<Field> fields) {
		for (Field f : fields) {
			if (f.isPrimaryKey()) {
				sql.append(PRIMARY_KEY)
				.append("(`" + f.getFieldName() + "`)")
				.append(",");
			}
		}
	}
	
	//�������SQL
	private void createForeignFields(StringBuffer sql, 
			List<ForeignField> foreignFields) {
		for (ForeignField f : foreignFields) {
			sql.append(FOREIGN_KEY)
			.append(" (`" + f.getField().getFieldName() + "`) ")
			.append(REFERENCES)
			.append(" `" + f.getReferenceField().getTable().getName() + "` ")
			.append("(`" + f.getReferenceField().getFieldName() + "`) ");
			if (f.getOnDelete() != null) {
				sql.append(ON_DELETE + " " + f.getOnDelete() + " ");
			}
			if (f.getOnUpdate() != null) {
				sql.append(ON_UPDATE + " " + f.getOnUpdate() + " ");
			}
			sql.append(",");
		}
	}
	
	//�����ֶδ���SQL
	private void createField(StringBuffer sql, List<Field> fields) {
		for (Field f : fields) {
			sql.append("`" + f.getFieldName() + "` ")
			.append(f.getType());
			//����������AUTO_INCREMENT
			if (f.isAutoIncrement()) sql.append(AUTO_INCREMENT + " ");
			//���ֶβ�����Ϊ��, ����NOT NULL
			if (!f.isAllowNull()) sql.append(NOT_NULL + " ");
			//���ֶ���Ĭ��ֵ,���Ҳ����Զ�����
			if (!f.isAutoIncrement()) {
				if (f.getDefaultValue() != null) {
					sql.append(DEFAULT + " '" + f.getDefaultValue() + "' ");
				}
			}
			sql.append(",");
		}
	}
}
