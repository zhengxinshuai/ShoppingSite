/**
 * 
 */

	$("#userName").blur(checkUserName);
	$("#password").blur(checkpwd);
	$("#birthday").blur(checkBirthday);
	$("#phoneNum").blur(checkMobile);

	function createXmlHttpRequest(){
		if(window.XMLHttpRequest){//如果当前浏览器>6的IE或者其他浏览器
			return new XMLHttpRequest();
		}else{
			//如果当前浏览器为<= IE6一下的浏览器
			return new ActiveXObject("Microsoft.XMLHTTP");
		}

	}
	
	var xmlHttpRequest= createXmlHttpRequest();
	function checkUserName() {//检测用户名不能为空
			var nameVal=$("#userName").val().trim();
			var reg=/^[a-zA-Z]\w{7,15}$/;
			if(reg.test(nameVal)){//已经通过正则验证，去服务器做验证
	
				xmlHttpRequest.open("get", "/SnacksProject/userServlet?opr=checkName&uname="+nameVal, true);
				xmlHttpRequest.onreadystatechange=callback;
				
				xmlHttpRequest.send(null);
					
				
				return true;
			}else{
				$("#unameFeedback").html("你输入的用户名不合法").css("color","red").css("font-size","12px");
				return false;
			}
		}
	
	function callback(){
		if(xmlHttpRequest.readyState=4&&xmlHttpRequest.status==200){
			var data=xmlHttpRequest.responseText;
			if(data==1){
				$("#unameFeedback").html("√").css("color","green").css("font-size","12px");
				
			}else if(data==0){
				$("#unameFeedback").html("用户名已被使用").css("color","red").css("font-size","12px");
			}
		}
		
	}
	
	function checkpwd() {//检测用户名不能为空
		var nameVal=$("#password").val().trim();
		var reg=/^\w{6,12}$/;
		if(reg.test(nameVal)){
			$("#pwdFeedback").html("√").css("color","green").css("font-size","12px");
			return true;
		}else{
			$("#pwdFeedback").html("你输入的密码不合法").css("color","red").css("font-size","12px");
			return false;
		}
	}
	
	function checkBirthday(){
		var nameVal=$("#birthday").val().trim();
		var reg=/^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		if(reg.test(nameVal)){
			$("#birthFeedback").html("√").css("color","green").css("font-size","12px");
			return true;
		}else{
			$("#birthFeedback").html("你输入的生日不合法").css("color","red").css("font-size","12px");
			return false;
		}
	}
	
	function checkMobile() {

		var mobileVal=$("#phoneNum").val().trim();

		var reg=/^1\d{10}$/;
		if(reg.test(mobileVal)==true){
			$("#phoneNumFeedback").html("√").css("color","green").css("font-size","12px");
			return true;
		}else{
			$("#phoneNumFeedback").html("手机号码格式不正确").css("color","red").css("font-size","12px");
			return false;
		}
	}
	function checkMail() {
			var mailVal=$("#email").val().trim();
			var reg=/^\w*@\w+\.com$/;
			if(reg.test(mailVal)){
				$("#emailt").html("√").css("color","green").css("font-size","12px");
				return true;
			}else{
				$("#emailt").html("你输入的邮箱不合法").css("color","red").css("font-size","12px");
				return false;
			}
		}
	$("#form").submit(function () {
			var selectVal1= $("#s1").val();
			var selectVal2= $("#s2").val();
			var selectVal3= $("#s3").val();
			if(selectVal1=="empty"||selectVal2=="empty"||selectVal3=="empty"){
				alert("请选择地区！");
				return false;
				
			}
			var flag=true;
			if(!checkUserName()){
				flag=false;
			}
			if(!checkpwd()){
				flag=false;
			}
			
			if(!checkMobile()){
				flag=false;
			}
			if(!checkBirthday()){
				flag=false;
			}
			return flag;
		});
