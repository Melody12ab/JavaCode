package com.review2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class MyTest {

	@Test
	public void test() throws Exception {
		Runtime r=Runtime.getRuntime();
		r.exec("notepad.exe");
	}
	@Test
	public void test1(){
		Runtime rt=Runtime.getRuntime();
		System.out.println(rt.availableProcessors()+"");
		System.out.println(rt.freeMemory()+"");
		System.out.println(rt.totalMemory()+"");
		System.out.println((rt.maxMemory()/1024)/1024+"");
	}
	@Test
	public void test2() throws Exception{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","123456");
			Statement ps=conn.createStatement();
			ResultSet rs=ps.executeQuery("select * from users");
			while(rs.next()){
				System.out.println(rs.getInt(1)+"\t"
						+rs.getString(2)+"\t"
						+rs.getString(3)+"\t"
						+rs.getInt(4)+"\t"
						+rs.getInt(5));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
