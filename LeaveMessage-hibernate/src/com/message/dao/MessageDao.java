package com.message.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.message.model.Message;
import com.message.util.HibernateUtil;
import com.message.util.pageModel;

public class MessageDao {
	public void saveMessage(Message message){
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			session.beginTransaction();
			session.saveOrUpdate(message);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	//得到当前总的索引数
	public int getTotalRecords(Session session){
		String hql="select count(*) from Message";
		Query query=session.createQuery(hql);
		//单值索引
		Long totalRecords=(Long) query.uniqueResult();
		return totalRecords.intValue();
	}
	//页数工具
		public pageModel findPage(int currPage,int pageSize){
			Session session=null;
			pageModel model=null;
			try {
				session=HibernateUtil.getSession();
				session.beginTransaction();
				String hql="from Message m order by m.createTime desc";
				List<Message> list=session.createQuery(hql)
											.setFirstResult((currPage-1)*pageSize)
											.setMaxResults(pageSize)
											.list();
				model=new pageModel();
				model.setCurrpage(currPage);
				model.setList(list);
				model.setPageSize(pageSize);
				model.setTotalRecord(getTotalRecords(session));
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			}finally{
				HibernateUtil.closeSession();
			}
			
			return model;
		}
}
