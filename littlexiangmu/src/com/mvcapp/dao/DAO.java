package com.mvcapp.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mvcapp.db.jdbcUtils;

/**
 * @author melody
 *封装了基本的DAO操作方法，以供子类继承使用
 *当前dao直接在方法中获取数据库连接
 *整个dao采取dbutils解决方案
 * @param <T>:当前DAO处理的实体类的类型
 */
public class DAO<T> {
	
	private QueryRunner queryRunner=new QueryRunner();
	
	private Class<T> clazz;
	
	public DAO(){
		Type superClass=getClass().getGenericSuperclass();
		if(superClass instanceof ParameterizedType){
			ParameterizedType parameterizedType=(ParameterizedType) superClass;
			Type [] typeArgs=parameterizedType.getActualTypeArguments();
			if(typeArgs!=null && typeArgs.length>0){
				if(typeArgs[0] instanceof Class){
					clazz=(Class<T>) typeArgs[0];
				}
			}
		}
	}
	
	/**
	 * 返回某一字段的值，例如返回某一记录的customerName
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql,Object ...args){
		Connection connection=null;
		try {
			connection =jdbcUtils.getConnection();
			return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 *返回T对应的List 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getForList(String sql,Object ... args){
		Connection connection=null;
		try {
			connection =jdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 * 返回对应的T的一个实体类的对象
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql,Object ... args){
		Connection connection=null;
		try {
			connection =jdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	/**
	 * 封装饿了Insert delete update 操作
	 * @param sql
	 * @param args ：填充sql语句的占位符
	 */
	public void update(String sql,Object ... args){
		Connection connection=null;
		try {
			connection =jdbcUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jdbcUtils.releaseConnection(connection);
		}
	}
}
