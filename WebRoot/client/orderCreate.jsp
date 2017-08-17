<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head></head>
  <body>
  
  <!-- 引入头部页面 -->
	<jsp:include page="/client/top.jsp"></jsp:include>

	
<div style="margin-left: 30%">

<form style="margin-top: 50px;" action="${pageContext.request.contextPath }/OrderServlet">
  	<input type="hidden" name="action" value="confirm" />
<!-- 地址信息 -->
<div style="margin-bottom: 100px;">
选择收货地址<br/>
<c:forEach items="${addressList}" var="address">
	<input style="margin: 10px;" type="radio" name="addressId" value="${address.id }" />${address }<br/>
</c:forEach>
<a href="${pageContext.request.contextPath }/client/addressAdd.jsp" >添加地址</a>
</div>

  <table border="1" cellpadding="5" cellspacing="0" >
  	<caption>订单信息</caption>
  	<thead>
  		<tr><td>书名</td><td>数量</td><td>总价格</td></tr>
	</thead>
	<c:set var="totalPrice" value="${0}"></c:set>
	<c:set var="totalBook" value="${0}"></c:set>
	<c:set var="totalCount" value="${0}"></c:set>
	<c:forEach items="${cart.cartItemList}" var="cartItem">
			<tr>
				<td>${cartItem.book.bookName }</td>
				<td>${cartItem.bookCount }</td>
				<td>${cartItem.book.price * cartItem.bookCount }</td>
				<c:set var="totalBook" value="${totalBook + 1}"></c:set>
				<c:set var="totalCount" value="${totalCount + cartItem.bookCount }"></c:set>
				<c:set var="totalPrice" value="${totalPrice + cartItem.book.price * cartItem.bookCount }"></c:set>
			</tr>
	</c:forEach>
			<tfoot>
			<tr>
				<td>共 ${totalBook } 种书</td>
				<td>共 ${totalCount } 本书</td>
				<td>共 ${totalPrice } ￥</td>
			</tr>
			</tfoot>
  </table>

  	<a href="${pageContext.request.contextPath }/BookServlet?action=pageClient">返回继续购物</a>
  	<input type="submit"  value="确认生成订单" />
  </form>
  </div>
  </body>
</html>
