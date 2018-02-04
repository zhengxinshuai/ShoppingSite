
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>  
<head>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />  
    <script type="text/javascript" src="http://libs.baidu.com/jquery/1.7.2/jquery.min.js"></script>  
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=K8aACxCRFvhAMDlYxK0Gm5ayOjygaMhc">  </script>
    <title>浏览器定位</title>  
</head>  
<body>  
    <div> 
        <span id="load_geolocation">点击获取位置</span>  
    </div>   
</body>  
</html>  
<script type="text/javascript">  
$(function(){  
    $("#load_geolocation").click(function(ev){  
        $(ev.currentTarget).text("正在获取位置......");  
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
                    $(ev.currentTarget).text(addComp.province+","+ addComp.city + ", " + addComp.district + ", " + addComp.street);  
                });      
            }  
            else {  
                $(ev.currentTarget).text('定位失败');  
            }          
        },{enableHighAccuracy: true})//指示浏览器获取高精度的位置，默认false  
    });  
});  
</script>  