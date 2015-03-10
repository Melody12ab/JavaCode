package com.message.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.message.dao.MessageDao;
import com.message.model.Message;
import com.message.model.User;
import com.message.util.HibernateUtil;
import com.message.util.pageModel;


public class MessageServlet extends HttpServlet {
	
	//判断是否登录
	public void isLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(request.getSession().getAttribute("user")==null){
			request.setAttribute("error", "对不起，您还没登录呢！");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
	

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method =request.getParameter("method");
		String title=request.getParameter("title");
		String content=request.getParameter("content");

		if("save".equalsIgnoreCase(method)){
			try {
				this.isLogin(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//替换所有换行符
		if(content.indexOf("\n")!=-1){
			content=content.replaceAll("\n", "<br>");
		}
		
		User user=(User) request.getSession().getAttribute("user");
		Message message=new Message();
		message.setTitle(title);
		message.setContent(content);
		message.setCreateTime(new Date());
		message.setUser(user);
		MessageDao dao= new MessageDao();
		dao.saveMessage(message);
		request.getRequestDispatcher("MessageServlet?method=view").forward(request, response);
		}else if("view".equalsIgnoreCase(method)){
			String page=request.getParameter("currPage");
			int currPage=1;
			int pageSize=5;
			if(page!=null){
				currPage=Integer.parseInt(page);
			}
			MessageDao dao=new MessageDao();
			pageModel model=dao.findPage(currPage, pageSize);
			request.setAttribute("pageModel", model);
			request.getRequestDispatcher("message_list.jsp").forward(request, response);
		}
	}

}
