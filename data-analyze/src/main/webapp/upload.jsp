<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
	<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
	<script type="text/javascript" src="uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#uploadify").uploadify({    
			'debug'     : false, //开启调试
	        'auto'           : false, //是否自动上传   
	        'swf'            : 'uploadify/uploadify.swf',  //引入uploadify.swf  
	        'uploader'       : 'upload',//请求路径  
	        'queueID'        : 'fileQueue',//队列id,用来展示上传进度的  
	        'width'     : '75',  //按钮宽度  
	        'height'    : '24',  //按钮高度
	        'queueSizeLimit' : 3,  //同时上传文件的个数  
	        'fileTypeDesc'   : '视频文件',    //可选择文件类型说明
	        'fileTypeExts'   : '*.jpg;*.gif', //控制可上传文件的扩展名  
	        'multi'          : true,  //允许多文件上传  
	        'buttonText'     : '图片上传',//按钮上的文字  
	        'fileSizeLimit' : '2MB', //设置单个文件大小限制   
	        'fileObjName' : 'uploadify',  //<input type="file"/>的name  
	        'method' : 'post',  
	        'removeCompleted' : true,//上传完成后自动删除队列  
	        'onFallback':function(){    
	            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");    
	        }, 
	        'onUploadSuccess' : function(file, data, response){//单个文件上传成功触发  
	                               //data就是action中返回来的数据  
	        },'onQueueComplete' : function(){//所有文件上传完成  
	        	alert("文件上传成功!");
	       		}  
	        });
	});
	</script>
  </head>
  
  <body>
   <input type="file" id="uploadify" name="uploadify">  
   <div id="fileQueue"></div>  
   <a href="javascript:$('#uploadify').uploadify('upload','*')">开始上传</a>  
   <a href="javascript:$('#uploadify').uploadify('cancel')">取消所有上传</a>  
  </body>
</html>
