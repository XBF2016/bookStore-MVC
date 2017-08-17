<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div style="margin: 10%;">
<label>图书分类管理</label><br/>
<a target="contentFrame"  href="${pageContext.request.contextPath}/manager/categoryAdd.jsp" >添加图书分类</a><br/>
<a target="contentFrame"  href="${pageContext.request.contextPath}/CategoryServlet?action=list" >查看图书分类</a><br/>

<label>图书管理</label><br/>
<a target="contentFrame"  href="${pageContext.request.contextPath}/manager/bookAdd.jsp" >添加图书</a><br/>
<a target="contentFrame"  href="${pageContext.request.contextPath}/BookServlet?action=list" >查看图书</a><br/>

<label>订单管理</label><br/>
<a target="contentFrame"  href="${pageContext.request.contextPath}/OrderServlet?action=list" >查看订单</a><br/>
</div>

</body>
</html>