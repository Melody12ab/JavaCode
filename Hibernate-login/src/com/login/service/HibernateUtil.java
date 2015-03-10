package com.login.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	//保证session安全，避免多个线程之间的数据共享
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static SessionFactory sessionFactory = null;

	// 静态初始化块
	static {
		try {
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
								.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.out.println("创建工厂方法失败");
			e.printStackTrace();
		}
	}

	// 获取session
	public static Session getSession() {
		Session session = threadLocal.get();
		if (session == null || session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}
		return session;
	}

	// 重建会话工厂
	public static void rebuildSessionFactory() {
		try {
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
								.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.out.println("创建工厂方法失败");
			e.printStackTrace();
		}
	}

	//获取sessionFactory对象
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	//关闭session
	public static void closeSession(){
		Session session=threadLocal.get();
		threadLocal.set(null);
		if(session!=null){
			session.close();
		}
	}

}
