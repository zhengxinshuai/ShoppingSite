<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单详情页面</title>
    <link href="/SnacksProject/onOprator/orderDesc/orderMsg.css" rel="stylesheet" type="text/css"/>
    <link href="/SnacksProject/static/LoginInCss.css" rel="stylesheet"
	type="text/css" />
	<link href="/SnacksProject/static/footerCss.css" rel="stylesheet"
	type="text/css" />
</head>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<body>
<header>
			<%@include file="/static/LoginInHeader.jsp"%>
		</header>
    <div class="bar">
        <div class="h3"><h3>订单信息</h3></div>
        <div class="bar_order">
            <ul>
            	 <li><span>收货人：</span><span><c:out value="${requestScope.order.address.contactPerson}"/>
            	  <li><span>联系电话：</span><span><c:out value="${requestScope.order.address.phoneNum}"/>
                <li><span>收获地址：</span><span><c:out value="${requestScope.order.address.wholeAddressStr}"/>
                <c:out value="${requestScope.order.address.street}"/></span></li>
                <li><span>订单编号：</span><span><c:out value="${requestScope.order.serialnum}"/></span></li>
                
                <li><span>下单时间：</span><span> <fmt:formatDate value="${requestScope.order.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            </ul>
        </div>
    </div>
    <div class="bar_msg">
        <div class="detailBlock">
            <h1>订单状态：<span><c:out value="${ requestScope.order.orderState}"/></span></h1>
            <c:if test="${requestScope.order.orderState eq '已发货' }">
             <p><span>发货时间：</span><span><fmt:formatDate value="${requestScope.order.deliverTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
</span></p>
            
            </c:if>
             <c:if test="${requestScope.order.orderState eq '交易成功' }">
             
             <p><span>送达时间：</span><span> <fmt:formatDate value="${requestScope.order.checkTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span></p>
            </c:if>
           
        </div>
    </div>
    <div class="order_list">
        <div class="order_list_menu">
        <ul>
            <li>商品</li>
            <li>单价</li>
            <li>数量</li>
            <li>状态</li>
        </ul>
        </div>
        <div class="order_list_li">
            <ul>
            	<c:forEach items="${requestScope.order.orderItems}" var="temp">
            		<li>
                    <div class="order_list_li_goods">
                        <img src="${temp.snack.iconPath }"><p><c:out value="${temp.snack.sname }"/></p>
                    </div>
                    <div class="order_list_li_price">￥<c:out value="${temp.itemCount }"/></div>
                    <div class="order_list_li_count"><c:out value="${temp.quantity }"/> </div>
                    <div class="order_list_li_state">交易成功</div>
                </li>
            	
            	</c:forEach>
        
            </ul>
        </div>
     </div>
    <div class="order_sel">
        <p><span>订单总价：</span><span class="order_sum">￥<c:out value="${requestScope.order.shouldPayMoney}"/></span></p>
        <p><span>实付款：</span><span class="order_pay">￥<c:out value="${requestScope.order.payMoney}"/></span></p>
    </div>
    <hr />

		<%@include file="/static/footer.jsp"%>
</body>
</html>