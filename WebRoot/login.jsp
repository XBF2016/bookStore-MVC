<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
<a href="index.jsp">返回首页</a>
<a href="register.jsp">注册</a>
<div style="margin-left: 30%">
	<div style="color: red;">${message }</div>
	<form action="${pageContext.request.contextPath }/" method="post">
		<input type="hidden"  name="action" value="login"/>
		<input type="hidden" name="path" value="${requestScope.path }" />
		<table>
			<tr>
				<td>用户名 </td><td><input type="text" name="userName" /></td>
			</tr>
			<tr>
				<td>密码 </td><td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td></td><td><input type="submit" value="登录" /></td>
			</tr>
		</table>
	</form>

</div>
</body>
</html>