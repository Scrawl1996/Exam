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
<title>外来员工管理</title>

<%@ include file="/public/cssJs.jsp"%>

<link rel="stylesheet" href="<%=path%>/css/outDepart/outdepartTree.css">

<script src="<%=path%>/js/outDepart/outEmployeeAllot.js"></script>
<link rel="stylesheet"
	href="<%=path%>/css/outDepart/outEmployeeAllot.css">
<!-- 日期格式转换 -->
<script
	src="${pageContext.request.contextPath }/js/questionLibrary/dateformat.js "></script>

<script>
	//定义一个全局变量
	var basePathUrl = "${pageContext.request.contextPath}";
	var hasOperatingEmpout = false;
</script>
<%-- <shiro:hasPermission name="empout:operating">
<script>
hasOperatingEmpout = true;
</script>
</shiro:hasPermission> --%>
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
						<span>外来单位管理</span><span>>外来单位员工管理</span>
					</div>
					<div class="el_main">

						<!--树-->
						<div class="el_leftTree">
							<!--标题类，添加了一个颜色-->
							<span class="el_treeTitle">部门</span>
							<ul id="departmentAndOverHaulTree" class="ztree"></ul>
						</div>

						<!--内容-->
						<div class="el_qlmContent">

							<!--索引-->
							<div class="row el_queryBox">
								<form id="form_findEmployeeOutBaseInfo">

									<div class="row el_queryBoxrow">
										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
												<input type="text" class="form-control"
													name="employeeOutName" />
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">性&nbsp;&nbsp;&nbsp;别：</span> <label
													class="el_radioBox"><input type="radio"
													name="employeeOutSex" value="1"> 男</label> <label
													class="el_radioBox"><input type="radio"
													name="employeeOutSex" value="2"> 女</label>
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">身&nbsp;份&nbsp;&nbsp;证：</span> <input
													type="text" class="form-control" name="employeeOutIdCard" />
											</div>
										</div>
									</div>

									
									<!-- 隐藏部门ID和大修ID -->
									<input type="hidden" name="unitId" id="query_unitId" /> <input
										type="hidden" name="bigId" id="query_bigId" />
									<!-- 隐藏当前页和显示条数 -->
									<input type="hidden" name="currentPage" id="currentPage" /> <input
										type="hidden" name="currentCount" id="currentCount" />
									<!--提交查询按钮-->
									<button type="button"
										class="btn btn-primary el_queryButton btn-sm"
										onclick="queryEmployeeOutBaseInfo()">查询</button>
									<button type="reset"
										class="btn btn-default el_queryButton0 btn-sm"
										onclick="clearQueryInfo()">清空</button>
								</form>

							</div>
							<!--结束 查询表单提交-->
							<!-- 部门基本信息 -->
							<h3 class="el_mainHead">部门基本信息</h3>
							<div class="panel panel-default el_Mainmain">
							<div class="panel-body">
							<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>单位名称</th>
												<th>所属检修</th>
												<th>项目经理</th>
												<th>经理电话</th>
												<th>安全员</th>
												<th>安全员电话</th>
												<th>参与项目</th>
											</tr>
										</thead>
										<tbody id="">
											<tr>
												<th>背景博齐</th>
												<th>#1机组大修</th>
												<th>王一</th>
												<th>4505005</th>
												<th>王二</th>
												<th>4505005</th>
												<th>#1机组大修</th>
											</tr>
										</tbody>
									</table>
							<!-- 部门基本信息结束 -->
							<!--显示内容-->
							<h3 class="el_mainHead">分配员工</h3>


									<!--按钮区-->
									<div class="panel panel-default">
										<div class="panel-body el_MainxiaoMain">

											<div class="el_topButton">
												
												<button class="btn btn-primary" id="el_lookTrainDocument"
													onclick="el_empTrainDoc()">分配员工</button>
											</div>
										</div>
									</div>

									<!--表格
                            	内容都提取到json里边
                        -->
									<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th><input type="checkbox" id="empCheckAll"></th>
												<th>序号</th>
												<th>姓名</th>
												<th>性别</th>
												<th>身份证</th>
												<th>所属检修</th>
												<th>所属单位</th>
												<th>工种</th>
												<th>黑名单</th>
												<th>分配部门</th>
												<th>分配班组</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="employeeOutBaseInfoList">
											<tr>
												<th><input class="el_checks" name="checkBox" type="checkbox" ></th>
												<th>1</th>
												<th>王一</th>
												<th>男</th>
												<th>342201199504248515</th>
												<th>#1机组大修</th>
												<th>背景博齐</th>
												<th>钳工</th>
												<th>否</th>
												<th>分配部门</th>
												<th>分配班组</th>
												<th><a onclick="el_changeAllot()">修改</a></th>
											</tr>
											
											
										</tbody>
									</table>

									<!--分页-->
									<div id="paginationIDU" class="paginationID"></div>
								</div>
							</div>
<script type="text/javascript">
$("#empCheckAll").click(function(){
	$("input[name='checkBox']").attr("checked","true");
		
})
</script>
							<!-- 模态框 分配员工的树-->
							<div class="modal fade" id="el_empTrainDoc" tabindex="-1"
								role="dialog" aria-labelledby="myModalLabel23"
								data-backdrop="static" data-keyboard="false" aria-hidden="true">
								<div class="modal-dialog" style="width: 50%;">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel234">分配员工</h4>
										</div>
										<form>
											<!--树-->
											<div class="el_leftTree">
												<!--标题类，添加了一个颜色-->
												<span class="el_treeTitle">部门</span>
											</div>
								
											<ul id="treeDemo_permission" class="ztree" style="width:auto !important;height:auto !important;border:none !important;"></ul>
                                
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary" onclick="on()">保存</button>
											</div>
										</form>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>
										<!-- 模态框 修改分配员工的树-->
							<div class="modal fade" id="el_changeAllot" tabindex="-1"
								role="dialog" aria-labelledby="myModalLabel23"
								data-backdrop="static" data-keyboard="false" aria-hidden="true">
								<div class="modal-dialog" style="width: 50%;">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel234">分配员工</h4>
										</div>
										<form>
											<!--树-->
											<div class="el_leftTree">
												<!--标题类，添加了一个颜色-->
												<span class="el_treeTitle">部门</span>
											</div>
								
											<ul id="treeDemo_permission1" class="ztree" style="width:auto !important;height:auto !important;border:none !important;"></ul>
                                
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary" onclick="on()">保存</button>
											</div>
										</form>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
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