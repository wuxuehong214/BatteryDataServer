<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>客户账号管理--Green</title>
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

<!-- datatable start -->
<link rel="stylesheet" type="text/css"
	href="lib/datatable/css/jquery.dataTables.min.css" />
<script src="lib/datatable/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
<!-- datatable end -->


<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">
</head>
<body class=" theme-blue">

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
		var table;
		$(function() {
			table = $('#example').DataTable({
				"ajax" : "users?role=0",
				"language" : {
					"emptyTable" : "当前无数据!",
					"info" : "当前第_PAGE_页 共[_PAGES_]页-[_TOTAL_]条记录",
					"infoEmpty" : "无",
					"infoFiltered" : "(从_MAX_条记录中检索出)",
					"lengthMenu" : "显示_MENU_条",
					"loadingRecords" : "加载中...",
					"processing" : "处理中...",
					"search" : "查找:",
					"zeroRecords" : "没有查询到符合条件的记录!",
					"paginate" : {
						"first" : "首页",
						"last" : "尾页",
						"next" : "下一页",
						"previous" : "上一页"
					},
				},
				"processing" : true,
				"retrieve" : true,
				"columns" : [ {
					data : "userName"
				}, {
					data : "name"
				}, {
					data : "sexs"
				}, {
					data : "phoneNum"
				}, {
					data : "email"
				}, {
					data : "qq"
				}, {
					data : "lastLogins"
				}, {
					data : "remark"
				} ]
			});
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
			<h1 class="page-title">管理账号管理</h1>
			<ul class="breadcrumb">
				<li><a href="index.jsp">首页</a></li>
				<li class="active">管理账号管理</li>
			</ul>
		</div>

		<div class="btn-toolbar list-toolbar">
			<a href="admin_add.jsp" class="btn btn-primary"> <i
				class="fa fa-plus"></i> 新增管理账号
			</a>
			<div class="btn-group"></div>
		</div>

		<table id="example" class="data display datatable" cellspacing="0"
			width="100%">
			<thead>
				<tr>
					<th>管理账号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>电话号码</th>
					<th>Email</th>
					<th>QQ</th>
					<th>最新登录时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tfoot>
				<tr>
					<th>管理账号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>电话号码</th>
					<th>Email</th>
					<th>QQ</th>
					<th>最新登录时间</th>
					<th>操作</th>
				</tr>
			</tfoot>
		</table>

		<form action="User_del" method="post" name="subForm2" id="subFormId2">
			<input type="hidden" id="uid" name="user.id" /> <input type="hidden"
				id="pageid" name="page" value="admin_manage" />
		</form>

		<form action="User_query" method="post" name="subForm3"
			id="subFormId3">
			<input type="hidden" id="uid2" name="user.id" /> <input type="hidden"
				id="pageid" name="page" value="admin_edit" />
		</form>

		<div class="modal small fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button>
						<h3 id="myModalLabel">确认</h3>
					</div>
					<div class="modal-body">
						<p class="error-text">
							<i class="fa fa-warning modal-icon"></i>确认要删除该账号么?<br>删除后将无法恢复！.
						</p>
					</div>

					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal"
							aria-hidden="true">取消</button>
						<button class="btn btn-primary" data-dismiss="modal"
							onclick="confirmIt()">确认</button>
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

		function delUser(id) {
			document.getElementById("uid").value = id;
			return false;
		}

		function confirmIt() {
			document.getElementById("subFormId2").submit();
		}

		function editUser(id) {
			document.getElementById("uid2").value = id;
			document.getElementById("subFormId3").submit();
		}
	</script>


</body>
</html>
