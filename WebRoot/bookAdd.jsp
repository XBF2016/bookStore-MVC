<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加图书</title>
</head>
<body>
<div>
	<form action="${pageContext.request.contextPath}/BookServlet?action=add" method="post" enctype="multipart/form-data">
		<div style="color: red;">${message }</div>
		<table>
			<tr>
				<td>书名</td><td><input type="text" name="bookName" /></td>
			</tr>
			<tr>
				<td>作者</td><td><input type="text" name="author" /></td>
			</tr>
			<tr>
				<td>类别</td>
				<td>
					<select name="categoryId">
					<option value="8eddb0ef-4938-46e0-b58c-b1880742cc4a">情感</option>
						<c:forEach items="${categoryList}" var="category">
							<option value="${category.id }">${category.categoryName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>价格</td><td><input type="text" name="price" /></td>
			</tr>
			<tr>
				<td>封面</td><td><input type="file" name="imagePath" /></td>
			</tr>
		<tr>
				<td></td><td><input type="submit" value="添加" /></td>
			</tr>
		</table>
	</form>

</div>
</body>
</html>