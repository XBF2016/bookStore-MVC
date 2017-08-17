<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加地址信息</title>
</head>
<body>

  <!-- 引入头部页面 -->
		<jsp:include page="/client/top.jsp"></jsp:include>
<a href="${pageContext.request.contextPath }/OrderServlet?action=create">返回</a>
<div style="margin-left: 30%">
	
	<form action="${pageContext.request.contextPath }/AddressServlet" method="post">
		<input type="hidden"  name="action" value="add"/>
		<table>
			<tr>
				<td>地址 </td><td><input type="text" name="location" /></td>
			</tr>
			<tr>
				<td>收件人 </td><td><input type="text" name="userName" /></td>
			</tr>
			<tr>
				<td>手机号码 </td><td><input type="text" name="phone" /></td>
			</tr>
			<tr>
				<td></td><td><input type="submit" value="添加" /></td>
			</tr>
		</table>
	</form>

</div>
</body>
</html>