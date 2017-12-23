<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<%@ include file="/public/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工管理</title>

<%@ include file="/public/cssJs.jsp"%>

    <!--分页-->
    <%-- <script src="<%=path %>/js/public/page.js"></script> --%>
    <script >
    var contextPath="${baseurl}";
    </script>
    
    <!--左边的树-->
    
    
    <script src="<%=path %>/js/innerDepart/innerdepartEmpManage.js"></script>
    <link rel="stylesheet" href="<%=path %>/css/innerDepart/innerdepartEmpManage.css">
    
    <link rel="stylesheet" href="<%=path %>/css/outDepart/outdepartTree.css">
    
    <!-- 验证 -->
    <script src="<%=path %>/controls/validate/jquery.validate.js"></script>
    <script src="<%=path %>/controls/validate/messages_zh.js"></script>
    
    <!-- 身份证阅读机的js -->
  	<script src="<%=path%>/js/outDepart/baseISSObject.js"></script>
	<script src="<%=path%>/js/outDepart/baseISSOnline.js"></script>
	<script src="<%=path%>/js/outDepart/common.js"></script>
	<script src="<%=path%>/js/outDepart/jquery.jBox-2.3.min.js"></script>
	<!-- 日期格式转换 -->
    <script src="${pageContext.request.contextPath }/js/questionLibrary/dateformat.js "></script>
	
	<script>
    	//定义一个全局变量
    	var basePathUrl = "${pageContext.request.contextPath}";
    </script>
</head>
<body onkeydown="keyLogin();">

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
        <div class="panel-heading"><span>部门管理</span><span>>员工管理</span></div>

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
                    <form id="InnerEmpQueryForm" method="POST" onsubmit="return false;">

                        <div class="row el_queryBoxrow">
                            <div class="col-md-3 el_qlmQuery">
                                <div class="input-group" role="toolbar">
                                    <span class="el_spans">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
                                    <input type="text" class="form-control" name="name" id="InnerEmpName"/>
                                    <input type="hidden" name="currentPage" id="currentPage" />
									<input type="hidden" name="currentCount" id="currentCount" />
									<input type="hidden" name="departmentid" id="queryDepartmentId" value='${result.getdepartmentid }' />
        							<!-- <input type="text" name="departmentid" id="queryDepartmentId" value="12001"/> -->
									<!-- 默认显示管理员 -->
									<input type="hidden" name="isOnlyManager" value="true"
										id="el_showManagerInput" />
                                </div>
                            </div>

                            <div class="col-md-3 el_qlmQuery">
                                 <div class="input-group" role="toolbar">
                                    <span class="el_spans">性别：</span>
                                    <label class="el_radioBox"><input type="radio" name="sex" value="1"> 男</label>
                                    <label class="el_radioBox"><input type="radio" name="sex" value="2"> 女</label>
                                </div>
                            </div>

                            <div class="col-md-3 el_qlmQuery">
                                <div class="input-group" role="toolbar">
                                    <span class="el_spans">身份证：</span>
                                    <input type="text" class="form-control" name="idcode" id="InnerEmpIdcode"/>
                                </div>
                            </div>

                        </div>

                       <!--  <div class="row el_queryBoxrow">

                            <div class="col-md-3 el_qlmQuery">
                                <div class="input-group" role="toolbar">
                                    <span class="el_spans">考试情况：</span>
                                    <select class="selectpicker form-control" title="请选择" name="trainstatus" id="InnerEmpTrainstatus">
                                        <option value="">--请选择--</option>
                                        <option value="1">通过一级考试</option>
                                        <option value="2">通过二级考试</option>
                                        <option value="3">通过三级考试</option>
                                        <option value="0">未通过</option>
                                    </select>
                                </div>
                            </div>
                        </div> -->
						<!--清空按钮-->
                       <button type="reset" class="btn btn-default el_queryButton0 btn-sm">清空</button>

                        <!--提交查询按钮-->
                        <button type="button" class="btn btn-default el_queryButton btn-sm btn-primary" onclick="clearPage()" >查询</button>
                        
                         
                    </form>

                </div>   <!--结束 查询表单提交-->

                <!--显示内容-->
                <h4 class="el_mainHead">员工信息</h4>
                <div class="panel panel-default el_Mainmain">

                    <!--按钮面板-->
                    <div class="panel-body">

                        <div class="panel panel-default">
                            <div class="panel-body el_MainxiaoMain">

                                <div class="el_topButton">
                                    <!-- 按钮触发模态框1 -->
                                    
                                    <!-- 在模态框中添加 -->
                                    <a id="el_addUserA" onclick="el_addEmployeeIn()">
                                        <button class="btn btn-primary">
                                            	添加员工
                                        </button>
                                    </a>
                                    
                                    <button class="btn btn-primary" data-toggle="modal" onclick="el_empTrainDoc()">
                                      	  查看员工培训档案
                                    </button>
                                    
									<!-- <label id="el_showAllUser" title="选中将会显示该部门的管理人员"
										class="btn btn-sm btn-primary"> <input
										type="checkbox" onclick="isShowOnlyManager()"
										id="el_showManager" class="checkbox-primary"> <span>&nbsp;管理人员</span>
									</label> -->

									<select class="btn btn-primary" id="el_showManager" title="请选择" onchange="isShowOnlyManager()" name="examLevel">
			                              <option value="true">管理人员</option>
			                              <option value="false">全部员工</option>
					                </select>
					                
                                    <!-- <button class="btn btn-primary" data-toggle="modal" onclick="el_batchInput()">
                                      	 批量导入
                                    </button> -->
                                </div>

                            </div>
                        </div>

                        <!--表格
                            内容都提取到json里边
                        -->
                        <table class="table table-hover  table-bordered">
                            <thead>
                            <tr>
                                <th>选择</th>
                                <th>姓名</th>
                                <th>性别</th>
                                <th>出生年月</th>
                                <th>联系方式</th>
                                <th>所属部门</th>
                                <th>职务</th>
                                <th>身份证</th>
                              
                                <th>操作</th>
                            </tr>
                            </thead>
                          
                            <tbody id="t_body"></tbody>
                        </table>

                        <!--分页-->
                       <div id="paginationIDU" class="paginationID"></div>

                    </div>
                </div>

							<!-- 模态框 添加员工-->
							<div class="modal fade" id="el_addEmployeeIn" tabindex="-1"
								role="dialog" aria-labelledby="myModalLabel23"
								data-backdrop="static" data-keyboard="false" aria-hidden="true">
								<div class="modal-dialog"
									style="width: 70%;position:relative; max-height: 550px; overflow-y: auto;">
									<div class="modal-content" style="min-height:420px !important; ">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel23">添加员工</h4>
										</div>

										<div class="modal-body" style="position: relative">
											<!--头像一-->
											<div id="localImag" class="big-photo"
												style="border:none;">
												<img style="width: 120px; height: 140px;"
													src="${pageContext.request.contextPath}/image/userImage.png"
													onerror="this.src='image/userImage.png'"
													style="margin-left:5px" id="id_img_pers">
											</div>
											<input id="idCardImageStr" type="hidden" />

											<div class="input-group el_modellist01" role="toolbar">
												<span class="el_spans0">员工姓名：</span> <input type="text"
													value="" id="personName" disabled
													class="form-control el_modelinput" name="EmployeeIn.name" />
												<span class="el_spans0">员工性别：</span> <input type="text"
													value="" id="gender" disabled
													class="form-control el_modelinput" name="EmployeeIn.sex" />
											</div>
											<div class="input-group el_modellist01" role="toolbar">
												<span class="el_spans0">出生日期：</span> <input type="text"
													value="" id="birthday" disabled
													class="form-control el_modelinput"
													name="EmployeeIn.birthday" /> <span class="el_spans0">身&nbsp;&nbsp;份&nbsp;证：</span>
												<input type="text" value="" id="certNumber" disabled
													class="form-control el_modelinput" name="EmployeeIn.idcode" />
											</div>
											
											<div class="input-group el_modellist01" role="toolbar">
												<span class="el_spans0">家庭住址：</span> <input type="text"
													value="" id="address" disabled
													class="form-control el_modelinput"
													name="EmployeeIn.address" /> <span class="el_spans0">所选部门：</span>
												<input type="text" class="form-control el_modelinput"
													disabled name="EmployeeIn.departmentid"
													id="add_departmentName" /> <input type="hidden"
													id="yincangzhiwu">
											</div>

											<div class="input-group el_modellist03" role="toolbar">
													<span class="el_spans0">职 &nbsp;&nbsp;&nbsp;&nbsp;务
													&nbsp;&nbsp;：</span> <select
													class="form-control el_modelinput" title="请选择"
													name="EmployeeIn.duty" id="addEmployeeInDuty"></select> 
											</div>
											<div class="input-group el_modellist04" role="toolbar">
													<span
													class="el_spans0">联系方式：</span>
												<input type="text" class="form-control el_modelinput"
													name="EmployeeIn.photo" id="addEmployeeInPhone"/>
											</div>
											<div id="message3"
												style="display: block; margin-left: 99px; color: red;"></div>

											<tr>
												<td id="readIDDiv" colspan="4" style="text-align: center;">
													<button class="btn btn-default" id="button_readID">读取身份证信息</button>
												</td>
											</tr>

											<!-- <button class="btn btn-default" id="button_readID">读取身份证信息</button> -->
											<button class="btn btn-primary el_modellist02"
												onclick="addEmployeeInInfo()">添加</button>

											<table class="table table-hover table-bordered" style="margin-bottom:40px;">
												<thead>
													<tr>
														<th>姓名</th>
														<th>性别</th>
														<th>身份证</th>
														<th>职务</th>
														<th>联系方式</th>
														<th>部门</th>
														<!-- <th>职务</th> -->
														<th>操作</th>
													</tr>
												</thead>
												<tbody id="addEmployeeOutInfoList">
												</tbody>
											</table>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											<button type="button" class="btn btn-primary"
												onclick="saveEmployeeAndHaulInfo()">保存</button>
										</div>

										<!-- <form id="form_addEmployeeOutInfo">											
										</form> -->


									</div>

									<form id="form_addEmployeeOutInfo"></form>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>

							<!-- 模态框  修改员工-->
							<div class="modal fade" id="el_modifyEmp" tabindex="-1"
								role="dialog" aria-labelledby="myModalLabel23"
								aria-hidden="true" data-backdrop="static" data-keyboard="false">
								<div class="modal-dialog"
									style="width: 50%; max-height: 550px; overflow-y: auto;">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel23">修改员工信息</h4>
										</div>
										<form id="updateEmployeeIn">
											<div class="modal-body" style="position: relative">
												<!--头像一-->
												<div class="big-photo"></div>
												
												<div class="input-group el_modellist01" role="toolbar">
													<span class="el_spans0">员工姓名：</span> <input type="text"
														class="form-control el_modelinput" disabled name="EmployeeIn.name" id="updateEmployeeInName"/>
														<input type="hidden" name="EmployeeIn.employeeid" id="updateEmployeeEmployeeid"/>
												</div>

												<div class="input-group el_modellist01">
													<span class="el_spans0"> 员工性别：</span>
													<div>
														<label><input type="radio" disabled name="EmployeeIn.sex" value="男"> 男</label> 
														<label><input type="radio" disabled name="EmployeeIn.sex" value="女">女</label>
													</div>
												</div>

												<div class="input-group el_modellist01" role="toolbar">
													<span class="el_spans0">出生日期：</span> <input type="text"
														class="form-control el_modelinput" disabled name="EmployeeIn.birthday" id="updateEmployeeInBirthday" />
												</div>

												<div class="input-group el_modellist01" role="toolbar">
													<span class="el_spans0">身&nbsp;&nbsp;份&nbsp;证：</span> <input
														type="text" class="form-control el_modelinput" disabled
														name="EmployeeIn.idcode" id="updateEmployeeInIdcode"/>
												</div>

												<div class="input-group el_modellist01" role="toolbar">
													<!-- <span class="el_spans0">选择单位：</span> <input type="text"
														class="form-control el_modelinput" disabled name="" />
														 -->
														
											
														
													<!-- 部门树 -->	
													<span class="el_spans el_chooseSpan">选择部门：</span>
							                       <input type="hidden"  id="updateEmployeeInDepartment" name="EmployeeIn.departmentid" />
							                       <ul id="el_chooseUpdateDepart" class="el_modelinput el_chooseInput log" ></ul>
							                       <img src="../../controls/selectDropTree/smallTriangle.png" class="el_smallTriangle"
							                            width="7"/>
							                       <ul id="treeDemo100" class="ztree" style="display:none"></ul>	
														
												</div>

											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
												<button type="button" class="btn btn-primary" onclick="saveUpdateEmployeeInButton()">保存</button>
											</div>
										</form>


									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>


                <!-- 模态框 查看培训档案-->
							<div class="modal fade" id="el_empTrainDoc" tabindex="-1"
								role="dialog" aria-labelledby="myModalLabel23"
								aria-hidden="true" data-backdrop="static" data-keyboard="false">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<!--关闭符号-->
											<!--标题-->
											<h4 class="modal-title" id="myModalLabel234">员工培训档案</h4>
										</div>
										<form id="hiddenidcodeForm" method="POST">
											<input type="hidden" id="hiddenidcode" value=""
												name="hiddenidcode" /> <input type="hidden"
												name="currentPage" id="currentPage2" /> <input
												type="hidden" name="currentCount" id="currentCount2" />
										</form>
										<form>
											<div class="modal-body">
												<div class="input-group el_empPhoto" role="toolbar"
													style="height: 127px;">
													<img id="myimg2" width="95" height="121">
												</div>
												<div class="el_threeScoreList">

													<table>
														<caption style="margin-bottom: 20px; margin-top: -10px;">员工信息：</caption>
														<tr>
															<td>姓名</td>
															<td id="TrainName"></td>
															<td>性别</td>
															<td id="TrainSex"></td>
														</tr>
														<tr>
															<td>联系方式</td>
															<td id="TrainPhone"></td>
															<!-- <td>违章记分</td>
                                                <td id="TrainMinusnum"></td> -->
															<td>所属部门</td>
															<td id="TrainUnit"></td>
														</tr>


													</table>
												</div>
												<table class="table table-hover  table-bordered"
													style="width: 95%; margin: 0 auto; font-size: 13px;">
													<thead>
														<tr>
															<th>考试名称</th>
															<th>考试级别</th>
															<th>考试时间</th>
															<th>考试总分数</th>
															<th>获得成绩</th>
															<!-- <th>是否通过</th> -->
														</tr>
													</thead>

													<tbody id="empTrainDoc_body">

													</tbody>


												</table>
												<!--分页-->
												<div id="paginationIDU2" class="paginationID"></div>
											</div>

											<div class="modal-footer">
												<button type="button" class="btn btn-primary"
													onclick="extEmpTrain()">导出培训档案</button>
												<button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
											</div>
										</form>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal -->
							</div>

                <!-- 模态框 员工详细信息-->
                <div class="modal fade" id="allInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button><!--关闭符号-->
                                <!--标题-->
                                <h4 class="modal-title" id="myModalLabel">员工详细信息</h4>
                            </div>
                            <form>
                                <div class="modal-body">

                                    <div class="input-group el_empPhoto" role="toolbar" style="height: 127px;border-left-width: 0px;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;">
                                        <img id="myimg" width="95" height="121">
                                    </div>
                                    <div class="el_parperInfo">
                                       <table>
                                            <tr>
                                                <td>员工姓名：</td>
                                                <td id="InfoName"></td>
                                            </tr>
                                            <tr>
                                                <td>性别：</td>
                                                <td id="InfoSex"></td>
                                            </tr>
                                            <tr>
                                                <td>出生日期：</td>
                                                <td id="InfoBirthday"></td>
                                            </tr>
                                            <tr>
                                                <td>联系方式：</td>
                                                <td id="InfoPhone"></td>
                                            </tr>
                                            <tr>
                                                <td>身份证：</td>
                                                <td id="InfoIdcode"></td>
                                            </tr>
                                            <!-- <tr>
                                                <td>安全培训情况：</td>
                                                <td id="InfoTrainstatus"></td>
                                            </tr> -->
                                            <tr>
                                                <td>所属单位：</td>
                                                <td id="InfoDepartmentid"></td>
                                            </tr>
                                            <tr>
                                                <td>职务：</td>
                                                <td id="Infozhiwu"></td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <!-- <button type="button" class="btn btn-primary">提交更改</button> -->
                                </div>
                            </form>

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>

                <!-- 模态框   信息删除确认 -->
                <div class="modal fade" id="delcfmModel" data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog">
                        <div class="modal-content message_align">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                        aria-hidden="true">×</span></button>
                                <h4 class="modal-title">提示</h4>
                            </div>
                            <div class="modal-body">
                                <p>您确认要删除该条信息吗？</p>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" id="url"/>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                <a onclick="urlSubmit()" class="btn btn-success" data-dismiss="modal">确定</a>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->

                <!-- 模态框 违章情况-->
                <div class="modal fade" id="el_breakRulesCase" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel23" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button><!--关闭符号-->
                                <!--标题-->
                                <h4 class="modal-title" id="myModalLabe5l23">违章情况</h4>
                            </div>
                            <form>
                                <div class="modal-body">
                                    <div class="el_threeScoreList">
                                        <table>
                                            <caption>员工信息：</caption>
                                            <tr>
                                                <td>名称</td>
                                                <td>asdf</td>
                                                <td>性别</td>
                                                <td>sadf</td>
                                            </tr>
                                            <tr>
                                                <td>联系方式</td>
                                                <td>asdf</td>
                                                <td>违章记分</td>
                                                <td>asdf</td>
                                            </tr>
                                            <tr>
                                                <td>所属单位</td>
                                                <td>asdf</td>
                                                <td>黑名单</td>
                                                <td>否</td>
                                            </tr>
                                        </table>
                                    </div>
                                    <table class="table table-bordered" id="table2">
                                        <thead>
                                        <th>违章时间</th>
                                        <th>违章地点</th>
                                        <th>违章积分</th>
                                        <th>违章内容</th>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>2011-12-45</td>
                                            <td>撒打发士大夫士大</td>
                                            <td>12</td>
                                            <td>啊撒旦飞洒地方撒地方沙发上阿飞士大夫撒旦发</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </form>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>

				<!-- 模态框 批量导入-->
                <div class="modal fade" id="el_batchInput2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button><!--关闭符号-->
                                <!--标题-->
                                <h4 class="modal-title" id="myModalLabel">批量导入员工信息</h4>
                            </div><!-- /Exam/employeein_updateEmployeeIn.action -->
                            <form action="/Exam/employeein_batchImportEmployeeIn.action" method="post" enctype="multipart/form-data">
                                <div class="modal-body">
                                	<p style="font-weight:bolder;margin-bottom:5px;margin-left:5px;">请选择文件：</p>
                                    <input type="file" name="fileName" style="height:22px;font-size:13px" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
        />
        <input type="hidden" value="${message }" id="message">
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <button type="submit" class="btn btn-primary" >上传</button>
                                </div>
                            </form>
                            	<!-- <script type="text/javascript">
                            		alert($("#message").val())
                            	
                            	</script> -->

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
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
