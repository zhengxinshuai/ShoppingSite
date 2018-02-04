<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    
    <title>付款页面</title>
  
    <link href="/SnacksProject/onOprator/pay/shopping_cart.css" rel="stylesheet" type="text/css"/>
     <link href="/SnacksProject/onOprator/pay/pay.css" rel="stylesheet" type="text/css"/>
      <link href="/SnacksProject/static/header.css" rel="stylesheet"
	type="text/css" />
	<link href="/SnacksProject/static/footerCss.css" rel="stylesheet"
	type="text/css" />
	<script type="text/javascript" src="http://libs.baidu.com/jquery/1.7.2/jquery.min.js"></script>  
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=K8aACxCRFvhAMDlYxK0Gm5ayOjygaMhc">  </script>
</head>
<body>
	<header>
	<%@include file="/static/LoginInHeader.jsp" %>
	</header>
	
<!--收货地址-->
    <div class="order_address">
        <h3>确认收货地址<a href="javascript:void(0)" id="manage">管理收货地址</a></h3>
        <div id="display" class="delete"> <input type="text" value="" id="position" readonly><input type="button" value="点我自动获取位置" id="load_geolocation"/></div>
       
        <div class="delete" id="addAdress">
        	<form id="myForm">
        		<span>新增收货地址:</span>
        	
        	<p>
   					<span class="needStar">*</span>省份：<select name="province" id="s1" class="otherInput">
   						<option>---请选择</option>
				<c:if test="${requestScope.provinces!=null}">
					<c:forEach items="${requestScope.provinces}" var="temp">
						<option value="${temp.provinceId}">${temp.province}</option>
					</c:forEach>
				</c:if>
			</select>
   					<span class="needStar">*</span>城市：<select name="city" id="s2" class="otherInput">
   						<option>---请选择</option>
   					</select><br/>
   					<span class="needStar">*</span>区域：<select name="area" id="s3" class="otherInput">
   						<option>---请选择</option>
   					</select>
   					<p><span class="needStar">*</span>具体街道:<input type="text" class="text" name="street" id="street"> </p>
   				</p>
        	
        	
        	<span>(请在街道后直接增加具体地址)</span>
        	<br/>
        	<span>新增联系人:</span>
        	<input type="text" value="" id="person" name="person">
        	<span>新增电话号:</span>
        	<input type="text" value="" id="phonenum" name="phonenum">

        	
        	<input type="button" value="新增" id="add">
        	
        	</form>
        	
        </div>
        <ul id="addressul">
        	<c:forEach items="${sessionScope.addresses}" var="temp">
        	<c:if test="${temp.isShow eq 'Y' }">
        		<li >
        		<c:if test="${temp.isDefault eq 'N'}">
        			 <input name="address" type="radio" class="radio" />
        		</c:if>
                <c:if test="${temp.isDefault eq 'Y'}">
        			 <input name="address" type="radio" checked="checked" class="radio"/>
        		</c:if> 
                <label class="clientaddress">
                <c:out value="${temp.provinceName} ${temp.cityName } ${temp.areaName} ${temp.street}"/> </label>
                <label class="persons"><c:out value=" ${temp.contactPerson } "/></label>+<label class="phones"><c:out value="${temp.phoneNum }"/></label>
                &nbsp;&nbsp;
                <a href="javascript:void(0)" class="delete">删除</a>
               <a href="javascript:void(0)" class="setDefault">设置为默认地址</a>
             	<span class="addressId"><c:out value="${temp.addressId}"/></span>
             	
             	 <c:if test="${temp.isDefault eq 'Y'}">
        			<span id="defaultAddress1">默认地址</span>
        		</c:if> 
             </li>
        	
        	
        	</c:if>
        	
        	
        	</c:forEach>
           <!--  <li >
                <input name="address" type="radio"  /><label>地址 地址 地址 地址 地址+姓名+电话 </label>&nbsp;&nbsp;<a href="javascript:void(0)" class="delete">删除</a>
             </li> -->
           
        </ul>
    </div>
    <div class="order_msg">
        <h3>确认订单信息</h3>
        <div class="cart_table">
            <div class="table_inner">商品信息</div>
            <div class="table_price">单价</div>
            <div class="table_num">数量</div>
            <div class="table_sum">金额</div>
            <div class="table_opr">操作</div>
        </div>
        <div class="goodList">
            <ul>
            	<c:forEach items="${sessionScope.cartItems}" var="temp">
            	<li class="item">
                    <div class="good_inner">
                        <a href="/SnacksProject/SnackServlet?opr=snackDetail&sid=${temp.sid}">
                            <img src="${temp.snack.iconPath}">
                            <span><c:out value="${temp.snack.sname}"/></span></a>
                    </div>
                    <div class="good_price" ><c:out value="${temp.snack.pricenew }"/></div>
                  	<div class="good_num">
                        <div class="buyNum" style="margin-left: 40px"><c:out value="${temp.quantity}"/></div>
                    </div>
                    <div class="good_sum"><c:out value="${temp.itemTotalPrice}"/></div>
                    <div class="good_opr">
                        <a href="javascript:void(0)" class="deleteItem">删除</a>
                        
                   </div>
                   <span class="sidclass"><c:out value="${temp.sid }"/></span>
                </li> 	
            	</c:forEach>
            		
               <!--  <li>
                    <div class="good_inner">
                        <a href="#">
                            <img src="img/headimg.jpg">
                            <span>我们豪哥哥亲手做的猪油渣</span></a>
                    </div>
                    <div class="good_price" >￥24.8</div>
                    <div class="good_num">
                        <div class="buyNum" style="margin-left: 40px">1</div>
                    </div>
                    <div class="good_sum">金额</div>
                    <div class="good_opr">
                        <a href="#">删除</a></div>
                </li> -->
              
            </ul>
        </div>
    </div>
    <div class="sum">
        <div>
            <span>合计</span><strong id="firstSum">￥24.8</strong>
        </div>
    </div>
    <div class="order_payInfo">
        <div class="payInfo_show">
            <div class="show_money"><span class="show_strong">实付：</span><strong id="pay_money">￥24.8</strong></div><br/>
            <div class="show_address"><span class="show_strong">送至：</span><span id="deliverAdd">杭州市拱墅区沈半路328号鲜屋商旅酒店</span></div><br/>
            <div class="show_user"><span class="show_strong">联系人：</span><span id="contactPerson">郑鑫帅</span>&nbsp;&nbsp;<span id="phoneNum1">13111111111</span></div>
        </div>
        <div class="pay_submit">
            <div><a href="/SnacksProject/SnackServlet?uid=${sessionScope.user.id}&opr=checkCart">返回购物车</a></div><div id="submitBtn">提交订单</div>
        </div>
    </div>
    	<%@include file="/static/footer.jsp"%>
</body>
<script type="text/javascript" src="/SnacksProject/register/js/address.js"></script>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript">


	function accDiv(arg1, arg2) {
		var t1 = 0,
			t2 = 0,
			r1,
			r2;
		try {
			t1 = arg1.toString().split(".")[1].length
		} catch (e) {}
		try {
			t2 = arg2.toString().split(".")[1].length
		} catch (e) {}
		with(Math){
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return accMul((r1 / r2), pow(10, t2 - t1));
		}
	}
	//乘法 
	function accMul(arg1, arg2) {
		var m = 0,
			s1 = arg1.toString(),
			s2 = arg2.toString();
		try {
			m += s1.split(".")[1].length
		} catch (e) {}
		try {
			m += s2.split(".")[1].length
		} catch (e) {}
		return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
	}
	//加法 
	function accAdd(arg1, arg2) {
		var r1,
			r2,
			m;
		try {
			r1 = arg1.toString().split(".")[1].length
		} catch (e) {
			r1 = 0
		}
		try {
			r2 = arg2.toString().split(".")[1].length
		} catch (e) {
			r2 = 0
		}
		m = Math.pow(10, Math.max(r1, r2))
		return (arg1 * m + arg2 * m) / m
	}
	//减法 
	function Subtr(arg1, arg2) {
		var r1,
			r2,
			m,
			n;
		try {
			r1 = arg1.toString().split(".")[1].length
		} catch (e) {
			r1 = 0
		}
		try {
			r2 = arg2.toString().split(".")[1].length
		} catch (e) {
			r2 = 0
		}
		m = Math.pow(10, Math.max(r1, r2));
		n = (r1 >= r2) ? r1 : r2;
		return ((arg1 * m - arg2 * m) / m).toFixed(n);
	}

	/* $html.children(".delete").bind("click", fDelete);
				//为新增的元素增加设置默认收货地址的功能
				$html.children(".setDefault").bind("click",setDefault);
 */
 
 	/* 	$("body").on("click",".delete",fDelete);
  		$("body").on("click",".setDefault",setDefault); */
 	

  
	//当点击提交订单的按钮被点击的时候。
	$("#submitBtn").click(function(){
		/* //如果什么商品都没有选择，则返回
		var totalStr= $("#pay_money").text();
		totalStr= totalStr.substring(1,totalStr.length);
		var total= parseFloat(totalStr);
		if(total==0){
			alert("你什么都没有选择");
			return;
		} */
		//获得数量和sid，并生成json
		var array = new Array();
		$(".buyNum").each(function(){
			var $this= $(this);
			var numStr= $this.text();
			var num=parseInt(numStr);
			var sidStr= $this.parents(".item").children(".sidclass").text();
			var sid=parseInt(sidStr);
			var json="{\"quantity\":\""+num+"\",\"sid\":\""+sid+"\"}";
			//将字符串变成对象
			json= JSON.parse(json);
			array.push(json);

		});
		if(array.length==0){
			alert("你没有选中任何商品!");
			return;
		}else{
			//得到选中的addressid
			
			var addressStr= $(".radio:checked").parent().children(".addressId").text();
			var addressId=parseInt(addressStr);
			 if(addressStr==""){
				alert("请选择一个收货地址！");
				return;
			} 
			
			
			
			
			var res=JSON.stringify( array ); 
			var uid="${sessionScope.user.id}";
			var finalJson="{\"uid\":\""+uid+"\",\"opr\":\"order\",\"addressid\":\""+addressId+"\"}";
		
			finalJson=JSON.parse(finalJson);
			finalJson.data=res;
			post("/SnacksProject/OrderServlet",finalJson);//通过虚拟表单提交请求
			
		}
		
	
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
	
	
	
	//当删除一个商品按钮被点击时
	$(".deleteItem").click(function(){
		 $(this).parents(".item").remove(); 
		var total1=0;
		$(".good_sum").each(function(){
		  var sum=	$(this).text();
		  sum=parseFloat(sum);
		  total1=accAdd(sum,total1);
		/*   total1+=sum;
		  total1=total1.toFixed(2); */
		});
		$("#firstSum").text("￥"+total1);
		$("#pay_money").text("￥"+total1);
	});

	//当设置默认地址按钮被点击的时候
	$(".setDefault").click(setDefault);
	//默认地址点击时候调用的方法
	function setDefault(){
		var uid="${sessionScope.user.id}";
		var addressIdStr= $(this).next().text();
		var addressId= parseInt(addressIdStr);
		var $this= $(this);
		$.ajax({
			"url" : "/SnacksProject/OrderServlet",
			"type" : "get",
			"data" : "addressId=" + addressId + "&opr=setDefault&uid="+uid,
			"success" : function(data) {
				if(data=="设置成功!"){
					$("#defaultAddress1").remove();
					$this.parent().append("<span id='defaultAddress1'>默认地址</span>");
				}		
			}
		})


		}

	//当更换地址的单选按钮被点击时
	$(".radio").click(changeAddr);
	
	function changeAddr(){
		//修改地址
		//1.得到省市区街
		var address= $(this).parent().children(".clientaddress").text();
		$("#deliverAdd").text(address);
		//2.得到联系人名字
		var name= $(this).parent().children(".persons").text();
		$("#contactPerson").text(name);
		//3.得到联系人手机
			var phones= $(this).parent().children(".phones").text();
		$("#phoneNum1").text(phones);
	
	}
//当删除地址的按钮被点击时
	$(".delete").click(fDelete);
	function fDelete() {
		var $this = $(this);
		var addressIdStr = $this.next().next().text();
		
		var addressId = parseInt(addressIdStr);
		
		$.ajax({
			"url" : "/SnacksProject/OrderServlet",
			"type" : "get",
			"data" : "addressId=" + addressId + "&opr=deleteAddress",
			"success" : function(data) {
				if (data == "删除成功!") {
					$this.parent().remove();

				}
			}
		});

	}




	//当页面加载完毕的时候，算出总价显示,给默认地址加上样式
	$(function() {
	//给地址初始化
		var address = "";
		var person = "";
		var phoneNum1 = "";
		<c:forEach items="${sessionScope.addresses}" var="temp">
		
		<c:if test="${temp.isDefault eq 'Y'}">
        	address= "${temp.provinceName} ${temp.cityName } ${temp.areaName} ${temp.street}";
        	person=" ${temp.contactPerson }";
        	phoneNum1= "${temp.phoneNum }";
        </c:if> 
	
	</c:forEach>
		$("#deliverAdd").text(address);
		$("#contactPerson").text(person);
		$("#phoneNum1").text(phoneNum1);

		//给默认地址加上样式

		var $this= $(".radio:checked");
		$this.parent().addClass("cur");	
		var total=0;
		<c:forEach items="${sessionScope.cartItems}" var="temp">
			var itemPrice= "${temp.itemTotalPrice}";
			var price= parseFloat(itemPrice);
			/* total+=price; */
			total=accAdd(total,price)
		</c:forEach>
	/* 	total=total.toFixed(2); */
		$("#firstSum").text("￥"+total);
		$("#pay_money").text("￥"+total);
	
	})
	//当管理收货地址按钮被点击的时候
	$("#manage").click(function(){
		if($(".delete").css("display")=="none"){
			$(".delete").show();
			$(".setDefault").show();
		}else{
			$(".delete").hide();
			$(".setDefault").hide();
		}
	})
	//切换样式
    $(function(){
        $(".order_address ul li").change(function(){
            $("input:checked").parent().addClass("cur").siblings().removeClass("cur");
        });
    });
    
    $(function(){  
    $("#load_geolocation").click(function(ev){  
    	
        $("#load_geolocation").val("正在获取位置......");  
        //创建百度地图控件  
        var geolocation = new BMap.Geolocation();  
        geolocation.getCurrentPosition(function(r){  
            if(this.getStatus() == BMAP_STATUS_SUCCESS){  
            	var x=0.05208142;
            	var y=0.06732754;
                //以指定的经度与纬度创建一个坐标点  
                var pt = new BMap.Point(r.point.lng-x,r.point.lat+y);  
                //创建一个地理位置解析器  
                var geoc = new BMap.Geocoder();  
                geoc.getLocation(pt, function(rs){//解析格式：城市，区县，街道  
                    var addComp = rs.addressComponents;  
                  
                    $("#position").val(addComp.province+","+ addComp.city + "," + addComp.district + "," + addComp.street);  
                     $("#load_geolocation").val('点我自动获取位置');  
                });      
            }  
            else {  
                $("#load_geolocation").val('定位失败');  
            }          
        },{enableHighAccuracy: true})//指示浏览器获取高精度的位置，默认false  
    });  
});
	//当增加的地址按钮被点击的时候
	$("#add").click(function() {
		var res = $("#myForm").serializeArray();
		/* res=JSON.stringify(res);
		alert(res); */
		var position;
		var person;
		var phonenum;
		var flag = 1;
		for (var i = 0; i < res.length; i++) {
			var name = res[i].name;
			var value = res[i].value;
			if (name === "position") {
				position = res[i].value;
			} else if (name === "person") {
				person = res[i].value;
			} else if (name === "phonenum") {
				phonenum = res[i].value;
			}
			if (value == "" || value == "---请选择") {
				flag = 0;
			}
		}
		if (person == "" || person == null) {
			alert("姓名不能为空");
			return;
		} else if (phonenum == "" || phonenum.length < 11 || isNaN(phonenum)) {
			alert("请输入正确的手机号码");
			return;
		} else if (flag == 0) {
			alert("请完整填写");
			return;
		}
		var uid = "${sessionScope.user.id}";
		var param = $.param(res) + "&opr=addAddress&uid=" + uid;
		$.ajax({
			"url" : "/SnacksProject/OrderServlet",
			"type" : "get",
			"data" : param,
			"success" : function(data) { //当地址加入成功的时候
				if (data != null || data != "")
					alert("添加成功!");
				//拿到省市区街道的值
				var province = $("#s1 option:selected").text();
				var city = $("#s2 option:selected").text();
				var area = $("#s3 option:selected").text();
				var street = $("#street").val();
				var person = $("#person").val();
				var phoneNum = $("#phonenum").val();
				var html = "<li ><input name='address' type='radio' class='radio'/><label> " + province + " " + city + " " + area + " " + street + "</label>" +
					"<label class='persons'>" + person + "</label>+<label class='phones'>" + phoneNum + "</label> &nbsp;&nbsp;" +
					"<a href='javascript:void(0)' class='delete' style='display:inline-block;'>删除</a>" +
					"<a href='javascript:void(0)' class='setDefault' style='display:inline-block;'>设置为默认地址</a><span class='addressId'>" + data + "</span> </li>";
				var $html = $(html);
				//为新增的元素增加删除的功能
				
				$html.children(".delete").bind("click", fDelete);
				//为新增的元素增加设置默认收货地址的功能
				$html.children(".setDefault").bind("click",setDefault);

				$html.children(".radio").bind("click",changeAddr); 
				
				$("#addressul").append($html);

				$(".order_address ul li").change(function() {
					$("input:checked").parent().addClass("cur").siblings().removeClass("cur");
				});

				$("select").each(function() {
					$(this).children(":first").attr("selected", true);
				})
				$("#person").val("");
				$("#phonenum").val("");
				$("#street").val("");
			}
		})
	})
</script>
</html>