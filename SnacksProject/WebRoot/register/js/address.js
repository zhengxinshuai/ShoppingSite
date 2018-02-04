/**
 * 
 */

	$(function(){
		/* 当省发生改变时 */
		$("#s1").change(function(){
			//拿到被选中的省的编号
			var res=$(this).find("option:selected").val();
	 		$("#s2").children(":gt(0)").remove();
			$("#s3").children(":gt(0)").remove();
			$.ajax({
				"url":"/SnacksProject/AddressServlet",
				"type":"post",
				"data":"pId="+res+"&opr=p",
				"dataType":"json",
				"success":successFunction
			});
			function successFunction(data){
				for(var i=0;i<data.length;i++){
				var item=data[i];
					var str="<option value="+item.cityid+">"+item.city+"</option>";
					$("#s2").append(str);
				}
			}
		});
		$("#s2").change(function(){
			//拿到被选中的城市的编号
			var res1=$(this).find("option:selected").val();
			//清空所有s3的子元素
			$("#s3").children(":gt(0)").remove();
			
			$.ajax({
				"url":"/SnacksProject/AddressServlet",
				"type":"post",
				"data":"cId="+res1+"&opr=city",
				"dataType":"json",
				"success":successFunction1
			});
			
			function successFunction1(data){
				for(var i=0;i<data.length;i++){
				var item=data[i];
					var str="<option value="+item.areaid+">"+item.area+"</option>";
					$("#s3").append(str);
				}
			}
		})
	})
	