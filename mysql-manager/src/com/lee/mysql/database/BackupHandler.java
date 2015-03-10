package com.lee.mysql.database;

import java.io.File;
import java.util.List;

import com.lee.mysql.object.GlobalContext;
import com.lee.mysql.object.list.TableData;
import com.lee.mysql.object.tree.Database;
import com.lee.mysql.object.tree.ServerConnection;

public interface BackupHandler {

	
	void dumpDatabase(GlobalContext ctx, Database db, File targetFile);
	
	void dumpTable(GlobalContext ctx, List<TableData> table, Database db, 
			File targetFile);
	

	void executeSQLFile(GlobalContext ctx, Database db, File sqlFile);
	

	void executeSQLFile(GlobalContext ctx, ServerConnection conn, File sqlFile);
}
