<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title><s:text name="dashboard.title"/></title>
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

<script src="lib/jQuery-Knob/js/jquery.knob.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".knob").knob();
	});
</script>


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
					<span class="label label-info"><s:property
							value="status.total" /></span> <s:text name="dashboard.requests"/>
				</p>
				<p class="stat">
					<span class="label label-success"><s:property
							value="status.finished" /></span> <s:text name="dashboard.finished"/>
				</p>
				<p class="stat">
					<span class="label label-danger"><s:property
							value="status.exception" /></span> <s:text name="dashboard.exceptions"/>
				</p>
			</div>

			<h1 class="page-title"><s:text name="left.dashboard.dashboard"/></h1>
			<ul class="breadcrumb">
				<li><a href="User_dashboard"><s:text name="global.index"></s:text></a></li>
				<li class="active"><s:text name="left.dashboard.dashboard"/></li>
			</ul>
		</div>
		<div class="main-content">

			<!-- 诊断任务状态统计 -->
			<div class="panel panel-default">
				<div class="panel-heading no-collapse">
					<span class="panel-icon pull-right"> <a
						href="Lang_jsp?page=analyze_manage" rel="tooltip" title='<s:text name="dashboard.diag"/>'><i
							class="fa fa-code-fork"></i></a>
					</span> <i class="fa fa-pie-chart"></i><strong> <s:text name="dashboard.stastic"/></strong>
				</div>

				<div class="row">
					<div class="col-md-3 col-sm-6">
						<div class="knob-container">
							<input class="knob" data-width="200" data-min="0"
								data-max="<s:property value='status.total' />"
								data-displayPrevious="true"
								value="<s:property value='status.wait' />"
								data-fgColor="#55A355" data-readOnly=true;>
							<h3 class="text-muted text-center">
								<label class="label label-primary">
								<s:text name="dashboard.wait"/>
								</label>
							</h3>
						</div>
					</div>
					<div class="col-md-3 col-sm-6">
						<div class="knob-container">
							<input class="knob" data-width="200" data-min="0"
								data-max="<s:property value='status.total' />"
								data-displayPrevious="true"
								value="<s:property value='status.executing' />"
								data-fgColor="#55A355" data-readOnly=true;>
							<h3 class="text-muted text-center text-primary">
								<label class="label label-info"><s:text name="dashboard.execute"/></label>
							</h3>
						</div>
					</div>
					<div class="col-md-3 col-sm-6">
						<div class="knob-container">
							<input class="knob" data-width="200" data-min="0"
								data-max="<s:property value='status.total' />"
								data-displayPrevious="true"
								value="<s:property value='status.finished' />"
								data-fgColor="#55A355" data-readOnly=true;>
							<h3 class="text-muted text-center">
								<label class="label label-success"><s:text name="dashboard.finshed"/></label>
							</h3>
						</div>
					</div>
					<div class="col-md-3 col-sm-6">
						<div class="knob-container">
							<input class="knob" data-width="200" data-min="0"
								data-max="<s:property value='status.total' />"
								data-displayPrevious="true"
								value="<s:property value='status.exception' />"
								data-fgColor="#55A355" data-readOnly=true;>
							<h3 class="text-muted text-center">
								<label class="label label-danger"><s:text name="dashboard.exception"/></label>
							</h3>
						</div>
					</div>
				</div>
			</div>


			<!-- 个人信息 以及 登录日志 -->
			<div class="row">
				<div class="col-sm-6 col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading no-collapse">
						<i class="fa fa-user"></i>
							<strong><s:text name="dashboard.personal"/></strong><span class="panel-icon pull-right">
								<a href="Lang_jsp?page=personal_profile" rel="tooltip" title='<s:text name="global.edit"/>'><i
									class="fa fa-edit"></i></a>
							</span>
						</div>
						<table class="table table-bordered table-striped">
							<tbody style="font-size: small;">
								<tr>
									<td align="right"><strong><s:text name="user.name"/></strong></td>
									<td colspan="1"><s:property value="#session.user.name" /></td>
									<td align="right"><strong><s:text name="user.lastlogin"/></strong></td>
									<td colspan="1"><span class="label label-info"> <s:date
												name="#session.user.lastLogin" format="yyyy/MM/dd HH:mm" /></span></td>
								</tr>
								<tr>
									<td align="right"><strong><s:text name="user.phone"/></strong></td>
									<td colspan="1"><s:property value="#session.user.phoneNum" /></td>
									<td align="right"><strong><s:text name="user.qq"/></strong></td>
									<td colspan="1"><s:property value="#session.user.qq" /></td>
								</tr>
								<tr>
									<td align="right"><strong><s:text name="user.email"/></strong></td>
									<td colspan="1"><s:property value="#session.user.email" /></td>
									<td align="right"><strong><s:text name="user.company"/></strong></td>
									<td colspan="1"><s:property value="#session.user.company" /></td>
								</tr>
								<tr>
									<td align="right"><strong><s:text name="user.address"/></strong></td>
									<td colspan="3"><s:property value="#session.user.address" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!--  
				<div class="col-sm-4 col-md-4">
					<div class="panel panel-default">
						<a href="#widget1container" class="panel-heading"
							data-toggle="collapse">
							<i class="fa fa-unlink"></i>
							<strong> 快速入口</strong> </a>
						<div id="widget1container" class="panel-body collapse in"
							align="center">
							<p>
								<a href="#">电池诊断</a>
							</p>
							<p>
								<a href="#">诊断查询</a>
							</p>
							<p>
								<a href="#">资料编辑</a>
							</p>
							<p>
								<a href="#">密码修改</a>
							</p>
							<p>
								<a href="#">更多...</a>
							</p>
						</div>
					</div>
				</div>
				-->
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
