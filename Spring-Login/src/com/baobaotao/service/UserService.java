package com.baobaotao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baobaotao.dao.LoginLogDao;
import com.baobaotao.dao.UserDao;
import com.baobaotao.domain.LoginLog;
import com.baobaotao.domain.User;
import com.mysql.jdbc.log.Log;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginLogDao loginLogDao;
	
	public boolean hasMatchUser(String userName,String password){
		int matchCount=userDao.getMatchCount(userName, password);
		return matchCount > 0;
	}
	
	public User findUserByUserName(String userName){
		return userDao.findUserByUserName(userName);
	}
	
	public void loginSuccess(User user){
		user.setCredits(5+user.getCredits());
		LoginLog loginLog=new LoginLog();
		loginLog.setUserId(user.getUserId());
		loginLog.setIp(user.getLastip());
		loginLog.setLoginDate(user.getLastVisit());
		userDao.updateLoginInfo(user);
		loginLogDao.insertLoginLog(loginLog);
	}
}
