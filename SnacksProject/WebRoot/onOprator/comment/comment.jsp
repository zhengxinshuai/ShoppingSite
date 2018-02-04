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
    <title>评价界面</title>
    <link href="/SnacksProject/onOprator/comment/comment.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/LoginInCss.css">
</head>
<body>
<header>
		<%@include file="/static/LoginInHeader.jsp" %>
	<header>
    <div class="comment">
       		<span id="oid"><c:out value="${requestScope.order.oid }"/></span>
            <ul id="cul">
            	<c:if test="${requestScope.order.isEvaluate eq 'N' }">
            		 <h2>零食动态评分:订单编号<c:out value="${requestScope.order.serialnum }"/></h2>
       				 <div class="comment_menu">商品信息</div><div class="comment_menu">商品与描述评价</div>
     				   <div class="comment_list">


							<c:forEach items="${requestScope.order.orderItems }" var="temp">
								 <li class="posLi">
									<div class="order_msg">
										<a href="/SnacksProject/SnackServlet?sid=${temp.sid }&opr=snackDetail"><img src="${temp.snack.iconPath }"> <span><c:out
													value="${temp.snack.sname }" /></span></a>
									</div>
									<div class="order_comment">
										<div class="text_input">
											<textarea name="commentText" placeholder="请输入你对该商品的评价" class="textarea"></textarea>
										</div>
										<p class="comment_tip">快来发表评价吧~</p>
										<div class="order_praise">
											<p class="good">点赞</p>
											<span class="sid"><c:out value="${temp.sid }"/></span>
										</div>
									</div>
								</li> 

							</c:forEach></c:if>
                <!--每个li是每个商品的信息与用户评价窗口-->
            
              
            
            </ul>
        </div>
        <div class="comment_submit">
            <a>发表评论</a>
        </div>
    </div>
       <hr/>
<%@include file="/static/footer.jsp" %> 
</body>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript" >
    $(".good").click(function(){
    	var sidStr= $(this).next().text();
    	var sid= parseInt(sidStr);
    	
    	$.ajax({
				"url" : "/SnacksProject/OrderServlet",
				"type" : "get",
				"data" : "opr=good&sid=" + sid,
				"success" : function(data) {					
						alert(data);
				}
			});
    
    });
    $(".comment_submit").bind("click",doComment);
    
    
    function doComment(){
    	var json = "";
		var array = new Array();
		var uid="${sessionScope.user.id}";
    	$(".textarea").each(function(){
    		var comment= $(this).val();//得到
    		var sidStr= $(this).parents(".posLi").children(".order_comment").children(".order_praise").children(".sid").text();
    		var sid=parseInt(sidStr);
    	
    		var json = "{\"uid\":\"" + uid + "\",\"sid\":\"" + sid + "\",\"comment\":\""+comment+"\"}";
    		json = JSON.parse(json);
    		array.push(json);
    	});
    	if (array.length == 0) {
			alert("你没有评价任何商品!");
			return;
		}
  		var res = JSON.stringify(array);
		//得到oid
		var oidStr = $("#oid").text();
		var oid=parseInt(oidStr);
		var finalJson = "{\"uid\":\"" + uid + "\",\"opr\":\"doComment\"}";
		
    	finalJson = JSON.parse(finalJson);//转成对象
    	finalJson.oid=oid;
    	finalJson.data = res;
    	var params= $.param(finalJson);
    	$.ajax({
				"url" : "/SnacksProject/OrderServlet",
				"type" : "post",
				"data" : params,
				"success" : function(data) {					
						alert(data);
						if(data=="评论成功!"){
							$(".textarea").val("");
							$(".comment_submit").unbind("click",doComment);
							$(".comment_submit").children().text("已评价！");
							
						}
						
				}
			});
    	}
   
    
    
   
</script>
</html>