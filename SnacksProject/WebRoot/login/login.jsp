<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<%-- <base href="<%=basePath%>"> --%>
<title>用户登录</title>

<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" type="text/css" href="/SnacksProject/login/css/less.css">
<link rel="stylesheet" type="text/css" href="/SnacksProject/login/css/basic.css">
<link rel="stylesheet" type="text/css" href="/SnacksProject/login/css/style.css">
<script src="/SnacksProject/static/jquery-1.9.0.min.js" ></script>
<script src="/SnacksProject/login/css/jquery.js" ></script>
	<script src="/SnacksProject/static/jquery.toggle-password.min.js"></script>
	
	
	
<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js"></script>  
   
<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.min.js"></script>  
</head>
        <script language="javascript">  
        
        
        
        
function myReload() {  //重新加载验证码
    document.getElementById("CreateCheckCode").src = document  
            .getElementById("CreateCheckCode").src  
            + "?nocache=" + new Date().getTime();  
}  
</script>  

<body>
	<div class="wrapper" style="background-color: white;">
		<div class="login-top">
			<!-- <div style="height: 60px;background-color: white;">
				<div style=";margin-left: 160px;">
					<img src="images/login_bg_black.png" />
					<span id="title">豪哥哥食品专卖</span>
				</div>
				<header
					style="float:right;margin-top: -34px;width: 360px;font-size: 12px;">
					<span>返回首页</span> <span>|</span> <span>忘记密码</span> <span>|</span> <span>在线客服</span>
				</header>
			</div> -->
			<div style="height: 60px;background-color: white;">
	<div style=";margin-left: 160px;">
		<!-- <img src="images/login_bg_black.png" /> -->
		<span id="title">食品专卖</span>
	</div>
	<header
		style="float:right;margin-top: -34px;width: 360px;font-size: 12px;">
		<a href="/SnacksProject/register/register.jsp">立即注册</a> <span>|</span> <span>忘记密码</span> <span>|</span> 
		
		<a href="#"><span>联系我们</span></a>
	</header>
</div>

			<div class="login-topBg">
				<div class="login-topBg1">

					<div class="login-topStyle">

						<div class="login-topStyle3" id="jqk"
							style="display:block;margin-top: 75px;">
							<div style="float:left; width:50%; height:50px">
								<h3>用户登录</h3>
							</div>
							<div style="float:right;margin-top: 12px;cursor:pointer;"
								onclick="f_test()">管理员登陆</div>
							<form action="/SnacksProject/userServlet?opr=login" method="post" id="myForm">
								
								<div class="ui-form-item loginUsername">
									<input type="username" placeholder="请输入用户名" name="username">
								</div>

								<div class="ui-form-item loginPassword">
									<input type="password" placeholder="请输入密码" id="pwd" name="pwd">
								</div>

								<div class="ui-form-item loginPassword">
									<input type="text" style="width:130px" placeholder="验证码" name="checkCode">
									<img src="/SnacksProject/PictureCheckCode"
										style="height:33px;float:right;" onclick="myReload()" id="CreateCheckCode">
								</div>

								<div class="login_reme">
									<input type="checkbox" name="isRemember"> <a class="reme1"
										style="color:#ffffff;">十分钟免登陆</a> <br/>
										<input type="checkbox" name="rememberPwd" id="togglePassword"><a class="reme1" style="color:#ffffff;">&nbsp;显示密码</a><a class="reme2" href="#">忘记密码?</a>
								</div>
								<c:if test="${sessionScope.msg!=null and sessionScope.msg!=''}">
									 <span class="error_xinxi">
									 	<c:out value="${sessionScope.msg}"/>
									 </span> 
							
									<c:set var="msg" value="" scope="session"/>
								</c:if>
								<c:if test="${sessionScope.msg==null or sessionScope.msg==''}">
									<span class="error_xinxi" style="display:none;"></span>
									
								</c:if>
								<input type="submit" value="立即登录"
									class="btnStyle btn-register submitBtn"> 
									
							</form>

						</div>

						<div class="login-topStyle3" id="jql"
							style="display:none;margin-top: 75px;">
							<div style="float:left; width:50%; height:50px">
								<h3>管理员登录</h3>
							</div>
							<div style="float:right;margin-top: 12px;cursor:pointer;"
								onclick="f_test()">用户登陆</div>
							<form action="/SnacksProject/ManagerServlet?opr=managerLogin" method="post" id="myFormm">
								<div class="ui-form-item loginPassword">
									<input type="text" placeholder="请输入您的管理账号" name="managerName">
								</div>

								<div class="ui-form-item loginPassword">
									<input type="password" placeholder="请输入密码" id="pwd2" name="pwd2">
								</div>

								<span class="error_xinxi" style="display:none;">您输入的密码不正确，请重新输入</span>

								<input type="submit" value="员工登录"
									class="btnStyle btn-register submitBtn">


							</form>
							<!-- <script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script> -->
							<script type="text/javascript">
								$("#myForm").submit(function(){
									var pwd= $("#pwd").val();
									pwd=md5(pwd);
									$("#pwd").val(pwd);
								})
								$("#pwd").togglePassword({
									el: '#togglePassword'
								});
								
								$("#myFormm").submit(function(){
									var pwd= $("#pwd2").val();
									pwd=md5(pwd);
									$("#pwd2").val(pwd);
								
								});
							</script>

						</div>

					</div>
				</div>
			</div>
		</div>
		<div class="loginCen" style="margin-top: 55px;">
			<div class="login-center">
				<div class="loginCenter-moudle">
					<div class="loginCenter-moudleLeft" style="margin-right: 60px;">
						&nbsp;</div>
					<div class="loginCenter-moudleRight" style="  width: 100%;">
						<!-- <div class="main_news">
							<div class="news_01">
								<div class="news_title">
									<h2>大闸蟹</h2>
									<b><a href="#" title="大闸蟹">更多>></a></b>
								</div>
								<ul>
									<li><a href="#" title="大闸蟹的10种做法">123</a></li>


								</ul>
								<div class="news_bot"></div>
							</div> -->

							<!-- <div class="news_01">
								<div class="news_title">
									<h2>猪油渣</h2>
									<b><a href="#" title="小龙虾">更多>></a></b>
								</div>
								<ul>
									<li><a href="#" title="猪油渣的10种做法">123</a></li>
								</ul>
								<div class="news_bot"></div>
							</div> -->

							<!-- <div class="news_01">
								<div class="news_title">
									<h2>方便面类</h2>
									<b><a href="#" title="鱼类">更多>></a></b>
								</div>
								<ul>
									<li><a href="#" title="康师傅牛肉面的10种美味做法">123</a></li>


								</ul>
								<div class="news_bot"></div>
							</div> -->

							<!-- <div class="news_01" id="news_011">
								<div class="news_title">
									<h2>公告</h2>
									<b><a href="#" title="本站公告">更多>></a></b>
								</div>
								<ul>
									<li class="r"><a href="#" title="中秋节放假通知">中秋节放假通知</a></li>



								</ul>
								<div class="news_bot"></div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="/static/footer.jsp" %>
	</div>
</body>
</html>

