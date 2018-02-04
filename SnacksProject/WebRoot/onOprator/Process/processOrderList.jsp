<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:forEach items="${requestScope.orders}" var="temp">
	<c:if test="${temp.isShow eq 'Y' }">
		<li class="findLi">
	<div class="order_number">
		<span>订单编号：</span><span class="serialnum"><c:out value="${temp.serialnum}"/></span>
	</div>
	<span class="oid"><c:out value="${temp.oid}"/></span>
	<c:forEach items="${temp.orderItems }" var="temp1">
		<div class="user_price_num">
		<div class="order_user">
			<a href="/SnacksProject/SnackServlet?sid=${temp1.sid }&opr=snackDetail"> <img src="${temp1.snack.iconPath}"> <span><c:out value="${temp1.snack.sname }"/></span>
			</a>
		</div>
		<div class="order_price">￥<c:out value="${temp1.itemCount}"/></div>
		<div class="order_num"><c:out value="${temp1.quantity}"/></div>
	</div>
	
	</c:forEach>
	
	<div class="order_opr">
		<c:if test="${temp.orderState == '交易成功' }">
			<a href="javascript:void(0)" class="deleteOrder">删除订单</a><br />
		</c:if>
		
		<br /> <a href="/SnacksProject/OrderServlet?opr=checkOrder&oid=${temp.oid}&uid=${sessionScope.user.id}">订单详情</a>
	</div>
	<div class="order_money">￥<c:out value="${temp.payMoney}"/></div>
	<div class="order_status">
		<p><c:out value="${temp.orderState }"/></p>
	</div>
	<div class="order_opr2">
		<c:if test="${temp.orderState eq '待收货' }">
			<a href="javascript:void(0)" class="check">点击确认收货</a>
		</c:if>
		<c:if test="${temp.orderState eq '交易成功' and temp.isEvaluate eq 'N' }">
			<a href="/SnacksProject/OrderServlet?opr=valueOrder&oid=${temp.oid}&uid=${sessionScope.user.id}" class="comment">点击评价</a>
		</c:if>
	</div>
</li>
	
	
	</c:if>
	

</c:forEach>
