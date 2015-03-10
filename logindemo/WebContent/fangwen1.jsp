<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站访问计数器</title>
</head>
<body>
	<%!synchronized void count() {
		ServletContext application = getServletContext();
		Integer num = (Integer) application.getAttribute("num");
		if (num == null) {
			num = new Integer(1);
			application.setAttribute("num", num);
		} else {
			num = new Integer(1 + num);
			application.setAttribute("num", num);
		}
	}%>

	<%
		if (session.isNew()) { //为了避免用户的刷新的问题
			count();
		}
		Integer tNum = (Integer) application.getAttribute("num");
	%>

	欢迎访问，您是第<%=tNum%>个访问的用户！
</body>
</html>
