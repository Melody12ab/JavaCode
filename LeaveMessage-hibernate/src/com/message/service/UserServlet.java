package com.message.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.message.dao.UserDao;
import com.message.model.Administrator;
import com.message.model.User;

public class UserServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断用户请求类型
		String method=request.getParameter("method");
		
		//用户注册
		if("guestReg".equals(method)){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			UserDao userDao=new UserDao();
			if(username!=null && !username.isEmpty()){
				if(userDao.findUserByName(username)){
					request.setAttribute("error", "注册的用户名已经存在！");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}else{
					User user=new User();
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					userDao.saveUser(user);
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			}
		}
		
		
		//用户登录模块
		if("userLogin".equals(method)){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			UserDao userDao=new UserDao();
			User user=userDao.findUser(username, password);
			if(user!=null){
				if(user instanceof Administrator){
					request.getSession().setAttribute("admin", user);
				}
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else{
				request.setAttribute("error", "用户名或密码错误！");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
		
		
	}
}
