package com.login.dao;

import org.hibernate.Session;

import com.login.Customer.Customer;
import com.login.service.HibernateUtil;

public class CustomerDao {
	//保存Customer对象
	public void saveCustomer(Customer customer){
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			session.save(customer);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	//保存或者更新对象
	public void saveOrUpdateCustomer(Customer customer){
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			session.saveOrUpdate(customer);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	//查询学生信息
	public Customer findCustomerById(Integer id){
		Session session=null;
		Customer customer=null;;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			customer=(Customer) session.get(Customer.class, id);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
		return customer;
	}
	
}
