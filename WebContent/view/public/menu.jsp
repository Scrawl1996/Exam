	<%@page import="cn.xm.exam.bean.system.Permission"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/public/tag.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!--菜单-->
<link rel="stylesheet" href="<%=path%>/css/home/menu.css" />

<style>
a:hover {
	color: black;
}
</style>

<script>
	$(function() {
		var u = location.pathname;

		u = u.substring(11,u.length);
		var fir = u.indexOf("/");
		var filename = u.substring(0,fir)
		//获取到当前页面的二根文件夹名称（view下一层的文件夹）
		/* 
		* 根据文件夹名称，展开相应的菜单
		* 菜单展开方式是 添加 in 类， 
		* 根据获取的file地址。截取view下一次文件夹。根据该文件夹，判断展开内容
		*/
		$(".in").removeClass("in");
		if (filename == "examParper") {
			$("#collapse001").addClass("in");
		} else if (filename == "gradeManage") {
			$("#collapse003").addClass("in");
		} else if (filename == "innerDepart") {
			$("#collapse004").addClass("in");
		} else if (filename == "outDepart") {
			$("#collapse007").addClass("in");
		} else if (filename == "questionLibrary") {
			$("#collapse002").addClass("in");
		} else if (filename == "systemManage") {
			$("#collapse010").addClass("in");
		} else if (filename == "train") {
			$("#collapse009").addClass("in");
		} else if (filename == "overhaul") {
			$("#collapse006").addClass("in");
		} else if (filename == "news") {
			$("#collapse008").addClass("in");
		}else if (filename == "newPeopleTrain") {
			$("#collapse005").addClass("in");
		}
		
		
		//alert(filename)
		
		//根据文件夹名称，展开相应的菜单
		//菜单展开方式是 添加 in 类，
		/*
		1、有 in 类的 div ，删除 in 类
		2、根据文件名，查找已知的每个文件夹的id
		3、根据id ，添加 in 类
		 */
		/* $(".in").removeClass("in");
		if (filename == "examParper") {
			$("#collapse1").addClass("in");
		} else if (filename == "gradeManage") {
			$("#collapse2").addClass("in");
		} else if (filename == "innerDepart") {
			$("#collapse3").addClass("in");
		} else if (filename == "outDepart") {
			$("#collapse4").addClass("in");
		} else if (filename == "questionLibrary") {
			$("#collapse5").addClass("in");
		} else if (filename == "systemManage") {
			$("#collapse6").addClass("in");
		} else if (filename == "train") {
			$("#collapse7").addClass("in");
		} */
		
		/* if(filename == "exam" || filename == "examManage" || filename == "examparperManage"|| filename == "examPaper") {
			$("#collapse1").addClass("in");
		} *//* 
		$("#collapse1").children("li").click(function(){
			$("#collapse1").addClass("in");
		}); */
		
		/* $(".list-group-item").click(function(){
			alert("sd")
			$(".collapse").removeClass("in");
			$(this).parents(".collapse").addClass("in");
		}) */

		//
	})
</script>

</head>
<body>


	<!-- 	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">

		考试和试卷管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading1">
				<h4 class="panel-title">
					<a role="button" data-toggle="collapse" data-parent="#accordion"
						href="#collapse1" aria-expanded="true" aria-controls="collapse1">
						考试及试卷管理 </a>
				</h4>
			</div>
			<div id="collapse1" class="panel-collapse collapse in"
				role="tabpanel" aria-labelledby="heading1">
				<ul class="list-group">
					<li class="list-group-item"><a
						href="../examParper/examManage.jsp"> 考试管理 </a></li>
					<li class="list-group-item"><a
						href="../examParper/examparperManage.jsp"> 试卷管理 </a></li>
					<li class="list-group-item"><a
						href="../examParper/createExamParper.jsp"> 创建试卷 </a></li>
					<li class="list-group-item"><a
						href="../examParper/invigilateCenter.jsp"> 监考中心 </a></li>
				</ul>
			</div>
		</div>

		题库管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingOne">
				<h4 class="panel-title">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse5" aria-expanded="false"
						aria-controls="collapse5"> 题库管理 </a>
				</h4>
			</div>
			<div id="collapse5" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingOne">
				<ul class="list-group">
					<li class="list-group-item"><a
						href="../questionLibrary/questionManage.jsp"> 试题管理 </a></li>
					<li class="list-group-item"><a
						href="../questionLibrary/quesLibManage.jsp"> 题库信息管理 </a></li>
				</ul>
			</div>
		</div>

		成绩管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingTwo">
				<h4 class="panel-title">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse2" aria-expanded="false"
						aria-controls="collapse2"> 成绩管理 </a>
				</h4>
			</div>
			<div id="collapse2" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingOne">
				<ul class="list-group">
					<li class="list-group-item"><a
						href="../gradeManage/gradeManage.jsp"> 成绩信息管理 </a></li>
					<li class="list-group-item"><a
						href="../gradeManage/gradeEmpInfo.jsp"> 员工成绩 </a></li>
				</ul>
			</div>
		</div>

		外来单位管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingTwo2">
				<h4 class="panel-title">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse4"
						aria-expanded="false" aria-controls="collapse4"> 外来单位管理 </a>
				</h4>
			</div>
			<div id="collapse4" class="panel-collapse collapse"
				role="tabpanel" aria-labelledby="headingTwo2">
				<ul class="list-group">
					<li class="list-group-item"><a
						href="../outDepart/outdepartManage.jsp"> 外来单位信息管理 </a></li>
					<li class="list-group-item"><a
						href="../outDepart/outdepartEmpManage.jsp"> 外来单位员工管理 </a></li>
					<li class="list-group-item"><a
						href="../outDepart/BreakRulesInfoManage.jsp"> 违章管理 </a></li>
					<li class="list-group-item"><a
						href="../outDepart/projectManage.jsp"> 工程管理 </a></li>
				</ul>
			</div>
		</div>

		部门管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingThree">
				<h4 class="panel-title">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse3" aria-expanded="false"
						aria-controls="collapse3"> 部门管理 </a>
				</h4>
			</div>
			<div id="collapse3" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingThree">
				<ul class="list-group">
					<li class="list-group-item"><a
						href="../innerDepart/innerdepartManage.jsp"> 部门信息管理 </a></li>
					<li class="list-group-item"><a
						href="../innerDepart/innerdepartEmpManage.jsp"> 员工管理 </a></li>
				</ul>
			</div>
		</div>


		培训内容管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingThree2">
				<h4 class="panel-title">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse7" aria-expanded="false"
						aria-controls="collapse7"> 培训管理 </a>
				</h4>
			</div>
			<div id="collapse7" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingThree2">
				<ul class="list-group">
					<li class="list-group-item"><a href="../train/trainManage.jsp">
							培训内容管理 </a></li>
				</ul>
			</div>
		</div>

		系统管理
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="headingThree3">
				<h4 class="panel-title">
					<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse6" aria-expanded="false"
						aria-controls="collapse6"> 系统管理 </a>
				</h4>
			</div>
			<div id="collapse6" class="panel-collapse collapse" role="tabpanel"
				aria-labelledby="headingThree3">
				<ul class="list-group">
					<li class="list-group-item"><a
						href="../systemManage/users.jsp"> 用户管理 </a></li>
					<li class="list-group-item"><a href="../systemManage/role.jsp">
							角色管理 </a></li>
					<li class="list-group-item"><a
						href="../systemManage/authority.jsp"> 权限管理 </a></li>
					<li class="list-group-item"><a
						href="../systemManage/dictionary.jsp"> 字典管理 </a></li>
					<li class="list-group-item"><a href="../systemManage/log.jsp">
							日志管理 </a></li>
				</ul>
			</div>
		</div>

	</div>



		 -->


	<!--左边索引模块-->
	<div class="panel-group" id="accordion" role="tablist"
		aria-multiselectable="true">
		<c:set var="a" value="0"></c:set>
		<c:set var="isshow" value="true"></c:set>

		<c:forEach var="per" items="${permissioninfo}">
			<c:if test="${per.type=='menutop' }">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="heading${a+1}">
						<h4 class="panel-title">
							<a role="button" data-toggle="collapse" data-parent="#accordion"
								href="#collapse${per.sortno}" aria-expanded="true"
								aria-controls="collapse${per.sortno}"> ${per.name} </a>
						</h4>
					</div>

					<div id="collapse${per.sortno}" class="panel-collapse collapse"
						role="tabpanel" aria-labelledby="heading${a+1}">
						<ul class="list-group">
							<c:forEach var="per1" items="${permissioninfo}">

								<c:if
									test="${per1.parentid==per.permissionid and per1.type=='menu'}">
									<li class="list-group-item">
									<a href="${baseurl }/view/${per1.url}"> ${per1.name} </a></li>
								</c:if>

							</c:forEach>
						</ul>
					</div>
				</div>
				<c:set var="a" value="${a+1}"></c:set>
				<c:set var="isshow" value="false"></c:set>
			</c:if>
		</c:forEach>
	</div>
</body>
</html>