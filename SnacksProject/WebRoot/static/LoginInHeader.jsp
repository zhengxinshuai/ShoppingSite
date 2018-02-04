<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

  <div id="login">
  		<c:if test="${sessionScope.user.nickName=='' or sessionScope.user.nickName==null }">
  			 <div id="user"><span>欢迎您，<c:out value="${sessionScope.user.userName}"></c:out> </span></div>
  		</c:if>
       <c:if test="${sessionScope.user.nickName!=null}">
  			 <div id="user"><span>欢迎您，<c:out value="${sessionScope.user.nickName}"></c:out> </span></div>
  		</c:if>
        <div id="register"><a href="/SnacksProject/userServlet?opr=loginout">退出登录</a></div>
        <div id="menu">
            <ul>
            	<li><a href="/SnacksProject/onOprator/mainPage/welcome.jsp">回到主页</a></li>
                <li><a href="/SnacksProject/onOprator/PersonalCenter/center.jsp">个人中心</a></li>
                <li><a href="/SnacksProject/SnackServlet?uid=${sessionScope.user.id}&opr=checkCart">我的购物车</a></li>
                <li><a href="/SnacksProject/SnackServlet?uid=${sessionScope.user.id}&opr=checkCollection">我的收藏夹</a></li>
                <li id="li1"><a href="/SnacksProject/AboutUs/aboutUs.jsp">关于我们</a></li>
            </ul>
        </div>
    </div>