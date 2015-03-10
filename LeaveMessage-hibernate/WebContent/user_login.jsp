<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
	<c:choose>
		<c:when test="$(empty user)">
					<form action="UserServlet" method="post" onsubmit="return login(this);">
					<input type="hidden" name="method" value="userLogin">
					<table align="center" border="0" cellpadding="3" cellspacing="1" width="500">
						<tr>
							<td align="right">用户名:</td>
							<td> <input name="username" type="text" /></td>
						</tr>
						<tr>
							<td align="right">密码:</td>
							<td> <input name="password" type="password" /></td>
						</tr>
						<tr>
						<tr>
							<td colspan="2" align="center" height="50">
								<input type="submit" value="登录">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="reset" value="重置">
							</td>
						</tr>
					</table>
					</form>
		</c:when>
		<c:otherwise>
			<form action="MessageServlet" method="post" onsubmit="return message(this);">
				<input type="hidden" name="method" value="save"/>
				<table border="0" align="center" cellpadding="1" cellspacing="1">
					<tr><td>我要留言</td></tr>
					<tr><td height="10"></td></tr>
					<tr>
						<td align="right">标题</td>
						<td><input type="text" name="title" size="30" /></td>
					</tr>
					<tr>
						<td align="right">内容</td>
						<td><textarea rows="8" cols="50" name="content" ></textarea> </td>
					</tr>
					<tr>
						<td colspan="2" align="center" height="50"><input type="submit" value="留言" /></td>
					</tr>
				</table>
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>