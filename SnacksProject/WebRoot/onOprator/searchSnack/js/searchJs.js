


//$("#orderPrice").mouseover(function(){
//	$("#sort_price").show();
//	
//	
//});
//$("#sort_price").mouseleave(function(){
//	
//	$("#sort_price").hide();
//	$("#sort_praise").hide();
//	$("#sort_num").hide();
//	$("#sort_date").hide();
//})
//
//$("#orderPraise").mouseover(function(){
//	$("#sort_praise").show();
//	
//	
//});
//$("#sort_praise").mouseleave(function(){
//	
//	$("#sort_price").hide();
//	$("#sort_praise").hide();
//	$("#sort_num").hide();
//	$("#sort_date").hide();
//	
//})
//
//
//$("#orderBySellNum").mouseover(function(){
//	$("#sort_num").show();
//	
//	
//});
//$("#sort_num").mouseleave(function(){
//	
//	$("#sort_price").hide();
//	$("#sort_praise").hide();
//	$("#sort_num").hide();
//	$("#sort_date").hide();
//})
//
//$("#orderByDate").mouseover(function(){
//	$("#sort_date").show();
//	
//	
//});
//$("#sort_date").mouseleave(function(){
//	
//	$("#sort_price").hide();
//	$("#sort_praise").hide();
//	$("#sort_num").hide();
//	$("#sort_date").hide();
//})

var keyword;
var totalPageNum;
var currentPageNum;
var totalPage;
var orderType="SellQuntityDesc";


function jump(){
	var pageNum= $("#pageText").val();
	if(isNaN(pageNum)){
		alert("请输入数字!");
	}else if(pageNum>totalPageNum||pageNum<1){
		alert("请输入页码范围内的数字!");
	}else{
		var url="/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType="+orderType+"&pNum="+pageNum;
		
		$.getJSON(url,function(data){
			parseDataToHtml(data);
		})
		
	}
}


/**
 * 当点击搜索按钮的时候。
 */
function search1(){
	
	keyword= $("#content").val();
	
	var judgeStr=keyword.trim();
	if(judgeStr==""||judgeStr==null){
		alert("请输入搜索内容!");
		return;
		
	}
	
	
	orderType="SellQuntityDesc";
	var url="/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType="+orderType+"&pNum=1";
	
	
	
	$.getJSON(url,function(data){
		parseDataToHtml(data);
	})

}


function DateAsc(){
	currentPageNum=0;
	orderType="DateAsc";
	onHeaderPageClick();
	
}
function DateDesc(){
	currentPageNum=0;
	orderType="DateDesc";
	onHeaderPageClick();
	
}


function PraiseAsc(){
	currentPageNum=0;
	orderType="PraiseAsc";
	onHeaderPageClick();
}

function PraiseDesc(){
	currentPageNum=0;
	orderType="PraiseDesc";
	onHeaderPageClick();
}

function PriceAsc(){
	currentPageNum=0;
	orderType="PriceAsc";
	onHeaderPageClick();
}

function PriceDesc(){
	currentPageNum=0;
	orderType="PriceDesc";
	onHeaderPageClick();
}

function SellQuntityAsc(){
	currentPageNum=0;
	orderType="SellQuntityAsc";
	onHeaderPageClick();
	
}
function SellQuntityDesc(){
	currentPageNum=0;
	orderType="SellQuntityDesc";
	onHeaderPageClick();
}

function onHeaderPageClick(){
	if(currentPageNum!=1){
		var url="/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType="+orderType+"&pNum=1";
		$.getJSON(url,function(data){
			parseDataToHtml(data);
		})
	}
			
}

function onPrePageClick(){
	if(currentPageNum!=1){
		var url="/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType="+orderType+"&pNum="+(currentPageNum-1);
		$.getJSON(url,function(data){
			parseDataToHtml(data);
		})
	}
	
	
}

function onNextPageClick(){
	
	if(currentPageNum<totalPageNum){
		var url="/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType="+orderType+"&pNum="+(currentPageNum+1);
		
		$.getJSON(url,function(data){
			parseDataToHtml(data);
		})
	}
	
	
}

function onLastPageClick(){
	if(currentPageNum!=totalPageNum){
		var url="/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType="+orderType+"&pNum="+totalPageNum;
		$.getJSON(url,function(data){
			parseDataToHtml(data);
		})
	}
	
}





//将得到的JSON data解析变成html并插入
function parseDataToHtml(data){
	var html = "";

	totalPageNum = data.totalPageNum;
	currentPageNum = data.currentPageNo;

	
	
	$("#pageInfo").html("第"+ currentPageNum + "/" + totalPageNum + "页");

	//下面显示零食
	var snackJson = data.snackJson;

	snackJson = JSON.parse(snackJson);
	for (var o in snackJson) {
		var snackHtml = "<div class=\"hotGood\"><img src=\"" + snackJson[o].iconPath + "\"><p class=\"sname\">" + snackJson[o].sname + "<span>已售" + snackJson[o].sellQuntity + "</span></p><p>￥" + snackJson[o].pricenew + "<span>原价￥" + snackJson[o].pricesold + "</span></p><a href=\"/SnacksProject/SnackServlet?sid=" + snackJson[o].sid + "&opr=snackDetail\">查看详情</a></div>";
		html += snackHtml;
	}
	$(".hot").children().remove();
	$(".hot").append(html);
	
}



function initKeyword(x){
	keyword=x;
}

	$(function(){
		
		if(keyword!=null) {

			$("#content").val(keyword);
			$.getJSON("/SnacksProject/SnackServlet?opr=search&keyword=" + keyword + "&orderType=SellQuntityDesc", function(data) {
				var html = "";

				totalPageNum = data.totalPageNum;
				currentPageNum = data.currentPageNo;
				
				var guideHtml = " <li class='pageText' onclick='onHeaderPageClick()' id='shouye'><a>首页</a></li>" +
					"<li class='pageText' onclick='onPrePageClick()'><a>上一页</a></li>" +
					" <li class='pageText' onclick='onNextPageClick()'><a>下一页</a></li>" +
					"<li class='pageText' onclick='onLastPageClick()'><a>尾页</a></li>" +
					" <li class=\"pageText\"><input type=\"text\" name=\"page\" id=\"pageText\"></li>" +
					"<li class=\"pageText\"><a onclick='jump()'>跳转</a></li>" +
					"<li id=\"pageInfo\">第" + currentPageNum + "/" + totalPageNum + "页</li>";
				$("#guideUl").prepend(guideHtml);

				//下面显示零食
				var snackJson = data.snackJson;

				snackJson = JSON.parse(snackJson);
				for (var o in snackJson) {
					var snackHtml = "<div class=\"hotGood\"><img src=\"" + snackJson[o].iconPath + "\"><p class=\"sname1\">" +
snackJson[o].sname + "<span>已售" + snackJson[o].sellQuntity + "</span></p><p>￥" + snackJson[o].pricenew + 
"<span>原价￥" + snackJson[o].pricesold + "</span></p><a href=\"/SnacksProject/SnackServlet?sid=" + 
snackJson[o].sid + "&opr=snackDetail\">查看详情</a></div>";
					html += snackHtml;
				}
				$(".hot").children().remove();
				$(".hot").append(html);
			})	
		}//keyword!=null
		
		

		
		
})
window.onload = qqq;


function qqq(){
		$(".sname1").each(function(){
			
			var sname= $(this).text();
			
			if(sname.length>12){
				sname= sname.substring(0,10);
				sname+="..";
				$(this).text(sname);
				
			}
})
		
	}
