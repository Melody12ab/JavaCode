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
		out.println("<img src='images/img.png'/>&nbsp;��ӭxx��½<a href='/logindemo/LoginServlet'>&nbsp;�������µ�¼</a><hr>");
		out.println("<h3>����Ҫ���е�ѡ�����</h3>");
		out.println("<a href='/logindemo/ManagerUsers'>�����û�</a><br>");//���ڴ�������� ����Ҫ��webӦ������
		out.println("<a href=''>����û�</a><br>");
		out.println("<a href=''>�����û�</a><br>");
		out.println("<a href=''>�˳�ϵͳ</a><br>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
