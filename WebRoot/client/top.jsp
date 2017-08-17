 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8" import="bs.domain.User"%>
 <div >
    	<h1 align="center">图书商城</h1>
    	<div style="float: left;margin-left: 10%;">
    	<%
    		User user =(User) request.getSession().getAttribute("user");
    		if(user==null){
    			%><a href="login.jsp">登录</a> <a href="register.jsp">注册</a><%
    		}else{
    			%>欢迎你 , <%=user.getUserName()%> ,<a href="UserServlet?action=logout">退出</a> <%
    		}
    	%>
    	</div>
    	<div style="color: red;float: left;margin-left: 10%;">${message }</div>
    	<div style="float: right;margin-right: 10%;"><a href="${pageContext.request.contextPath}/index.jsp">返回首页</a> <a href="${pageContext.request.contextPath}/client/cartList.jsp">查看购物车</a> <a href="${pageContext.request.contextPath}/OrderServlet?action=list">查看订单</a></div>
    	<div style="clear: both;"></div>
</div>
<hr/>