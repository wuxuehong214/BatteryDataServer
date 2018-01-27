<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<head>
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">
</head>
<div class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="" href="index.html"><span class="navbar-brand"><span
				class="fa fa-paper-plane"></span>
				<s:text name="global.head.title"/></span></a>
	</div>

	<div class="navbar-collapse collapse" style="height: 1px;">
		<ul id="main-menu" class="nav navbar-nav navbar-right">
			<li class="dropdown hidden-xs"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"> <span
					class="glyphicon glyphicon-user padding-right-small"
					style="position: relative; top: 3px;"></span> 
					<s:if test="#session.user.name!=null">
					   <s:property value="#session.user.name"></s:property> 
					</s:if>
					<s:else>
					     <s:property value="#session.user.userName"></s:property> 
					</s:else>
					<i class="fa fa-caret-down"></i>
			</a>

				<ul class="dropdown-menu">
					<li><a href="personal_profile.jsp">个人信息管理</a></li>
					<li class="divider"></li>
					<!-- 
					<li class="dropdown-header">gongzuotai</li>
					 -->
					<li><a href="User_dashboard">工作台</a></li>
					<!-- 
					<li><a tabindex="-1" href="./">Payments</a></li>
					 -->
					<li class="divider"></li>
					<li><a tabindex="-1" href="User_logout">退出</a></li>
				</ul></li>
		</ul>

	</div>
</div>