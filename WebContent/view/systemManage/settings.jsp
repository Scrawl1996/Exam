<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<%@ include file="/public/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>用户管理</title>
<%@ include file="/public/cssJs.jsp"%>

<!--验证-->
<script src="${pageContext.request.contextPath }/controls/validate/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath }/controls/validate/messages_zh.js"></script>

</head>
<body>
	<!--头-->
	<jsp:include page="/view/public/header.jsp"></jsp:include>

	<!--中部-->
	<div class="html_middle">

		<!--放菜单框-->
		<div class="el_left">
			<jsp:include page="/view/public/menu.jsp"></jsp:include>
		</div>

		<!--放主界面内容-->
		<div class="el_right">

			<div class="container-fluid">
				<div class="panel panel-default">
					<!--菜单连接标题-->
					<div class="panel-heading">
						<span>系统管理</span><span>>后台设置</span>
					</div>

					<div class="el_main">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active">
							<a href="#home" data-toggle="tab">
								 常用设置
							</a>
						</li>
						<li><a href="#ios" data-toggle="tab">iOS</a></li>
						<li class="dropdown">
							<a href="#" id="myTabDrop1" class="dropdown-toggle" 
							   data-toggle="dropdown">Java 
								<b class="caret"></b>
							</a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
								<li><a href="#jmeter" tabindex="-1" data-toggle="tab">jmeter</a></li>
								<li><a href="#ejb" tabindex="-1" data-toggle="tab">ejb</a></li>
							</ul>
						</li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<!-- 安全帽设置 -->
						<div class="tab-pane fade in active" id="home">
							<form action="${baseurl}/view/settingAction_settings.do" method="post">
								安全帽编号长度:<input type="text" name="safeHatNumLength" value="${safeHatNumLength}"/>
								<br/>
								<input type="submit" value="保存"/>
							</form>
						</div>
						<div class="tab-pane fade" id="ios">
							<p>iOS 是一个由苹果公司开发和发布的手机操作系统。最初是于 2007 年首次发布 iPhone、iPod Touch 和 Apple 
								TV。iOS 派生自 OS X，它们共享 Darwin 基础。OS X 操作系统是用在苹果电脑上，iOS 是苹果的移动版本。</p>
						</div>
						<div class="tab-pane fade" id="jmeter">
							<p>jMeter 是一款开源的测试软件。它是 100% 纯 Java 应用程序，用于负载和性能测试。</p>
						</div>
						<div class="tab-pane fade" id="ejb">
							<p>Enterprise Java Beans（EJB）是一个创建高度可扩展性和强大企业级应用程序的开发架构，部署在兼容应用程序服务器（比如 JBOSS、Web Logic 等）的 J2EE 上。
							</p>
						</div>
					</div>
					

					</div>

				</div>
			</div>

		</div>
	</div>
	<!--放脚-->
	<jsp:include page="/view/public/footer.jsp"></jsp:include>
</body>
</html>