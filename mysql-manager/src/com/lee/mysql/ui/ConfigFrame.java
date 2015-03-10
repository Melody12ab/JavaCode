package com.lee.mysql.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.lee.mysql.object.GlobalContext;
import com.lee.mysql.object.tree.ServerConnection;
import com.lee.mysql.util.FileUtil;
import com.lee.mysql.util.MySQLUtil;

/**
 * ����ϵͳ�����ý���, ��Ҫ��������mysql_home
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ConfigFrame extends JFrame {

	//�ı������Box
	private Box textBox = Box.createHorizontalBox();
	//��ť��Box
	private Box buttonBox = Box.createHorizontalBox();
	//����������Box
	private Box mainBox = Box.createVerticalBox();
	//MySQL��װĿ¼����
	private JLabel mysqlHomeLabel = new JLabel("MySQL�İ�װĿ¼��");
	//ѡ��Ŀ¼����ʾ��JTextField
	private JTextField mysqlHomeField = new JTextField(20);
	//ȷ����ť
	private JButton confirmButton = new JButton("ȷ��");
	//ȡ����ť
	private JButton cancelButton = new JButton("ȡ��");
	//���Ŀ¼��ť
	private JButton chooseButton = new JButton("ѡ��");
	//���������
	private MainFrame mainFrame;
	
	public ConfigFrame() {
		this.mysqlHomeField.setEditable(false);
		//�������ļ��ж�ȡMySQL Home��ֵ����JTextField��
		this.mysqlHomeField.setText(FileUtil.getMySQLHome());
		this.textBox.add(Box.createHorizontalStrut(20));
		this.textBox.add(this.mysqlHomeLabel);
		this.textBox.add(this.mysqlHomeField);
		this.textBox.add(Box.createHorizontalStrut(10));
		this.textBox.add(this.chooseButton);
		this.textBox.add(Box.createHorizontalStrut(20));
		this.buttonBox.add(this.confirmButton);
		this.buttonBox.add(Box.createHorizontalStrut(30));
		this.buttonBox.add(this.cancelButton);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.mainBox.add(textBox);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.mainBox.add(buttonBox);
		this.mainBox.add(Box.createVerticalStrut(20));
		this.add(mainBox);
		this.pack();
		this.setLocation(250, 250);
		this.setResizable(false);
		this.setTitle("MySQL������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initListeners();
	}
	
	private void showMainFrame() {
		//ȡ���û�����ֵ
		String mysqlHome = this.mysqlHomeField.getText();
		//Ѱ���û�ѡ���Ŀ¼���ж��Ƿ�����ҵ�MySQL��װĿ¼�µ�binĿ¼
		File file = new File(mysqlHome + MySQLUtil.MYSQL_HOME_BIN);
		//�Ҳ���MySQL�İ�װĿ¼����ʾ
		if (!file.exists()) {
			showMessage("��ѡ����ȷMySQL��װĿ¼", "����");
			return;
		}
		//��������ļ���ֵ���û������ֵ����ȣ�������д�������ļ���
		if (!mysqlHome.equals(FileUtil.getMySQLHome())) {
			FileUtil.saveMysqlHome(this.mysqlHomeField.getText());
		}
		GlobalContext ctx = new GlobalContext(mysqlHome);
		//��ȡȫ���ķ�������������
		List<ServerConnection> conns = ctx.getPropertiesHandler().getServerConnections();
		addConnectionToContext(ctx, conns);
		this.mainFrame = new MainFrame(ctx);
		this.mainFrame.setVisible(true);
		this.setVisible(false);
	}
	
	/**
	 * �������е�����ServerConnection����ӵ�GlobalContext��Map��
	 * @param ctx
	 * @param conns
	 */
	private void addConnectionToContext(GlobalContext ctx, 
			List<ServerConnection> conns) {
		for (ServerConnection conn : conns) ctx.addConnection(conn);
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
	
	//��ʼ��������
	public void initListeners() {
		this.confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showMainFrame();
			}
		});
		this.cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		this.chooseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FileChooser chooser = new FileChooser(mysqlHomeField);
				chooser.showOpenDialog(null);
			}
		});
	}
}
class FileChooser extends JFileChooser {
	
	private JTextField field;
	
	public FileChooser(JTextField field) {
		this.field = field;
		//����ֻ����ѡ��Ŀ¼
		this.setFileSelectionMode(FileChooser.DIRECTORIES_ONLY);
	}
	
	public void approveSelection() {
		//����JTextField��ֵ
		this.field.setText(this.getSelectedFile().getAbsolutePath());
		super.approveSelection();
	}
	
}
