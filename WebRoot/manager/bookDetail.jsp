<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看图书详情</title>
</head>
<body>
<div>
<form action="${pageContext.request.contextPath}/BookServlet"  method="post">
			<input type="hidden" name="action" value="update" />
			<input type="hidden" name="id" value="${book.id }" />
			<input type="hidden" name="imagePath" value="${book.imagePath }" />
	<div style="color: red;">${message }</div>
	<img width="250"   src="${pageContext.request.contextPath}/${book.imagePath }">		
	<table>
			<tr>
				<td>名称</td>
				<td>
						<input type="text" name="bookName" value="${book.bookName }" />
				</td>
			</tr>
			<tr>
				<td>作者</td>
				<td>
						<input type="text" name="author" value="${book.author }" />
				</td>
			</tr>
			<tr>
				<td>图书类别</td>
				<td>
						<select name="categoryId">
							<c:forEach items="${categoryList}"  var="category">
								<c:if test="${book.categoryId == category.id}">
									<option selected="selected" value="${category.id }">${category.categoryName }</option>
								</c:if>
								<c:if test="${book.categoryId != category.id}">
									<option  value="${category.id }">${category.categoryName }</option>
								</c:if>
							</c:forEach>
						</select>
				</td>
			</tr>
			<tr>
				<td>价格</td>
				<td>
						<input type="text" name="price" value="${book.price }" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
						<input type="submit"  value="修改" />
				</td>
			</tr>
	</table>
</form>
</div>
</body>
</html>