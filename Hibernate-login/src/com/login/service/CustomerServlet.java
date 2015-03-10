package com.login.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.login.Customer.Customer;
import com.login.dao.CustomerDao;


public class CustomerServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String sex=request.getParameter("sex");
		String age=request.getParameter("age");
		String desc=request.getParameter("description");
		
		System.out.println(username+" "+password+" "+sex+" "+age+" "+desc);
		if(username!=null && password!=null){
			Customer customer=new Customer();
			customer.setUsername(username);
			customer.setPassword(password);
			if(sex!=null){
				customer.setSex(sex.equals("1") ? true : false);
			}
			customer.setAge(Integer.parseInt(age));
			customer.setDescription(desc);
			CustomerDao customerDao=new CustomerDao();
			customerDao.saveCustomer(customer);
			request.setAttribute("info", "恭喜你，注册成功！");
		}
		request.getRequestDispatcher("result.jsp").forward(request, response);
	}
}
