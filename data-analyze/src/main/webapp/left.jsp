<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@taglib uri="/struts-tags" prefix="s"%>

<div class="sidebar-nav">
	<ul>


		<!-- 个人工作台 -->
		<li data-popover="true" rel="popover" data-placement="right"><a
			href="#" data-target=".dashboard-menu" class="nav-header collapsed"
			data-toggle="collapse"><i class="fa fa-fw fa-dashboard"></i> <s:text
					name="left.dashboard.title"/>
					<i class="fa fa-collapse"></i></a></li>
		<li><ul class="dashboard-menu nav nav-list collapse in">
				<li><a href="User_dashboard"><span
						class="fa fa-caret-right"></span> <s:text
							name="left.dashboard.dashboard" /></a></li>
				<li><a href="Lang_jsp?page=personal_profile"><span
						class="fa fa-caret-right"></span> <s:text
							name="left.dashboard.profile" /></a></li>
				<li><a href="Lang_jsp?page=personal_password"><span
						class="fa fa-caret-right"></span> <s:text
							name="left.dashboard.password" /></a></li>
			</ul></li>


		<!-- 电池诊断 -->
		<li data-popover="true" rel="popover" data-placement="right"><a
			href="#" data-target=".analyze-menu" class="nav-header collapsed"
			data-toggle="collapse"><i class="fa fa-code-fork"></i><s:text
					name="left.diag.title" /> <i class="fa fa-collapse"></i></a></li>
		<li><ul class="analyze-menu nav nav-list collapse in">
				<li onclick="test(this)"><a href="Lang_jsp?page=analyze_manage"><span
						class="fa fa-caret-right"></span> <s:text name="left.diag.diag" /></a></li>
				<li onclick="test(this)"><a href="Lang_jsp?page=analyze_mine"><span
						class="fa fa-caret-right"></span> <s:text name="left.diag.mine" /></a></li>

				<s:if test="#session.user.role == 0">
					<li onclick="test(this)"><a href="Lang_jsp?page=analyze_history"><span
							class="fa fa-caret-right"></span> <s:text
								name="left.diag.history" /></a></li>
				</s:if>
			</ul></li>


		<!-- 账号管理 -->

		<s:if test="#session.user.role == 0">
			<li><a href="#" data-target=".account-menu"
				class="nav-header collapsed" data-toggle="collapse"><i
					class="fa fa-fw fa-users"></i> <s:text name="left.account.title" />
					<i class="fa fa-collapse"></i></a></li>
			<li><ul class="account-menu nav nav-list collapse in">
					<li><a href="Lang_jsp?page=admin_manage"><span
							class="fa fa-caret-right"></span> <s:text name="left.account.admin" /></a></li>
					<li><a href="Lang_jsp?page=user_manage"><span
							class="fa fa-caret-right"></span> <s:text
								name="left.account.customer" /></a></li>
				</ul></li>
		</s:if>

		<!-- 日志管理 -->
		<s:if test="#session.user.role == 0">
			<li><a href="#" data-target=".log-menu"
				class="nav-header collapsed" data-toggle="collapse"><i
					class="fa fa-fw fa-calendar"></i> <s:text name="left.log.title" />
					<i class="fa fa-collapse"></i></a></li>
			<li><ul class="log-menu nav nav-list collapse in">
					<li><a href="Lang_jsp?page=log_analyze"><span
							class="fa fa-caret-right"></span> <s:text name="left.log.diag" /></a></li>
					<li><a href="Lang_jsp?page=log_login"><span
							class="fa fa-caret-right"></span> <s:text name="left.log.login" /></a></li>
				</ul></li>
		</s:if>
		<!-- 电池管理 
		<li><a href="#" data-target=".dashboard-menu"
			class="nav-header collapsed" data-toggle="collapse"><i
				class="fa fa-fw fa-cubes"></i> 电池管理<i class="fa fa-collapse"></i></a></li>
			<li><ul class="accounts-menu nav nav-list collapse in">
				<li><a href="log_analyze.jsp"><span
						class="fa fa-caret-right"></span> 电池出售管理</a></li>
			</ul></li>
			-->
		<!-- 系统设置
		<li><a href="#" data-target=".legal-menu"
			class="nav-header collapsed" data-toggle="collapse"><i
				class="fa fa-fw fa-legal"></i> 系统设置 <i class="fa fa-collapse"></i></a></li>
		<li><ul class="legal-menu nav nav-list collapse in">
				<li><a href="privacy-policy.html"><span
						class="fa fa-caret-right"></span> 系统主题</a></li>
			</ul></li>
 		-->

		<li><a href="#" data-target=".legal-menu"
			class="nav-header collapsed" data-toggle="collapse"><i
				class="fa fa-fw fa-legal"></i> <s:text name="left.system.title" /> <i
				class="fa fa-collapse"></i></a></li>
		<li><ul class="legal-menu nav nav-list collapse in">
				<li><a href="User_logout"><span class="fa fa-caret-right"></span>
						<s:text name="left.system.exit"></s:text></a></li>
			</ul></li>
	</ul>
</div>

<script type="text/javascript">
	function test(a) {
	}

	
</script>