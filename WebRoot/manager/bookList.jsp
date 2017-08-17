<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看图书</title>
</head>
<body>
<div>
<form action="${pageContext.request.contextPath}/BookServlet"  method="post">
			<input type="hidden" name="action" value="update" />
			<input type="hidden" name="id" value="${book.id }" />
	<div style="color: red;">${message }</div>
	<table border="1px">
		<thead>
			<tr><td>图书名称</td><td>作者</td><td>价格</td><td></td></tr>
		</thead>
		<c:forEach items="${requestScope.bookList}" var="book">
			<tr>
				<td>
						${book.bookName }
				</td>
				<td>
						${book.author }
				</td>
				<td>
						${book.price }
				</td>
				<td>
					<a href="BookServlet?action=detail&id=${book.id }">查看详情</a>
					<a href="BookServlet?action=delete&id=${book.id }">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</form>
</div>
</body>
</html>