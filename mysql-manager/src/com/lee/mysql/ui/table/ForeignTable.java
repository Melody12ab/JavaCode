package com.lee.mysql.ui.table;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.lee.mysql.object.list.TableData;
import com.lee.mysql.object.table.ForeignItem;
import com.lee.mysql.table.object.Field;
import com.lee.mysql.ui.TableFrame;

/**
 * ����б����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ForeignTable extends JTable {
	
	public final static String FIELD_NAME = "�ֶ�����";
	public final static String REFERENCE_TABLE = "Լ����";
	public final static String REFERENCE_FIELD = "Լ���ֶ�";
	public final static String ON_DELETE = "����ɾ��";
	public final static String ON_UPDATE = "��������";

	//�����ĸ���ֵ
	private ForeignItem RESTRICT = new ForeignItem("RESTRICT", "RESTRICT");
	private ForeignItem CASCADE = new ForeignItem("CASCADE", "CASCADE");
	private ForeignItem SETNULL  = new ForeignItem("SET NULL", "SET NULL");
	private ForeignItem NOACTION = new ForeignItem("NO ACTION", "NO ACTION");
	
	//����ɾ��������
	private JComboBox foreignDelete = new JComboBox();
	//�������µ�����
	private JComboBox foreignUpdate = new JComboBox();
	
	private TableFrame tableFrame;
	
	//�ֶ����Ʊ༭������
	private DefaultCellEditor fieldNameEditor;
	private JComboBox fieldNameComboBox = new JComboBox();
	
	//Լ����
	private DefaultCellEditor referenceTableEditor;
	private JComboBox referenceTableComboBox = new JComboBox();
	
	//Լ���ֶ�
	private DefaultCellEditor referenceFieldEditor;
	private JComboBox referenceFieldComboBox = new JComboBox();
	
	//����ɾ��
	private DefaultCellEditor onDeleteEditor;
	
	//��������
	private DefaultCellEditor onUpdateEditor;
	
	
	public ForeignTable(DefaultTableModel model, TableFrame tableFrame) {
		super(model);
		this.tableFrame = tableFrame;
		model.setDataVector(tableFrame.getForeignDatas(), getForeignColumns());
		setTableFace();
		initListeners();
	}
	
	private void initComboBox() {
		createFieldNameComboBox();
		createReferenceTableComboBox();
		createReferenceFieldComboBox();
		createOnDeleteComboBox();
		createOnUpdateComboBox();
	}

	//��ʼ��������
	private void initListeners() {
		this.fieldNameComboBox.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				createFieldNameComboBox();
			}			
		});
		this.fieldNameComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeField();
				}
			}
		});
		this.referenceTableComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					createReferenceFieldComboBox();
				}
			}
		});
		this.referenceFieldComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeReferenceField();
				}
			}
		});
		this.foreignDelete.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeOnDelete();
				}
			}
		});
		this.foreignUpdate.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changeOnUpdate();
				}
			}
		});
	}
	
	//�����б���ʽ
	public void setTableFace() {
		this.setRowHeight(25);
		//��ʼ��Լ�����Լ���ֶε�����ֵ
		initComboBox();
		//������ʾ����
		this.fieldNameEditor = new DefaultCellEditor(this.fieldNameComboBox);
		this.referenceTableEditor = new DefaultCellEditor(this.referenceTableComboBox);
		this.referenceFieldEditor = new DefaultCellEditor(this.referenceFieldComboBox);
		this.onDeleteEditor = new DefaultCellEditor(this.foreignDelete);
		this.onUpdateEditor = new DefaultCellEditor(this.foreignUpdate);
		this.getColumn(FIELD_NAME).setCellEditor(this.fieldNameEditor);
		this.getColumn(REFERENCE_TABLE).setCellEditor(this.referenceTableEditor);
		this.getColumn(REFERENCE_FIELD).setCellEditor(this.referenceFieldEditor);
		this.getColumn(ON_DELETE).setCellEditor(this.onDeleteEditor);
		this.getColumn(ON_UPDATE).setCellEditor(this.onUpdateEditor);
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//�����д�С
		this.getColumn(ON_DELETE).setMinWidth(100);
		this.getColumn(ON_UPDATE).setMinWidth(100);
		this.getColumn(ON_DELETE).setMaxWidth(150);
		this.getColumn(ON_UPDATE).setMaxWidth(150);
	}
	
	//�����ֶ���������
	private void createFieldNameComboBox() {
		this.fieldNameComboBox.removeAllItems();
		//ʹ���ֶ��б����Ѿ����ڵ��ֶδ���������
		List<Field> fields = this.tableFrame.getFields();
		for (Field f : fields) this.fieldNameComboBox.addItem(f);
	}
	
	//��������ɾ��������
	private void createOnDeleteComboBox() {
		this.foreignDelete.removeAllItems();
		this.foreignDelete.addItem(CASCADE);
		this.foreignDelete.addItem(SETNULL);
		this.foreignDelete.addItem(RESTRICT);
		this.foreignDelete.addItem(NOACTION);
	}
	
	//�����������µ�����
	private void createOnUpdateComboBox() {
		this.foreignUpdate.removeAllItems();
		this.foreignUpdate.addItem(CASCADE);
		this.foreignUpdate.addItem(SETNULL);
		this.foreignUpdate.addItem(RESTRICT);
		this.foreignUpdate.addItem(NOACTION);
	}
	
	//����Լ��������
	private void createReferenceTableComboBox() {
		this.referenceTableComboBox.removeAllItems();
		//ʹ��ĳ�����ݿ��������еı�������������
		List<TableData> tables = this.tableFrame.getAllTables();
		for (TableData table : tables) {
			this.referenceTableComboBox.addItem(table);
		}
	}

	//����Լ���ֶ���������Լ��������������ı�ʱ�����÷���
	private void createReferenceFieldComboBox() {
		TableData data = (TableData)this.referenceTableComboBox.getSelectedItem();
		if (data == null) return;
		this.referenceFieldComboBox.removeAllItems();
		List<Field> fields = data.readFields();
		for (Field f : fields) {
			this.referenceFieldComboBox.addItem(f);
		}
	}
	
	//�����ֶη����ı�ʱ�������÷���
	private void changeField() {
		Field field = (Field)this.fieldNameComboBox.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return;
		this.tableFrame.changeForeignField(row, field);
	}
	
	//��Լ���ֶη����ı�ʱ�������÷���
	private void changeReferenceField() {
		Field field = (Field)this.referenceFieldComboBox.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return;
		this.tableFrame.changeReferenceField(row, field);
	}
	
	//��ON DELETE�����ı�ʱ�������÷���
	private void changeOnDelete() {
		ForeignItem item = (ForeignItem)this.foreignDelete.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return; 
		this.tableFrame.changeOnDelete(row, item.getValue());
	}
	
	//��ON UPDATE�����ı�ʱ�������÷���
	private void changeOnUpdate() {
		ForeignItem item = (ForeignItem)this.foreignUpdate.getSelectedItem();
		int row = this.getSelectedRow();
		if (row == -1) return; 
		this.tableFrame.changeOnUpdate(row, item.getValue());
	}
		
	//���������������
	@SuppressWarnings("unchecked")
	public Vector getForeignColumns() {
		Vector cols = new Vector();
		cols.add(FIELD_NAME);
		cols.add(REFERENCE_TABLE);
		cols.add(REFERENCE_FIELD);
		cols.add(ON_DELETE);
		cols.add(ON_UPDATE);
		return cols;
	}
}

