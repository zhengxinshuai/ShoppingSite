<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>收藏夹页面</title>
    <link href="collection.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
	<link rel="stylesheet" type="text/css" href="/SnacksProject/static/LoginInCss.css">
</head>
<body>
 	<header>
		<%@include file="/static/LoginInHeader.jsp" %>
	<header>
	
	
    <div class="wrap_line"></div>
    <ul class="collection_list" >
       <!--  <li>
            <img src="img/猪油渣.jpg">
            <p>豪哥哥牌猪油渣</p>
            <span>￥</span><strong>24.8</strong>
            <div class="delete"><a href="#" onclick="del()">删除</a></div>
        </li> -->
        <c:if test="${fn:length(sessionScope.collectionItems) eq 0}">
        	<c:out value="您还没有收藏商品哦~快去逛逛吧！"/>
        
        </c:if>
        <c:forEach items="${sessionScope.collectionItems}" var="temp">
					<li><a href="/SnacksProject/SnackServlet?sid=${temp.sid }&opr=snackDetail"
						class="link"></a> <img src="${temp.snack.iconPath}">
						<p class="sname">
							<c:out value="${temp.snack.sname }" />
						</p> <span>￥</span><strong><c:out
								value="${temp.snack.pricenew }" /></strong>
						<div class="delete">
							<a href="javascript:void(0)" class="deletef">删除</a>
						</div>
						<span class="notShow"><c:out value="${temp.sid}"/></span>
					</li>
				</c:forEach>
       
       
       




    </ul>
      <hr/>
<%@include file="/static/footer.jsp" %> 
</body>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript" >
    $(".collection_list li").hover(function (){
        $(this).find(".delete").show();
    },function (){
        $(this).find(".delete").hide();
    });
    
    $(".deletef").bind("click",del);
    
    function del() {
        var msg = "您真的确定要删除吗？请确认！";
        if(confirm(msg)==true) {
        	var sidStr= $(this).parent().next().text();
        	var $this= $(this);
        	var sid = parseInt(sidStr);
        	
        	var uid="${sessionScope.user.id}";
        	
			$.ajax({
				"url" : "/SnacksProject/SnackServlet",
				"type" : "get",
				"data" : "opr=deleteCollection&sid=" + sid + "&uid=" + uid,
				"success" : function(data) {
					if (data == "删除成功!")
						$this.parent().parent().remove();

				}
			});

			}
    }

</script>
</html>
