<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="FindCustomer" method="post" onsubmit="return find();">
		<table align="center" border="0" cellpadding="3" cellspacing="1" width="500">
			<tr>
				<td align="right">学号：</td>
				<td>
					<select name="id">
						<option value="-1" selected="selected">请选择号码</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
					<input type="submit" value="查询">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>