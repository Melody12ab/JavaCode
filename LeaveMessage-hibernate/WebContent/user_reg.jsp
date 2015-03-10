<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
</head>
<body>
	<form action="UserServlet" method="post" onsubmit="return reg(this);">
		<input name="method" value="guestReg" type="hidden"/>
		<table align="center" width="100%" border="0" bgcolor="#c1c1c1" cellpadding="1" cellspacing="1">
			<tr>
				<td colspan="2" align="center" height="30" bgcolor="#941F53">
					用户注册
				</td>
			</tr>
			<tr bgcolor="#fafafa" height="30">
				<td align="right">用户名：</td>
				<td>
					<input type="text" name="username" /><font color="red">*</font>
					不能有空格，可以使中文，长度在3-50字节以内
				</td>
			</tr>
			<tr bgcolor="#fafafa">
				<td align="right">密码：</td>
				<td>
					<input type="password" name="password" /><font color="red">*</font>
					英文字母或者是数字，不能少于6位
				</td>
			</tr>
			<tr bgcolor="#fafafa">
				<td align="right">确认密码：</td>
				<td>
					<input type="password" name="password" /><font color="red">*</font>
				</td>
			</tr>
			<tr bgcolor="#fafafa">
				<td align="right">电子邮件：</td>
				<td>
					<input type="text" name="email" /><font color="red">*</font>
					空开邮箱
				</td>
			</tr>
			<tr bgcolor="#fafafa">
				<td colspan="2" align="center" height="50">
					<input type="submit" value="注册">
					<input type="reset" value="重置">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>