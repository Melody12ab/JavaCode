package com.lee.mysql.ui.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.lee.mysql.ui.TableFrame;
import com.lee.mysql.util.ImageUtil;

/**
 * �ֶ��б��JTable
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FieldTable extends JTable {

	public final static String FIELD_NAME = "�ֶ���";
	public final static String FIELD_TYPE = "����";
	public final static String ALLOW_NULL = "�����";
	public final static String PRIMARY_KEY = "����";
	
	private TableFrame tableFrame;
	
	private FieldTableIconCellRenderer cellRenderer;
	
	public FieldTable(DefaultTableModel model, TableFrame tableFrame) {
		super(model);
		this.tableFrame = tableFrame;
		this.cellRenderer = new FieldTableIconCellRenderer();
		model.setDataVector(tableFrame.getFieldDatas(), getFieldTableColumn());
		setTableFace();
		//���õ��
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				selectCell();
			}
		});
	}
	
	public void setTableFace() {
		//�����и�
		this.setRowHeight(25);
		//����ѡ��һ����Ԫ��
		this.setColumnSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//���ø��еĿ��
		this.getColumn(PRIMARY_KEY).setMaxWidth(50);
		this.getColumn(ALLOW_NULL).setMaxWidth(50);
		this.getColumn(ALLOW_NULL).setCellRenderer(this.cellRenderer);
		this.getColumn(PRIMARY_KEY).setCellRenderer(this.cellRenderer);
	}
	
	
	public boolean isCellEditable(int row, int column) {
		//"�����"�в����Ա��༭
		TableColumn notNullColumn = this.getColumn(ALLOW_NULL);
		if (notNullColumn.getModelIndex() == column) {
			return false;
		}
		TableColumn primaryKeyColumn = this.getColumn(PRIMARY_KEY);
		if (primaryKeyColumn.getModelIndex() == column) {
			return false;
		}
		return super.isCellEditable(row, column);
	}
	
	//�����JTable�е����ʱ�򴥷��÷���
	private void selectCell() {
		int column = this.getSelectedColumn();
		int row = this.getSelectedRow();
		if (column == -1 || row == -1) return;
		//�޸�ͼƬ��
		selectAllowNullColumn(row, column);
		selectPrimaryKeyColumn(row, column);
		//�޸�Ĭ��ֵ
		this.tableFrame.setDefaultValue(row);
		//�޸��Ƿ��Զ�������checkbox
		this.tableFrame.setIsAutoIncrement(row);
		//���õ�����ı��ֵ��ע���ǵ�������������룬���ֻ���������պ�����
		changeClickValue(row, column);
	}
	
	//�������������Ԫ���¼���ʱ�򣬸ı�ֵ��һ��ֻ�ı�����պ�������
	private void changeClickValue(int row, int column) {
		TableColumn primaryColumn = this.getColumn(PRIMARY_KEY);
		if (primaryColumn.getModelIndex() == column) {
			this.tableFrame.changePrimaryKeyValue(row);
		}
		TableColumn allowNullColumn = this.getColumn(ALLOW_NULL);
		if (allowNullColumn.getModelIndex() == column) {
			this.tableFrame.changeAllowNullValue(row);
		}
	}
	
	//��������������"������"��ȥ�����߼���ͼ��
	private void selectPrimaryKeyColumn(int row, int column) {
		//�õ���Ҫ����ͼƬ���У������У�
		TableColumn tc = this.getColumn(PRIMARY_KEY);
		if (tc.getModelIndex() == column) {
			Object obj = this.getValueAt(row, column);
			if (ImageUtil.PRIMARY_KEY_BLANK.equals(obj)) {
				this.setValueAt(ImageUtil.PRIMARY_KEY, row, column);
			} else {
				this.setValueAt(ImageUtil.PRIMARY_KEY_BLANK, row, column);
			}
		}
	}
	
	//��������������"�����"�У� �����ͼ��
	private void selectAllowNullColumn(int row, int column) {
		//�õ���Ҫ����ͼƬ���У�������У�
		TableColumn tc = this.getColumn(ALLOW_NULL);
		if (tc.getModelIndex() == column) {
			Icon currentIcon = (Icon)this.getValueAt(row, column);
			if (ImageUtil.CHECKED_ICON.equals(currentIcon)) {
				//��ǰΪѡ��״̬
				this.setValueAt(ImageUtil.UN_CHECKED_ICON, row, column);
			} else {
				//��ǰΪûѡ��״̬
				this.setValueAt(ImageUtil.CHECKED_ICON, row, column);
			}
		}
	}
	
	//��дJTable�ķ���, �б�ֹͣ�༭��ʱ�򴥷��÷���
	public void editingStopped(ChangeEvent e) {
		int column = this.getEditingColumn();
		int row = this.getEditingRow();
		super.editingStopped(e);
		//��õ�ǰ�༭������
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		String columnName = model.getColumnName(column);
		//�õ��༭��ĵ�Ԫ���ֵ
		String value = (String)this.getValueAt(row, column);
		if (columnName.equals(FIELD_NAME)) {
			//�����ֶ�����
			this.tableFrame.changeFieldName(row, value);
		} else if (columnName.equals(FIELD_TYPE)) {
			//�����ֶ�����
			this.tableFrame.changeFieldType(row, value);
		}
	}
	
	

	//�������ݿ��е��б����
	@SuppressWarnings("unchecked")
	public Vector getFieldTableColumn() {
		Vector cols = new Vector();
		cols.add(FIELD_NAME);
		cols.add(FIELD_TYPE);
		cols.add(ALLOW_NULL);
		cols.add(PRIMARY_KEY);
		return cols;
	}
}
