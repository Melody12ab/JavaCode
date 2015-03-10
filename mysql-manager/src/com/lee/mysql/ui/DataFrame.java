package com.lee.mysql.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.lee.mysql.exception.QueryException;
import com.lee.mysql.object.QueryObject;
import com.lee.mysql.object.table.DataCell;
import com.lee.mysql.object.table.DataColumn;
import com.lee.mysql.ui.table.DataTable;
import com.lee.mysql.util.MySQLUtil;

/**
 * ��ʾ���ݵĽ���
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class DataFrame extends JFrame {

	private Box mainBox = Box.createVerticalBox();
	
	private JScrollPane tablePane;
	
	private DataTable table;
	
	private DefaultTableModel model;
	
	private JToolBar toolBar = new JToolBar();
	
	//ˢ������
	private Action refresh = new AbstractAction("ˢ������", new ImageIcon("images/refresh-data.gif")){
		public void actionPerformed(ActionEvent e) {
			refresh();
		}
	};
	
	//����
	private Action sortDesc = new AbstractAction("����", new ImageIcon("images/sort-desc.gif")){
		public void actionPerformed(ActionEvent e) {
			desc();
		}
	};
	
	//����
	private Action sortAsc = new AbstractAction("����", new ImageIcon("images/sort-asc.gif")){
		public void actionPerformed(ActionEvent e) {
			asc();
		}
	};
	
	//��ѯ����
	private QueryObject queryObject;
	//��ǰ��ResultSet����
	private ResultSet rs;
	//��ǰ�б���м���
	private List<DataColumn> columns;
	//�����ַ���
	private String orderString;
	
	public DataFrame(QueryObject queryObject) {
		this.queryObject = queryObject;
		this.rs = queryObject.getDatas(this.orderString);
		this.table = createTable();
		this.tablePane = new JScrollPane(this.table);
		this.tablePane.setPreferredSize(new Dimension(600, 400));
		this.mainBox.add(this.tablePane);
		this.add(this.mainBox);
		initToolBar();
		this.setLocation(200, 100);
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setTitle(queryObject.getQueryName());
		this.pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//�ͷű����������ڴ�
				dispose();
			}
		});
	}
	
	//��ʼ��������
	private void initToolBar() {
		this.toolBar.add(this.refresh).setToolTipText("ˢ��");
		this.toolBar.add(this.sortDesc).setToolTipText("����");
		this.toolBar.add(this.sortAsc).setToolTipText("����");
	}
	
	//ˢ������
	private void refresh() {
		//��������
		this.rs = this.queryObject.getDatas(this.orderString);
		//�õ�ȫ����
		this.columns = getColumns(this.rs);
		//�������ݵ��б���
		this.model.setDataVector(getDatas(), this.columns.toArray());
		setTableColumn(this.table);
	}
	
	//����
	private void desc() {
		String column = getSelectColumnIdentifier();
		//û��ѡ������, ������
		if (column == null) return;
		this.orderString = column + " " + MySQLUtil.DESC;
		//ˢ���б�
		refresh();
	}
	
	//����
	private void asc() {
		String column = getSelectColumnIdentifier();
		if (column == null) return;
		this.orderString = column + " " + MySQLUtil.ASC;
		//ˢ���б�
		refresh();
	}
	
	//�����е�����
	private String getSelectColumnIdentifier() {
		//�õ�ѡ��������
		int selectIndex = this.table.getSelectColumn();
		if (selectIndex == -1) return null;
		DefaultTableColumnModel colModel = (DefaultTableColumnModel)this.table.getColumnModel();
		return (String)colModel.getColumn(selectIndex).getIdentifier();
	}
	
	//�����б�
	private DataTable createTable() {
		//�õ�ȫ����
		this.columns = getColumns(this.rs);
		//����JTableģ�ͣ���ʼ������
		this.model = new DefaultTableModel(getDatas(), 
				columns.toArray());
		DataTable table = new DataTable(this.model);
		setTableColumn(table);
		return table;
	}
	
	
	//����ÿ�еĿ�
	private void setTableColumn(JTable table) {
		DefaultTableColumnModel model = (DefaultTableColumnModel)table.getColumnModel();
		for (int i = 0; i < table.getColumnCount(); i++) {
			//�õ��ж���
			TableColumn column = model.getColumn(i);
			//�õ�����
			String columnName = (String)column.getIdentifier();
			//������С���
			int width = columnName.length() * 10;
			//�����п���СΪ150
			int prefectWidth = (width < 150) ? 150 : width;
			column.setMinWidth(prefectWidth);
		}
	}
	
	//�������
	@SuppressWarnings("unchecked")
	private DataCell[][] getDatas() {
		int columnCount = this.columns.size();
		//�����д��һ��DataCell���飬���ת����һ����ά����
		List<DataCell[]> datas = new ArrayList<DataCell[]>();
		try {
			int i = 0;
			while (this.rs.next()) {
				DataCell[] data = new DataCell[columnCount];
				//������, ����ÿһ����Ԫ�����
				for (int j = 0; j < columnCount; j++) {
					//�õ������ĳһ��
					DataColumn column = this.columns.get(j);
					//������Ԫ�����
					DataCell dc = new DataCell(i, column, 
							this.rs.getString(column.getText()));
					data[j] = dc;
				}
				datas.add(data);
			}
			//ȡ�����ݣ�����ǰ��ResultSet����ر�
			this.rs.close();
			return datas.toArray(EMPTY_DATAS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("��ȡ���ݴ���" + this.queryObject.getQueryName());
		}
	}
	
	private final static DataCell[][] EMPTY_DATAS = new DataCell[0][0];
	
	//�����
	@SuppressWarnings("unchecked")
	private List<DataColumn> getColumns(ResultSet rs) {
		try {
			List<DataColumn> columns = new ArrayList<DataColumn>();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				columns.add(new DataColumn(i, metaData.getColumnLabel(i)));
			}
			return columns;
		} catch (Exception e) {
			e.printStackTrace();
			throw new QueryException("�������쳣��" + 
					this.queryObject.getQuerySQL(this.orderString));
		}	
	}
	
}
