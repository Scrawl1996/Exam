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
<title>安全帽台账信息</title>

<%@ include file="/public/cssJs.jsp"%>

<script>
	//定义一个全局变量
	var baseurl = "${pageContext.request.contextPath}";
</script>
<style>
.mark-type {
	font-size: 10px;
	padding: 2px 2px;
}

.el_treeTitle {
	display: inline;
	padding-right: 9px;
	padding-left: 9px;
}
</style>
</head>
<body>

	<!--头-->
	<jsp:include page="/view/public/header.jsp"></jsp:include>

	<!--中部-->
	<div class="html_middle">

		<!--放主界面内容-->
		<div class="newContent">

			<div class="container-fluid">
				<div class="panel panel-default">
					<!--菜单连接标题-->
					<div class="panel-heading">
						<span>安全帽台账信息</span>
					</div>
					<div class="el_main">

						<!--内容-->
						<div class="el_qlmContent111">
							<!--索引-->
							<div class="row el_queryBox">
								<form id="queryTaizhangForm">
									<div class="row el_queryBoxrow">
										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
												<input type="text" class="form-control clearInput"
													name="userName" />
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">身&nbsp;份&nbsp;&nbsp;证：</span> <input
													type="text" class="form-control clearInput" name="idCard" />
											</div>
										</div>

										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">安全帽编号:</span> <input type="text"
													class="form-control clearInput" name="safeHatNum" />
											</div>
										</div>
									</div>
									
									<div class="row el_queryBoxrow">
										<div class="col-md-3 el_qlmQuery">
											<div class="input-group" role="toolbar">
												<span class="el_spans">部&nbsp;门&nbsp;名&nbsp;称:</span> <input type="text"
													class="form-control clearInput" name="departName" />
											</div>
										</div>
									</div>

									<!-- 隐藏当前页和显示条数 -->
									<input type="hidden" name="currentPage" id="currentPage" /> <input
										type="hidden" name="currentCount" id="currentCount" value="8" />
									<!--提交查询按钮-->
									<button type="button"
										class="btn btn-primary el_queryButton btn-sm"
										onclick="queryTaizhangInfo()">查询</button>
									<button type="button"
										class="btn btn-default el_queryButton0 btn-sm"
										onclick="extTaizhang()">导出</button>
								</form>
							</div>
						</div>

						<!-- 部门基本信息 -->
						<div id="unitInfoDiv">
							<h3 class="el_mainHead">安全帽台账信息</h3>
							<div class="panel panel-default el_Mainmain">
								<div class="panel-body">
									<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>序号</th>
												<th>姓名</th>
												<th>性别</th>
												<th>年龄</th>
												<th>部门</th>
												<th>学历</th>
												<th>身体状况</th>
												<th>职务</th>
												<th>身份证号</th>
												<th>安全帽编号</th>
												<th>安全帽编号是否注销</th>
												<shiro:hasPermission name="safehat:operate">
													<th>操作</th>
												</shiro:hasPermission>
											</tr>
										</thead>
										<tbody id="taizhangTbody">
										</tbody>
									</table>
									<!--分页-->
									<div id="paginationIDU" class="paginationID"></div>
									<!-- 部门基本信息结束 -->
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>


			<!-- 模态框安全帽详情-->
			<div class="modal fade" id="SafeHatModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel23" data-backdrop="static"
				data-keyboard="false" aria-hidden="true">
				<div class="modal-dialog" style="width: 40%;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<!--关闭符号-->
							<!--标题-->
							<h4 class="modal-title" id="safeHatTitle"></h4>
						</div>
						<div class="modal-body" style="padding: 10px 30px 0 30px;">
							<div>
								<table
									class="table table-bordered table-hover el_threeScoreListTable">
									<thead>
										<tr>
											<th>序号</th>
											<th>安全帽变更记录</th>
										</tr>
									</thead>

									<tbody id="safeHatChangeDetail">
									</tbody>
								</table>
								<br />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
						</div>
						</form>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>


		</div>
	</div>

	<!--放脚-->
	<jsp:include page="/view/public/footer.jsp"></jsp:include>
</body>

</html>
<script>
var hasOperatingsafeHat=false;
	<shiro:hasPermission name="safehat:operate">
		hasOperatingsafeHat = true;
	</shiro:hasPermission>
</script>


<script>
$(function(){
	queryTaizhangInfo();
});

/**
 * 一个值如果是null或者''返回-
 * @param value 需要处理的值
 * @param length 需要截取的字符的长度的值,未指定的时候返回全部
 * @returns {*} 处理过的值
 */
function replaceNull(value,length) {
    //判断截取的值是否为空
    if(value == null || value==undefined || value == "" || value=='undefined'){
        return "";
    }
    //判断长度是否为空
    if(length == null || length == ''){
        return value;
    }
    return value.toString().substr(0,length);
}

function queryTaizhangInfo(){
	$.post(baseurl+"/safeHatIn_getSafehatTaizhang.do",$("#queryTaizhangForm").serialize(),function(responseMap){
		if(responseMap && responseMap.pageInfo){
			$("#taizhangTbody").html("");
			var currentCount = responseMap.pageInfo.pageSize;// 页大小
			var totalCount = responseMap.pageInfo.total;// 页总数
			var currentPage = responseMap.pageInfo.pageNum;// 当前页
			
			var datas = responseMap.pageInfo.list;
			for(var i=0;i<datas.length;i++){
				var data = datas[i];
				var tr = "<tr>"+
					"<td>"+(parseInt(currentCount) * parseInt(currentPage - 1) + (i + 1))+"</td>"
					+"<td>"+replaceNull(data.name)+"</td>"
					+"<td>"+replaceNull(data.sex)+"</td>"
					+"<td>"+replaceNull(data.age)+"</td>"
					+"<td>"+replaceNull(data.departmentName)+"</td>"
 					+"<td>"+replaceNull(data.empeducate)+"</td>"
					+"<td>"+replaceNull(data.empphysicalstatus)+"</td>"
					+"<td>"+replaceNull(data.duty)+"</td>"
					+"<td>"+replaceNull(data.idCode)+"</td>"
					+"<td><a href=javascript:void(0) onclick='queryHatChangeLog(this)'>"+replaceNull(data.safeHatNum)+"</a></td>"
					+"<td></td>";
					if(hasOperatingsafeHat){
						tr += "<td>";
						tr += "<a class='button' title='点击修改编号' href=javascript:void(0) onclick='updateSafeNum(this)'><span class='glyphicon glyphicon-pencil'></span></a>";
						tr += "<a class='button' title='点击删除安全帽' href=javascript:void(0) onclick='deleteSafeHat(this)'><span class='glyphicon glyphicon-trash'></span></a>";
						tr +="</td>";						
					}
					tr+="</tr>";
				$("#taizhangTbody").append(tr);
			}
			// 动态开启分页组件
			page(currentPage, totalCount, currentCount);
		}
	},"json");
}
//显示分页
function page(currentPage, totalCount, currentCount) {
	// 修改分页的基本属性
	$('#paginationIDU').pagination(
			{
				// 组件属性
				"total" : totalCount,// 数字 当分页建立时设置记录的总数量 1
				"pageSize" : currentCount,// 数字 每一页显示的数量 10
				"pageNumber" : currentPage,// 数字 当分页建立时，显示的页数 1
				"pageList" : [ 8, 15, 50 ],// 数组 用户可以修改每一页的大小，
				// 功能
				"layout" : [ 'list', 'sep', 'first', 'prev', 'manual', 'next',
						'last', 'links' ],
				"onSelectPage" : function(pageNumber, pageSize) {
					$("#currentPage").val(pageNumber);
					$("#currentCount").val(pageSize);
					queryTaizhangInfo();
				}
			});
}

function queryHatChangeLog(obj){
	if(obj){
		$("#safeHatChangeDetail").html("");
		$.post(baseurl+"/safeHatIn_getSafehatChangelog.do",{"safeHatNum":$(obj).text()},function(res){
			if(res){
				for(var i=0,length_1=res.data.length;i<length_1;i++){
					var str = "<tr><td>"+(i+1)+"</td><td>"+res.data[i]+"</td></tr>";
					$("#safeHatChangeDetail").append(str);
				}
				$("#SafeHatModal").modal({
					backdrop : 'static',
					keyboard : false
				}); // 手动开启
			}
		},'json')
	}
}

function extTaizhang(){
	//动态创建表单加到fbody中，最后删除表单
    var queryForm = $("#queryTaizhangForm");
    var exportForm = $("<form action='"+baseurl+"/extSafeHatInTaizhang.do' method='post'></form>")     
    
    exportForm.append("<input type='hidden' name='userName' value='"+$("[name='userName']").val()+"'/>")
    exportForm.append("<input type='hidden' name='idCard' value='"+$("[name='idCard']").val()+"'/>")
    exportForm.append("<input type='hidden' name='safeHatNum' value='"+$("[name='safeHatNum']").val()+"'/>")
    exportForm.append("<input type='hidden' name='departName' value='"+$("[name='departName']").val()+"'/>")
    $(document.body).append(exportForm);
    exportForm.submit();
    exportForm.remove(); 
}

function deleteSafeHat(obj){
	if(obj){
		if(confirm("确认删除?")){
			var safeHatNum = $(obj).parents("tr").find("td:eq(7)").text();
			$.post(baseurl+"/safeHatIn_deleteSafeHat.do",{"safeHatNum":safeHatNum},function(res){
				alert(res.msg);
				if("删除成功" == res.msg){
					queryTaizhangInfo();
				}
			},'json');
		}
	}
}

function updateSafeNum(obj){
	if(obj){
			var safeHatNum = $(obj).parents("tr").find("td:eq(7)").text();
			var newSafeHatNum= prompt("请输入新的编号",safeHatNum);
			if(newSafeHatNum==null){//点击确定
				return;
		    }
			$.post(baseurl+"/safeHatIn_updateSafeNum.do",
					{"safeHatNum":safeHatNum,
					"newSafeHatNum":newSafeHatNum
					},function(res){
				alert(res.msg);
				if("修改成功" == res.msg){
					queryTaizhangInfo();
				}
			},'json');
	}
}
</script>

