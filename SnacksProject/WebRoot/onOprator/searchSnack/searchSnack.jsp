<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>onOprator/searchSnack/">
    <meta charset="UTF-8">
    <title>欢迎访问豪哥哥零食网</title>
    <link href="index.css" rel="stylesheet" type="text/css"/>
    <link href="sort.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
</head>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<body>
        <!--网页头部 用户登录注册等用户操作菜单-->
    <header>
	
   	<%@ include file="/static/LoginInHeader.jsp" %>
        <div id="search"><!--logo和搜索框-->
            <div id="logo">	<img src="/SnacksProject/static/img/logo.png" class="logo"></div>
            <div id="searchInput">
                    <input type="text" placeholder="请输入您要搜索的内容" class="search1" id="content"/>
                    <a class="search2" onclick="search1()">搜好吃的</a>
            </div>
        </div>
    </header>
        <div id="page">
            <ul id="guideUl">
      			
      			
      			 <li class="Page_sort" onclick="sort_price()" id="orderPrice">按价格排序</li>
                <li class="Page_sort" onclick="sort_praise()" id="orderPraise">按点赞排序</li>
                <li class="Page_sort" onclick="sort_num()" id="orderBySellNum">按销量排序</li>
                <li class="Page_sort" onclick="sort_date()" id="orderByDate">按日期排序</li>
                
                <script type="text/javascript">
                    function sort_price() {
                        $("#sort_price").toggle();
                    }
                    function sort_praise() {
                        $("#sort_praise").toggle();
                    }
                    function sort_num() {
                        $("#sort_num").toggle();
                    }
                    function sort_date() {
                        $("#sort_date").toggle();
                    }
       
                </script>
            </ul>
            <ul id="sort_price" class="hiddenn">
            <li class="sortopr" onclick="PriceDesc()">从高到低</li>
            <li class="sortopr" onclick="PriceAsc()">从低到高</li>
        </ul>
        <ul id="sort_praise" class="hiddenn">
            <li class="sortopr" onclick="PraiseDesc()">从高到低</li>
            <li class="sortopr" onclick="PraiseAsc()">从低到高</li>
        </ul>
        <ul id="sort_num" class="hiddenn">
            <li class="sortopr" onclick="SellQuntityDesc()">从高到低</li>
            <li class="sortopr" onclick="SellQuntityAsc()">从低到高</li>
        </ul>
        <ul id="sort_date" class="hiddenn">
            <li class="sortopr" onclick="DateDesc()">从高到低</li>
            <li class="sortopr" onclick="DateAsc()">从低到高</li>
        </ul>
            
            
        </div><!--显示页数等信息-->
        
    <!--网页主体部分-->
    <section>
       
       
        </div>
        <!--热门零食-->
        
        <script type="text/javascript">


        </script>
        <div class="hot">
           
          
        </div>
    </section>
        <hr/>
     <script type="text/javascript" src="/SnacksProject/static/js/SnacksControll.js"></script>
   
      <%@include file="/static/footer.jsp" %> 
  <script type="text/javascript" src="js/searchJs.js"></script>
    <script type="text/javascript">
    var keyword="${requestScope.keyword}";
    initKeyword(keyword);
    
     $(".hiddenn").click(function(){
                    	 $(".hiddenn").hide();
                    
                    });
    $('#content').on('keypress', function(event) {
   					 if (event.keyCode === 13) {
  					 		 $('.search2').trigger('click');
 					}
				});
    
 
    </script>
</body>
</html>