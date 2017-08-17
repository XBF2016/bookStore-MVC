<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <body>
  
<!-- 引入头部页面 -->
<jsp:include page="/client/top.jsp"></jsp:include>


<!-- ------------------------------------------------------------- -->
<div style="margin-left: 10%">
  <table border="1" cellpadding="5" cellspacing="0" >
  	<caption>订单列表</caption>
  	<thead>
  		<tr><td>订单编号</td><td>订单价格</td><td>收件地址</td><td>订单状态</td><td>详情</td></tr>
	</thead>
	<c:forEach items="${orderList}" var="order">
			<tr>
				<td>${order.orderNumber }</td>
				<td> ${order.totalPrice} </td>
				<td>${addressMap[order.id] }</td>
				<td>${order.status}</td>
				<td>
				<!-- 每个订单的订单项列表 -->
					<table>
					<tr> <td>书名</td> <td> 总价格 </td> <td>数量</td> </tr>
					<c:forEach items="${orderItemListMap[order.id]}" var="orderItem">
					<tr >
						<td>${orderItem.bookName }</td>
						<td> ${orderItem.totalPrice} </td>
						<td>${orderItem.bookCount}</td>
					</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
	</c:forEach>
  </table>
  </div>
  </body>
</html>
