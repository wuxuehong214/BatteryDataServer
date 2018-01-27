<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title><s:text name="login.title" /></title>
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

<script src="js/login.js" type="text/javascript"></script>
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

	<!-- Le fav and touch icons -->

	<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
	<!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
	<!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
	<!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
	<!--[if (gt IE 9)|!(IE)]><!-->

	<!--<![endif]-->

	<div class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<a class="" href="index.jsp"><span class="navbar-brand"><span
					class="fa fa-paper-plane"></span> <s:text name="global.head.title" /></span></a>
		</div>
		<br />
		<p class="pull-right">
			<a href="Lang_change?request_locale=zh_CN" style="color: white;">中文</a>
			| <a href="Lang_change?request_locale=en_US" style="color: white;">English</a>
		</p>
	</div>
	</div>



	<div class="dialog">
		<div class="panel panel-default">
			<p class="panel-heading no-collapse">
				<s:text name="login.form.title" />
			</p>
			<div class="panel-body">

				<form name="form1" action="User_login" method="post">
					<div class="form-group">
						<label><s:text name="login.form.username" /></label> <input
							type="text" class="form-control span12" name="user.userName">
					</div>
					<div class="form-group">
						<label><s:text name="login.form.userpwd" /></label> <input
							type="password" class="form-controlspan12 form-control"
							name="user.passWord">
					</div>
					<button class="btn btn-primary pull-right" onclick="goLogin(form1)">
						<s:text name="login.form.login" />
					</button>
					<label class="remember-me"><input type="checkbox">
						<s:text name="login.form.remember" /></label> <font color="red"><s:property
							value="loginTip" /></font>
					<div class="clearfix"></div>
				</form>

			</div>
		</div>
		<p class="pull-right" style="">
			<a href="http://www.greenway-battery.com/" target="blank"
				style="font-size: .75em; margin-top: .25em;"><s:text
					name="login.form.support" /></a>
		</p>
		<p>
			<a href="#myModal" role="button" data-toggle="modal"><s:text name="login.form.forgetpwd" /></a>
		</p>
	</div>

	<div class="modal small fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button>
						<h3 id="myModalLabel"><s:text name="login.forget.title" /></h3>
					</div>
					<div class="modal-body">
						<p class="error-text">
							<i class="fa fa-warning modal-icon"></i><s:text name="login.forget.tips" />
						</p>
					</div>
					
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal"
							aria-hidden="true"><s:text name="global.confirmed" /></button>
					</div>
				</div>
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
