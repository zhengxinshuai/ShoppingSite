<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>零食详情况页面</title>
    <link href="/SnacksProject/onOprator/snackDesc/message.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
</head>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/SnacksProject/onOprator/snackDesc/js/SnackDesc.js"></script>
<script type="text/javascript">
	var sid= "${sessionScope.snack.sid}";
	var uid="${sessionScope.user.id}";
	init(sid,uid);

</script>




<body>
<!--网页头部 用户登录注册等用户操作菜单-->
<header>
	<%@include file="/static/LoginInHeader.jsp" %>
  <%--   <div id="login">
        <div id="user"><span>欢迎您，<c:out value="${sessionScope.user.userName}"></c:out> </span></div>
        <div id="register"><a href="/SnacksProject/userServlet?opr=loginout">退出登录</a></div>
        <div id="menu">
            <ul>
                <li><a href="#">我的订单</a></li>
                <li><a href="#">我的购物车</a></li>
                <li><a href="#">我的收藏夹</a></li>
                <li id="li1"><a href="#">联系商家</a></li>
            </ul>
        </div>
    </div> --%>
    <div id="search"><!--logo和搜索框-->
        <div id="logo"><img src="/SnacksProject/static/img/logo.png" class="logo"></div>
        <div id="searchInput">
            <input type="text" placeholder="请输入您要搜索的内容" class="search1">
            <a href="#"  class="search2">搜好吃的</a>
        </div>
    </div>
</header>

    <div class="snackMessage">
            <img src="${sessionScope.snack.iconPath}">
            <div id="orderMessage">
                <p><c:out value="${sessionScope.snack.sname}"/></p>
                <p>惊爆价：<span>￥<c:out value="${sessionScope.snack.pricenew}"/></span><span>价格：￥<c:out value="${sessionScope.snack.pricesold}"/></span></p>
                <p>销量:<c:out value="${sessionScope.snack.sellQuntity}"/></p>
                <p>点赞人数:<c:out value="${sessionScope.snack.praise}"/></p>
                <div id="buyNum">
                    <a class="jian" onclick="jian()">-</a><input type="text" name="buyNum" value="1" class="goodsNum"><a class="jia" onclick="jia()">+</a><span>库存:<c:out value="${sessionScope.snack.stocks}"/>件</span>
                </div>
                <a href="javascript:void(0)" class="buy">立即购买</a>
                <input type="button" class="add_Cart" value="加入购物车"/>
                <c:if test="${sessionScope.isTheSidCollect eq 0 }">
                	  <a href="javascript:void(0)" class="collect">加入收藏</a>
                </c:if>
              	 <c:if test="${sessionScope.isTheSidCollect eq 1 }">
                	  <a href="javascript:void(0)" class="cancelcollect">取消收藏</a>
                </c:if>
            </div>
    </div>
    <div class="information">
        <div class="information_menu">
               <ul>
                   <li id="click" onclick="a()"><a>零食详情</a></li>
                   <li id="noClick" onclick="b()"><a>累计评价</a></li>
               </ul>
        </div>
        <div class="jieShao">
            <div class="goodsInformation">
                <p><c:out value="${sessionScope.snack.comment}"/></p>
                <c:forEach items="${sessionScope.snackPictures}" var="temp">
                	 <img src="${temp.picturePath}">
                </c:forEach>
                
            </div>
            <div class="comment">
                <ul>
                    <!-- <li>
                        <div class="comment_user">
                            <img src="img/headimg.jpg">
                            <em>username</em>
                        </div>
                        <div class="comment_comment">
                            <p>我们豪哥哥亲手做的猪油渣</p>
                            <p>获取当前时间</p>
                        </div>
                    </li> -->
                    <c:forEach items="${sessionScope.snackComments}" var="temp">
                    <li class="comment">
                    	
                         <div class="comment_user">
                            <img src="${temp.user.iconPath}">
                            <c:if test="${temp.user.nickName ==null }">
                            	   <em class="commentUname">用户名：<c:out value="${temp.user.userName }"/></em>
                            </c:if>
                        	 <c:if test="${temp.user.nickName!=null }">
                            	   <em class="commentUname">昵称：<c:out value="${temp.user.nickName }"/></em>
                            </c:if>
                        </div> 
                         <div class="comment_comment">
                            <p class="commentcontent"><c:out value="${temp.comment}"/></p>
                            <p class="commentdate"><fmt:formatDate value="${temp.date}" pattern="yyyy/MM/dd  HH:mm:ss" /></p>
                        </div>
                    </li> 
                    </c:forEach>

                  
                </ul>
            </div>
        </div>

    </div>

<%@include file="/static/footer.jsp" %>
 </body>

<script type="text/javascript">
		$('.search1').on('keypress', function(event) {
   					 if (event.keyCode === 13) {
  					  $('.search2').trigger('click');
 						 }
				});
	
	
	$(".buy").click(function(){
		var json="";
		var array = new Array();
		
		var numStr= $(".goodsNum").val();
		var num=parseInt(numStr);
		var sid="${sessionScope.snack.sid}";
		var json="{\"quantity\":\""+num+"\",\"sid\":\""+sid+"\"}";
		json= JSON.parse(json);
			
		array.push(json)
		var res = JSON.stringify(array);
		var uid = "${sessionScope.user.id}";
		var finalJson = "{\"uid\":\"" + uid + "\",\"opr\":\"enterBuyPage\"}";
		finalJson = JSON.parse(finalJson);
		finalJson.data = res;
		post("/SnacksProject/OrderServlet", finalJson); //通过虚拟表单提交请求
		
	});

function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}        


	$(".collect").click(onAddCollectionClick);
	var sid="${sessionScope.snack.sid}";
	var uid="${sessionScope.user.id}";
	
	function onAddCollectionClick(){
			var $this= $(this);
			$.ajax({
				"url" : "/SnacksProject/CollectionServlet",
				"type" : "get",
				"data" : "opr=addCollection&sid=" + sid + "&uid=" + uid,
				"success" : function(data) {
					if (data == "加入成功!")
						$this.attr("class","cancelcollect");
						$this.text(" 取消收藏");
						$this.unbind("click",onAddCollectionClick);
						$this.bind("click",onDeleteCollectionClick);
				}
			});
	}
	$(".cancelcollect").click(onDeleteCollectionClick);

	function onDeleteCollectionClick(){
		var $this= $(this);
		$.ajax({
				"url" : "/SnacksProject/SnackServlet",
				"type" : "get",
				"data" : "opr=deleteCollection&sid=" + sid + "&uid=" + uid,
				"success" : function(data) {
					if (data == "删除成功!")
						$this.attr("class","cancelcollect");
						$this.text(" 加入收藏");
						$this.unbind("click",onDeleteCollectionClick);
						$this.bind("click",onAddCollectionClick);
				}
			});
	}

	$(function(){
		a();
	})
    function jian(){
            var num=parseInt($(".goodsNum").val());
            if(num>1){
                num--;
                $(".goodsNum").val(num);
            }
    }
    function jia() {
    var stocks= ${sessionScope.snack.stocks};
   
        var num = parseInt($(".goodsNum").val());
        if (num < stocks) {
            num++;
            $(".goodsNum").val(num);
        }
    }
    function a(){
            $(".information_menu ul li").first().attr("id","click");
               $(".information_menu ul li").last().attr("id","noClick");
            $(".goodsInformation").css("display","inline-block");
            $(".comment").css("display","none");
    }
    function b() {
            $(".information_menu ul li").last().attr("id","click");
             $(".information_menu ul li").first().attr("id","noClick");
        $(".goodsInformation").css("display","none");
        $(".comment").css("display","inline-block");
    }
    
    $(".search2").click(function(){
		var content= $(".search1").val();
		
		window.location.href="${requestScope.basePath}SnackServlet?keyword="+content+"&opr=enterSearch";
	})
    
    
</script>
</html>