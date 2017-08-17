<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <body>
  
  <!-- 引入头部页面 -->
		<jsp:include page="/client/top.jsp"></jsp:include>
	<div style="margin-left: 30%">
  <table border="1" cellpadding="5" cellspacing="0" >
  	<caption>购物车</caption>
  	<thead>
  		<tr><td>书名</td><td>数量</td><td>总价格</td><td></td></tr>
	</thead>
	<c:forEach items="${cart.cartItemList}" var="cartItem">
			<tr>
				<td>${cartItem.book.bookName }</td>
				<td>
					<form action="${pageContext.request.contextPath }/CartServlet">
						<input type="hidden" name="action" value="update" />
						<input type="hidden" name="bookId" value="${cartItem.book.id }" />
						<input type="text" name="bookCount" value="${cartItem.bookCount }" />
						<input type="submit"  value="修改数量" />
					</form>
				</td>
				<td>${cartItem.book.price * cartItem.bookCount }</td>
				<td><a href="${pageContext.request.contextPath }/CartServlet?action=delete&bookId=${cartItem.book.id}">删除</a></td></tr>
	</c:forEach>
  </table>
  <form style="margin-top: 30px;" action="${pageContext.request.contextPath }/OrderServlet">
  	<input type="hidden" name="action" value="create" />
  	
  	<input type="submit" value="去结算" />
  </form>
  </div>
  </body>
</html>
