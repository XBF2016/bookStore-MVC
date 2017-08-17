 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8" import="bs.domain.User"%>
 <script type="text/javascript">
 function closeWindow(){
		window.parent.location = "${pageContext.request.contextPath}/UserServlet?action=logout&"+new Date().getTime();
}
</script>
 <div >
    	<h1 align="center">图书商城后台</h1>
    	<div style="color: red;float: left;margin-left: 10%;">${message }</div>
    	<div style="float: right;margin-right: 10%;">
    	
    	<%
    		User user =(User) request.getSession().getAttribute("user");
    		if(user==null){
    			%><jsp:forward page="index.jsp"></jsp:forward><%
    		}else{
    			%>欢迎你 , <%=user.getUserName()%> ,<a href="#" onclick="closeWindow()">退出</a> <%
    		}
    	%>
    	
    	</div>
    	<span style="clear: both;"></span>
</div>
