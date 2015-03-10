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
		out.println("<h1>用户登录<h1>");
		out.println("<form action='/logindemo/LoginClServlet' method='post'>");
		out.println("用户id：<input type='text' name='id'/><br>");
		out.println("密&nbsp;&nbsp;码：<input type='password' name='password'/><br>");
		out.println("<input type='submit' value='登陆'>");
		out.println("<input type='reset' value='重置'>");
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
