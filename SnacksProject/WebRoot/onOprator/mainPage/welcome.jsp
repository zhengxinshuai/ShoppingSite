<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>onOprator/mainPage/">
   
    <title>豪哥哥零食商城</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="/SnacksProject/onOprator/mainPage/css/welcome.css">
	<link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">

  </head>
  
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/SnacksControll.js"></script>

<body>
        <!--网页头部 用户登录注册等用户操作菜单-->
    <header>

		<%@include file="/static/LoginInHeader.jsp" %>
		<div id="search">
			<!--logo和搜索框-->
			<div id="logo">
				<img src="/SnacksProject/static/img/logo.png" class="logo">
			</div>
			<div id="searchInput">
				<input type="text" placeholder="请输入您要搜索的内容" class="search1" id="content">
				<a class="search2">搜好吃的</a>
			</div>
		</div>
	</header>

    <!--网页主体部分-->
    <section>
        <div id="left">
            <ul>
                    <li><a href="javascipr:void(0)"  onclick="return onClassSearchClick(this)">饼干</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">肉</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">香辣丝</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">凤爪</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">鱿鱼丝</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">华夫饼</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">凤梨酥</a></li>
                    <li><a href="javascipr:void(0)" onclick="return onClassSearchClick(this)">麦丽素</a></li>
            </ul>
        </div>
        <div class="banner">
            <!--
     这里为上一页下一页点击按钮
    -->
            <span class="pre"><</span>
            <span class="next">></span>
            <!--
             此处为轮播主体，颜色块代替。图片自加
            -->
            <c:forEach items="${sessionScope.newestSnacks}" var="temp">
            	<a href="/SnacksProject/SnackServlet?opr=snackDetail&sid=${temp.sid}" class="first" ><img src="${temp.iconPath}"/></a>
            </c:forEach>
            
            <!-- <a href="#" ><img src="img/猪油渣.jpg"/></a>
            <a href="#" ><img src="img/辣条.jpg"/></a>
            <a href="#" ><img src="img/蜜饯干果.jpg"/></a> -->
            <!--
             此处为轮播部分下方小点选择
            -->
            <div class="choose">
                <span class="red"></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
            <script type="text/javascript">
            	//给搜索框有回车的功能
            	
            	$('#content').on('keypress', function(event) {
   					 if (event.keyCode === 13) {
  					  $('.search2').trigger('click');
 						 }
				});
            	
            	function onClassSearchClick(object){
            		var basePath="${pageScope.basePath}";
            		var $this= $(object);
            		var keyword=  $this.text();
            		
            		
            		
            		window.location.href=basePath+"SnackServlet?keyword="+keyword+"&opr=enterSearch";
            		return false;
            	}
            
            
            
                /*定义两个变量，保存当前页码和上一页页码*/
                var $index=0;
                var $exdex=0;
                /*小点的鼠标移入事件，触发图片左移还是右移*/
                $(".choose span").mouseover(function(){
                    //获取当前移入的index值
                    $index=$(this).index();
                    //首先让点的颜色变化，表示选中
                    $(".choose span").eq($index).addClass("red").siblings().
                    removeClass("red");
                    //判断如果index变小，证明图片要往左移动。变大则为右移
                    if($index>$exdex){
                        next();
                    }else if($index<$exdex){
                        pre();
                    }
                    //移动完毕将当前index值替换了前页index
                    return $exdex=$index;
                });
                //下一页的点击事件。在右移基础上加了最大index判断
                $(".next").click(function(){
                    $index++;
                    if($index>3){
                        $index=0
                    }
                    $(".choose span").eq($index).addClass("red").siblings().
                    removeClass("red");
                    next();
                    return $exdex=$index;
                });
                //上一页的点击事件
                $(".pre").click(function(){
                    $index--;
                    if($index<0){
                        $index=3
                    };
                    $(".choose span").eq($index).addClass("red").siblings().
                    removeClass("red");
                    pre();
                    return $exdex=$index;
                });
                //加个定时器，正常轮播
                var atime=setInterval(function(){
                    $(".next").click();
                },3000);
                //这里为右移和左移的事件函数。
                //右移基本原理就是先让exdex定位的left左移百分百，而选中的当前页从屏幕右边移入,left变为0
                function next(){
                    $(".banner a").eq($index).stop(true,true).
                    css("left","100%").animate({"left":"0"});
                    $(".banner a").eq($exdex).stop(true,true).
                    css("left","0").animate({"left":"-100%"});
                }
                function pre(){
                    $(".banner a").eq($index).stop(true,true).
                    css("left","-100%").animate({"left":"0"});
                    $(".banner a").eq($exdex).stop(true,true).
                    css("left","0").animate({"left":"100%"});
                }

            </script>
        </div>
        <div id="loginMsg">
            <div id="restrain">
               <c:if test="${sessionScope.user.iconPath!=null}">
               		<c:set var="iconpath" value="${sessionScope.user.iconPath}" scope="page"></c:set>
               		<img src="${pageScope.iconpath}" class="icon"/>
               </c:if>
               <c:if test="${sessionScope.user.iconPath==null}">
               		<img src="img/logo.png" class="icon">
               </c:if>
               
               
               
 
               
            </div>
            <div id="loginList">
				<!--个人信息-->
				<c:if
					test="${sessionScope.user.nickName=='' or sessionScope.user.nickName==null }">
					<p>
						hi,欢迎:
						<c:out value="${sessionScope.user.userName}" />
					</p>
				</c:if>
				<c:if test="${sessionScope.user.nickName!=null}">
					<p>
						hi,欢迎:
						<c:out value="${sessionScope.user.nickName}" />
					</p>
				</c:if>

				<ul>
                    <li id="leftMoney">余&nbsp;&nbsp;&nbsp;额</li>
                   
                    <li><a class="aLabel" href="/SnacksProject/userServlet?opr=loginout">退&nbsp;&nbsp;&nbsp;出</a></li>
                </ul>
                <span id="balance">￥<c:out value="${sessionScope.user.money }"/></span>
            </div>
            <div></div>
        </div>
        <!--热门零食-->

		<script type="text/javascript">
			$("#leftMoney").mouseover(function() {
				
				$("#balance").show();
		
			})
			$("#leftMoney").mouseout(function() {
				
				$("#balance").hide();
		
			})
		</script>
		<div class="hot">
        
        	<c:if test="${sessionScope.snacks!=null }">
				<c:forEach items="${sessionScope.snacks}" var="temp">
					<div class="hotGood">
						<img src="${temp.iconPath }">
						<p>
						<span>已售<c:out value="${temp.sellQuntity }"/></span>
							<span class="sname"><c:out value="${temp.sname }" /></span>
							
						</p>
						<p>
							<c:out value="${temp.pricenew }" />
							<br/>
							<span>原价￥<c:out value="${temp.pricesold }" /></span>
						</p>
						<a href="/SnacksProject/SnackServlet?sid=${temp.sid}&opr=snackDetail">查看详情</a>
					</div>

				</c:forEach>
			</c:if>
        
        
            <!-- <div class="hotGood">
                <img src="img/猪油渣.jpg">
                <p>豪哥哥牌猪油渣<span>已售number</span></p>
                <p>￥14.8<span>原价￥16</span></p>
                <a href="#">查看详情</a>
            </div> -->
           
        </div>
    </section>
        <hr/>
<%@include file="/static/footer.jsp" %> 
<script type="text/javascript" src="/SnacksProject/static/OutsideSearchBtnClick.js"></script>
<script type="text/javascript">
	var res= "${pageScope.basePath}";
	
	initbasePath(res);
</script>

</body>
</html>