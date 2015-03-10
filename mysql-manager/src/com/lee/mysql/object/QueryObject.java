package com.lee.mysql.object;

import java.sql.ResultSet;


public interface QueryObject {
	

	ResultSet getDatas(String orderString);
	

	String getQueryName();
	

	String getQuerySQL(String orderString);
	
}
