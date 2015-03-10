package com.lee.mysql.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import com.lee.mysql.object.list.TableData;
import com.lee.mysql.table.object.Field;
import com.lee.mysql.table.object.ForeignField;
import com.lee.mysql.table.object.UpdateField;
import com.lee.mysql.table.object.UpdateForeignField;
import com.lee.mysql.ui.table.FieldTable;
import com.lee.mysql.ui.table.ForeignTable;
import com.lee.mysql.util.ImageUtil;

/**
 * ����(�޸�)�����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("serial")
public class TableFrame extends CommonFrame {

	private Box mainBox = Box.createVerticalBox();
	
	//�ֶε��б�
	private JScrollPane fieldPane;
	private FieldTable fieldTable;
	private Box fieldBox = Box.createHorizontalBox();
	private JLabel defaultLabel = new JLabel("Ĭ��ֵ��");
	private JTextField defaultField = new JTextField(20);
	private JCheckBox isAutoIncrementBox = new JCheckBox("�Զ�����");
	private Box fieldButtonBox = Box.createHorizontalBox();
	private JButton newFieldButton = new JButton("���ֶ�");
	private JButton inserFieldButton = new JButton("�����ֶ�");
	private JButton deleteFieldButton = new JButton("ɾ���ֶ�");
	//����б�
	private JScrollPane foreignPane;
	private ForeignTable foreignTable;
	private Box foreignButtonBox = Box.createHorizontalBox();
	private JButton newForeignButton = new JButton("�����");
	private JButton deleteForeignButton = new JButton("ɾ�����");
	//������
	private JToolBar toolBar = new JToolBar();
	
	private Action save = new AbstractAction("����", new ImageIcon("images/save-table.gif")) {
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	private TableData table;
	
	//���浱ǰȫ���ֶεļ���
	private List<Field> fields = new ArrayList<Field>();;
	
	//��������ļ���
	private List<ForeignField> foreignFields = new ArrayList<ForeignField>();
	
	//���ݿ��������еı���
	private List<TableData> allTables;
	
	/*****����Ϊ�޸ı�����Ҫ�ļ���****/
	//���ݿ��е��ֶμ���
	private List<Field> sourceFields = new ArrayList<Field>();
	//���ݿ��е��������
	private List<ForeignField> sourceForeignFields = new ArrayList<ForeignField>();
	//��ɾ�����ֶμ���
	private List<Field> dropFields = new ArrayList<Field>();
	//��ӵ��ֶμ���
	private List<Field> addFields = new ArrayList<Field>();
	//��ӵ��������
	private List<ForeignField> addForeignFields = new ArrayList<ForeignField>();
	//��ɾ�����������
	private List<ForeignField> dropForeignFields = new ArrayList<ForeignField>();
	
	
	//���������JFrame
	private NameFrame nameFrame;
	
	private MainFrame mainFrame;
	
	public TableFrame(TableData table, MainFrame mainFrame) {
		this.nameFrame = new NameFrame(this);
		this.mainFrame = mainFrame;
		//�õ�ȫ���ı�
		this.allTables = table.getDatabase().getTables();
		this.table = table;
		//���table.getNameΪ�գ���Ϊ�½���
		if (table.getName() != null) {
			//�õ������ֶεļ���
			this.fields = table.readFields();
			//�õ�����ļ���
			this.foreignFields = table.getForeignFields(this.fields);
			//��ʼ������Դ���ݼ���
			this.sourceFields = table.readFields();
			this.sourceForeignFields = table.getForeignFields(this.sourceFields);
			setFieldUUID();
			setForeignFieldUUID();
		}
		this.toolBar.add(save).setToolTipText("����");
		this.toolBar.setFloatable(false);
		
		this.fieldBox.add(Box.createHorizontalStrut(120));
		this.fieldBox.add(this.defaultLabel);
		this.fieldBox.add(this.defaultField);
		this.fieldBox.add(Box.createHorizontalStrut(20));
		this.fieldBox.add(this.isAutoIncrementBox);
		this.fieldBox.add(Box.createHorizontalStrut(120));
		this.fieldButtonBox.add(this.newFieldButton);
		this.fieldButtonBox.add(Box.createHorizontalStrut(20));
		this.fieldButtonBox.add(this.inserFieldButton);
		this.fieldButtonBox.add(Box.createHorizontalStrut(20));
		this.fieldButtonBox.add(this.deleteFieldButton);
		this.foreignButtonBox.add(this.newForeignButton);
		this.foreignButtonBox.add(Box.createHorizontalStrut(20));
		this.foreignButtonBox.add(this.deleteForeignButton);
		
		this.fieldTable = createFieldTable();
		this.foreignTable = createForeignTable();
		this.fieldPane = new JScrollPane(fieldTable);
		this.fieldPane.setPreferredSize(new Dimension(750, 200)); 
		this.foreignPane = new JScrollPane(foreignTable);
		this.foreignPane.setPreferredSize(new Dimension(750, 200)); 
		
		this.mainBox.add(this.fieldPane);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(this.fieldBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(fieldButtonBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(this.foreignPane);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.mainBox.add(this.foreignButtonBox);
		this.mainBox.add(Box.createVerticalStrut(10));
		this.add(this.mainBox);
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setLocation(150, 50);
		this.setTitle("�±�");
		this.setResizable(false);
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//�ͷű����������ڴ�
				dispose();
			}
		});
		initListeners();
	}
	
	//���ý���Field���Ϻ�ʵ���ֶ����ݵ�uuidֵ
	private void setFieldUUID() {
		for (int i = 0; i < this.fields.size(); i++) {
			String uuid = UUID.randomUUID().toString();
			Field newField = this.fields.get(i);
			Field sourceField = this.sourceFields.get(i);
			newField.setUuid(uuid);
			sourceField.setUuid(uuid);
		}
	}
	
	//���ý���ForeignField���Ϻ�ʵ���ֶ����ݵ�uuidֵ
	private void setForeignFieldUUID() {
		for (int i = 0; i < this.foreignFields.size(); i++) {
			String uuid = UUID.randomUUID().toString();
			ForeignField newField = this.foreignFields.get(i);
			ForeignField sourceField = this.sourceForeignFields.get(i);
			newField.setUuid(uuid);
			sourceField.setUuid(uuid);
		}
	}
	
	//��ʼ��������
	private void initListeners() {
		this.newFieldButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				newField();
			}
		});
		this.newForeignButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				newForeignField();
			}
		});
		this.deleteForeignButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				deleteForeignField();
			}
		});
		this.inserFieldButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				insertField();
			}
		});
		this.deleteFieldButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				deleteField();
			}
		});
		this.defaultField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e) {
				changeDefaultValue();
			}
		});
		this.isAutoIncrementBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickIsAutoIncrementBox();
			}
		});
	}
	
	public List<TableData> getAllTables() {
		return this.allTables;
	}
	
	//ʵ�ָ����ȷ�Ϸ�������confirm������NameFrame����
	protected void confirm(String name) {
		add(name);
	}
	
	//����һ����
	private void save() {
		for (ForeignField ff : this.foreignFields) {
			if (ff.getField() == null || ff.getReferenceField() == null) {
				showMessage("��ɾ��û���õ�������ֶ�����", "����");
				return;
			}
		}
		//ֹͣ�б�༭
		if (this.fieldTable.getCellEditor() != null) {
			this.fieldTable.getCellEditor().stopCellEditing();
		}
		//�жϱ����е�TableData�Ƿ�������
		if (this.table.getName() == null)this.nameFrame.setVisible(true);
		else update();
	}
	
	//���һ����
	private void add(String name) {
		try {
			this.table.setName(name);
			this.table.addTable(this.fields, this.foreignFields);
			this.setVisible(false);
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			this.table.setName(null);
			showMessage(e.getMessage(), "����");
		}
		this.nameFrame.setVisible(false);
	}
	
	//�޸�һ����
	private void update() {
		try {
			//�����޸ĵ��ֶμ���
			List<UpdateField> updateFields = getUpdateFieldList();
			//�����޸ĵ��������
			List<UpdateForeignField> updateFF = getUpdateForeignFieldList();
			this.table.updateTable(this.addFields, updateFields, this.dropFields, 
					this.addForeignFields, updateFF, this.dropForeignFields);
			this.setVisible(false);
			this.mainFrame.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "����");
		}
	}
	
	//�ҳ��޸ĵ��ֶμ���
	private List<UpdateField> getUpdateFieldList() {
		//�ҳ����ݿ��е��ֶμ���������е��ֶμ��ϵĲ���
		List<UpdateField> fields = new ArrayList<UpdateField>();
		for (Field newField : this.fields) {
			for (Field sourceField : this.sourceFields) {
				if (newField.uuidEquals(sourceField)) {
					fields.add(new UpdateField(sourceField, newField));
				}
			}
		}
		return fields;
	}
	
	//�ҳ��޸ĵ��������
	private List<UpdateForeignField> getUpdateForeignFieldList() {
		List<UpdateForeignField> result = new ArrayList<UpdateForeignField>();
		for (ForeignField newField : this.foreignFields) {
			for (ForeignField sourceField : this.sourceForeignFields) {
				if (newField.uuidEquals(sourceField)) {
					result.add(new UpdateForeignField(sourceField, newField));
				}
			}
		}
		return result;
	}
	
	//�������ֶ�
	private void newField() {
		Field field = new Field();
		this.fields.add(field);
		refreshFieldTable();
		//������޸�״̬�������addFields������
		if (this.table.getName() != null) this.addFields.add(field);
	}
	
	//�������ֶ�
	private void insertField() {
		int selectRow = this.fieldTable.getSelectedRow();
		if (selectRow == -1) {
			//û��ѡ�У����ü����ֶη������������ֶ�
			newField();
			return;
		}
		Field field = new Field();
		this.fields.add(selectRow, field);
		refreshFieldTable();
		//������޸�״̬�������addFields������
		if (this.table.getName() != null) this.addFields.add(field);
	}
	
	//ɾ���ֶ�
	private void deleteField() {
		//�õ�ѡ�е���
		int selectRow = this.fieldTable.getSelectedRow();
		if (selectRow == -1) return;
		Field field = this.fields.get(selectRow);
		if (field == null) return;
		//���ֶμ�����ɾ��
		this.fields.remove(field);
		//ˢ���б�
		refreshFieldTable();
		//�ж��Ƿ�����ӵļ�����
		if (isInFields(field, this.addFields)) this.addFields.remove(field);
		//��ӵ�ɾ���ļ�����
		if (isInFields(field, this.sourceFields)) this.dropFields.add(field);
	}
	
	
	//�ж�һ���ֶ��Ƿ�����ڼ�����
	private boolean isInFields(Field field, List<Field> fields) {
		for (Field f : fields) {
			if (f.uuidEquals(field)) return true;
		}
		return false;
	}
	
	//����һ������ֶ�
	private void newForeignField() {
		ForeignField foreignField = new ForeignField();
		this.foreignFields.add(foreignField);
		//���ø������constraintName����UUID����
		foreignField.setConstraintName(UUID.randomUUID().toString());
		refreshForeignFieldTable();
		//������޸�״̬���ӵ���ӵ����������
		if (this.table.getName() != null) this.addForeignFields.add(foreignField);
	}
	
	//ɾ��һ���ֶ�
	private void deleteForeignField() {
		//�õ�ѡ�е���
		int selectRow = this.foreignTable.getSelectedRow();
		if (selectRow == -1) return;
		ForeignField field = this.foreignFields.get(selectRow);
		if (field == null) return;
		//���ֶμ�����ɾ��
		this.foreignFields.remove(field);
		refreshForeignFieldTable();
		//�ж��Ƿ�����ӵļ�����
		if (isInForeignFields(field, this.addForeignFields)) this.addForeignFields.remove(field);
		//�ж��Ƿ������ݿ�ļ�����
		if (isInForeignFields(field, this.sourceForeignFields)) this.dropForeignFields.add(field);
	}
	
	//�ж�һ����������Ƿ�����ڲ���������
	private boolean isInForeignFields(ForeignField foreignField, List<ForeignField> ffs) {
		for (ForeignField ff : ffs) {
			if (ff.uuidEquals(foreignField)) return true;
		}
		return false;
	}
	
	//����б�ı����ֶΣ���ForeignTable���ø÷���
	public void changeForeignField(int row, Field field) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setField(field);
	}
	
	//Լ���ֶη����˸ı�����������������������ֶμ��ϵ�ֵ����ForeignTable���ø÷���
	public void changeReferenceField(int row, Field field) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setReferenceField(field);
	}
	
	//��ON DELETE�����ı�ʱ�������÷���
	public void changeOnDelete(int row, String value) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setOnDelete(value);
	}
	
	//��ON DELETE�����ı�ʱ�������÷���
	public void changeOnUpdate(int row, String value) {
		ForeignField foreign = this.foreignFields.get(row);
		if (foreign == null) return;
		foreign.setOnUpdate(value);
	}
	
	//ˢ���ֶ��б�
	public void refreshFieldTable() {
		DefaultTableModel tableModel = (DefaultTableModel)this.fieldTable.getModel();
		//��������
		tableModel.setDataVector(getFieldDatas(), this.fieldTable.getFieldTableColumn());
		//�����б���ʽ
		this.fieldTable.setTableFace();
	}
	
	//ˢ������ֶ��б�
	public void refreshForeignFieldTable() {
		//�����������
		DefaultTableModel tableModel = (DefaultTableModel)this.foreignTable.getModel();
		tableModel.setDataVector(getForeignDatas(), this.foreignTable.getForeignColumns());
		//��������б����ʽ
		this.foreignTable.setTableFace();
	}
	
	//��������ֶ�����
	@SuppressWarnings("unchecked")
	public Vector getForeignDatas() {
		Vector datas = new Vector();
		for (int i = 0; i < this.foreignFields.size(); i++) {
			ForeignField foreignField = this.foreignFields.get(i);
			Vector data = new Vector();
			//�ֶ�����
			if (foreignField.getField() == null) data.add(null);
			else data.add(foreignField.getField().getFieldName());
			if (foreignField.getReferenceField() == null) {
				data.add(null);
				data.add(null);
			} else {
				data.add(foreignField.getReferenceField().getTable().getName());
				data.add(foreignField.getReferenceField().getFieldName());
			}
			data.add(foreignField.getOnDelete());
			data.add(foreignField.getOnUpdate());
			datas.add(data);
		}
		return datas;
	}
	
	//��������б�
	private ForeignTable createForeignTable() {
		DefaultTableModel model = new DefaultTableModel();
		ForeignTable table = new ForeignTable(model, this);
		return table;
	}
	
	//�����ֶε�����
	@SuppressWarnings("unchecked")
	public Vector getFieldDatas() {
		Vector datas = new Vector();
		for (int i = 0; i < this.fields.size(); i++) {
			Field field = this.fields.get(i);
			Vector data = new Vector();
			data.add(field.getFieldName());//�ֶ�����
			data.add(field.getType());//�ֶ�����
			data.add(getNullIcon(field));//����Ƿ�����յ�ͼƬ
			data.add(getPrimaryKeyIcon(field));//�������ͼƬ
			datas.add(data);
		}
		return datas;
	}
	
	//�÷�����FieldTable���ã��������ĳ����Ԫ��ʱ�������øõ�Ԫ�����ж�Ӧ���ֶ�Ĭ��ֵ
	public void setDefaultValue(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		this.defaultField.setText(field.getDefaultValue());
	}
	
	//�÷�����FieldTable���ã����ĳ��Fieldʱ�������ø��е��Զ�������CheckBox
	public void setIsAutoIncrement(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		if (field.isAutoIncrement()) this.isAutoIncrementBox.setSelected(true);
		else this.isAutoIncrementBox.setSelected(false);
	}
	
	//����Զ�����checkBox�ķ���
	private void clickIsAutoIncrementBox() {
		//�õ��ֶ��б�����ѡ�е�������
		int row = this.fieldTable.getSelectedRow();
		if (row == -1) return;
		//�õ���ǰ��ѡ����Field����
		Field field = this.fields.get(row);
		//����Field�����е��Զ���������
		if (this.isAutoIncrementBox.isSelected()) field.setAutoIncrement(true);
		else field.setAutoIncrement(false);
	}
	
	/**
	 * �ı��ֶε�Ĭ��ֵ
	 */
	public void changeDefaultValue() {
		//�õ�ѡ�е���
		int selectRow = this.fieldTable.getSelectedRow();
		if (selectRow == -1) return;
		//ȡ��Ĭ��ֵ
		String defaultValue = this.defaultField.getText();
		//ȡ�õ�ǰ�༭��Field����
		Field field = this.fields.get(selectRow);
		//�����ֶ�Ĭ��ֵ
		field.setDefaultValue(defaultValue);
	}

	/**
	 * �ı�������ֶε�ֵ���Ѿ��ı���ͼƬ��ʾ���÷����ı��Ӧ��Field�����ֵ��
	 * @param row
	 */
	public void changeAllowNullValue(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		if (field.isAllowNull()) field.setAllowNull(false);
		else field.setAllowNull(true);
	}
	
	/**
	 * �ֶ��б���ֶ����ƣ�ͬ��ȥ�޸��ֶμ����е�Ԫ��ֵ
	 * @param row
	 * @param value
	 */
	public void changeFieldName(int row, String value) {
		Field field = this.fields.get(row);
		if (field == null) return;
		field.setFieldName(value);
	}
	
	/**
	 * �ֶ��б���ֶ����ͣ�ͬ��ȥ�޸��ֶμ����е�ֵ
	 * @param row
	 * @param value
	 */
	public void changeFieldType(int row, String value) {
		Field field = this.fields.get(row);
		if (field == null) return;
		field.setType(value);
	}
	
	/**
	 * �ֶ��б���ֶγ��ȣ�ͬ��ȥ�޸��ֶμ����е�ֵ
	 * @param row
	 * @param value
	 */
//	public void changeFieldLength(int row, String value) {
//		Field field = this.fields.get(row);
//		if (field == null) return;
//		try {
//			if (value.trim().equals("")) value = "10";
//			int length = Integer.parseInt(value);
//			field.setLength(length);
//		} catch (Exception e) {
//			//����û�����ķ����֣���catch�쳣��ʾ
//			showMessage("�ֶγ�������������", "����");
//			return;
//		}
//	}
	
	public List<Field> getFields() {
		return this.fields;
	}
	
	/**
	 * �ı�������ֵ���ı�ͼƬ��ʾ���÷����ı��Ӧ��Field�����ֵ��
	 * @param row
	 */
	public void changePrimaryKeyValue(int row) {
		Field field = this.fields.get(row);
		if (field == null) return;
		if (field.isPrimaryKey()) field.setPrimaryKey(false);
		else field.setPrimaryKey(true);
	}

	//�жϲ����ֶ��Ƿ�Ϊ���������ز�ͬ��ͼƬ
	private Icon getPrimaryKeyIcon(Field field) {
		if (field.isPrimaryKey()) return ImageUtil.PRIMARY_KEY;
		return ImageUtil.PRIMARY_KEY_BLANK;
	}

	
	/*
	 * �ж��ֶ��Ƿ����Ϊ�գ����ز�ͬͼƬ
	 */
	private Icon getNullIcon(Field field) {
		if (field.isAllowNull()) return ImageUtil.CHECKED_ICON;
		return ImageUtil.UN_CHECKED_ICON;
	}
	
	//���������ֶεı��
	private FieldTable createFieldTable() {
		DefaultTableModel model = new DefaultTableModel();
		FieldTable table = new FieldTable(model, this);
		return table;
	}

	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
}
