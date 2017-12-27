<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
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
<title>内部员工违章管理</title>

<%@ include file="/public/cssJs.jsp"%>
<!-- 设置一个JS全局变量记录项目名字 -->
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
</script>
<!--分页-->
<script>
	
</script>

<!-- 点击左边的树之后的分页查询 -->
<script type="text/javascript">
	
</script>


<!--左边的树-->

<script src="<%=path%>/js/innerDepart/emplyInBreakRulesManage.js"></script>
<link rel="stylesheet" href="<%=path%>/css/outDepart/outdepartTree.css">

<!--验证-->
<script
	src="${pageContext.request.contextPath }/controls/validate/jquery.validate.js"></script>
<script
	src="${pageContext.request.contextPath }/controls/validate/messages_zh.js"></script>
<script>
	$(function() {

		$("#inpstart2").jeDate({
			isinitVal : false,
			minDate : '2000-06-16',
			maxDate : '2225-06-16',
			format : 'YYYY-MM-DD',
			zIndex : 3000
		})

		$("#inpend2").jeDate({
			isinitVal : false,
			minDate : '2000-06-16',
			maxDate : '2225-06-16',
			format : 'YYYY-MM-DD',
			zIndex : 3000
		})

	})
</script>
<!-- 与左边的树绑定之后的点击查询按钮的点击事件  未点击黑名单  和点击了黑名单的情况 -->
<script type="text/javascript">
	
</script>

<style>
#el_breakTimeIndex input {
	width: 33% !important;
	margin-right: 10px;
}

#el_breakTimeIndex {
	margin-left: 15px;
}

#el_breakType {
	font-size: 13px;
}

#el_breakType option {
	background-color: white;
	color: black;
}
</style>
</head>
<body>

	<!-- 用于提交数据的 -->
	<form id="detailInForm"
		action="${pageContext.request.contextPath}/empInbreakrules_detailOp.action"
		method="post">
		<input type="hidden" name="fstarttime" id="q_starttime"> <input
			type="hidden" name="fendtime" id="q_endtime"> <input
			id="detailunitName" name="detailunitName" type="hidden" value="" />
		<input id="detailemployeeId" name="detailemployeeId" type="hidden"
			value="" /> <input id="detail_breakInfoType" name="empBreakInfoType"
			type="hidden" value="0" />
	</form>

	<!-- 隐藏域 start -->


	<!-- 隐藏 操作哪块地标记 -->
	<input id="allMark" type="hidden" value="" />


	<!-- 隐藏域  end -->
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
						<span>内部单位管理</span><span>>内部员工违章管理</span>
					</div>
					<div class="el_main">

						<!--树-->
						<div class="el_leftTree">
							<!--标题类，添加了一个颜色-->
							<span class="el_treeTitle">部门</span>
							<ul id="treeDemo" class="ztree"></ul>
						</div>

						<!--内容-->
						<div class="el_qlmContent">

							<!--索引-->
							<div class="row el_queryBox">
								<form id="findForm">
									<div class="row el_queryBoxrow">
										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
												<input id="initName" type="text" class="form-control"
													name="fName" value="" />
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">身份证：</span> <input id="initIdCard"
													type="text" class="form-control" name="fIdCode" value="" />
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">性别：</span> <label class="el_radioBox"><input
													class="initsex" type="radio" name="fSex" value="1">
													男</label> <label class="el_radioBox"><input class="initsex"
													type="radio" name="fSex" value="2"> 女</label>
											</div>
										</div>
									</div>

									<div class="row el_queryBoxrow">

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">违章积分：</span> <select
													class="selectpicker form-control" id="el_breakSelect"
													name="fBreakScore" title="请选择">
													<option value="0,100">--请选择--</option>
													<option value="0,3">3分以下</option>
													<option value="4,6">4-6</option>
													<option value="7,11">7-11</option>
													<option value="12,1000">12分及以上</option>
												</select>
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" id="el_blackCheckbox" role="toolbar">
												<span class="el_spans">黑名单：</span> <label
													class="el_radioBox"><input class="initBlack"
													type="radio" name="fIsBreak" value="是"> 是</label> <label
													class="el_radioBox"><input class="initBlack"
													type="radio" name="fIsBreak" value="否"> 否</label>
											</div>
										</div>


									</div>

									<div class="row el_queryBoxrow">
										<div class="col-md-6" id="el_breakTimeIndex">
											<div class="input-group" id="el_startEndTime" role="toolbar">
												<span class="el_spans">违章时间：</span> <input type="text"
													class=" form-control query_dep_starttime" name="fstarttime"
													placeholder="开始时间" id="inpstart2" readonly> <input
													type="text" class="form-control query_dep_endtime"
													id="inpend2" placeholder="结束时间" name="fendtime" readonly>
											</div>
										</div>
									</div>
									<!-- 当前页页号 -->
									<input id="yeHao" type="hidden" name="fcurpage" value="1" />
									<!-- 每页显示的记录数 -->
									<input id="jiLuShu" type="hidden" name="fcurtotal" value="8" />
									<!-- 隐藏一个左侧的树选中的部门的部门id -->
									<input id="departmentidTree" type="hidden" name="fdepartmentid"
										value="" />
									<!-- 隐藏违章信息查询类型，默认查询当前年 -->
									<input id="breakInfo_Type" type="hidden" value="0"
										name="empBreakInfoType">
									<!--提交查询按钮-->
									<button type="button" onclick="clearPagenum()"
										class="btn btn-primary el_queryButton btn-sm">查询</button>
									<button type="button" onclick="clearBtn()"
										class="btn btn-default el_queryButton btn-sm"
										style="right: 12px">清空</button>
								</form>

							</div>
							<!--结束 查询表单提交-->

							<!-- 清空按钮的点击事件 -->
							<script type="text/javascript">
								
							</script>
							<!-- 解决违章记分和黑名单冲突 -->
							<script>
								$(
										function() {
											$("#el_breakSelect")
													.change(
															function() {
																var breakValue = $(
																		this)
																		.val();

																if (breakValue != "") {
																	$(
																			"#el_blackCheckbox")
																			.find(
																					"input")
																			.attr(
																					"disabled",
																					true);
																	$(
																			"#el_blackCheckbox")
																			.find(
																					"input")
																			.attr(
																					"checked",
																					false);
																} else {
																	$(
																			"#el_blackCheckbox")
																			.find(
																					"input")
																			.attr(
																					"disabled",
																					false);
																}
															})
										})
							</script>





							<!--显示内容-->
							<h3 class="el_mainHead">员工违章信息</h3>
							<div class="panel panel-default el_Mainmain">

								<!--按钮面板-->
								<div class="panel-body">

									<div class="panel panel-default">
										<div class="panel-body el_MainxiaoMain">

											<div class="el_topButton">
												<div class="col-md-5">
													<shiro:hasPermission name="breakrules:add">
														<!-- 按钮触发模态框1 -->

														<button id="el_addBreakRules" class="btn btn-primary"
															onclick="el_addBreakInfo()">添加违章信息</button>


													</shiro:hasPermission>
													<select class="btn btn-primary" id="el_breakType"
														title="请选择" onclick="historyBreakInfoFind()">
														<!-- <select class="btn btn-primary" id="el_breakType"
													title="请选择" name="examLevel"> -->
														<option value="0">当前违章</option>
														<option value="1">历史违章</option>
													</select>
												</div>
											</div>

										</div>
									</div>

									<!--表格    内容都提取到json里边             -->
									<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>选择</th>
												<th>姓名</th>
												<th>性别</th>
												<th>身份证</th>
												<th>违章积分</th>
												<th>黑名单状态</th>
												<th>所属单位</th>
												<th>职务</th>
												<th width="190">操作</th>
											</tr>
										</thead>
										<tbody id="tbody">

										</tbody>
									</table>

									<!--分页  用于左侧的树的-->
									<div id="paginationIDU" class="paginationID"></div>

								</div>
							</div>

							<!-- 模态框 违章信息添加-->
							<div class="modal fade" id="el_addBreakInfo" tabindex="-1"
								data-backdrop="static" data-keyboard="false" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabeld2">添加违章信息</h4>
										</div>
										<form id="addForm">

											<div class="modal-body">
												<span>员工信息：</span>
												<div class="el_threeScoreList">
													<table class="table table-bordered">
														<thead>
															<tr>
																<th>姓名</th>
																<!--  <td>联系方式</td> -->
																<th>性别</th>
																<th>所属单位</th>
																<th>违章记分</th>
																<th>黑名单</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td id="addName"></td>
																<td id="addSex"></td>
																<!--  <td id="addPhone">asdf</td> -->
																<td id="addunitName"></td>
																<td id="addbreakScore"></td>
																<td id="addIsBreak"></td>
															</tr>
														</tbody>
													</table>
												</div>

												<!-- 隐藏一个员工id -->
												<input id="addEmpID" type="hidden"
													name="emplyinBreakrules.empinemployeeid" value="" />

												<!-- 隐藏一个身份证 -->
												<input id="addIdCard" type="hidden" name="addIdCard"
													value="" />
												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章时间：</span> <input type="text"
														id="test41" class="workinput wicon form-control"
														name="emplyinBreakrules.empinbreaktime" readonly />
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章积分：</span>
													<!--不得超过12分-->
													<input id="breakScoreID" type="text"
														class="form-control el_modelinput"
														name="emplyinBreakrules.empinminusnum" />
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章内容：</span>
													<textarea id="addBreakContent"
														class="form-control el_modelinput" rows="3" value=""
														name="emplyinBreakrules.empinbreakcontent"></textarea>
												</div>

											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" onclick="addSaveBtn()"
													class="btn btn-primary">保存</button>
											</div>
										</form>

									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>

							<!-- 模态框，用于提示本次添加的 违章记分>=12分的情况   start-->
							<div class="modal fade" id="addyAlertModel2"
								data-backdrop="static" data-keyboard="false">
								<div class="modal-dialog">
									<div class="modal-content message_align">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">×</span>
											</button>
											<h4 class="modal-title">提示</h4>
										</div>
										<div class="modal-body">
											<p>您当前添加的违章积分大于等于12分，该员工被永久进入黑名单，是否继续添加？</p>
										</div>
										<div class="modal-footer">
											<input type="hidden" id="url" />
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
											<a onclick="addAlertModelBtn2()" class="btn btn-success"
												data-dismiss="modal">确定</a>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- /.modal -->
							<!-- 模态框，用于提示本次添加的 违章记分>=12分的情况  end-->

							<!-- 隐藏域，隐藏一个添加前的违章总积分 -->
							<input id="breakScoreSum" type="hidden" value="" />
							<script type="text/javascript">
								
							</script>


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
