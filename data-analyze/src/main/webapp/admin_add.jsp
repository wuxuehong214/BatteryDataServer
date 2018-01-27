<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title><s:text name="user.admin.add" />--Greenway</title>
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
		<!-- 
			<div class="stats">
				<p class="stat">
					<span class="label label-info">5</span> Tickets
				</p>
				<p class="stat">
					<span class="label label-success">27</span> Tasks
				</p>
				<p class="stat">
					<span class="label label-danger">15</span> Overdue
				</p>
			</div>
 -->
			<h1 class="page-title"><s:text name="user.admin.add" /></h1>
			<ul class="breadcrumb">
				<li><a href="User_dashboard"><s:text name="global.index" /></a></li>
				<li><a href="Lang_jsp?page=admin_manage"><s:text name="left.account.admin" /></a></li>
				<li class="active"><s:text name="user.admin.add" /></li>
			</ul>

		</div>
		
		<div class="btn-toolbar list-toolbar">
			<button onclick="javascript:form1.submit()" class="btn btn-primary">
				<i class="fa fa-save"></i> <s:text name="global.save"/>
			</button>
			 <font color="red"><s:property value="tip"/></font>
			<div class="btn-group"></div>
		</div>
		

		<ul class="nav nav-tabs">
			<li class="active"><a href="#profile" data-toggle="tab"><s:text name="user.admin.account"/></a></li>
			<li><a href="#home" data-toggle="tab"><s:text name="user.basic"/></a></li>
		</ul>

		<div class="row">
			<div class="col-md-4">
				<br>
				<form id="tab" action="User_add" method="post" name="form1" id="formId">
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade" id="home">
							<div class="form-group">
								<label><s:text name="user.name"/></label> <input type="text"
									value='<s:property value=""/>' class="form-control"
									name="user.name" maxlength="30">
									<input type="hidden" value="0" name="user.role">
									<input type="hidden" value="admin_manage" name="pagetmp">
							</div>
							<div class="form-group">
								<label><s:text name="user.sex"/></label> <br> <input type="radio" value="0"
									name="user.sex" checked><s:text name="user.sex.male"/> &nbsp;&nbsp;&nbsp;&nbsp; <input
									type="radio" value="1" name="user.sex"><s:text name="user.sex.female"/>
							</div>
							<div class="form-group">
								<label><s:text name="user.phone"/></label> <input type="text"
									value='<s:property value=""/>' name="user.phoneNum"
									class="form-control" maxlength="20">
							</div>
							<div class="form-group">
								<label><s:text name="user.email"/></label> <input type="text"
									value='<s:property value=""/>' class="form-control"
									name="user.email" maxlength="36">
							</div>

							<div class="form-group">
								<label><s:text name="user.qq"/></label> <input type="text"
									value='<s:property value=""/>' class="form-control"
									name="user.qq" maxlength="36">
							</div>

							<div class="form-group">
								<label><s:text name="user.company"/></label> <input type="text"
									value='<s:property value=""/>' class="form-control"
									name="user.company" maxlength="64">
							</div>

							<div class="form-group">
								<label><s:text name="user.address"/></label> <input type="text"
									value='<s:property value=""/>' class="form-control"
									name="user.address" maxlength="64">
							</div>

						</div>

						<div class="tab-pane active in" id="profile">

							<div class="form-group">
								<label><s:text name="login.form.username"/></label> <input type="text" class="form-control"
									name="user.userName">
							</div>
							<div class="form-group">
								<label><s:text name="login.form.userpwd"/></label> <input type="password" value="123456"
									class="form-control" name="user.passWord">
							</div>
							<!--  
							<div class="form-group">
								<label>确认密码</label> <input type="password" value="123456"
									class="form-control" name="password">
							</div>
							-->
						</div>
					</div>
				</form>

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
