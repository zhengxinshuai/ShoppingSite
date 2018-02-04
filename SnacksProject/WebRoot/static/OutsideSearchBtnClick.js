/**
 * 
 */

	var basePath;
	function initbasePath(x){
		basePath=x;
		
	}
	$(".search2").click(function(){
		var content= $("#content").val();
		var judgeStr=content.trim();
		if(judgeStr==""||judgeStr==null){
			alert("请输入搜索内容!");
			return;
			
		}
		
		
		window.location.href=basePath+"SnackServlet?keyword="+content+"&opr=enterSearch";
	})
	
	
	
