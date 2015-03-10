package com.baobaotao.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baobaotao.domain.User;
import com.baobaotao.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/index.html")
	public String loginPage(){
		return "login";
	}
	
	public ModelAndView loginCheck(HttpServletRequest request,LoginCommand loginConmand){
		boolean isValidUser=userService.hasMatchUser(loginConmand.getUserName(), loginConmand.getPassword());
		if(!isValidUser){
			return new ModelAndView("login","error","用户名或者密码错误");
		}else{
			User user=userService.findUserByUserName(loginConmand.getUserName());
			user.setLastip(request.getRemoteAddr());
			user.setLastVisit(new Date());
			userService.loginSuccess(user);
			request.getSession().setAttribute("user", user);
			return new ModelAndView("main");
		}
	}
}
