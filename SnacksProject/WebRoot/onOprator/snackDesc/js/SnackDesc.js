/**
 * 
 */
var sid;
var uid;
function init(x,y){
	sid=x;//传进来零食的id
	uid=y;//传进来用户
	
}




$(function(){
	$(".add_Cart").click(function(){
		var goodNum= $(".goodsNum").val();//得到要加入购物车的数量
		
		goodNum= parseInt(goodNum);
		if(isNaN(goodNum)){
			alert("请输入数字！");
			return;
		}
		
		//发起ajax请求
		$.ajax({
			"url":"/SnacksProject/SnackServlet",
			"type":"get",
			"data":"opr=addToCart&sid="+sid+"&goodNum="+goodNum+"&uid="+uid,
			"success":function(data){
				alert(data);
				
				
			}
			
		})
		
		
		
		
	})
	
	
})