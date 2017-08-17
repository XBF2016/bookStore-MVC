<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body>
<a href="index.jsp">返回首页</a>
<div style="margin-left: 30%">
	
	<form action="UserServlet" method="post">
		<input type="hidden"  name="action" value="register"/>
		<font color="red">${requestScope.message }</font>
		<table>
			<tr>
				<td>用户名 </td><td><input type="text" name="userName" /></td>
			</tr>
			<tr>
				<td>密码 </td><td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td>重复密码 </td><td><input type="password" name="repassword" /></td>
			</tr>
			<tr>
				<td></td><td><input type="submit" value="注册" /></td>
			</tr>
		</table>
	</form>

</div>
</body>
</html>