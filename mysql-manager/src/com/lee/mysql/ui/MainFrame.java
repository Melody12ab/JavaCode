package com.lee.mysql.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.lee.mysql.object.GlobalContext;
import com.lee.mysql.object.ViewObject;
import com.lee.mysql.object.list.AbstractData;
import com.lee.mysql.object.list.ProcedureData;
import com.lee.mysql.object.list.TableData;
import com.lee.mysql.object.list.ViewData;
import com.lee.mysql.object.tree.ConnectionNode;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.object.tree.ProcedureNode;
import com.lee.mysql.object.tree.RootNode;
import com.lee.mysql.object.tree.ServerConnection;
import com.lee.mysql.object.tree.TableNode;
import com.lee.mysql.object.tree.ViewNode;
import com.lee.mysql.ui.list.ListCellRenderer;
import com.lee.mysql.ui.tree.TreeCellRenderer;
import com.lee.mysql.ui.tree.TreeListener;
import com.lee.mysql.util.MySQLUtil;

/**
 * ��������������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	//������ķָ�Pane
	private JSplitPane mainPane;
	//����JScrollPane
	private JScrollPane treePane;
	//�������������, ���ĵ�һ���ӽڵ��Ǹ������ӣ��ڶ����ӽڵ��Ǹ������µ����ݿ��б�
	//�������ӽڵ��Ǳ���ͼ��
	private JTree tree;
	//����Model
	private DefaultTreeModel treeModel;
	//JList��JScrollPane
	private JScrollPane dataPane;
	//����ͼ�ȴ�ŵ�JList
	private JList dataList;
	//�յ�����
	private Object[] emptyData = new Object[]{};
	//������
	private JToolBar toolBar = new JToolBar();
	//�����ӽ���
	private ConnectionFrame connectionFrame;
	//�½����ݿ����
	private DatabaseFrame databaseFrame;
	
	//������
	private Action newConnection = new AbstractAction("������", new ImageIcon("images/connection.gif")) {
		public void actionPerformed(ActionEvent e) {
			newConnection();
		}
	};

	//��
	private Action table = new AbstractAction("��", new ImageIcon("images/table.gif")) {
		public void actionPerformed(ActionEvent e) {
			viewTables();
		}
	};
	
	//��ͼ
	private Action view = new AbstractAction("��ͼ", new ImageIcon("images/view.gif")) {
		public void actionPerformed(ActionEvent e) {
			viewViews();
		}
	};
	
	//�洢����
	private Action procedure = new AbstractAction("�洢����", new ImageIcon("images/procedure.gif")) {
		public void actionPerformed(ActionEvent e) {
			viewProcedures();
		}
	};
	
	//��ѯ
	private Action query = new AbstractAction("��ѯ", new ImageIcon("images/query.gif")) {
		public void actionPerformed(ActionEvent e) {
			query();
		}
	};
	
	//�����˵�
	JPopupMenu menu = new JPopupMenu();

	//��Ĳ˵�
	private JMenuItem addTableItem = new JMenuItem("�½���", new ImageIcon("images/add-table.gif"));
	private JMenuItem editTableItem = new JMenuItem("�༭��", new ImageIcon("images/edit-table.gif"));
	private JMenuItem deleteTableItem = new JMenuItem("ɾ����", new ImageIcon("images/delete-table.gif"));
	private JMenuItem dumpTableItem = new JMenuItem("������", null);
	private JMenuItem refresh = new JMenuItem("ˢ    ��", new ImageIcon("images/refresh.gif"));
	//��ͼ�˵� 
	private JMenuItem addViewItem = new JMenuItem("�½���ͼ", new ImageIcon("images/add-view.gif"));
	private JMenuItem editViewItem = new JMenuItem("�༭��ͼ", new ImageIcon("images/edit-view.gif"));
	private JMenuItem dropViewItem = new JMenuItem("ɾ����ͼ", new ImageIcon("images/delete-view.gif"));
	//�洢���̲˵� 
	private JMenuItem addProcedureItem = new JMenuItem("�½��洢����", new ImageIcon("images/add-procedure.gif"));
	private JMenuItem editProcedureItem = new JMenuItem("�༭�洢����", new ImageIcon("images/edit-procedure.gif"));
	private JMenuItem dropProcedureItem = new JMenuItem("ɾ���洢����", new ImageIcon("images/delete-procedure.gif"));

	//���ĵ����˵�
	JPopupMenu treeMenu = new JPopupMenu();
	private JMenuItem closeConnection = new JMenuItem("�ر�����", null);
	private JMenuItem removeConnection = new JMenuItem("ɾ������", null);
	private JMenuItem closeDatabase = new JMenuItem("�ر����ݿ�", null);
	private JMenuItem newDatabase = new JMenuItem("�½����ݿ�", null);
	private JMenuItem removeDatabase = new JMenuItem("ɾ�����ݿ�", null);
	private JMenuItem executeSQLFile = new JMenuItem("ִ��SQL�ļ�", null);
	private JMenuItem dumpSQLFile = new JMenuItem("����SQL�ļ�", null);
	//ȫ�������Ķ���
	private GlobalContext ctx;
	//��ǰ�б�ǰ����ʾ��������
	private ViewObject currentView;
	
	public MainFrame(GlobalContext ctx) {
		this.ctx = ctx;
		//��ʼ��������
		createTree();
		this.treePane = new JScrollPane(this.tree);
		//��ʼ������ͼ��JList
		this.dataList = createList();
		this.dataPane = new JScrollPane(dataList);
		this.dataPane.setPreferredSize(new Dimension(600, 400));
		this.mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.treePane, 
				this.dataPane);
		this.mainPane.setDividerLocation(170);
		this.add(this.mainPane);
		//����������
		createToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		this.setLocation(150, 100);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("MySQL������");
		//��ʼ����������
		this.connectionFrame = new ConnectionFrame(this.ctx, this);
		initMenuItemListeners();
	}
	
	//��ʼ���Ҽ��˵�
	private void initMenuItemListeners() {
		this.addTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newTable();
			}
		});
		this.editTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				editTable();
			}
		});
		this.deleteTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dropTable();
			}
		});
		this.dumpTableItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dumpTable();
			}
		});
		this.addViewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newView();
			}
		});
		this.editViewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				editView();
			}
		});
		this.dropViewItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dropView();
			}
		});
		this.addProcedureItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newProcedure();
			}
		});
		this.editProcedureItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				editProcedure();
			}
		});
		this.dropProcedureItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dropProcedure();
			}
		});
		this.refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				refreshDataList();
			}
		});
		this.closeConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				closeConnection();
			}
		});
		this.closeDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				closeDatabase();
			}
		});
		this.dumpSQLFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dumpDatabase();
			}
		});
		this.executeSQLFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				executeSQLFile();
			}
		});
		this.removeConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeConnection();
			}
		});
		this.newDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				newDatabase();
			}
		});
		this.removeDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeDatabase();
			}
		});
	}
	
	//��ʼ����dataList����
	@SuppressWarnings("static-access")
	private JList createList() {
		JList dataList = new JList();
		//����������������� 
		dataList.setLayoutOrientation(JList.VERTICAL_WRAP);
		dataList.setFixedCellHeight(30);
		dataList.setVisibleRowCount(this.HEIGHT / 30);
		dataList.setCellRenderer(new ListCellRenderer());
		dataList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)  clickList();
			}
		});
		return dataList;
	}
	
	//˫���б���
	private void clickList() {
		AbstractData selectData = getSelectData();
		if (selectData instanceof TableData) {
			//ѡ����򿪱�����
			openTable((TableData)selectData);
		} else if (selectData instanceof ViewData) {
			//ѡ����ͼ������ͼ
			openView((ViewData)selectData);
		} else if (selectData instanceof ProcedureData) {
			//ѡ��洢���̣��༭�洢����
			editProcedure();
		}
	}
	
	//����ͼ
	private void openView(ViewData view) {
		DataFrame dataFrame =new DataFrame(view);
		dataFrame.setVisible(true);
	}
	
	//�򿪱�
	private void openTable(TableData table) {
		DataFrame dataFrame =new DataFrame(table);
		dataFrame.setVisible(true);
	}
	
	//����б���ѡ��ı���ͼ���ߴ洢����
	private AbstractData getSelectData() {
		return (AbstractData)this.dataList.getSelectedValue();
	}
	
	//��ʾ�½����ӽ���
	private void newConnection() {
		this.connectionFrame.setVisible(true);
	}
	
	
	//����ѡ�еĽڵ��ȡ�ýڵ�������Database�ڵ㣬���ѡ���˷������ڵ㣬�򷵻�null
	private DefaultMutableTreeNode getDatabaseNode(DefaultMutableTreeNode selectNode) {
		if (selectNode == null) return null;
		if (selectNode.getUserObject() instanceof Database) {
			return selectNode;
		}
		if (selectNode.getParent() == null) return null;
		return getDatabaseNode((DefaultMutableTreeNode)selectNode.getParent());
	}
	
	//�ڹ������е���˱�
	private void viewTables() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//���õ����ڵ�ķ���
		clickTableNode(db);
	}
	

	//����һ�����ݿ�(�˵����ִ��)
	private void dumpDatabase() {
		Database db = this.getSelectDatabase();
		DumpFolderChooser fc = new DumpFolderChooser(this, db);
		fc.showOpenDialog(this);
	}
	
	//�������˵����ִ�У�
	private void dumpTable() {
		Database db = this.getSelectDatabase();
		DumpTableChooser fc = new DumpTableChooser(this, db);
		fc.showOpenDialog(this);
	}
	
	//���ļ�ѡ����ִ�е�����
	public void executeDumpTable(File file, Database db) {
		List<TableData> tables = new ArrayList<TableData>();
		Object[] selects = this.dataList.getSelectedValues();
		for (Object obj : selects) {
			TableData table = (TableData)obj;
			tables.add(table);
		}
		this.ctx.getBackupHandler().dumpTable(this.ctx, tables, db, file);
	}
	
	//���ļ�ѡ����ִ�еķ���, ִ�е���
	public void executeDumpDatabase(File file, Database db) {
		this.ctx.getBackupHandler().dumpDatabase(this.ctx, db, file);
	}
	
	//ִ��һ��sql�ļ�(�˵����ִ��)
	private void executeSQLFile() {
		SQLFileChooser fc = new SQLFileChooser(this);
		fc.showOpenDialog(this);
	}
	
	//�ļ�ѡ����ִ��
	public void executeSQLFile(File file) {
		DefaultMutableTreeNode selectNode = getSelectNode();
		if (selectNode.getUserObject() instanceof ServerConnection) {
			ServerConnection conn = (ServerConnection)selectNode.getUserObject();
			//����������������ִ��һ��SQL
			this.ctx.getBackupHandler().executeSQLFile(ctx, conn, file);
		} else if (selectNode.getUserObject() instanceof Database) {
			//��һ�����ݿ�ִ��һ��SQL
			Database db = (Database)selectNode.getUserObject();
			this.ctx.getBackupHandler().executeSQLFile(this.ctx, db, file);
		}
	}
	
	//�ڹ������е���˴洢����
	private void viewProcedures() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//���õ����ڵ�ķ���
		clickProcedureNode(db);
	}
	
	//�ڹ������е������ͼ
	private void viewViews() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//���õ����ڵ�ķ���
		clickViewNode(db);
	}
	
	//�ڹ������е���˲�ѯ
	private void query() {
		Database db = getSelectDatabase();
		if (db == null) return;
		QueryFrame queryFrame = new QueryFrame(db);
		queryFrame.setVisible(true);
	}
	
	//��ʾ�½������
	private void newTable() {
		Database db = getSelectDatabase();
		TableData table = new TableData(db);
		TableFrame tableFrame = new TableFrame(table, this);
		tableFrame.setVisible(true);
	}
	
	//�޸ı�
	private void editTable() {
		TableData table = (TableData)this.getSelectData();
		TableFrame tableFrame = new TableFrame(table, this);
		tableFrame.setVisible(true);
	}
	
	//���ص�ǰ��ѡ�е�Database�ڵ�
	private DefaultMutableTreeNode getDatabaseNode() {
		//�õ�ѡ�еĽڵ�
		DefaultMutableTreeNode selectNode = getSelectNode();
		//�õ�Database�ڵ�
		return getDatabaseNode(selectNode);
	}
	
	//������ͼ
	private void newView() {
		Database db = this.getSelectDatabase();
		if (db == null) return;
		//����һ���µ�ViewData
		ViewData viewData = new ViewData(db, null);
		ViewFrame viewFrame = new ViewFrame(viewData, this);
		viewFrame.setVisible(true);
	}
	
	//�޸���ͼ
	private void editView() {
		ViewData selectData = (ViewData)getSelectData();
		if (selectData == null) return;
		ViewFrame viewFrame = new ViewFrame(selectData, this);
		viewFrame.setVisible(true);
	}
	
	//ɾ����ͼ
	private void dropView() {
		//�õ�ȫ��ѡ�е���ͼ
		Object[] views = this.dataList.getSelectedValues();
		try {
			//ѭ��ɾ��
			for (Object view : views) {
				ViewData vd = (ViewData)view;
				vd.dropView();
			}
			//ˢ���б�
			this.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "����");
		}
	}
	
	//ɾ����
	private void dropTable() {
		Object[] tables = this.dataList.getSelectedValues();
		try {
			for (Object table : tables) {
				TableData td = (TableData)table;
				td.dropTable();
			}
			//ˢ���б�
			this.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "����");
		}
	}
	
	//ˢ���б�
	public void refreshDataList() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//ʹ�õ�ǰ��ʾ�����������ж�Ӧ�����ˢ��
		if (this.currentView instanceof TableData) {
			this.dataList.setListData(db.getTables().toArray());//ˢ�±�
		} else if (this.currentView instanceof ViewData) {
			this.dataList.setListData(db.getViews().toArray());//ˢ����ͼ
		} else if (this.currentView instanceof ProcedureData) {
			this.dataList.setListData(db.getProcedures().toArray());//ˢ�´洢����
		}
	}
				
	//�����洢����
	private void newProcedure() {
		Database db = getSelectDatabase();
		if (db == null) return;
		//����һ���µ�ProcedureData����, ��ʾ����������
		ProcedureData procedureData = new ProcedureData(db, MySQLUtil.PROCEDURE_TYPE, null);
		ProcedureFrame procedureFrame = new ProcedureFrame(procedureData, this);
		procedureFrame.setVisible(true);
	}
		
	//�޸Ĵ洢����
	private void editProcedure() {
		ProcedureData procedureData = (ProcedureData)getSelectData();
		if (procedureData == null) return;
		ProcedureFrame procedureFrame = new ProcedureFrame(procedureData, this);
		procedureFrame.setVisible(true);
	}
	
	//ɾ���洢����
	private void dropProcedure() {
		//�õ�ȫ��ѡ�еĴ洢����
		Object[] procedures = this.dataList.getSelectedValues();
		try {
			//ѭ��ɾ��
			for (Object procedure : procedures) {
				ProcedureData data = (ProcedureData)procedure;
				data.drop();
			}
			//ˢ���б�
			this.refreshDataList();
		} catch (Exception e) {
			showMessage(e.getMessage(), "����");
		}
	}
	
	//���ѡ�е�Database�ڵ�
	private Database getSelectDatabase() {
		DefaultMutableTreeNode databaseNode = getDatabaseNode();
		if (databaseNode == null) return null;
		return (Database)databaseNode.getUserObject();
	}

	//ΪJList����Ҽ��˵�
	private void addMouseMenu() {
		this.dataList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					menu.show(dataList, e.getX(), e.getY());
				}
			}
		});
	}
	
	//
	private void createToolBar() {
		this.toolBar.add(this.newConnection).setToolTipText("������");
		this.toolBar.addSeparator(new Dimension(20, 0));
		this.toolBar.add(this.table).setToolTipText("��");
		this.toolBar.add(this.view).setToolTipText("��ͼ");
		this.toolBar.add(this.procedure).setToolTipText("�洢����");
		this.toolBar.add(this.query).setToolTipText("��ѯ");
	}
	
	//������
	private void createTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new RootNode());
		//�������ӽڵ�
		createNodes(root);
		this.treeModel = new DefaultTreeModel(root);
		//������
		JTree tree = new JTree(this.treeModel);
		TreeCellRenderer cr = new TreeCellRenderer();
		tree.setCellRenderer(cr);
		tree.addMouseListener(new TreeListener(this));
		tree.setRootVisible(false);
		//����Ҽ��˵�
		tree.add(this.treeMenu);
		this.tree = tree;
	}
	
	//��ʾ�����Ҽ��˵�
	public void showTreeMenu(MouseEvent e) {
		DefaultMutableTreeNode selectNode = getSelectNode();
		if (selectNode == null) return;
		if (selectNode.getUserObject() instanceof ServerConnection) {
			createServerConnectionMenu();
			this.treeMenu.show(this.tree, e.getX(), e.getY());
		} else if (selectNode.getUserObject() instanceof Database) {
			createDatabaseMenu();
			this.treeMenu.show(this.tree, e.getX(), e.getY());
		}
	}
	
	//�����ǰ�б�����ʾ���Ǳ�, �򴴽��Ҽ��˵�
	private void createTableMenu() {
		this.menu.removeAll();
		this.menu.add(this.addTableItem);
		this.menu.add(this.editTableItem);
		this.menu.add(this.deleteTableItem);
		this.menu.add(this.dumpTableItem);
		this.menu.add(this.refresh);
		addMouseMenu();
	}
	
	//�����ǰ�б�����ʾ������ͼ, �򴴽��Ҽ��˵�
	private void createViewMenu() {
		this.menu.removeAll();
		this.menu.add(this.addViewItem);
		this.menu.add(this.editViewItem);
		this.menu.add(this.dropViewItem);	
		this.menu.add(this.refresh);
		addMouseMenu();
	}
	
	//�����ǰ�б�����ʾ���Ǵ洢����, �򴴽��Ҽ��˵�
	private void createProcedureMenu() {
		this.menu.removeAll();
		this.menu.add(this.addProcedureItem);
		this.menu.add(this.editProcedureItem);
		this.menu.add(this.dropProcedureItem);	
		this.menu.add(this.refresh);
		addMouseMenu();
	}
	
	//��ʾ���������ӵĲ˵� 
	private void createServerConnectionMenu() {
		this.treeMenu.removeAll();
		this.treeMenu.add(this.closeConnection);
		this.treeMenu.add(this.removeConnection);
		this.treeMenu.add(this.newDatabase);
		this.treeMenu.add(this.executeSQLFile);
	}
	
	//��ʾ���ݿ����ӵĲ˵� 
	private void createDatabaseMenu() {
		this.treeMenu.removeAll();
		this.treeMenu.add(this.closeDatabase);
		this.treeMenu.add(this.removeDatabase);
		this.treeMenu.add(this.executeSQLFile);
		this.treeMenu.add(this.dumpSQLFile);
	}
	
	//�½����ݿ�˵�ִ�еķ���
	private void newDatabase() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		ServerConnection conn = (ServerConnection)selectNode.getUserObject();
		if (this.databaseFrame == null) {
			this.databaseFrame = new DatabaseFrame(this, conn);
		}
		this.databaseFrame.setVisible(true);
	}
	
	//ɾ��һ�����ݿ�
	private void removeDatabase() {
		//�õ�ѡ���еĽڵ�
		DefaultMutableTreeNode selectNode = getSelectNode();
		Database db = (Database)selectNode.getUserObject();
		db.remove();
		this.treeModel.removeNodeFromParent(selectNode);
	}
	
	//DatabaseFrameִ�еķ��������һ�����ݿ�
	public void addDatabase(Database db) {
		DefaultMutableTreeNode parent = getSelectNode();
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(db);
		this.treeModel.insertNodeInto(newChild, parent, parent.getChildCount());
	}
	
	//�رշ���������
	private void closeConnection() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		ServerConnection sc = (ServerConnection)selectNode.getUserObject();
		//��ServerConnection�����Ӷ�����Ϊnull
		sc.setConnection(null);
		//ɾ�����е��ӽڵ�
		removeNodeChildren(selectNode);
		//��������ѡ��
		this.tree.setSelectionPath(null);
	}
	
	//�ر����ݿ�����
	private void closeDatabase() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		Database db = (Database)selectNode.getUserObject();
		db.setConnection(null);
		//ɾ�����е��ӽڵ�
		removeNodeChildren(selectNode);
		//��������ѡ��
		this.tree.setSelectionPath(null);
	}
	
	//ɾ��һ���ڵ�������ӽڵ�
	private void removeNodeChildren(DefaultMutableTreeNode node) {
		//��ȡ�ڵ�����
		int childCount = this.treeModel.getChildCount(node);
		for (int i = 0; i < childCount; i++) {
			//�����һ����ʼɾ��
			this.treeModel.removeNodeFromParent((DefaultMutableTreeNode)node.getLastChild());
		}
	}
	
	//ɾ��һ������
	private void removeConnection() {
		DefaultMutableTreeNode selectNode = getSelectNode();
		ServerConnection conn = (ServerConnection)selectNode.getUserObject();
		//�������ļ���ɾ��
		this.ctx.removeConnection(conn);
		//�����ڵ���ɾ��
		this.treeModel.removeNodeFromParent(selectNode);
	}
	
	//�������з��������ӵĽڵ�
	private void createNodes(DefaultMutableTreeNode root) {
		Map<String, ServerConnection> conns = this.ctx.getConnections();
		for (String key : conns.keySet()) {
			ServerConnection conn = conns.get(key);
			//�������ӽڵ�
			DefaultMutableTreeNode conntionNode = new DefaultMutableTreeNode(conn);
			root.add(conntionNode);
		}
	}
	
	//�������ѡ�еĽڵ�
	private DefaultMutableTreeNode getSelectNode() {
		TreePath treePath = this.tree.getSelectionPath();
		if (treePath == null) return null;
		//���ѡ�еĽڵ�
		return (DefaultMutableTreeNode)treePath.getLastPathComponent();
	}

	
	//������ڵ�Ĳ���
	public void viewTreeDatas() {
		//���ѡ�еĽڵ�
		DefaultMutableTreeNode selectNode = getSelectNode();
		if (selectNode == null) return;
		//�������
		this.dataList.setListData(this.emptyData);
		//�жϵ���ڵ������
		if (selectNode.getUserObject() instanceof ServerConnection) {
			clickServerNode(selectNode);//���������ӽڵ�
		} else if (selectNode.getUserObject() instanceof Database) {
			clickDatabaseNode(selectNode);//���ݿ����ӽڵ�
		} else if (selectNode.getUserObject() instanceof TableNode) {
			Database db = getDatabase(selectNode);
			clickTableNode(db);//��ڵ�
		} else if (selectNode.getUserObject() instanceof ViewNode) {
			Database db = getDatabase(selectNode);
			clickViewNode(db);//��ͼ�ڵ�
		} else if (selectNode.getUserObject() instanceof ProcedureNode) {
			Database db = getDatabase(selectNode);
			clickProcedureNode(db);//�洢���̺ͺ����ڵ�
		}
	}
	
	//��������ӽ��������һ�����Ӻ�ִ�еķ���, ���������һ������
	public void addConnection(ServerConnection sc) {
		//�õ�Ҫ�ڵ�
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)this.treeModel.getRoot();
		DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(sc);
		//��Ҫ�ڵ�������ӽڵ�
		this.treeModel.insertNodeInto(newChild, root, root.getChildCount());
		if (root.getChildCount() == 1) this.tree.updateUI();		
	}
	
	//����һ���ڵ�(�ýڵ�ΪDatabase�ڵ���ӽڵ�)���Database�ڵ�
	private Database getDatabase(DefaultMutableTreeNode selectNode) {
		//��ȡ���ڵ�
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectNode.getParent();
		return (Database)parentNode.getUserObject();
	}
	
	//����洢���̽ڵ�
	private void clickProcedureNode(Database db) {
		List<ProcedureData> datas = db.getProcedures();
		this.dataList.setListData(datas.toArray());
		//��ʾ�洢���̺󣬴����Ҽ��˵�
		createProcedureMenu();
		//���õ�ǰ��ʾ����������Ϊ�洢����
		this.currentView = new ProcedureData(db, null, null);
	}
	
	//�����ͼ�ڵ㣬����ȫ������ͼ
	private void clickViewNode(Database db) {
		List<ViewData> datas = db.getViews();
		this.dataList.setListData(datas.toArray());
		//��ʾ��ͼ�󣬴����Ҽ��˵�
		createViewMenu();
		//���õ�ǰ��ʾ����������Ϊ��ͼ
		this.currentView = new ViewData(db, null);
	}
	
	//�����ڵ㣬����ȫ���ı�
	private void clickTableNode(Database db) {
		List<TableData> datas = db.getTables();
		this.dataList.setListData(datas.toArray());
		//��ʾ��󣬴����Ҽ��˵�
		createTableMenu();
		//���õ�ǰ��ʾ����������Ϊ��
		this.currentView = new TableData(db);
	}
	
	/*
	 * �ж������Ƿ���������ڷ������ڵ�����ݿ�ڵ�
	 */
	private void validateConnect(DefaultMutableTreeNode selectNode, ConnectionNode node) {
		try {
			//��������
			node.connect();
		} catch (Exception e) {
			showMessage(e.getMessage(), "����");
		}
	}
	
	//������ݿ�ڵ�
	public void clickDatabaseNode(DefaultMutableTreeNode selectNode) {
		//��ȡ������ڵ�Ķ���
		Database database = (Database)selectNode.getUserObject();
		validateConnect(selectNode, database);
		//�����ڵ�
		buildDatabaseChild(database, selectNode);
	}
	
	//�������ݿ�ڵ��ӽڵ�
	private void buildDatabaseChild(Database database, 
			DefaultMutableTreeNode databaseNode) {
		//�ж�����Ѿ����ӣ��򲻴����ڵ�
		if (databaseNode.getChildCount() != 0) return;
		//���������ӽڵ㣨����ͼ���洢���̣�
		DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(new TableNode(database));
		DefaultMutableTreeNode viewNode = new DefaultMutableTreeNode(new ViewNode(database));
		ProcedureNode pNode = new ProcedureNode(database);
		DefaultMutableTreeNode procedureNode = new DefaultMutableTreeNode(pNode);
		//��������
		this.treeModel.insertNodeInto(tableNode, databaseNode, databaseNode.getChildCount());
		this.treeModel.insertNodeInto(viewNode, databaseNode, databaseNode.getChildCount());
		this.treeModel.insertNodeInto(procedureNode, databaseNode, databaseNode.getChildCount());
	}
	
	//����������ڵ�
	public void clickServerNode(DefaultMutableTreeNode selectNode) {
		ServerConnection server = (ServerConnection)selectNode.getUserObject();
		validateConnect(selectNode, server);
		//�����������ӽڵ�
		buildServerChild(server, selectNode);
	}
	
	
	//�������ݿ�һ��Ľڵ㣨���ĵڶ��㣩
	public void buildServerChild(ServerConnection server, 
			DefaultMutableTreeNode conntionNode) {
		//������ӽڵ㣬���ٴ���
		if (conntionNode.getChildCount() != 0) return;
		List<Database> databases = server.getDatabases();
		//�ٴ������ӽڵ���������ݽڵ�
		for (Database database : databases) {
			DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(database);
			//�����ݿ�ڵ���뵽���ӽڵ���
			this.treeModel.insertNodeInto(databaseNode, conntionNode, conntionNode.getChildCount());
		}
	}
	
	private int showMessage(String s, String title) {
		return JOptionPane.showConfirmDialog(this, s, title,
				JOptionPane.OK_CANCEL_OPTION);
	}
	
}
//����SQL�ļ����ļ�ѡ����
class SQLFileChooser extends JFileChooser {
	
	private MainFrame mainFrame;
	
	public SQLFileChooser(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	
	public void approveSelection() {
		File file = this.getSelectedFile();
		this.mainFrame.executeSQLFile(file);
		super.approveSelection();
	}

}
//�������ݿ�SQL�ļ���Ŀ¼ѡ����
class DumpFolderChooser extends JFileChooser {
	
	private MainFrame mainFrame;
	
	private Database db;
	
	public DumpFolderChooser(MainFrame mainFrame, Database db) {
		this.mainFrame = mainFrame;
		this.db = db;
		this.setFileSelectionMode(DIRECTORIES_ONLY);
	}
	
	public void approveSelection() {
		File file = this.getSelectedFile();
		if (file.isDirectory()) {
			//�û�ѡ����Ŀ¼
			File targetFile = new File(file.getAbsolutePath() + File.separator + 
					this.db.getDatabaseName() + ".sql");
			this.mainFrame.executeDumpDatabase(targetFile, this.db);
		} else {
			this.mainFrame.executeDumpDatabase(file, this.db);
		}
		super.approveSelection();
	}
}
/**
 * ������ʱ��ѡ����
 * @author yangenxiong
 *
 */
class DumpTableChooser extends JFileChooser {
	
	private MainFrame mainFrame;
	
	private Database db;
	
	public DumpTableChooser(MainFrame mainFrame, Database db) {
		this.mainFrame = mainFrame;
		this.db = db;
		this.setFileSelectionMode(DIRECTORIES_ONLY);
	}
	
	public void approveSelection() {
		File file = this.getSelectedFile();
		if (file.isDirectory()) {
			//�û�ѡ����Ŀ¼
			File targetFile = new File(file.getAbsolutePath() + File.separator + 
					this.db.getDatabaseName() + ".sql");
			this.mainFrame.executeDumpTable(targetFile, this.db);
		} else {
			this.mainFrame.executeDumpTable(file, this.db);
		}
		super.approveSelection();
	}
}