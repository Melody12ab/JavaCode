package com.index;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseUtil
{
  public static Connection getConnection()
  {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      String url = "jdbc:mysql://localhost:3306/Teamintro";
      conn = DriverManager.getConnection(url, "root", "199429");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return conn;
  }
  public static void closeConnection(Connection conn) {
    if (conn != null)
      try {
        conn.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
  }
}


/*CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(10) NOT NULL,
  `email` varchar(20) NOT NULL,
  `subject` varchar(30) NOT NULL,
  `message` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 */