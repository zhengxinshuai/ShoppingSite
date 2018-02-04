<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员页面</title>

    <link href="manager.css" rel="stylesheet" type="text/css"/>
    <link href="center.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="center_list">
    <h3>全部功能</h3>
    <ul>
        <li onclick="a()">上架新商品</li>
        <li onclick="b()">管理现有商品</li>
        <li onclick="d()">查看订单信息</li>
        <li onclick="e()">查看所有在线用户</li>
    </ul>
</div>
<div class="manager_show">
    <div class="show_newGoods">
        <div class="show_newGoods_menu">
            <ul>
                <li onclick="showGoodsUp()">上架商品信息</li>
                <li ><a href="upload.html" target="_blank">上架商品图片</a></li>
            </ul>
        </div>
        <br/>
        <div class="goods_msg">
            <form action="#" method="post" class="goods_msg_form">
                <label>商品名称：</label><input type="text" name="sname"><br/>
                <label>单价：</label><input type="text" name="price"><br/>
                <label>库存：</label><input type="text" name="stocks"><br/>
                <label>商品简介：</label><input type="text" name="comment"><br/>
                <input type="submit" value="提交" name="submit" class="input_submit">
            </form>
        </div>
    </div>
    <div class="show_managerGoods">
        <div class="goods_manage_menu">
            <ul>
                <li>商品品名</li>
                <li>商品单价</li>
                <li>商品库存</li>
                <li>商品简介</li>
                <li>商品操作</li>
            </ul>
        </div> <!--管理商品导航-->
        <div class="goods_manage_list">
            <ul>
                <li>
                    <input type="text" name="goodName" value="豪哥哥牌猪油渣"/>
                    <input type="text" name="goodPrice" value="24.8"/>
                    <input type="text" name="goodStocks" value="50"/>
                    <input type="text" name="goodComment" value="我们豪哥哥亲手做的猪油渣，远销海外。"/>
                    <div class="change_opr"><a href="#">修改信息</a><br/>
                        <a onclick="c()" class="tupian">查看图片</a><br/>
                        <a href="#">下架</a></div>
                </li>
            </ul>
        </div>
    </div>
    <div class="show_manage_list_img">
        <div class="manage_list_img">
            <h2>该商品保存的图片有：<a href="upload.html" target="_blank">添加图片</a></h2>
            <div class="list_img">
                <div class="img_msg">
                <img src="img/猪油渣.jpg">
                <p onclick="setMainImg()">设为主图片</p>
                <p onclick="delImg()">删除图片</p>
            </div>
                <div class="img_msg">
                    <img src="img/猪油渣.jpg">
                    <p onclick="setMainImg()">设为主图片</p>
                    <p onclick="delImg()">删除图片</p>
                </div>

            </div>
        </div>
    </div>
    <div class="show_userOrder">
        <div class="paging">
            <ul class="paging_paging">
                <li id="firstPage">首页</li>
                <li id="prePage">上一页</li>
                <li id="nextPage">下一页</li>
                <li id="lastPage">尾页</li>
                <li class="paging_paging_five">第1/5页
                	 <div class="bbbb" id="bbbb">
                        <input type="text" name="page" value="5" id="nn">
                        <p class="confirm">确定</p>
                    </div>
                
                   
                </li>
                
            </ul>
            <ul class="paging_opr">
                <li id="ALL">全部订单</li>
                <li id="WEIFAHUO">待发货</li>
                <li id="YIFAHUO">待收货</li>
                <li id="YISHOUHUO">已完成</li>
            </ul>
        </div>
        <div class="show_userOrder_menu">
            <ul>
                <li>订单编号</li>
                <li></li>
                <li>实付款</li>
                <li>订单状态</li>
                <li>订单操作</li>
            </ul>
        </div>
        <div class="show_userOrder_list">
            <ul id="insert">
               <!--  <li>
                    <div onclick="aashow(this)" class="c1">285175412</div>
                    <div class="c2"></div>
                    <div class="c3">24.8</div>
                    <div class="c4">未发货</div>
                    <div onclick="delive()" class="c5">发货</div>
                    <div class="orderList" onclick="aahide()">
                        <div class="orderList_menu">
                             <div><p>地址</p><p>电话</p><p>姓名</p></div>
                             <div><p>浙江省杭州市拱墅区沈半路328号鲜屋商旅酒店</p><p>1588863729</p><p>林荣林荣</p></div>
                        </div>
                        <div class="orderList_list">
                            <div><p>零食详情</p><p>数量</p></div>
                        </div>
                        <div class="orderList_list_msg">
                            <div><p>豪哥哥牌猪油渣</p><p>2</p></div>
                            <div><p>豪哥哥牌香辣丝</p><p>5</p></div>
                            <div><p>豪哥哥的肥肠</p><p>9</p></div>
                        </div>
                    </div>
                </li>
                -->
          
            </ul>
        </div>

    </div>
    <div class="show_onlineUser">
    
        <div class="onlineUser_list">
        	<c:forEach items="${applicationScope.users }" var="temp">
        	
        			<div>
        				<li class="userLi"><c:out value="${temp.key }"/></li><span class="drop">&nbsp;&nbsp;&nbsp;踢出</span>
        				
        			</div>
        			
        		
        		
        		
        	</c:forEach>
        
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript" >
	$(".drop").click(function(){
		var $this= $(this);
		var uname= $(this).prev().text();
		alert(uname);
		var data="opr=dropUser&userName="+uname;
		var url="/SnacksProject/ManagerServlet";
		$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"post",
						"dataType":"text",
						"success":function(result){
				
							if(result=="踢出成功!"){
								$this.parent().remove();
								alert(result);
								
							
							}
						}
					});
	
	});	
	
	
	$("body").on('keypress',"#nn", function(event) {
   					 if (event.keyCode === 13) {
  					  $(".confirm").trigger("click");
 					 }
				});

	$("body").on("click",".c5",function(){
		$this= $(this);
		var serinum= $(this).parents(".findLi").children(".c1").text();
		serinum= parseInt(serinum);//发货的操作
		var data="opr=deleverGood&serinum="+serinum;
		var url="/SnacksProject/ManagerServlet";
		$.ajax({ 								
						"url"	:url,
						"data"	:data,
						"type"	:"post",
						"dataType":"text",
						"success":function(result){
							alert(result);
							if(result=="发货成功!"){
								
								$this.prev().text("待收货");
								$this.remove();
							
							}
						}
					});
		
		
		
		
	
	
	});
	$("#YIFAHUO").click(function(){
		currentPageNum=1;
		searchType="YIFAHUO";
		search();
	
	});
	
	$("#YISHOUHUO").click(function(){
		currentPageNum=1;
		searchType="YISHOUHUO";
		search();
	
	});
	$("#WEIFAHUO").click(function(){
		currentPageNum=1;
		searchType="WEIFAHUO";
		search();
	});
	$("#ALL").click(function(){
		currentPageNum=1;
		searchType="ALL";
		search();
	});
	$("#firstPage").click(function(){
		currentPageNum=1;
		search();
	
	});
	$("#lastPage").click(function(){
		currentPageNum=totalPageNum;
		search();
	});
	$("#prePage").click(function(){
		var temp= currentPageNum-1;
		if(temp<=0){
			alert("已经是首页了");
			return;
		}else{
			currentPageNum=temp;
			search();
			var ee="<div class='bbbb' id='bbbb'> <input type='text' name='page' value='5' id='nn'>"+
                       " <p class='confirm'>确定</p>";
				
				$(".paging_paging_five").html("第" + currentPageNum + "/" + totalPageNum + "页"+ee);
			
		
		}
	
	
	});
	$("#nextPage").click(function(){
		var temp=currentPageNum+1;
		if(temp>totalPageNum){
			alert("已到最大页数!");
			return;
		}else{
			currentPageNum=temp;
			search();
			
			
		}
		
	
	
	});
	function cutName(str1){
		return str1.substring(0,23)+"..";
		
	}

	var searchType;//定义查找的种类 WEIFAHUO YIFAHUO,YISHOUHUO
	var perPageCount;//定义每页的数量
	var totalPageNum;//定义总页数 全局变量
	var currentPageNum;//当前页数  全局变量
	
	$(function(){
		currentPageNum=1;
		searchType="ALL";
		perPageCount=5;
		search();
		
		
	})
	
	function setTotal(){
	
	var ee="<div class='bbbb' id='bbbb'> <input type='text' name='page' value='5' id='nn'>"+
                       " <p class='confirm'>确定</p>";
				
				$(".paging_paging_five").html("第" + currentPageNum + "/" + totalPageNum + "页"+ee);
	}
	
	function search() { //查找的方法
		var data = "opr=searchPage&searchType=" + searchType + "&perPageCount=" + perPageCount + "&currentPageNum=" + currentPageNum;

		var url = "/SnacksProject/ManagerServlet";
		$.ajax({
			"url" : url,
			"data" : data,
			"type" : "post",
			"dataType" : "json",
			"success" : function(json) { //获得拿回来的订单json
				totalPageNum = json.totalPageNum;
				currentPageNum = json.currentPageNo;
				
				
				//下面显示订单
				var orderJson = json.orderJson;
				var html = "";
				orderJson = JSON.parse(orderJson);
				for (var o in orderJson) {
					var orderOpr="";
					if(orderJson[o].orderState=="未发货"){
						orderOpr="<div onclick='delive()' class='c5'>发货</div>";
					}

					var orderHtml = "<li class='findLi'><div onclick='aashow(this)' class='c1'>" + orderJson[o].serialnum + "</div>" +
						"<div class='c2'></div><div class='c3'>" + orderJson[o].payMoney + "</div>" +
						"<div class='c4'>" + orderJson[o].orderState + "</div> " +orderOpr+
						" <div class='orderList' onclick='aahide()'>" +
						" <div class='orderList_menu'>" +
						" <div><p>地址</p><p>电话</p><p>姓名</p></div>" +
						"<div><p>" + orderJson[o].address.wholeAddressStr + orderJson[o].address.street + "</p><p>" + orderJson[o].address.phoneNum + "</p><p>" + orderJson[o].address.contactPerson + "</p></div>" +
						"</div><div class='orderList_list'> <div><p>零食详情</p><p>数量</p></div></div><div class='orderList_list_msg'><div>下单时间:"+orderJson[o].createTime+"</div>";

					var orderItemsJson = orderJson[o].orderItems;
					/*  orderItemsJson = JSON.parse(orderItemsJson); */

					for (var oo in orderItemsJson) {
						var sname = cutName(orderItemsJson[oo].snack.sname);
						var orderItemHtml = "<div><p>" + sname + "</p><p>" + orderItemsJson[oo].quantity + "</p></div>";
						orderHtml += orderItemHtml;


					}

					orderHtml += "</div></li>";
					html += orderHtml;
				}

				$("#insert").children().remove();
				$("#insert").append(html);

				setTotal();
			}
		});

	}




	function showGoodsUp(){
        $(".goods_msg").show();
        $(".goods_img").hide();
    }
    function setMainImg() {
        alert("设置成功!");
    }
    function delImg() {
        alert("删除成功!");
    }
    function delive(){

    
    }
    function a(){
        $(".show_newGoods").show();
        $(".show_managerGoods").hide();
        $(".show_manage_list_img").hide();
        $(".show_userOrder").hide();
        $(".show_onlineUser").hide();
    }
    function b(){
        $(".show_newGoods").hide();
        $(".show_managerGoods").show();
        $(".show_manage_list_img").hide();
        $(".show_userOrder").hide();
        $(".show_onlineUser").hide();
    }
    function c(){
        $(".show_newGoods").hide();
        $(".show_managerGoods").hide();
        $(".show_manage_list_img").show();
        $(".show_userOrder").hide();
        $(".show_onlineUser").hide();
    }
    function d(){
        $(".show_newGoods").hide();
        $(".show_managerGoods").hide();
        $(".show_manage_list_img").hide();
        $(".show_userOrder").show();
        $(".show_onlineUser").hide();
    }
    function e(){
        $(".show_newGoods").hide();
        $(".show_managerGoods").hide();
        $(".show_manage_list_img").hide();
        $(".show_userOrder").hide();
        $(".show_onlineUser").show();
    }
    function aashow(o){
        $(".orderList").hide();
        $(o).parent().children(".orderList").show();
    }
    function aahide(){
        $(".orderList").hide();
    }
    $(".paging_paging_five").click(showInput);
	
	
	
    function showInput(){
        $(".bbbb").show();

    }
    
    $("body").on("click",".confirm",hideInput);
    
    function hideInput(){
        var a=document.getElementById("nn").value;
        if(isNaN(a)){
        	alert("请输入数字");
        	return;
        	
        }
        
       currentPageNum=1;
       perPageCount=a;
       
       search();
       
       
        $(".bbbb").hide(1);
    }
    $(".paging_opr li").click(aaa);
    function aaa(){
        $(this).addClass("selectPink").siblings().removeClass("selectPink");
    }

</script>
</html>