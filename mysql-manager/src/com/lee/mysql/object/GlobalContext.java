package com.lee.mysql.object;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.lee.mysql.database.BackupHandler;
import com.lee.mysql.database.impl.BackupHandlerImpl;
import com.lee.mysql.object.tree.ServerConnection;
import com.lee.mysql.system.PropertiesHandler;
import com.lee.mysql.system.impl.PropertiesHandlerImpl;
import com.lee.mysql.util.FileUtil;


public class GlobalContext {

	private String mySQLHome;
	
	private PropertiesHandler propertiesHandler = new PropertiesHandlerImpl();
	private BackupHandler backHandler = new BackupHandlerImpl();
	
	public GlobalContext(String mySQLHome) {
		this.mySQLHome = mySQLHome;
		Properties props = System.getProperties();
		props.setProperty("mysql.home", mySQLHome);
		System.setProperties(props);
	}
	
	public BackupHandler getBackupHandler() {
		return this.backHandler;
	}
	
	private Map<String, ServerConnection> connections = new HashMap<String, ServerConnection>();
	
	public void addConnection(ServerConnection connection) {
		this.connections.put(connection.getConnectionName(), connection);
	}
	
	public void removeConnection(ServerConnection connection) {
		File configFile = new File(FileUtil.CONNECTIONS_FOLDER + 
				connection.getConnectionName() + ".properties");
		configFile.delete();
		this.connections.remove(connection.getConnectionName());
	}
	
	public ServerConnection getConnection(String connectionName) {
		return this.connections.get(connectionName);
	}
	
	public String getMySQLHome() {
		return this.mySQLHome;
	}
	
	public PropertiesHandler getPropertiesHandler() {
		return this.propertiesHandler;
	}
	
	public Map<String, ServerConnection> getConnections() {
		return this.connections;
	}
}
