package com.logindemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class LoginClServlet
 */
public class LoginClServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		/*out.print("<h1>"+username+password+"</h1>");
		System.out.println(username+" "+password);
		if("manager".equals(username)&&"manager".equals(password)){
			//页面跳转 1.Sendredirct 转向  2.forword 转发
			response.sendRedirect("/logindemo/MainFrame");
		}else{
			response.sendRedirect("/logindemo/LoginServlet");
		}*/
		Connection ct=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","123456");
			ps=ct.prepareStatement("select * from users where username=? and password=?");
			ps.setObject(1, id);
			ps.setObject(2, password);
			rs=ps.executeQuery();
			if(rs.next()){
				request.getRequestDispatcher("/MainFrame").forward(request, response);
			}else{
				request.setAttribute("err", "用户id或密码错误！");//通过这种方式不会出现乱码
				request.getRequestDispatcher("/LoginServlet").forward(request, response);
			}
		} catch (Exception e) {
				request.setAttribute("err", "您输入的id不正确");
				request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				rs=null;
				if(ps!=null){
					ps.close();
				}
				ps=null;
				if(ct!=null){
					ct.close();
				}
				ct=null;
			} catch (Exception e2) {
				// TODO: handle exception
			}
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
