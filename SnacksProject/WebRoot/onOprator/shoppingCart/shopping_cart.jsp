<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>购物车页面</title>
<link href="/SnacksProject/onOprator/shoppingCart/shopping_cart.css"
	rel="stylesheet" type="text/css" />
<link href="/SnacksProject/static/header.css" rel="stylesheet"
	type="text/css" />
<link href="/SnacksProject/static/footerCss.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div id="father">
		<header>
			<%@include file="/static/LoginInHeader.jsp"%>
		</header>
		<div class="wrap_line"></div>
		<div class="cart_table">
			<span id="title">您的购物车</span>
			<div class="table_inner">商品信息</div>
			<div class="table_price">单价</div>
			<div class="table_num">数量</div>
			<div class="table_sum">金额</div>
			<div class="table_opr">操作</div>
		</div>
		<div class="goodList">
			<input type="checkbox" name="allcheck" id="allcheck" />
			<ul>
				<form id="myForm">
					<c:forEach items="${sessionScope.cartItems}" var="temp">
						<li class="noteli">
							<div class="check">
								<input type="checkbox" name="isCheck" class="checkbox" />
							</div>
							<div class="good_inner">
								<a
									href="/SnacksProject/SnackServlet?opr=snackDetail&sid=${temp.sid}">
									<img src="${temp.snack.iconPath}"> <span><c:out
											value="${temp.snack.sname}" /></span>
								</a>
							</div>
							<div class="good_price">￥${temp.snack.pricenew}</div>
							<div class="good_num">
								<div class="buyNum">
									<a class="jian">-</a> <input type="text" name="buyNum"
										value="${temp.quantity}" class="goodsNum"> <a
										class="jia">+</a> <span class="notShow" name="sid"> <c:out
											value="${temp.sid}" /></span>
								</div>
							</div>
							<div class="good_sum">￥0</div>
							<div class="good_opr">
								<c:if test="${temp.isCollect eq 1 }">
									<a href="javascript:void(0)" class="deletecoll">取消收藏</a>
								</c:if>
								<c:if test="${temp.isCollect eq 0 }">
									<a href="javascript:void(0)" class="coll">加入收藏夹</a>
								</c:if>




								<br /> <br /> <a href="javascript:void(0)" class="delete1">删除</a>
							</div>
						</li>

					</c:forEach>
					<a href="javascript:void(0)" id="buyNow">现在下单</a>
				</form>
			</ul>
			<div id="sum">
				<span>总价:￥</span> <span id="allPrice">0.00</span>
			</div>
		</div>

		<hr />

		<%@include file="/static/footer.jsp"%>
	</div>

</body>
<script type="text/javascript"
	src="/SnacksProject/static/jquery-1.12.4.js"></script>
<script type="text/javascript">
	$("#allcheck").click(function(){
	
		$(".checkbox").trigger("click")
	
	
	});

	$(".coll").click(onAddCollectionClick);


	function onAddCollectionClick() {
		var $this = $(this);
		var sidStr = $this.parents(".noteli").children(".good_num").children(".buyNum").children(".notShow").text();
		var sid = parseInt(sidStr);
		var uid = "${sessionScope.user.id}";

		$.ajax({
			"url" : "/SnacksProject/CollectionServlet",
			"type" : "get",
			"data" : "opr=addCollection&sid=" + sid + "&uid=" + uid,
			"success" : function(data) {
				if (data == "加入成功!")
					$this.attr("class", "deletecoll");
				$this.text(" 取消收藏");
				$this.unbind("click", onAddCollectionClick);
				$this.bind("click", onDeleteCollectionClick);
			}
		});
	}
	$(".deletecoll").click(onDeleteCollectionClick);

	function onDeleteCollectionClick() {
		var $this = $(this);
		var sidStr = $this.parents(".noteli").children(".good_num").children(".buyNum").children(".notShow").text();

		var sid = parseInt(sidStr);
		var uid = "${sessionScope.user.id}";
		$.ajax({
			"url" : "/SnacksProject/SnackServlet",
			"type" : "get",
			"data" : "opr=deleteCollection&sid=" + sid + "&uid=" + uid,
			"success" : function(data) {
				if (data == "删除成功!")
					$this.attr("class", "coll");
				$this.text(" 加入收藏夹");
				$this.unbind("click", onDeleteCollectionClick);
				$this.bind("click", onAddCollectionClick);

			}
		});
	}



	$(function() {

		//选择单选框为不选中.
		$(".checkbox").each(function() {
			$(this).attr("checked", false);
		})

	})

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



	//当购买按钮被点击的时候
	$("#buyNow").click(function() {
		var json = "";
		var array = new Array();
		$(".checkbox").each(function() {
			if ($(this).is(":checked")) {
				var sidStr = $(this).parents(".noteli").children(".good_num").children().children(".notShow").text();
				//拿到了选中的商品sid和数量
				var num = $(this).parents(".noteli").children(".good_num").children().children(".goodsNum").val();
				var sid = parseInt(sidStr);
				//json=json.concat("{\"num\":\""+num+"\",\"sid\":\""+sid+"\"}");
				var json = "{\"quantity\":\"" + num + "\",\"sid\":\"" + sid + "\"}";

				json = JSON.parse(json);

				array.push(json)

			}
		});
		if (array.length == 0) {
			alert("你没有选中任何商品!");
			return;
		}
		var res = JSON.stringify(array);
		var uid = "${sessionScope.user.id}";
		var finalJson = "{\"uid\":\"" + uid + "\",\"opr\":\"enterBuyPage\"}";
		finalJson = JSON.parse(finalJson);

		finalJson.data = res;

		post("/SnacksProject/OrderServlet", finalJson); //通过虚拟表单提交请求



	})


	//当多选按钮被点击时
	$(".checkbox").click(function() {
		var $this = $(this);
		//获得此条目总价
		var itemPriceStr = $this.parents(".noteli").children(".good_sum").text();
		itemPriceStr = itemPriceStr.substring(1, itemPriceStr.length);
		var itemPrice = parseFloat(itemPriceStr);
		//得到当前总总价
		var priceAllStr = $("#allPrice").text();
		var priceAll = parseFloat(priceAllStr);
		var res = 0;
		if ($this.is(":checked")) {

			//现在被选中了，增加计算
			/* res = priceAll + itemPrice; */
			res=accAdd(priceAll,itemPrice);

		} else {
			/* res = priceAll - itemPrice; */
			res=Subtr(priceAll,itemPrice);
		}
		/* res = res.toFixed(2); */
		$("#allPrice").text(res);
	})

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

	$(".delete1").bind("click", function() {

		if (confirm("确定要删除吗？")) {

			var $this = $(this);
			var sidStr = $this.parents(".noteli").children(".good_num").children().children(".notShow").html();
			var sid = parseInt(sidStr);
			var uid = "${sessionScope.user.id}";
			$.ajax({
				"url" : "/SnacksProject/SnackServlet",
				"type" : "get",
				"data" : "opr=deleteCart&sid=" + sid + "&uid=" + uid,
				"success" : function(data) {
					if (data == "删除成功!")
						$this.parent().parent().remove();



					countALL();

				}
			});
		}

	})


	$(".goodsNum").blur(function() {
		var $this = $(this);
		var numStr = $(this).val();

		if (isNaN(numStr)) {
			alert("请输入数字");
		} else {
			var uid = "${sessionScope.user.id}";
			var sidStr = $this.next().next().text();
			var sid = parseInt(sidStr);
			var num = parseInt(numStr);
			$.ajax({
				"url" : "/SnacksProject/SnackServlet",
				"type" : "get",
				"data" : "opr=updateCart&sid=" + sid + "&quantity=" + num + "&uid=" + uid,
				"success" : function(data) {
					if (data == "更新成功!") {
						var priceStr = $this.parent().parent().prev().text();

						priceStr = priceStr.substring(1, priceStr.length);
						var price = parseFloat(priceStr);
						total=accMul(price,num)
						//var total = price * num;
						/* total = total.toFixed(2); */
						$this.parent().parent().next().text("￥" + total);
						countALL();
					}



				}
			})

		}
	})


	$(function() {



		//计算一项总价
		$(".goodsNum").each(function() {
			var numStr = $(this).val();
			var num = parseInt(numStr);

			var priceStr = $(this).parent().parent().prev().text();
			priceStr = priceStr.substring(1, priceStr.length);
			var price = parseFloat(priceStr);
			var total = price * num;
			total = total.toFixed(2)
			$(this).parent().parent().next().text("￥" + total);

		});
	})



	//计算总价的方法
	function countALL() {
		var allCount = 0;
		$(".good_sum").each(function() {

			var flag = $(this).parent().children(".check").children().is(":checked");
			if (flag) {

				var numStr = $(this).text();
				numStr = numStr.substring(1, numStr.length);

				var num = parseFloat(numStr);
				allCount= accAdd(allCount,num);
				//allCount = allCount + num;
			}
		})
		//allCount = allCount.toFixed(2)
		$("#allPrice").text(allCount);
	}



	//给减按钮绑定时间，计算单项总价
	$(".jian").bind("click", function() {
		var $this = $(this);
		var numStr = $(this).next().val();
		var num = parseInt(numStr);
		if (num > 1) {
			num--;
			var uid = "${sessionScope.user.id}";
			var sidStr = $(this).next().next().next().text(); //得到sid
			var sid = parseInt(sidStr);



			$.ajax({
				"url" : "/SnacksProject/SnackServlet",
				"type" : "get",
				"data" : "opr=addToCart&sid=" + sid + "&goodNum=-1&uid=" + uid,
				"success" : function(data) {
					if (data == "加入成功!") {
						$this.next().val(num);

						var priceStr = $this.parent().parent().prev().text();
						var priceStr = priceStr.substring(1, priceStr.length);
						var price = parseFloat(priceStr);
						var total = price * num;
						total = total.toFixed(2);
						$this.parent().parent().next().text("￥" + total);
						countALL();

					}

				}
			})
		}
	})
	//给加按钮绑定时间，计算单项总价
	$(".jia").bind("click", function() {
		var $this = $(this);
		var numStr = $(this).prev().val();
		var num = parseInt(numStr);
		var uid = "${sessionScope.user.id}";
		var sidStr = $(this).next().text(); //得到sid
		var sid = parseInt(sidStr);
		if (num < 500) {
			num++;
			$.ajax({
				"url" : "/SnacksProject/SnackServlet",
				"type" : "get",
				"data" : "opr=addToCart&sid=" + sid + "&goodNum=1&uid=" + uid,
				"success" : function(data) {
					if (data == "加入成功!") {
						$this.prev().val(num);

						var priceStr = $this.parent().parent().prev().text();
						var priceStr = priceStr.substring(1, priceStr.length);
						var price = parseFloat(priceStr);
						//var total = price * num;
						var total= accMul(price,num);
						//total = total.toFixed(2);
						$this.parent().parent().next().text("￥" + total);
						countALL();

					}

				}
			})

		}
	})
	$(".coll").bind("click", function() {
		$(this).html("已加入");

	})
</script>
</html>