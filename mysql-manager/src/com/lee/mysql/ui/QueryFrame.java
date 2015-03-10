package com.lee.mysql.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import com.lee.mysql.object.list.QueryData;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.util.FileUtil;

/**
 * ��ѯ����
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class QueryFrame extends JFrame {

	private JScrollPane editPane;
	
	private JTextArea editArea = new JTextArea(20, 40);
	
	private JToolBar toolBar = new JToolBar();
	
	private Action query = new AbstractAction("��ѯ", new ImageIcon("images/run-query.gif")) {
		public void actionPerformed(ActionEvent e) {
			execute();
		}
	};
	
	private Action save = new AbstractAction("�������ļ�", new ImageIcon("images/save-query.gif")) {
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	//�ò�ѯ��Ҫ��ĳ�����ݿ���ִ��
	private Database database;
	
	public QueryFrame(Database database) {
		this.database = database;
		this.editPane = new JScrollPane(this.editArea);
		this.add(editPane);
		this.setLocation(250, 150);
		this.toolBar.add(query).setToolTipText("��ѯ");
		this.toolBar.add(save).setToolTipText("�������ļ�");
		this.add(toolBar, BorderLayout.NORTH);
		this.setTitle("ִ�в�ѯ");
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//�ͷű����������ڴ�
				dispose();
			}
		});
		this.pack();
	}
	
	//����SQL���ļ������ļ�ѡ����
	private void save() {
		FolderChooser chooser = new FolderChooser(this);
		chooser.showSaveDialog(this);
	}
	
	//д���ļ�
	public void writeToFile(File file) {
		String content = this.editArea.getText();
		//������д���ļ���
		FileUtil.writeToFile(file, content);
	}
	
	//ִ�з���
	private void execute() {
		String sql = this.editArea.getText();
		if (sql.toLowerCase().indexOf("select") != -1) {
			query();
		} else if (sql.toLowerCase().indexOf("call") != -1) {
			query();
		} else {
			try {
				QueryData queryData = new QueryData(this.database, sql);
				queryData.execute();
				showMessage("ִ�гɹ�", "");
			} catch (Exception e) {
				showMessage(e.getMessage(), "����");
			}
		}
	}
	
	//��ѯ
	private void query() {
		//�õ�SQL
		String sql = this.editArea.getText();
		try {
			//��װһ��QueryData����
			QueryData queryData = new QueryData(this.database, sql);
			//��ʾDataFrame
			DataFrame dataFrame =new DataFrame(queryData);
			dataFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			showMessage(e.getMessage(), "����");
		}
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
}

class FolderChooser extends JFileChooser {
	
	private QueryFrame queryFrame;
	
	public FolderChooser(QueryFrame queryFrame) {
		this.queryFrame = queryFrame;
		this.setFileSelectionMode(this.DIRECTORIES_ONLY);
	}

	
	public void approveSelection() {
		File file = this.getSelectedFile();
		if (file.isDirectory()) {
			//�û�ѡ����Ŀ¼����ʹ��UUID�����ļ���
			String fileName = UUID.randomUUID().toString();
			File newFile = new File(file.getAbsolutePath() + 
					File.separator + fileName + ".sql");
			this.queryFrame.writeToFile(newFile);
		} else {
			//�û�ѡ�����ļ�
			this.queryFrame.writeToFile(file);
		}
		super.approveSelection();
	}
	
	
}


