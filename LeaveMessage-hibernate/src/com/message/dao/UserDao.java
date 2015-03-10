package com.message.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import com.message.model.User;
import com.message.util.HibernateUtil;

public class UserDao {
	
	//保存用户
	public void saveUser(User user){
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	//更具用户名查询用户  判断是否已经注册
	public boolean findUserByName(String username){
		Session session=null;
		boolean exist=false;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			String hql="from User u where u.username=?";
			Query query=session.createQuery(hql)
								.setParameter(0, username);
			Object user=query.uniqueResult();
			if(user!=null){
				exist=true;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
		return exist;
	}
	
	//查找用户  用来做验证
	public User findUser(String username,String password){
		Session session=null;
		User user=null;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			String hql="from User u where u.username=? and u.password=?";
			Query query=session.createQuery(hql)
								.setParameter(0, username)
								.setParameter(1, password);
			user=(User) query.uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
		return user;
	}
	
}
