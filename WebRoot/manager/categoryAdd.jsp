<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加图书分类</title>
</head>
<body>
<div>
	<form action="${pageContext.request.contextPath}/CategoryServlet" method="post">
		<div style="color: red;">${message }</div>
		<input type="hidden" name="action" value="add" />
		<label>请输入新的图书分类名称</label>
		<input type="text" name="categoryName" /><br/>
		<input type="submit" value="添加" />
	</form>

</div>
</body>
</html>