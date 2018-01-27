<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title><s:text name="left.diag.title" />--Greenway</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">


<script src="lib/jquery-1.11.1.min.js" type="text/javascript"></script>

<!-- uploadify start -->
<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css" />
<script src="uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<!-- uploadify end -->


<!-- datatable start -->
<link rel="stylesheet" type="text/css"
	href="lib/datatable/css/jquery.dataTables.min.css" />
<script src="lib/datatable/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
<!-- datatable end -->


<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">
</head>
<body class=" theme-blue" onload="init()">

	<!-- Demo page code -->

	<script type="text/javascript">
		$(function() {
			var match = document.cookie.match(new RegExp('color=([^;]+)'));
			if (match)
				var color = match[1];
			if (color) {
				$('body').removeClass(function(index, css) {
					return (css.match(/\btheme-\S+/g) || []).join(' ')
				})
				$('body').addClass('theme-' + color);
			}

			$('[data-popover="true"]').popover({
				html : true
			});

		});
	</script>
	<style type="text/css">
#line-chart {
	height: 300px;
	width: 800px;
	margin: 0px auto;
	margin-top: 1em;
}

.navbar-default .navbar-brand,.navbar-default .navbar-brand:hover {
	color: #fff;
}
</style>

	<script type="text/javascript">
		$(function() {
			var uls = $('.sidebar-nav > ul > *').clone();
			uls.addClass('visible-xs');
			$('#main-menu').append(uls.clone());
		});
	</script>

	<script type="text/javascript">
		var countr = 0;
		var counti = 0;
		function init() {
			var random = Math.random() * 100000000;
			var rvalue = Math.round(random);
			document.getElementById("taskId").value = "" + rvalue;
			$("#uploadify").uploadify({
				'debug' : false, //开启调试  
				'auto' : true, //是否自动上传     
				'swf' : 'uploadify/uploadify.swf', //引入uploadify.swf    
				'uploader' : "upload",//请求路径    
				'queueID' : 'fileQueue',//队列id,用来展示上传进度的    
				'width' : '180', //按钮宽度    
				'formData':{"path": document.getElementById("taskId").value},
				'height' : '35', //按钮高度  
				'rollover':true,
				'queueSizeLimit' : 2, //同时上传文件的个数    
				'fileExt': '*.GWR; *.GWI',
				'fileTypeDesc' : '电池数据', //可选择文件类型说明  
				'fileTypeExts' : '*.GWR; *.GWI', //控制可上传文件的扩展名    
				'multi' : true, //允许多文件上传    
				'buttonText' : '<s:text name="diag.upload.button"/>',//按钮上的文字    
				'fileSizeLimit' : '33MB', //设置单个文件大小限制     
				'fileObjName' : 'uploadify', //<input type="file"/>的name    
				'method' : 'post',
				'removeCompleted' : false,//上传完成后自动删除队列    
				'onFallback' : function() {
					alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
				},
				'onUploadSuccess' : function(file, data, response) {//单个文件上传成功触发    
					//data就是action中返回来的数据    
				},
				'onSelect': function(file)
		        {
					if(file.name.endsWith("GWR")){
						countr = 1;
					}
					if(file.name.endsWith("GWI")){
						counti = 1;
					}
		        },
				'onQueueComplete' : function() {//所有文件上传完成  
					if(countr == 1  && counti == 1)
						$('#subtn').removeAttr("disabled");
				}
			});
		}

		$(function() {
		});
		
	</script>

	<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

	<!-- Le fav and touch icons -->

	<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
	<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
	<!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
	<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
	<!--[if (gt IE 9)|!(IE)]><!-->

	<!--<![endif]-->

	<%@ include file="head.jsp"%>

	<%@ include file="left.jsp"%>

	<div class="content">
		<div class="header">
		
			<div class="stats">
				<p class="stat">
				<label class="fa fa-warning"></label>
					<label class="label label-warning"><s:text name="diag.attentation"/></label>
				</p>
			</div>

			<h1 class="page-title"><s:text name="left.diag.title" /></h1>
			<ul class="breadcrumb">
				<li><a href="User_dashboard"><s:text name="global.index" /></a></li>
				<li class="active"><s:text name="left.diag.title" /></li>
			</ul>


		</div>
						
		<div class="main-content">
			<div class="row">
				<div class="col-md-6">
					<form action="Task_submit" method="post" name="subForm"
						id="subFormId">
						
						<div class="form-group">
							<label><strong><s:text name="diag.task.name"/></strong></label>
							 <input type="text" value='<s:text name="diag.name.default"/>'
								maxlength="30" name="task.name" class="form-control"> <input
								type="hidden" name="task.path" value="" id="taskId">
								<!-- 
								<input type="text" name="sname" id="sid" value="<%=request.getSession().getId()%>">
								<input id="sessionId" type="text" value="${pageContext.session.id}"/> 
								 -->
						</div>
						
						<div class="form-group">
							<!-- 
							<label>上传电池数据</label> 
							 -->
							<input type="file" id="uploadify" name="uploadify" size="90">
							<div id="fileQueue"></div>
							
						</div>
						
						
						<!-- 
						<div class="form-group">
							<label>电池序列号</label> <input type="text" value="" maxlength="30"
								name="task.serialNum" class="form-control">
						</div>
 						-->
 						
						<div class="btn-toolbar list-toolbar">
							<a href="#myModal" data-toggle="modal" class="btn btn-primary"
								disabled id="subtn"><i class="fa fa-save"></i>&nbsp;&nbsp;<s:text name="diag.submit"/></a>
						</div>

					</form>
				</div>
			</div>

			<div class="modal small fade" id="myModal" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">X</button>
							<h3 id="myModalLabel"><s:text name="global.confirmed"/></h3>
						</div>
						<div class="modal-body">
							<p class="error-text">
								<i class="fa fa-warning modal-icon"></i><s:text name="diag.submit.confirm"/>
							</p>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal"
								aria-hidden="true"><s:text name="global.cancel"/></button>
							<button class="btn btn-primary" data-dismiss="modal"
								onclick="submitIt()"><s:text name="global.confirmed"/></button>
						</div>
					</div>
				</div>
			</div>


			<%@ include file="foot.jsp"%>
		</div>


		<script src="lib/bootstrap/js/bootstrap.js"></script>
		<script type="text/javascript">
			$("[rel=tooltip]").tooltip();
			$(function() {
				$('.demo-cancel-click').click(function() {
					return false;
				});
			});

			function submitIt() {
				document.getElementById("subFormId").submit();
			}

			function show() {
				alert(document.getElementById("taskId").value);
			}
		</script>
</body>
</html>
