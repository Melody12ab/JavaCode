package com.baobaotao.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.baobaotao.domain.User;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int getMatchCount(String username, String password) {
		String sql = "select count(*) from t_user where user_name=? and password=?";
		return jdbcTemplate.queryForInt(sql,
				new Object[] { username, password });
	}

	public User findUserByUserName(final String username){
		String sql="select user_id,user_name,credits from t_user where user_name=?";
		final User user=new User();
		jdbcTemplate.query(sql, new Object[]{username},
				new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(username);
				user.setCredits(rs.getInt("credits"));
			}
		});
		return user;
	}
	
	public void updateLoginInfo(User user){
		String sql="update t_user set last_visit=?,last_ip=?,credits=? where user_id=?";
		jdbcTemplate.update(sql, new Object[]{user.getLastVisit(),user.getLastip(),
				user.getCredits(),user.getUserId()});
	}
}
