<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String haulId = request.getParameter("haulId");//获取请求的Haulid
	if (haulId != null) {
		request.setAttribute("haulId", haulId);
	}
%>

<%@ include file="/public/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外来单位黑名单管理</title>

<%@ include file="/public/cssJs.jsp"%>

<!-- 格式化日期函数 -->
<script src="<%=path%>/js/public/dateformat.js"></script>
<!--验证-->
<script src="<%=path%>/controls/validate/jquery.validate.js"></script>
<script src="<%=path%>/controls/validate/messages_zh.js"></script>

<!--select2-->
<link rel="stylesheet" href="<%=path%>/controls/select2/select2.min.css">
<script src="<%=path%>/controls/select2/select2.min.js"></script>
<script src="<%=path%>/controls/select2/select2.full.min.js"></script>
<script>
	/* 	$(document).ready(function() {
	 $('.js-example-tags').select2({
	 tags : true
	 });
	 }); */
	var contextPath = "${baseurl}";//记录项目名字
	var hasOutunitOperating = false;//记录是否有删除外来单位权限
	var haulId = "${haulId}";//记录传过来的检修ID
</script>
<!-- 有修改删除外来单位的权限就修改全局变量的值 -->
<shiro:hasPermission name="outunit:operating">
<script>
hasOutunitOperating = true;
</script>
</shiro:hasPermission>
<style type="text/css">
/*validate中不成功显示的样式设置*/
label.error {
	background: url(${baseurl }/controls/validate/unchecked.gif) no-repeat
		10px 3px;
	padding-left: 30px;
	font-family: georgia;
	font-size: 15px;
	font-style: normal;
	color: red;
}

label.success {
	background: url(${baseurl }/controls/validate/checked.gif) no-repeat
		10px 3px;
	padding-left: 30px;
}
</style>

<link rel="stylesheet"
	href="<%=path%>/css/outDepart/outdepartManage_style.css" />
<script src="<%=path%>/js/outDepart/outdepartBlackList.js"></script>

<link rel="stylesheet" href="<%=path%>/css/outDepart/outdepartTree.css">
</head>
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
						<span>外来单位管理</span><span>>外来单位黑名单管理</span>
					</div>

					<div class="el_main">
						
						<!--内容-->
						<div class="el_qlmContent" style="width: 100%">

							<!--显示内容-->
							<h4 class="el_mainHead">外来单位黑名单信息</h4>
							<div class="panel panel-default el_Mainmain">

								<!--按钮面板-->
								<div class="panel-body">

									<div class="panel panel-default">
										<div class="panel-body el_MainxiaoMain">

											<div class="el_topButton">
												<shiro:hasPermission name="outunit:add">
													<button class="btn btn-primary" data-toggle="modal" data-target="#myModal" >
														拉黑违章单位</button>
												</shiro:hasPermission>
											</div>

										</div>
									</div>

									<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>序号</th>
												<th>单位名称</th>
												<th>拉黑时间</th>
												<th>拉黑原因</th>
												<th width="120">操作</th>
											</tr>
										</thead>
										<tbody>
										<tr>
												<th>1</th>
												<th>单位名称</th>
												<th>1018-01-09</th>
												<th>拉黑原因</th>
												<th width="120">
												<a href="javascript:void(0)" onclick="openUpdateModal(this)">修改</a>
												<a href="javascript:void(0)" onclick="deleteUnit(this)">删除</a>
												</th>
											</tr>
										</tbody>
										<!-- <tbody id="haunUnitTbody"> -->
										</tbody>
									</table>

									<!--分页-->
									<div id="paginationIDU" class="paginationID"></div>

								</div>
							</div>

							<!-- 模态框黑名单单位添加-->
							<div class="modal fade" id="myModal" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel2">拉黑违章单位</h4>
										</div>
										<form id="addUnitForm">
											<div class="modal-body">
												<div class="input-group el_modellist" role="toolbar">
													<!--<span class="input-group-addon">单位名称</span>-->
													<span class="el_spans">单位名称：</span> <input type="text"
														id="addUnitname" class="form-control addUnitInput"
														name="name" onkeyup="findNames(this)" /> <span
														id="validateName" style="color: red"></span>
													<div id="showDiv"
														style="margin: 25px 0px 0px 0px; position: absolute; width: 78%; z-index: 3000; background-color: white; border: 1px solid; display: none;"></div>
												</div>
												<div class="input-group el_modellist" role="toolbar">
													<!--<span class="input-group-addon">单位地址</span>-->
													<span class="el_spans">拉黑时间：</span> <input type="text" id="test4"
														class="form-control addUnitInput" name="haulUnit.manager"
														id="manager" />
												</div>
												<div class="input-group el_modellist" role="toolbar">
													<!--<span class="input-group-addon">联系方式</span>-->
													<span class="el_spans">拉黑原因：</span> <input type="text"
														class="form-control addUnitInput"
														name="haulUnit.projectnames" id="projectnames" />
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary"
													onclick="saveUnit()">保存</button>
											</div>
										</form>

									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>
							<!-- 模态框 黑名单单位修改-->
							<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel24">修改单位信息</h4>
										</div>
										<form id="updateForm">
											<div class="modal-body">
												<!-- 隐藏一个部门ID -->
												<input type="hidden" id="update_unitid" name="unitid">
												<!-- 隐藏一个大修部门ID -->
												<input type="hidden" id="update_haulUnitid"
													name="haulUnit.unitbigid">

												<div class="input-group el_modellist" role="toolbar">
													<!--<span class="input-group-addon">单位名称</span>-->
													<span class="el_spans">单位名称：</span> <input type="text"
														class="form-control el_modelinput clearFormInput"
														name="name" id="update_name" /> <span></span>
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<!--<span class="input-group-addon">单位地址</span>-->
													<span class="el_spans">拉黑时间：</span> <input type="text" id="test41"
														class="form-control addUnitInput" name="haulUnit.manager"
														id="update_manager" />
												</div>


												<div class="input-group el_modellist" role="toolbar">
													<!--<span class="input-group-addon">联系方式</span>-->
													<span class="el_spans">拉黑原因：</span> <input type="text"
														class="form-control addUnitInput"
														name="haulUnit.projectnames" id="update_projectnames" />
												</div>

											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary"
													onclick="updateUnit()">保存</button>
											</div>
										</form>

									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>

							<!-- 模态框  信息删除确认 -->
							<div class="modal fade" id="delcfmModel">
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
											<form id="deleteUnitForm">
												<!-- 隐藏需要删除的id -->
												<input type="hidden" id="deleteBigId" class="queryIsFinish"
													name="bigId" /> <input type="hidden" id="deleteUnitId"
													name="unitId" />
											</form>
											<p style="font-size: 23px">您确认要删除该条信息吗?</p>
											<br /> <br />
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
											<a onclick="deleteSubmit()" class="btn btn-success"
												data-dismiss="modal">确定</a>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- /.modal -->

						</div>

					</div>

				</div>
			</div>

			<br /> <br /> <br /> <br /> <br /> <br />

		</div>
	</div>

	<!--放脚-->
	<jsp:include page="/view/public/footer.jsp"></jsp:include>
</body>
</html>
