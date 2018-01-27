<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title><s:text name="result.title"/>--Greenway</title>
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

			<h1 class="page-title"><s:text name="result.title"/></h1>
			<ul class="breadcrumb">
				<li><a href="User_dashboard"><s:text name="global.index"/></a></li>
				<li><a href="Lang_jsp?page=analyze_mine"><s:text name="left.diag.mine"/></a></li>
				<li class="active"><s:text name="result.title"/></li>
			</ul>

		</div>

		<div class="main-content">
		
			<!-- 诊断任务概述 -->
			<div class="panel panel-default">
				<div class="panel-heading no-collapse">
					<strong><s:text name="result.task.summary"/></strong> 
					
					<s:if test="task.state == 'EXCEPTION'">
							<span class="label label-danger">
					</s:if>
					<s:elseif test="task.state == 'FINISHED'">
							<span class="label label-success">
					</s:elseif>
					<s:else>
							<span class="label label-primary">
					</s:else>
					<s:text name="global.state"/> : <s:property value="task.stateDescp" /></span>
				</div>
				<table class="table list">
					<tbody>
						<tr align="center">
							<td><p class="text-info h5"><s:text name="task.name"/></p>
								<p class="info">
									<s:property value="task.name" />
								</p></td>
							<td><p class="text-info h5"><s:text name="task.subtime"/></p>
								<p class="info">
									<s:property value="task.subTimes" />
								</p></td>
							<td><p class="text-info h5"><s:text name="task.executetime"/></p>
								<p class="info">
									<s:property value="task.executeTimes" />
								</p></td>
							<td><p class="text-info h5"><s:text name="task.finishtime"/></p>
								<p class="info">
									<s:property value="task.finishTimes" />
								</p></td>
						</tr>
					</tbody>
				</table>
			</div>

			<!-- 电池概述 -->
			<div class="panel panel-default">
				<div class="panel-heading no-collapse">
					<strong><s:text name="result.battery.summary"/></strong> 
					<s:if test="battery.dczt == 0">
						<span class="label label-success">
						<s:text name="global.state"/> : <s:text name="battery.state.ok"/>
					</span>
					</s:if>
					<s:else>
						<span class="label label-danger">
						<s:text name="global.state"/> : <s:text name="battery.state.notok"/>
						</span>
					</s:else>
				</div>
				<table class="table list">
					<tbody>
						<tr align="center">
							<td><p class="text-info h5"><s:text name="battery.zzcs"/></p>
								<p class="info">
									<s:property value="battery.zzsmc" />
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.dcxh"/></p>
								<p class="info">
									<s:property value="battery.dcxh" />
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.dxxh"/></p>
								<p class="info">
									<s:property value="battery.dxxh" />
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.serialnum"/></p>
								<p class="info">
									<s:property value="battery.serialNum" />
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.eddy"/></p>
								<p class="info">
									<s:property value="battery.sjdy" />
									mV
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.capacity"/></p>
								<p class="info">
									<s:property value="battery.sjrl" />
									mAh
								</p></td>
						</tr>
					</tbody>
				</table>
			</div>

			<!-- 电池概述 -->
			<div class="panel panel-default">
				<div class="panel-heading no-collapse">
					<strong><s:text name="battery.latest.summary"/></strong> <span class="label label-primary"><s:text name="battery.rtc"/> : <s:property
							value="battery.curTime" /></span>
				</div>
				<table class="table list">
					<tbody>
						<tr align="center">
							<td><p class="text-info h5"><s:text name="battery.dy"/></p>
								<p class="info">
									<s:property value="battery.zdy" />
									mV
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.wd"/></p>
								<p class="info">
									<s:property value="battery.wd" />
									ºC
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.dl"/></p>
								<p class="info">
									<s:property value="battery.dl" />
									mA
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.syrl"/></p>
								<p class="info">
									<s:property value="battery.syrl" />
									mAh
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.cycle"/></p>
								<p class="info">
									<s:property value="battery.xhcs" />
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.syrl_bfb"/></p>
								<p class="info">
									<s:property value="battery.syrl_bfb" />
									%
								</p></td>
							<td><p class="text-info h5"><s:text name="battery.jkzt_bfb"/></p>
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
			<li class="active"><a href="#tab1" data-toggle="tab"><strong><s:text name="battery.statistics"/></strong></a></li>
			<li><a href="#tab2" data-toggle="tab"><strong><s:text name="battery.cfd"/></strong></a></li>
			<li><a href="#tab3" data-toggle="tab"><strong><s:text name="battery.maxmin"/></strong></a></li>
			<li><a href="#tab4" data-toggle="tab"><strong><s:text name="battery.withoutcharge"/></strong><span
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
								data-toggle="collapse" ><strong><s:text name="battery.statistics.dl"/></strong> </a>
							<div id="widget1container" class="panel-body collapse in">
								<div id="chart_dl" style="height: 250px;"></div>
							</div>
						</div>
					</div>

					<div class="col-md-12">
						<div class="panel panel-default">
							<a href="#widget2container" class="panel-heading"
								data-toggle="collapse"><strong><s:text name="battery.statistics.dy"/></strong> </a>
							<div id="widget2container" class="panel-body collapse in">
								<div id="chart_dy" style="height: 250px;"></div>
							</div>
						</div>
					</div>

					<div class="col-md-12">
						<div class="panel panel-default">
							<a href="#widget3container" class="panel-heading"
								data-toggle="collapse"><strong><s:text name="battery.statistics.wd"/></strong> </a>
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
						var titles = [ '<s:text name="battery.statistics.dl2"/>', '<s:text name="battery.statistics.dy2"/>', '<s:text name="battery.statistics.wd2"/>' ];
						var snames = [ '<s:text name="battery.dl.unit"/>', '<s:text name="battery.dy.unit"/>', '<s:text name="battery.wd.unit"/>' ]
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
									data : [ 'Tend' ]
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
										name : '<s:text name="battery.wd.unit"/>',
										data : data.data[0].values
									} ]

								});
								myChart_dl.setOption({
									xAxis : {
										data : data.data[1].xs
									},
									series : [ {
										name : '<s:text name="battery.dl.unit"/>',
										data : data.data[1].values
									} ]

								});
								myChart_dy.setOption({
									xAxis : {
										data : data.data[2].xs
									},
									series : [ {
										name : '<s:text name="battery.dy.unit"/>',
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
								data-toggle="collapse"><strong><s:text name="battery.cdjl"/></strong><span
								class="label label-primary"><s:text name="battery.records"/>:<s:property
										value="cds.size" /></span></a>

							<div id="widget4container" class="panel-body collapse in">
								<table id="cdid" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th><s:text name="battery.cfd.start"/></th>
											<th><s:text name="battery.cfd.end"/></th>
											<th><s:text name="battery.cfd.last"/></th>
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
								data-toggle="collapse"><strong><s:text name="battery.fdjl"/></strong> <span
								class="label label-primary"><s:text name="battery.records"/>:<s:property
										value="fds.size" /></span>
							</a>
							<div id="widget5container" class="panel-body collapse in">
								<table class="table table-bordered table-striped">
									<thead>
										<tr>
											<th><s:text name="battery.cfd.start"/></th>
											<th><s:text name="battery.cfd.end"/></th>
											<th><s:text name="battery.cfd.last"/></th>
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
									<th><s:text name="battery.mm.item"/></th>
									<th><s:text name="battery.mm.value"/></th>
									<th><s:text name="battery.cfd.start"/></th>
									<th><s:text name="battery.cfd.end"/></th>
									<th><s:text name="battery.cfd.last"/></th>
								</tr>
							</thead>
							<tbody>
								<s:iterator var="mm" value="mmrecods" status="st">
									<tr>
										<td>
											<s:property value="#mm.typeDescp" />
										</td>
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
									<th><s:text name="battery.cfd.start"/></th>
									<th><s:text name="battery.cfd.end"/></th>
									<th><s:text name="battery.cfd.last"/></th>
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
