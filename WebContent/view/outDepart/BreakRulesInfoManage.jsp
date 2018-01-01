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
<title>违章管理</title>

<!-- 隐藏域   start -->
<!-- 隐藏左侧选中的树的名字 -->
<input type="hidden" id="leftTreeName" value="" />

<!-- 隐藏域  隐藏单位大修编号  左侧的树 和 按条件查询的都要用的数据-->
<input id="unitBigHual" type="hidden" value="" />
<!-- 隐藏域  隐藏单位编号(所属单位的单位编号) -->
<input id="unitBH" type="hidden" value="" />

<!-- 表单初始化用的单位编号 -->
<input id="unitID" type="hidden" value="">

<!-- 隐藏一个大修名称，用于界面的显示 -->
<input id="hualInfoName" type="hidden" value="" />
<!-- 用于获取大修名称的大修id -->
<input id="haulInfoBigId" type="hidden" vlaue="" />
<!-- 隐藏和查看详情有关的数据end -->

<!-- 添加操作的时候的隐藏域 start -->
<!-- 设置一个JS全局变量记录项目名字 -->
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
</script>

<!-- 点击左侧的树中的部门的时候，查询该部门下的所有外来职工的信息 -->
<script type="text/javascript">

</script>


<%@ include file="/public/cssJs.jsp"%>
<script>

	$(function() {

		$("#inpstart2").jeDate({
		    isinitVal:false,
		    minDate: '2000-06-16',
		    maxDate: '2225-06-16',
		    format : 'YYYY-MM-DD',
		    zIndex:3000
		})

		$("#inpend2").jeDate({
		    isinitVal:false,
		    minDate: '2000-06-16',
		    maxDate: '2225-06-16',
		    format : 'YYYY-MM-DD',
		    zIndex:3000
		})
	})
</script>
<!--分页-->
<%-- <script src="<%=path %>/js/public/page.js"></script> --%>
<script>
	
</script>


<!--左边的树-->
<%--  <script src="<%=path %>/js/public/tree.js"></script> --%>

<script src="<%=path%>/js/outDepart/BreakRulesInfoManage.js"></script>
<link rel="stylesheet"
	href="<%=path%>/css/outDepart/BreakRulesInfoManage.css">

<link rel="stylesheet" href="<%=path%>/css/outDepart/outdepartTree.css">

<!--验证-->
<script
	src="${pageContext.request.contextPath }/controls/validate/jquery.validate.js"></script>
<script
	src="${pageContext.request.contextPath }/controls/validate/messages_zh.js"></script>
</head>
<body>

	<!-- 用于提交数据的 -->
	<form id="detailForm" action="${pageContext.request.contextPath}/breakrules_detailOp.action"	method="post">
		<input type="hidden" name="fstarttime" id="q_starttime">
		<input	type="hidden" name="fendtime" id="q_endtime">
		<input id="detailName" name="detailName" type="hidden" value="" /> 
		<input id="detailSex" name=detailSex type="hidden" value="" /> 
		<input	id="detailIsBreak" name="detailIsBreak" type="hidden" value="" />
		<input	id="detailIdCard" name="detailIdCard" type="hidden" value="" />
		<input id="detailUnitName" name="detailUnitName" type="hidden" value="" />
		<input id="detailBigId" name="detailBigId" type="hidden" value="" />
		<input id="detailEmployeeId" name="detailEmployeeId" type="hidden"	value="" />
		<input id="detailBigEmployeeoutId"	name="detailBigEmployeeoutId" type="hidden" value="" /> 
		<input id="detailUnitBigHual" name="detailUnitBigHual" type="hidden" value="" />
	</form>

	<!-- 用于提交数据的form表单结束 -->
	
	<!-- 用于返回页面数据的更新标记 -->
	<input id="dataFlushReturn" type="hidden" value="0"/>
	
	<!-- 隐藏一个添加前的违章总积分 -->
	<input id="beforeBreakScore" type="hidden" value="" />

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
						<span>外来单位管理</span><span>>违章管理</span>
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
												<span class="el_spans">姓名：</span> <input id="initName"
													type="text" class="form-control" name="fName" value="" />
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">身份证：</span> <input id="initIdCard"
													type="text" class="form-control" name="fIdCard" value="" />
											</div>
										</div>

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
									</div>

									<div class="row el_queryBoxrow">

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">性别：</span> <label class="el_radioBox"><input
													class="initsex" type="radio" name="fSex" value="1">
													男</label> <label class="el_radioBox"><input class="initsex"
													type="radio" name="fSex" value="2"> 女</label>
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
												<span class="el_spans">违章时间：</span> 
												<input type="text"
													class=" form-control query_dep_starttime" name="fstarttime"
													id="inpstart2" readonly> 
												<input type="text"
													class="form-control query_dep_endtime" id="inpend2" 
													name="fendtime" readonly>
											</div>
										</div>
									</div>

									<!-- 隐藏域，隐藏当前页页号、每页显示的记录数、部门id-->
									<!-- 隐藏当前页页号 -->
									<!-- 当前页页号 -->
									<input id="yeHao" type="hidden" name="fcurpage" value="1" />
									<!-- 每页显示的记录数 -->
									<input id="jiLuShu" type="hidden" name="fcurtotal" value="8" />

									<!-- 隐藏单位大修编号 -->
									<input id="findUnitBigBH" type="hidden" name="funitBigHaulBH"
										value="" />
									<!-- 隐藏单位(部门)编号 -->
									<input id="ffunitId" name="funitId" type="hidden" value="" />
									<!--提交查询按钮-->
									<button type="button" onclick="clearPagenum()" class="btn btn-primary el_queryButton btn-sm">查询</button>
									<button type="button" onclick="clearBtn()" class="btn btn-default el_queryButton btn-sm" style="right: 12px">清空</button>
								</form>

							</div>
							<!--结束 查询表单提交-->
							<!-- 清空按钮的点击事件 -->
							<script type="text/javascript">
								
							</script>
							<!-- 解决违章记分和黑名单冲突 -->
							<script>
								$(function() {
									$("#el_breakSelect").change(function() {
										var breakValue = $(this).val();

										if (breakValue != "") {
											$("#el_blackCheckbox").find("input").attr("disabled",true);
											$("#el_blackCheckbox").find("input").attr("checked",false);
										} else {
											$("#el_blackCheckbox").find("input").attr("disabled",false);
										}
									})
								})
							</script>

							<!-- 隐藏一个职工id，用于获取当前职工的的总积分 -->
							<input id="findEmpId" type="hidden" value="" />
							<!-- 隐藏一个是否黑名单的状态，用于显示是否是黑名单的(没有点击黑名单为"是"的情况) -->
							<input id="findIsBreak" type="hidden" value="" />

							<!-- 与左边的树绑定之后的点击查询按钮的点击事件  未点击黑名单  和点击了黑名单的情况 -->
							<script type="text/javascript">
								
							</script>


							<!--显示内容-->
							<h3 class="el_mainHead">员工违章信息</h3>
							<div class="panel panel-default el_Mainmain">

								<!--按钮面板-->
								<div class="panel-body">

									<div class="panel panel-default">
										<div class="panel-body el_MainxiaoMain">

											<div class="el_topButton">
												<!-- 按钮触发模态框1 -->
												<div class="col-md-2">
												<shiro:hasPermission name="outempbreak:add">
													<button id="el_addBreakRules" class="btn btn-primary"
														onclick="el_addBreakInfo()">添加违章信息</button>
														</shiro:hasPermission>
												</div>
											</div>

										</div>
									</div>

									<!--表格  内容都提取到json里边   -->
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
												<th>工种</th>
												<th>所属大修</th>
												<th width="100">操作</th>
											</tr>
										</thead>
										<tbody id="tbody">
									
										</tbody>
									</table>

									<!--分页  用于左侧的树的-->
									<div id="paginationIDU" class="paginationID"></div>

									<!-- <!--分页  用于条件查询的-->
									<div id="paginationIDU2" class="paginationID"></div>
								</div>
							</div>

							<!-- 模态框 违章信息添加-->
							<div class="modal fade" id="el_addBreakInfo" tabindex="-1" data-backdrop="static" data-keyboard="false"
								role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
													<table class="table table-hover table-bordered">
														<thead>
															<tr>
																<th>姓名</th>
																<th>性别</th>
																<!--  <td>联系方式</td> -->
																<th>违章记分</th>
																<th>所属单位</th>
																<th>黑名单</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td id="addName">asdf</td>
																<td id="addSex">asdf</td>
																<!--  <td id="addPhone">asdf</td> -->
																<td id="addbreakScore">asdf</td>
																<td id="addunitName">asdf</td>
																<td id="addIsBreak">asdf</td>
															</tr>
														</tbody>
													</table>
												</div>

												<!-- 隐藏域 隐藏外来职工的id  隐藏域的值要一起提交到后台-->
												<!-- 隐藏域，隐藏 、职工id、大修外来职工id -->
												<!-- 职工id -->
												<input id="addEmployee" type="hidden" value=""
													name="breakrules.employeeid" />
												<!-- 参加大修外来职工id -->
												<input id="addBigHaulEmployeeId" type="hidden" value=""
													name="breakrules.bigemployeeoutid" />
												<!-- 隐藏一个大修id -->
												<input id="addBigHaulId" type="hidden" name="addHaulBigId"
													value="" />
												<!-- 隐藏一个职工的违章id -->
												<input id="addEmpBreakId" type="hidden" name="addempBreakId"
													value="" />
												<!-- 隐藏一个单位id -->
												<input id="addUnitidM" type="hidden" name="addUnitidM"
													value="" />
												

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章时间：</span> <input type="text"
														id="test4" class="wicon form-control el_noVlaue"
														name="breakrules.breaktime" readonly />
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章积分：</span>
													<!--不得超过12分-->
													<input id="breakScoreID" type="text"
														class="form-control el_modelinput"
														name="breakrules.minusnum" />
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章内容：</span>
													<textarea id="addBreakContent"
														class="form-control el_modelinput" rows="3" value=""
														name="breakrules.breakcontent"></textarea>
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

							<!-- 模态框  用于当前添加的违章积分 大于12分的情况的提示 start-->
							<div class="modal fade" id="addAlertMsg" data-backdrop="static" data-keyboard="false">
								<div class="modal-dialog" data-backdrop="static" data-keyboard="false">
									<div class="modal-content message_align">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">×</span>
											</button>
											<h4 class="modal-title">提示</h4>
										</div>
										<div class="modal-body">
											<p>当前添加的违章积分大于等于12，即将永久加入黑名单，是否继续添加？</p>
										</div>
										<div class="modal-footer">
											<input type="hidden" id="url" />
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
											<a onclick="addAlertMsgBtn()" class="btn btn-success"
												data-dismiss="modal">确定</a>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- /.modal -->
							<!-- 模态框  用于违当前添加的 章记分>=12分的情况 end-->

					<script type="text/javascript">

				
							</script>

							<!-- 模态框 违章信息修改-->
							<div class="modal fade" id="modifyBreak" tabindex="-1" data-backdrop="static" data-keyboard="false"
								role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel2">修改违章信息</h4>
										</div>
										<form id="upForm">

											<div class="modal-body">
												<span>员工信息：</span>
												<div class="el_threeScoreList">
													<table class="table table-bordered">
														<tr>
															<td>姓名</td>
															<!-- <td>联系方式</td> -->
															<td>违章记分</td>
															<td>所属单位</td>
															<td>黑名单</td>
															<td>性别</td>
														</tr>
														<tr>
															<td id="upName">asdf</td>
															<!-- <td id="upPhone">asdf</td> -->
															<td id="upbreakScore">asdf</td>
															<td id="upunitName">asdf</td>
															<td id="upIsBreak">asdf</td>
															<td id="upSex">asdf</td>
														</tr>
													</table>
												</div>

												<!-- 隐藏域，隐藏 职工的违章id、职工id、大修外来职工id -->
												<!-- 职工的违章id -->
												<input id="upBreakId" type="hidden" value=""
													name="breakrules.breakid" />
												<!-- 职工id -->
												<input id="upEmployee" type="hidden" value=""
													name="breakrules.employeeid" />
												<!-- 大修外来职工id -->
												<input id="upBigHaulEmployeeId" type="hidden" value=""
													name="breakrules.bigemployeeoutid" />
												<!-- 隐藏域，隐藏单位大修编号 -->
												<input id="uppUnitBigHaulId" type="hidden"
													name="unitBigHaulId" value="" />
												<!-- 隐藏域  隐藏一个当前的(修改前的)违章记分 -->
												<input id="upQianBreakGrade" type="hidden"
													name="upQianBreakScore" />
												<!-- <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">违章地点：</span>
                                        <input type="text" class="form-control el_modelinput" name=""/>
                                    </div>
 -->
												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章时间：</span> <input id="upBreakTime"
														type="text" id="test41" class="wicon form-control"
														name="breakrules.breaktime" readonly />
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章积分：</span>
													<!--不得超过12分-->
													<input id="upbreakGrade" type="text"
														class="form-control el_modelinput"
														name="breakrules.minusnum" />
												</div>

												<div class="input-group el_modellist" role="toolbar">
													<span class="el_spans">违章情况：</span>
													<textarea id="upbreakContent"
														class="form-control el_modelinput" rows="3"
														name="breakrules.breakcontent"></textarea>
												</div>

											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" onclick="upSaveBtn()"
													class="btn btn-primary">提交更改</button>
											</div>
										</form>

									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>
							<!-- 隐藏域，隐藏是操作左边的树的还是顶部的分页条的 -->
							<input id="allMark" type="hidden" value="" />
							<!-- 点击修改按钮之后的操作  -->
							<script type="text/javascript">

							</script>



							<!-- 模态框   信息删除确认 -->
							<div class="modal fade" id="delcfmModel" data-backdrop="static" data-keyboard="false">
								<div class="modal-dialog" data-backdrop="static" data-keyboard="false">
									<div class="modal-content message_align">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">×</span>
											</button>
											<h4 class="modal-title">提示</h4>
										</div>
										<div class="modal-body">
											<p>您确认要删除该条信息吗？</p>
										</div>
										<div class="modal-footer">
											<input type="hidden" id="url" />
											<button type="button" class="btn btn-default"
												data-dismiss="modal">取消</button>
											<a onclick="urlSubmit()" class="btn btn-success"
												data-dismiss="modal">确定</a>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- /.modal -->

							<!-- 隐藏域  ，隐藏一个当前和删除按钮同一行的职工的违章id,用于删除操作 -->
							<input id="delEmployeeBreakId" type="hidden" value="" />
							<!-- 隐藏域，隐藏一个当前和删除按钮同一行的职工的职工id，用于删除操作 -->
							<input id="delEmployeeId" type="hidden" value="" />
							<!-- 隐藏单位大修id 用于删除操作的 -->
							<input id="delunitId" type="hidden" value="" />
							<!-- 和删除数据有关的操作 -->
				
				
				
				<script type="text/javascript">
					//<!-- 删除按钮的点击事件 -->
							
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
