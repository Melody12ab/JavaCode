package com.lee.mysql.system;

import java.util.List;

import com.lee.mysql.object.tree.ServerConnection;

/**
 * properties�ļ�����ӿ�
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface PropertiesHandler {

	/**
	 * ��һ��ServerConnection���󱣴���ϵͳ��properties��, 
	 * ��properties�ļ����ļ�����ServerConnection��connectioName
	 * @param conn
	 */
	void saveServerConnection(ServerConnection conn);
	
	/**
	 * ȥ���ص�connectionsĿ¼�ж�ȡȫ���������ļ�, ����װ��ServerConnection����
	 * @return
	 */
	List<ServerConnection> getServerConnections();
}
