/**
 * 
 */
$(function(){
	//控制显示字数
	$(".sname").each(function(){
		var sname= $(this).html();
		
		if(sname.length>10){
			sname= sname.substring(0,7);
			sname+="..";
			$(this).html(sname);
			
		}

	})
	


	
});