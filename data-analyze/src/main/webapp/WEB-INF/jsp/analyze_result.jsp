<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>电池诊断结果--Green</title>
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

<!-- jqplot start -->
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="jqpllot/excanvas.js"></script><![endif]-->
<script language="javascript" type="text/javascript"
	src="jqplot/jquery.jqplot.min.js"></script>
<link rel="stylesheet" type="text/css" href="jqplot/jquery.jqplot.css" />
<!-- jqplot end -->

<!-- echart start -->
<script language="javascript" type="text/javascript"
	src="echart/echarts.common.min.js"></script>
<!-- echart end -->

<!-- datatable start
<link rel="stylesheet" type="text/css"
	href="lib/datatable/css/jquery.dataTables.min.css" />
<script src="lib/datatable/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
	 -->
<!-- datatable end -->

<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="stylesheets/premium.css">

<script src="lib/jQuery-Knob/js/jquery.knob.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".knob").knob();
	});
</script>

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
		var table;
		$(function() {
			table = $('#cdid').DataTable({
				"ajax" : "cfds",
				"processing" : true,
				"retrieve" : true,
				"columns" : [ {
					data : "startTime"
				}, {
					data : "endTime"
				}, {
					data : "continus"
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

			<h1 class="page-title">诊断结果</h1>
			<ul class="breadcrumb">
				<li><a href="User_dashboard">首页</a></li>
				<li><a href="analyze_history.jsp">诊断历史</a></li>
				<li class="active">诊断结果</li>
			</ul>

		</div>

		<div class="main-content">

			<!-- 电池概述 -->
			<div class="panel panel-default">
				<div class="panel-heading no-collapse">
					<strong>电池信息概述</strong> <span class="label label-success">状态:正常</span>
				</div>
				<table class="table list">
					<tbody>
						<tr align="center">
							<td><p class="text-info h4">电池制造商</p>
								<p class="info">
									<s:property value="battery.zzsmc" />
								</p></td>
							<td><p class="text-info h4">电池型号</p>
								<p class="info">
									<s:property value="battery.dcxh" />
								</p></td>
							<td><p class="text-info h4">电芯型号</p>
								<p class="info">
									<s:property value="battery.dxxh" />
								</p></td>
							<td><p class="text-info h4">电池序列号</p>
								<p class="info">
									<s:property value="battery.serialNum" />
								</p></td>
							<td><p class="text-info h4">额定电压</p>
								<p class="info">
									<s:property value="battery.sjdy" />
									mV
								</p></td>
							<td><p class="text-info h4">额定容量</p>
								<p class="info">
									<s:property value="battery.sjrl" />
									mAh
								</p></td>
							<td><p class="text-info h4">生产日期</p>
								<p class="info">2015-12-32</p></td>
						</tr>
					</tbody>
				</table>
			</div>

			<!-- 电池概述 -->
			<div class="panel panel-default">
				<div class="panel-heading no-collapse">
					<strong>最新状态值</strong> <span class="label label-primary">电池时钟:<s:property
							value="battery.curTime" /></span>
				</div>
				<table class="table list">
					<tbody>
						<tr align="center">
							<td><p class="text-info h4">电池电压</p>
								<p class="info">
									<s:property value="battery.zdy" />
									mV
								</p></td>
							<td><p class="text-info h4">电池温度</p>
								<p class="info">
									<s:property value="battery.wd" />
									ºC
								</p></td>
							<td><p class="text-info h4">电池电流</p>
								<p class="info">
									<s:property value="battery.dl" />
									mA
								</p></td>
							<td><p class="text-info h4">剩余容量</p>
								<p class="info">
									<s:property value="battery.syrl" />
									mAh
								</p></td>
							<td><p class="text-info h4">电池循环数</p>
								<p class="info">
									<s:property value="battery.xhcs" />
								</p></td>
							<td><p class="text-info h4">剩余容量百分比</p>
								<p class="info">
									<s:property value="battery.syrl_bfb" />
									%
								</p></td>
							<td><p class="text-info h4">健康状态百分比</p>
								<p class="info">
									<s:property value="battery.jkzt_bfb" />
									%
								</p></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab1" data-toggle="tab"><strong>电压/电流/温度统计</strong></a></li>
			<li><a href="#tab2" data-toggle="tab"><strong>充放电记录</strong></a></li>
			<li><a href="#tab3" data-toggle="tab"><strong>最值统计</strong></a></li>
			<li><a href="#tab4" data-toggle="tab"><strong>3个月未充电记录</strong><span
					class="label label-danger"><s:property value="wcds.size" /></span></a></li>
		</ul>


		<div id="myTabContent" class="tab-content">

			<!-- 电流 电压  温度统计 -->
			<div class="tab-pane active in" id="tab1">
				<br />
				<div class="row">
					<div class="col-md-12">
						<div class="panel panel-default">
							<a href="#widget1container" class="panel-heading"
								data-toggle="collapse" onclick="alert(9)">电池电流统计 </a>
							<div id="widget1container" class="panel-body collapse in">
								<div id="chart_dl" style="height: 250px;"></div>
							</div>
						</div>
					</div>

					<div class="col-md-12">
						<div class="panel panel-default">
							<a href="#widget2container" class="panel-heading"
								data-toggle="collapse">电池电压统计 </a>
							<div id="widget2container" class="panel-body collapse in">
								<div id="chart_dy" style="height: 250px;"></div>
							</div>
						</div>
					</div>

					<div class="col-md-12">
						<div class="panel panel-default">
							<a href="#widget3container" class="panel-heading"
								data-toggle="collapse">电池温度统计 </a>
							<div id="widget3container" class="panel-body collapse in">
								<div id="chart_wd" style="height: 250px;"></div>
							</div>
						</div>
					</div>

					<script type="text/javascript">
						var dom_dl = document.getElementById("chart_dl");
						var dom_dy = document.getElementById("chart_dy");
						var dom_wd = document.getElementById("chart_wd");
						var myChart_dl = echarts.init(dom_dl);
						var myChart_dy = echarts.init(dom_dy);
						var myChart_wd = echarts.init(dom_wd);
						var option = new Array(3);
						var titles = [ "历史电流数据统计图", "历史电压数据统计图", "历史温度数据统计图" ];
						var snames = [ "电流值(mA)", "电压值(mV)", "温度(C)" ]
						for (var i = 0; i < 3; i++)
							//电流  电压  温度
							option[i] = {
								tooltip : {
									trigger : 'axis'
								},
								title : {
									left : 'center',
									text : titles[i],
								},
								legend : {
									top : 'bottom',
									data : [ '意向' ]
								},
								toolbox : {
									show : true,
									feature : {
										dataView : {
											show : true,
											readOnly : false
										},
										magicType : {
											show : true,
											type : [ 'line', 'bar', 'stack',
													'tiled' ]
										},
										restore : {
											show : true
										},
										saveAsImage : {
											show : true
										}
									}
								},
								xAxis : {
									type : 'category',
									boundaryGap : false,
									data : []
								},
								yAxis : {
									type : 'value',
									boundaryGap : [ 0, '100%' ]
								},
								dataZoom : [ {
									type : 'inside',
									start : 0,
									end : 10
								}, {
									start : 0,
									end : 10
								} ],
								series : [ {
									name : snames[i],
									type : 'line',
									smooth : true,
									symbol : 'none',
									sampling : 'average',
									itemStyle : {
										normal : {
											color : 'rgb(255, 70, 131)'
										}
									},
									areaStyle : {
										normal : {
											color : new echarts.graphic.LinearGradient(
													0,
													0,
													0,
													1,
													[
															{
																offset : 0,
																color : 'rgb(255, 158, 68)'
															},
															{
																offset : 1,
																color : 'rgb(255, 70, 131)'
															} ])
										}
									},
									data : []
								} ]
							};

						myChart_dl.setOption(option[0], true);
						myChart_dy.setOption(option[1], true);
						myChart_wd.setOption(option[2], true);

						$.ajax({
							async : true,
							url : 'ylws?taskid='+<s:property value="taskid"/>,
							dataType : "json",
							success : function(data) {
								console.log(data.data[0].xs);
								myChart_wd.setOption({
									xAxis : {
										data : data.data[0].xs
									},
									series : [ {
										name : "温度(C)",
										data : data.data[0].values
									} ]

								});
								myChart_dl.setOption({
									xAxis : {
										data : data.data[1].xs
									},
									series : [ {
										name : "电流值(mA)",
										data : data.data[1].values
									} ]

								});
								myChart_dy.setOption({
									xAxis : {
										data : data.data[2].xs
									},
									series : [ {
										name : "电压值(mV)",
										data : data.data[2].values
									} ]

								});

							},
							error : function() {
								console.log("error");
							}
						});
					</script>
				</div>
			</div>

			<!-- 充放电记录 -->
			<div class="tab-pane fade" id="tab2">
				<br />
				<div class="row">
					<div class="col-md-6 col-sm-6">
						<div class="panel panel-default">
							<a href="#widget4container" class="panel-heading"
								data-toggle="collapse">电池充电记录<span
								class="label label-primary">记录数:<s:property
										value="cds.size" /></span></a>

							<div id="widget4container" class="panel-body collapse in">
								<table id="cdid" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>开始充电时刻</th>
											<th>结束充电时刻</th>
											<th>持续充电时间(秒)</th>
										</tr>
									</thead>

									<tbody>
										<s:iterator var="cd" value="cds" status="st">
											<tr>
												<td><s:property value="#cd.startTimes" /></td>
												<td><s:property value="#cd.endTimes" /></td>
												<td><s:property value="#cd.dateDesc" /></td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="col-md-6 col-sm-6">
						<div class="panel panel-default">
							<a href="#widget5container" class="panel-heading"
								data-toggle="collapse">电池放电记录 <span
								class="label label-primary">记录数:<s:property
										value="fds.size" /></span>
							</a>
							<div id="widget5container" class="panel-body collapse in">
								<table class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>开始放电电时刻</th>
											<th>结束放电电时刻</th>
											<th>持续放电时间</th>
										</tr>
									</thead>
									<tbody>
										<s:iterator var="fd" value="fds" status="st">
											<tr>
												<td><s:property value="#fd.startTimes" /></td>
												<td><s:property value="#fd.endTimes" /></td>
												<td><s:property value="#fd.dateDesc" /></td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
			</div>


			<!-- 最值统计 -->
			<div class="tab-pane fade" id="tab3">
				<br />
				<div class="row">
					<div class="col-md-12">
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>统计项</th>
									<th>值</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>持续时间</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator var="mm" value="mmrecods" status="st">
									<tr>
										<td><s:property value="#mm.typeDescp" /></td>
										<td><s:property value="#mm.valueDescp" /></td>
										<td><s:property value="#mm.startTimes" /></td>
										<td><s:property value="#mm.endTimes" /></td>
										<td><s:property value="#mm.continuesDescp" /></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<!-- 三个月未充电记录 -->

			<div class="tab-pane fade" id="tab4">
				<br />
				<div class="row">
					<div class="col-md-12">
						<table class="table table-bordered table-striped">
							<thead>
								<tr>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>持续时间</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator var="wcd" value="wcds" status="st">
									<tr>
										<td><s:property value="#wcd.startTime" /></td>
										<td><s:property value="#wcd.endTime" /></td>
										<td><s:property value="#wcd.dateDesc" /></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
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
