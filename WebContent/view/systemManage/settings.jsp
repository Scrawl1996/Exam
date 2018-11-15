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
<style type="text/css">
label,.label{
font-weight:bold;
font-size:16px;
color:#000;
}
</style>
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
						<ul id="myTab" class="nav nav-tabs" style="margin-top: 20px;">
							<li class="active">
								<a href="#home" data-toggle="tab">
									 常用设置
								</a>
							</li>
							<!-- <li><a href="#ios" data-toggle="tab">iOS</a></li> -->
						</ul>
						<div id="myTabContent" class="tab-content" style="margin-top: 20px;">
							<!-- 安全帽设置 -->
							<div class="tab-pane fade in active" id="home">
								<form action="${baseurl}/view/settingAction_saveSettings.do" method="post" id="saveSettings">
									<label for="safeHatNumLength">安全帽编号长度:</label>
									<input type="text" name="safeHatNumLength" value="${safeHatNumLength}">
									<span>比如安全帽编号长度为3,编号为1，系统会自动格式化编号为001</span>
									<br/><br/>
									<s:textarea label="员工身体状况" name="physicalStatus" cols="30" rows="3" labelposition="left" value="%{physicalStatus}"/>
									<span>多个值用英文状态的逗号分隔</span>
									<br/><br/>
									<s:textarea label="员工学历状况" name="educateBackground" cols="30" rows="3" labelposition="left" value="%{educateBackground}"/>
									<span>多个值用英文状态的逗号分隔</span>
									<br/><br/>
									<input type="button" value="保存" onclick="ajaxSubmitForm('saveSettings')"/>
								</form>
							</div>
							<!-- <div class="tab-pane fade" id="ios">
								<p>iOS 是一个由苹果公司开发和发布的手机操作系统。最初是于 2007 年首次发布 iPhone、iPod Touch 和 Apple 
									TV。iOS 派生自 OS X，它们共享 Darwin 基础。OS X 操作系统是用在苹果电脑上，iOS 是苹果的移动版本。</p>
							</div> -->
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

<script>
function ajaxSubmitForm(formId) {
	if(!confirm("确认保存?")){
		return;
	}
	var form = $("#"+formId);
	var url = form.attr("action");
	$.post(url, form.serialize(), function(responseMap) {
		if(responseMap){
			alert(responseMap.msg);
		}else{
			alert("失败!");
		}
	}, 'json');
}
</script>