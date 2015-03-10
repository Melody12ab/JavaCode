package com.logindemo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainFrame
 */
public class MainFrame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("<img src='images/img.png'/>&nbsp;欢迎xx登陆<a href='/logindemo/LoginServlet'>&nbsp;返回重新登录</a><hr>");
		out.println("<h3>请您要进行的选择操作</h3>");
		out.println("<a href='/logindemo/ManagerUsers'>管理用户</a><br>");//由于从浏览器来 必须要有web应用名称
		out.println("<a href=''>添加用户</a><br>");
		out.println("<a href=''>查找用户</a><br>");
		out.println("<a href=''>退出系统</a><br>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
