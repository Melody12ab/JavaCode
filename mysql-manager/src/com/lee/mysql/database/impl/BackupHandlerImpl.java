package com.lee.mysql.database.impl;

import java.io.File;
import java.util.List;

import com.lee.mysql.database.BackupHandler;
import com.lee.mysql.object.GlobalContext;
import com.lee.mysql.object.list.TableData;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.object.tree.ServerConnection;
import com.lee.mysql.object.tree.TableNode;
import com.lee.mysql.util.CommandUtil;
import com.lee.mysql.util.MySQLUtil;


public class BackupHandlerImpl implements BackupHandler {

	private String getMySQLBin(GlobalContext ctx) {
		return ctx.getMySQLHome() + MySQLUtil.MYSQL_HOME_BIN + File.separator;
	}
	
	public void dumpDatabase(GlobalContext ctx, Database db, File targetFile) {
		StringBuffer dumpCommand = new StringBuffer();
		dumpCommand.append('"' + getMySQLBin(ctx));
		dumpCommand.append(MySQLUtil.MYSQLDUMP_COMMAND + '"');
		getExecuteCommand(dumpCommand, db.getServerConnection());
		dumpCommand.append(" --force --databases " + db.getDatabaseName() + " > ");
		dumpCommand.append('"' + targetFile.getAbsolutePath() + '"');
		System.out.println(dumpCommand.toString());
		CommandThread thread = new CommandThread(dumpCommand.toString());
		thread.start();
	}
	

	public void dumpTable(GlobalContext ctx, List<TableData> tables, Database db, 
			File targetFile) {
		StringBuffer dumpCommand = new StringBuffer();
		StringBuffer tableNames = new StringBuffer();
		for (TableData table : tables) tableNames.append(table.getName() + " ");
		dumpCommand.append('"' + getMySQLBin(ctx));
		dumpCommand.append(MySQLUtil.MYSQLDUMP_COMMAND + '"');
		getExecuteCommand(dumpCommand, db.getServerConnection());
		dumpCommand.append(" --databases " + db.getDatabaseName());
		dumpCommand.append(" --tables " + tableNames.toString() + " > ");
		dumpCommand.append('"' + targetFile.getAbsolutePath() + '"');
		System.out.println(dumpCommand.toString());
		new CommandThread(dumpCommand.toString()).start();
	}

	public void executeSQLFile(GlobalContext ctx, Database db, File sqlFile) {
		StringBuffer dumpCommand = new StringBuffer();
		dumpCommand.append('"' + getMySQLBin(ctx));
		dumpCommand.append(MySQLUtil.MYSQL_COMMAND + '"');
		getExecuteCommand(dumpCommand, db.getServerConnection());
		dumpCommand.append(" -D" + db.getDatabaseName());
		dumpCommand.append(" < \"" + sqlFile.getAbsolutePath() + "\"");
		System.out.println(dumpCommand.toString());
		new CommandThread(dumpCommand.toString()).start();
	}

	public void executeSQLFile(GlobalContext ctx, ServerConnection conn,
			File sqlFile) {
		StringBuffer dumpCommand = new StringBuffer();
		dumpCommand.append('"' + getMySQLBin(ctx));
		dumpCommand.append(MySQLUtil.MYSQL_COMMAND + '"');
		getExecuteCommand(dumpCommand, conn);
		dumpCommand.append(" < \"" + sqlFile.getAbsolutePath() + "\"");
		new CommandThread(dumpCommand.toString()).start();
	}
	
	private StringBuffer getExecuteCommand(StringBuffer command, ServerConnection conn) {
		command.append(" -u" + conn.getUsername());
		command.append(" -p" + conn.getPassword());
		command.append(" -h" + conn.getHost());
		return command;
	}


}

class CommandThread extends Thread {

	private String command;
	
	public CommandThread(String command) {
		this.command = command;
	}
	
	public void run() {
		CommandUtil.executeCommand(this.command);
	}
	
}
