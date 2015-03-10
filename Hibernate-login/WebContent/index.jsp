<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="CustomerServlet" method="post" onsubmit="return save();">
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
				<td align="right">确认密码:</td>
				<td> <input name="password" type="password" /></td>
			</tr>
			<tr>
				<td align="right">年龄:</td>
				<td> <input name="age" type="text" /></td>
			</tr>
			<tr>
				<td align="right">性别:</td>
				<td>
					<input name="sex" type="radio" value="1" checked="checked">男
					<input name="sex" type="radio" value="0">女
				</td>
			</tr>
			<tr>
				<td align="right">描述:</td>
				<td><textarea rows="5" cols="30" name="description"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center" height="50">
					<input type="submit" value="注册">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>