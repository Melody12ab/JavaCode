package com.logindemo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;cahrset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		out.println("<img src='images/img.png'/><hr>");
		out.println("<h1>�û���¼<h1>");
		out.println("<form action='/logindemo/LoginClServlet' method='post'>");
		out.println("�û�id��<input type='text' name='id'/><br>");
		out.println("��&nbsp;&nbsp;�룺<input type='password' name='password'/><br>");
		out.println("<input type='submit' value='��½'>");
		out.println("<input type='reset' value='����'>");
		out.println("</form>");
		String errInfo=(String)request.getAttribute("err");
		if(errInfo!=null){
			out.println("<font color='red'>"+errInfo+"</font>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
