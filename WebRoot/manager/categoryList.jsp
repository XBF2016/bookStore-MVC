<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看图书分类</title>
</head>
<body>
<div>
<div style="color: red;">${message }</div>
	<table>
		<thead>
			<tr><td>图书分类名称</td></tr>
		</thead>
		<c:forEach items="${requestScope.categoryList}" var="category">
			<tr>
				<td>
					<form action="${pageContext.request.contextPath}/CategoryServlet" >
						<input type="hidden" name="action" value="update" />
						<input type="hidden" name="id" value="${category.id }" />
						<input type="text" name="categoryName" value="${category.categoryName }" />
					</form>
				</td>
				<td><a href="${pageContext.request.contextPath}/CategoryServlet?action=delete&id=${category.id }">删除</a></td>
			</tr>
		</c:forEach>
	</table>

</div>
</body>
</html>