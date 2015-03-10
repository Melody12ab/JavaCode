package com.mvcapp.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.mvcapp.dao.CustomerDAO;
import com.mvcapp.dao.impl.CustomerDAOjdbcImpl;
import com.mvcapp.domain.Customer;

public class CustomerDAOjdbcImplTest {
	
	private CustomerDAO customerDAO=new CustomerDAOjdbcImpl();
	
	@Test
	public void testGetAll() {
		List<Customer> customers=customerDAO.getAll();
		System.out.println(customers);
	}

	@Test
	public void testSave() {
		Customer customer=new Customer();
		customer.setAddress("上海");
		customer.setName("John");
		customer.setPhone("13654632367");
		customerDAO.save(customer);
	}

	@Test
	public void testGetInteger() {
		Customer cust=customerDAO.get(1);
		System.out.println(cust);
	}

	@Test
	public void testDelete() {
		customerDAO.delete(1);
	}

	@Test
	public void testGetCountWithName() {
		Long count=customerDAO.getCountWithName("John");
		System.out.println(count);
	}

}
