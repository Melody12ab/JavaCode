<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="a" scope="session" class="fangwen.addone"/>
<jsp:useBean id="b" scope="page" class="fangwen.display"/>
已经有<%b.counter();
    for(int i=9;i>=0;--i)
    {
        out.print(b.img[i]);
    }
    
 %>人访问该网页，欢迎您！
</body>
</html>