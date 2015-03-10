package com.login.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.login.Customer.Customer;
import com.login.dao.CustomerDao;


public class FindCustomer extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		if(id!=null && !id.isEmpty()){
			CustomerDao customerDao=new CustomerDao();
			Customer customer=customerDao.findCustomerById(Integer.parseInt(id));
			request.setAttribute("customer", customer);
			System.out.println(customer);
		}
//		request.getRequestDispatcher("search_result.jsp").forward(request, response);
	}

}
