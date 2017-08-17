<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书商城后台</title>
</head>
<frameset rows="15%,85%"  style="border: 1px;" >

	<frame src="${pageContext.request.contextPath }/manager/top.jsp"  scrolling="no"/>
	
	<frameset cols="20%,80%" style="margin: 10%;">
		<frame src="${pageContext.request.contextPath }/manager/left.jsp" ></frame>
		<frame name="contentFrame"></frame>
	</frameset>
	
</frameset>
</html>