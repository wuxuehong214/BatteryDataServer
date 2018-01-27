<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>个人信息管理--Green</title>
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

	<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


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
			<h1 class="page-title">个人信息管理</h1>
			<ul class="breadcrumb">
				<li><a href="User_dashboard">首页</a></li>
				<li class="active">个人信息管理</li>
			</ul>

		</div>

		<div class="row" align="left">
			<div class="col-md-8">
				<div id="myTabContent" class="tab-content">
					<form id="tab" action="User_updateBasic" method="post" name="form1">
						<table width="100%" border="0">
							<tr>
								<td width="45%">
									<div class="form-group">
										<label><strong>姓名</strong></label> <input type="text"
											value='<s:property value="#session.user.name"/>'
											class="form-control" name="user.name">
									</div>
								</td>
								<td width="10%">&nbsp;</td>
								<td width="45%">
									<div class="form-group">
										<label><strong>性别</strong></label> <br> <input type="radio" value="0"
											name="user.sex"
											<s:if test="#session.user.sex == 0">checked</s:if>>男
										&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" value="1"
											name="user.sex"
											<s:if test="#session.user.sex == 1">checked</s:if>>女
									</div>
								</td>
							</tr>

							<tr>
								<td>
									<div class="form-group">
										<label><strong>手机号</strong></label> <input type="text"
											value='<s:property value="#session.user.phoneNum"/>'
											name="user.phoneNum" class="form-control" maxlength="20">
									</div>
								</td>
								<td>&nbsp;</td>
								<td>
									<div class="form-group">
										<label><strong>QQ号码</strong></label> <input type="text"
											value='<s:property value="#session.user.qq"/>'
											class="form-control" name="user.qq" maxlength="36">
									</div>
								</td>
							</tr>

							<tr>
								<td>
									<div class="form-group">
										<label><strong>Email</strong></label> <input type="text"
											value='<s:property value="#session.user.email"/>'
											class="form-control" name="user.email" maxlength="36">
									</div>
								</td>
								<td>&nbsp;</td>
								<td>
									<div class="form-group">
										<label><strong>公司</strong></label> <input type="text"
											value='<s:property value="#session.user.company"/>'
											class="form-control" name="user.company" maxlength="64">
									</div>
								</td>
							</tr>

							<td colspan="3">
								<div class="form-group">
									<label><strong>地址</strong></label> <input type="text"
										value='<s:property value="#session.user.address"/>'
										class="form-control" name="user.address" maxlength="64">
								</div>
							</td>

							<tr>
								<td colspan="3">
									<div class="btn-toolbar list-toolbar">
										<button class="btn btn-primary"
											onclick="javascript:form1.submit()">
											<i class="fa fa-save"></i> 保存
										</button>
										<font color="red"><s:property value="tip"/></font>
									</div>
								</td>
							</tr>
						</table>
					</form>

				</div>
			</div>
		</div>



		<%@ include file="foot.jsp"%>
	</div>
	</div>


	<script src="lib/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		$(function() {
			$('.demo-cancel-click').click(function() {
				return false;
			});
		});
	</script>


</body>
</html>
