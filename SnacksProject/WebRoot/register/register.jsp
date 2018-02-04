<%@page import="com.dfbb.entity.Province"%>
<%@page import="com.dfbb.serverImpl.AddressServerImpl"%>
<%@page import="com.dfbb.server.AddressServer"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>豪哥哥用户注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="login/css/less.css">
<link rel="stylesheet" type="text/css" href="login/css/basic.css">
<link rel="stylesheet" type="text/css" href="login/css/style.css">
<link rel="stylesheet" type="text/css" href="register/registercss.css">

</head>

  

<body>
   		<%@include file="/static/head.jsp" %>
   		<section>
   		
   			<div id="border">
   				<h1>注册</h1>
   			<form action="/SnacksProject/userServlet?opr=register" method="post" enctype="multipart/form-data" id="form" multiple="multiple">
		<p>
			<span class="needStar">*</span>用户名：<input type="text" name="userName"
				class="text" id="userName" placeholder="字母开头,8-15位字母或数字"><span
				id="unameFeedback" class="ftext"></span>
		</p>
		<p>
			<span class="needStar">*</span>密码：<input type="password"
				name="password" class="text" id="password" placeholder="6-12位字母或数字"><span
				id="pwdFeedback" class="ftext"></span>
		</p>
		<p>
			真实姓名：<input type="text"
				name="realName" class="text" id="password" placeholder="真实姓名(可选)">
		</p>
		
		<p>
			上传头像：<input type="file" name="file" value="点我上传" class="otherInput"><span></span>
		</p>
		<p>
			<span class="needStar">*</span>生日：<input type="text" name="birthday"
				placeholder="格式例如：1995-10-23" class="text" id="birthday"><span
				class="ftext" id="birthFeedback"></span>
		</p>
		<p>
			<span class="needStar">*</span>性别: <label class="otherInput">&nbsp;男：<input
				type="radio" name="sex" value="M">&nbsp;女：<input
				type="radio" name="sex" value="F"></label>
		</p>
		<p>
			<span class="needStar">*</span>手机号码：<input type="text"
				name="phoneNum" placeholder="不小于11位" class="text" id="phoneNum"><span
				class="ftext" id="phoneNumFeedback"></span>
		</p>
		<p>
   					<span class="needStar">*</span>省份：<select name="province" id="s1" class="otherInput">
   						<option value="empty">---请选择</option>
				<c:if test="${requestScope.provinces!=null}">
					<c:forEach items="${requestScope.provinces}" var="temp">
						<option value="${temp.provinceId}">${temp.province}</option>
					</c:forEach>
				</c:if>
			</select><br/>
   					<span class="needStar">*</span>城市：<select name="city" id="s2" class="otherInput">
   						<option value="empty">---请选择</option>
   					</select><br/>
   					<span class="needStar">*</span>区域：<select name="area" id="s3" class="otherInput">
   						<option value="empty">---请选择</option>
   					</select><br/>
   					<p><span class="needStar">*</span>具体街道:<input type="text" class="text" name="street"> </p>
   				</p>
   				<p><input type="submit" value="立即注册" id="submit"></p>
   				<p>
   					<c:if test="${sessionScope.msg!=null}">
   						<span id="msg">
   							<c:out value="${sessionScope.msg}"/>
   							<c:set var="msg" value="" scope="session"/>
   						</span>
   					</c:if>
   				</p>
   			</form>
   			
   			
   			</div>
   		</section>
   		<%@include file="/static/footer.jsp" %>
   		
		
   		<script type="text/javascript" src="static/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="/SnacksProject/register/js/address.js"></script>
	<script type="text/javascript" src="/SnacksProject/register/js/check.js"></script>
  </body>
</html>
