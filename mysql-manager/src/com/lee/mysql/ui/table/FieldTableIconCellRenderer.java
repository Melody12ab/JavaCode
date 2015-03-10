package com.lee.mysql.ui.table;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * �ַ��б����Ⱦ����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class FieldTableIconCellRenderer extends DefaultTableCellRenderer {

	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		//�жϵ�Ԫ���ֵ���ͣ��ֱ����setIcon��setText����
		if (value instanceof Icon) this.setIcon((Icon)value);
		else this.setText((String)value);
		this.setHorizontalAlignment(CENTER);
		return this;
	}
}
