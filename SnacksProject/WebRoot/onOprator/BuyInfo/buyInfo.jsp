<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'buyInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
	<link rel="stylesheet" type="text/css" href="/SnacksProject/static/LoginInCss.css">
	<style type="text/css">
		body{
			display:block;
			margin:0px auto;
			width:1000px;
		}
		#menu li{
			display:inline-block;
		}
		section{
			font-size:14px;
			width:800px;
			height:500px;
			border:1px black solid;
		}
	
	</style>
  </head>
  
  <body>
      
    <header>
		<%@include file="/static/LoginInHeader.jsp" %>
	<header>
	<section>
		
		<c:out value="${sessionScope.buyInfo }"/>
	</section>
	
	       <hr/>
<%@include file="/static/footer.jsp" %> 
	
	
  </body>
</html>
