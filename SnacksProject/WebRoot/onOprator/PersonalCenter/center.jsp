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
    <title>用户个人信息页面</title>
    <link href="center.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/footerCss.css">
    <link rel="stylesheet" type="text/css" href="/SnacksProject/static/LoginInCss.css">
    
    <script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js"></script>  
   
	<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.min.js"></script>  
</head>
<body>
	<header>
		<%@include file="/static/LoginInHeader.jsp" %>
	<header>
<section>
	
    <div class="center_list">
        <h3>全部功能</h3>
        <ul id="myUl">
            <li onclick="showMsg()">个人信息</li>
       <%--   <li><a href="/SnacksProject/SnackServlet?uid=${sessionScope.user.id }&opr=checkCart">我的购物车</a></li> --%>
            <li id="MyAllOrder" onclick="showOrder()">我的订单</li>
       <%--      <li><a href="/SnacksProject/SnackServlet?uid=${sessionScope.user.id}&opr=checkCollection">我的收藏夹</a> </li> --%>
        </ul>
    </div>
    <div class="center_show">
        <div class="show_message">
            <ul>
                <li onclick="data()" class="personal_css">个人资料</li>
                <li onclick="safe()">安全设置</li>
                <li onclick="msg()">账户信息</li>
            </ul>
            <div class="personal_data">
                <div class="sns_nf">
                    <div class="header">
                        <p>欢迎你，<c:out value="${sessionScope.user.userName }"/>，填写真实资料，有助于好友找到你哦。</p>
                        <label>当前头像：</label>
                        <c:if test="${sessionScope.user.iconPath==null}">
                      	  <img src="/SnacksProject/static/img/logo.png"  onmouseover="Ashow()" onmouseout="Ahide()" class="icon">
                        </c:if>
                         <c:if test="${sessionScope.user.iconPath!=null }">
                      	 	 <img src="${sessionScope.user.iconPath }" onmouseover="Ashow()" onmouseout="Ahide()" class="icon">
                        </c:if>
                        <a href="javascript:void(0)" onmouseover="Ashow()" onmouseleave="Ahide()" id="edit">编辑头像</a>
                       
                       
                        <form action="" method="post" enctype="multipart/form-data" id="form1">
                        	<input type="file" name="file" id="file">
                        	<input type="text" class="hidden" value="${sessionScope.user.id }" name="uid">
                        	<input type="button" id="imgSubmitBtn" value="保存"/>
                        </form>
                        
                        
                        <form method="post" id="changePeronalDetailForm">
                        <label>昵称:</label><input type="text" name="nickname" id="nickname"><br/>
                        <label>真实姓名:</label><input type="text" name="realName" id="realName"><br/>
                        <label>性别：</label><input type="radio" name="sex" value="M">男
                        <label><input type="radio" name="sex" value="F">女</label><br/>
                            <div class="submit">
                                <input type="button" value="保存信息" class="submit_button" id="changePeronalDetailBtn">
                            </div>
                        </form>
                        
                        
                    </div>
                </div>
            </div>
            <div class="personal_safe">
                <p>请及时修改自己的密码，以防被不法人士利用.</p>
                <form id="changeInfoForm">
                    <label>您的当前密码：</label><input type="password" name="nowPassword" id="nowPassword"><br/>
                    <label>设置新的密码：</label><input type="password" name="newPassword" id="newPassword"><br/>
                    <label>再次输入密码：</label><input type="password" name="newPasswordAgain" id="newPasswordAgain"><br/>
                    <div class="submit">
                        <input type="submit" value="保存" class="submit_button" id="chanePwdBtn">
                    </div>
                </form>
            </div>
            <div class="personal_msg">
                <div class="sns_nf">
                    <div class="header">
                        <p>欢迎你，<c:out value="${sessionScope.user.userName }"/></p>
                        <label>当前头像：</label>
                      
                        <c:if test="${sessionScope.user.iconPath==null}">
                      	  <img src="/SnacksProject/static/img/logo.png">
                        </c:if>
                         <c:if test="${sessionScope.user.iconPath!=null }">
                      	 	<img src="${sessionScope.user.iconPath }">
                        </c:if>
                        
                        
                        <br/>
                        
                        	<c:if test="${sessionScope.user.nickName!=null }">
					  <label>昵称:</label><span><c:out value="${sessionScope.user.nickName }"/></span><br/>
				</c:if>
				<c:if test="${sessionScope.user.nickName==null}">
					<label>昵称:</label><span>暂时没有昵称哦~快去设置吧~</span><br/>
				</c:if>
   
                            <label>真实姓名:</label><span><c:out value="${sessionScope.user.realName}"/></span><br/>
                            <label>余额：</label><span>￥</span><span id="soceMoney"><c:out value="${sessionScope.user.money}"/></span><br/>
                            <div class="submit">
                                <input type="submit" value="充值" class="submit_button" onclick="rechargeShow()">
                            </div>
                            <div class="recharge"><!--充值窗口，隐藏，点击充值显示-->
                                <div class="sns_nf">
                                    <div class="header">
                                        <p>及时补充弹药，才能轻松购物~</p>
                                            <label>请输入充值金额:</label><input type="text" name="money" id="money"><br/>
                                            <div class="submit">
                                                <input type="submit" value="确定" class="submit_button" onclick="rechargeHide()">
                                            </div>
                                    </div>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="show_order">
            <div class="show_order_menu"><!--我的订单导航栏-->
                <ul>
                    <li onclick="a()" id="myOrders">我的订单</li>
                    <li onclick="b()" id="waitDeliverBtn">待发货</li>
                    <li onclick="c()" id="waitCheckBtn">待收货</li>
                    <li onclick="d()" id="waitValueBtn">待评价</li>
                    <li >订单疑问？</li>
                </ul>
            </div>
            <div class="show_order_List"><!--我的订单列表显示栏-->
                <div class="show_order_my">
                    <img src="img/order_menu.png">
                    <div class="show_order_my_list">
                       <ul id="insertPlace">
                           <!--  <li>
                                <div class="order_number"><span>订单编号：</span><span>285175412</span></div>
                                <div class="user_price_num">
                                <div class="order_user">
                                    <a href="#">
                                        <img src="img/猪油渣.jpg">
                                        <span>我们豪哥哥亲手做的猪油渣，驰名海外。我们豪哥哥亲手做的猪油渣，驰名海外。</span>
                                    </a>
                                </div>
                                <div class="order_price">￥24.8</div>
                                <div class="order_num">1</div>
                                </div>
                                <div class="user_price_num">
                                    <div class="order_user">
                                        <a href="#">
                                            <img src="img/猪油渣.jpg">
                                            <span>我们豪哥哥亲手做的猪油渣，驰名海外。我们豪哥哥亲手做的猪油渣，驰名海外。</span>
                                        </a>
                                    </div>
                                    <div class="order_price">￥24.8</div>
                                    <div class="order_num">1</div>
                                </div>
                                <div class="user_price_num">
                                    <div class="order_user">
                                        <a href="#">
                                            <img src="img/猪油渣.jpg">
                                            <span>我们豪哥哥亲手做的猪油渣，驰名海外。我们豪哥哥亲手做的猪油渣，驰名海外。</span>
                                        </a>
                                    </div>
                                    <div class="order_price">￥24.8</div>
                                    <div class="order_num">1</div>
                                </div>
                                <div class="order_opr"><a href="#">删除订单</a><br/><br/>
                                      <a href="#">订单详情</a></div>
                                <div class="order_money">￥24.8</div>
                                <div class="order_status"><p>交易完成</p></div>
                                <div class="order_opr2"><a href="#">点击评价</a></div>
                            </li> -->
                        </ul>
                    </div>
                </div><!--我的订单显示栏-->
                <div class="show_order_delivery">
                    <img src="img/order_menu.png">
                    <div class="show_order_my_list">
                        <ul id="waitUl">


                        </ul>
                    </div>
                </div><!--待发货显示栏-->
                <div class="show_order_received">
                    <img src="img/order_menu.png">
                    <div class="show_order_my_list">
                        <ul id="checkUl">


                        </ul>
                    </div>
                </div><!--待收货显示栏-->
                <div class="show_order_comment">
                    <img src="img/order_menu.png">
                    <div class="show_order_my_list">
                        <ul id="valueUl">


                        </ul>
                    </div>
                </div><!--待评价显示栏-->
             </div>
        </div>
    </div>
</section>
<div class="confirmation">
    <span class="span1">请输入密码确认收货</span>
    <input type="password" name="confirmation_pwd">
    <span class="span2">提交</span>
    <span class="span3">取消</span>
</div>
      <hr/>
<%@include file="/static/footer.jsp" %> 
</body>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript">

	//当删除订单的按钮被点击的时候
	$("body").on("click",".deleteOrder",function(){
		if(confirm("真的要删除吗？")){
			var oidStr= $(this).parents(".findLi").children(".oid").text();
			var oid= parseInt(oidStr);
			var uid="${sessionScope.user.id}";//得到用户id
			
			var url="/SnacksProject/OrderServlet";
		
		$.ajax({
			"url":url,
			"type":"post",
			"data":"uid="+uid+"&oid="+oid+"&opr=deleteOrder",
			
			
			"success":function(result){
				alert(result);
				if(result=="删除成功!"){
					
					 $("#myOrders").trigger('click');
				
				}
			
			}
			
		
		
		});
			
			
		
		}
	
	});



	$("#edit").click(function(){
		 $("#file").trigger('click');
	});
	
	$("#file").change(function(){
		var reader=new FileReader();
		reader.readAsDataURL(this.files[0]);//获得这个input对象中的文件对象
		reader.onloadend = function (e) {
			$(".icon").attr("src",e.target.result);//e.target.result就是最后的路径地址 ,给img附上图像
        	
        };    
	
	});
	//下面写点击保存的时候进行文件上传
	
	$("#imgSubmitBtn").click(function(){
		var fileName= $("#file").val();
		if(fileName==""||fileName==null){
			alert("请选择图片！");
			return;
		}
		var formData =new FormData();
		formData.append("icon",$("#file")[0].files[0]);
		formData.append("opr","changeIcon");
		var url="/SnacksProject/fileUploadServlet";
		
		$.ajax({
			"url":url,
			"type":"post",
			"data":new FormData($("#form1")[0]),
			"processData":false,
			"contentType":false,
			
			"success":function(result){
				alert(result);
			
			}
			
		
		
		});
		
	
	});
	
	
	

	

	function detectOS() {
  var sUserAgent = navigator.userAgent;
  var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
  var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
  if (isMac) return "Mac";
  var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
  if (isUnix) return "Unix";
  var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
  if (isLinux) return "Linux";
  if (isWin) {
    var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 ||sUserAgent.indexOf("Windows 2000") > -1;
    if (isWin2K) return "Win2000";
    var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
    if (isWinXP) return "WinXP";
    var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
    if (isWin2003) return "Win2003";
    var isWinVista= sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
    if (isWinVista) return "WinVista";
    var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
    if (isWin7) return "Win7";
  }
   return "other";
}











	$("body").on("click",".check",showConfirmation);
	var nowClickObject;
	//当确认收货的按钮被点击的时候
	$(".check").click(showConfirmation);
	
	$(".span2").click(function(){
		var pwd=hideConfirmation();
		
		checkDeliver(pwd);
	
	});
	$(".span3").click(function(){
		$(".confirmation").hide();
	});
	
	
	function checkDeliver(pwd){
		var uid="${sessionScope.user.id}";//得到user的id
		 var serialnum=nowClickObject.parents(".findLi").children(".order_number").children(".serialnum").text();//得到这个订单的序列号
		
		
		
		if(pwd==""||pwd==null){
			return;
		}
		pwd= md5(pwd);
		var url="/SnacksProject/OrderServlet";
		$.ajax({ 								
						"url"	:url,
						"data"	:"uid="+uid+"&serialnum="+serialnum+"&opr=confirmOrder&pwd="+pwd,
						"type"	:"get",
						"dataType":"text",
						"success":function(result){
							if(result=="确认成功!"){
								 $("#myOrders").trigger('click');
								alert("收货成功!");
							}else{
								alert(result);
							}
							
							
						}
					});
	 	
	}

	//当修改个人信息的功能被点击的时候
	
	$("#changePeronalDetailBtn").click(function(){
		var nickname= $("#nickname").val();
		var realname=$("#realName").val();
		var sexvalue= $('input:radio:checked').val();
		if(nickname.length<3||nickname.length>=15||realname.length<2||realname.length>=5||sexvalue==""||sexvalue==null){
			alert("输入不合法！");
			return;
		}
		
		
		var paramsArray= $("#changePeronalDetailForm").serializeArray();

		var params= $.param(paramsArray);
		
		
		var uid="${sessionScope.user.id}";//得到用户id
		var data="opr=changePersonDetail&uid="+uid+"&"+params;
		
		var url="/SnacksProject/userServlet";
				$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"get",
						"dataType":"text",
						"success":function(result){
							alert(result);
							
						}
					});
		
		
	
	});


	$("#chanePwdBtn").click(function(){

		var nowPassword= $("#nowPassword").val();
		var newPassword= $("#newPassword").val();
		var newPasswordAgain=$("#newPasswordAgain").val();
		
		if(newPassword.length>15||newPassword<8){
			alert("新密码必须在8到15位之间");
			return;
		}
		
		
		if(newPassword!=newPasswordAgain){
			alert("两次输入密码不一致");
			$("#newPasswordAgain").val("");
			$("#newPassword").val("");
			return;
		}
		nowPassword=md5(nowPassword);
		$("#nowPassword").val(nowPassword);
		newPasswordAgain=md5(newPasswordAgain);
		$("#newPasswordAgain").val(newPasswordAgain);
		
		newPassword=md5(newPassword);
		$("#newPassword").val(newPassword);
		var uid="${sessionScope.user.id}"; 
		var params= $("#changeInfoForm").serialize();//序列化表单元素 得到加密后的新密码和旧密码
		
		$.ajax({
				"url" : "/SnacksProject/userServlet",
				"type" : "post",
				"data" : params+"&opr=changePwd&uid="+uid,
				"success" : function(data) {
					alert(data);

				}
			});

	});
	


	$("#MyAllOrder").bind("click",showMyOrder);

	$("#myOrders").bind("click",showMyOrder);
	//显示所有待发货订单的显示
	$("#waitDeliverBtn").bind("click",function(){
		var uid="${sessionScope.user.id}";//得到用户id
		var data="opr=waitDeliverOrderList&uid="+uid;
			
			var url="/SnacksProject/OrderServlet";
				$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"get",
						"dataType":"html",
						"success":function(result){
						
							$("#waitUl").children("li").remove();
							$("#waitUl").append(result);
							
						}
					});
	});

	$("#waitCheckBtn").bind("click",function(){
			var uid="${sessionScope.user.id}";//得到用户id
			var data="opr=waitCheckOrderList&uid="+uid;
			
			var url="/SnacksProject/OrderServlet";
				$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"get",
						"dataType":"html",
						"success":function(result){
							
							$("#checkUl").children("li").remove();
							$("#checkUl").append(result);
							
						}
					});	
	});
	
	$("#waitValueBtn").bind("click",function(){
			var uid="${sessionScope.user.id}";//得到用户id
			var data="opr=waitValueOrderList&uid="+uid;
			
			var url="/SnacksProject/OrderServlet";
				$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"get",
						"dataType":"html",
						"success":function(result){
							
							$("#valueUl").children("li").remove();
							$("#valueUl").append(result);
						
						}
					});	
	});
	
	
	function showMyOrder(){
		var uid="${sessionScope.user.id}";//得到用户id
		var data="opr=getAllOrders&uid="+uid;
		//得到这个用户所有的order
			var url="/SnacksProject/OrderServlet";
				$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"get",
						"dataType":"html",
						"success":function(result){
							
							 $("#insertPlace").children("li").remove();
							$("#insertPlace").append(result); 
						}
					});
	
	}



    function personal() {
        $(".show_message ul li").onclick(function(){
            $(this).addClass();
        })
    }
    function data(){
        $(".personal_data").show();
        $(".personal_safe").hide();
        $(".personal_msg").hide();
    }
    function safe(){
        $(".personal_data").hide();
        $(".personal_safe").show();
        $(".personal_msg").hide();
    }
    function msg(){
        $(".personal_data").hide();
        $(".personal_safe").hide();
        $(".personal_msg").show();
    }
    function Ashow() {
        $(".header a").show();
    }
    function Ahide() {
        $(".header a").hide();
    }
    function rechargeShow(){
        $(".recharge").show();
    }
    function rechargeHide(){
        var money= parseInt( $("#money").val());
        alert("您已经成功充值"+money);
        var soceMoney= parseInt( $("#soceMoney").text());
        $("#soceMoney").text(soceMoney+money);
        $(".recharge").hide();
    }
    function a() {
        $(".show_order_my").show().siblings().hide();
    }
    function b() {
        $(".show_order_delivery").show().siblings().hide();
    }
    function c() {
        $(".show_order_received").show().siblings().hide();
    }
    function d() {
        $(".show_order_comment").show().siblings().hide();
    }
    
    function showMsg(){
        $(".show_message").show();
        $(".show_order").hide();
    }
    function showOrder(){
        $(".show_message").hide();
        $(".show_order").show();
    }
       function showConfirmation(){
       nowClickObject=$(this);
        $(".confirmation").show();
    }
    function hideConfirmation(){
        var pwd=$(".confirmation input").val();
         $(".confirmation").hide();
      	return pwd;
       
    }
</script>
</html>