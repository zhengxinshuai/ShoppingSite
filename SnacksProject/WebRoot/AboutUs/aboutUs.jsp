<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>关于我们</title>
    <link href="aboutUs.css" rel="stylesheet" type="text/css"/>
      <link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/LoginInCss.css">
</head>
<body>
<header>
		<%@include file="/static/LoginInHeader.jsp" %>
	<header>
    <div class="about_us">
        <div class="bar"></div>
        <div class="about_us_menu">
            <ul>
                <li class="onclock" onclick="web_msg()">网站详情</li>
                <li  onclick="producer()">制作人员</li>
                <li  onclick="demeanor()">团队风采</li>
                <li  onclick="aim()">我的目标</li>
                <li  onclick="service_tenet()">服务宗旨</li>
            </ul>
        </div>
        <div>
        <!--网站详情页面-->
        <div class="web_msg" >
            <img src="/SnacksProject/AboutUs/img/logo.png">
            <p>我们豪哥哥零食售卖系统是最新兴起的轻量型外卖产业，我们专业零售零食。</p>
            <p>我们自从国庆末发布了《豪哥哥零食系统1.0》之后，受到了一致好评，后因操作繁琐，我们开发了2.0版本。</p>
            <p>《豪哥2.0版本》现已经正式上线！</p>
            <p>相对1.0版本，我们增加了用户操作页面，用户可以更加简洁明了的访问我们的页面，可以看到零食的图片，以便更好的挑选，下单收货也一目了然。</p>
            <p>并且相对于之前的版本，我们的零食<span>更加便宜啦！</span></p>
        </div>
        <!--制作人员页面-->
        <div class="producer">
           <div class="producer_one">
               <img src="img/wuyanzu.jpg">
               <p>郑鑫帅</p>
           </div>
           <div class="producer_two">
               <img src="img/wuyanzu.jpg">
               <p>林荣</p>
           </div>
        </div>
        <!--团队风采页面-->
        <div class="demeanor" >
            如要提出意见，请拨打电话12345678联系我们！<br/>
            我们欢迎你的来电。
       
        </div>
        <!--我的目标页面-->
        <div class="aim">
            我们的目标：冲出亚洲，走向世界，成为零食外卖届的老大！
        </div>
        <!--服务宗旨页面-->
        <div class="service_tenet">
            我们本着，简易操作，却又不失专业性，价格公道，童叟无欺。<br/>
            让老人小孩都能对这款产品上手，为他们带来美味的零食，增添生活的乐趣。
        </div>
        </div>
    </div>
          
</body>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript" >
    $(".about_us_menu ul li").click(function(){
        $(this).addClass("onclock").siblings().removeClass("onclock");
    });
    function web_msg(){
        $(".web_msg").show();
        $(".producer").hide();
        $(".demeanor").hide();
        $(".aim").hide();
        $(".service_tenet").hide();
    }
    function producer(){
        $(".web_msg").hide();
        $(".producer").show();
        $(".demeanor").hide();
        $(".aim").hide();
        $(".service_tenet").hide();
    }
    function demeanor(){
        $(".web_msg").hide();
        $(".producer").hide();
        $(".demeanor").show();
        $(".aim").hide();
        $(".service_tenet").hide();
    }
    function aim(){
        $(".web_msg").hide();
        $(".producer").hide();
        $(".demeanor").hide();
        $(".aim").show();
        $(".service_tenet").hide();
    }
    function service_tenet(){
        $(".web_msg").hide();
        $(".producer").hide();
        $(".demeanor").hide();
        $(".aim").hide();
        $(".service_tenet").show();
    }
</script>
</html>