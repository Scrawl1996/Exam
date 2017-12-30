/** *************************分配员工************************** */
function el_empTrainDoc(){
	
	var chooseEmpNum = 0;//判断是否有员工被选中
	
	$(".el_checks").each(function(){ //获取选择的员工
		
		if ($(this).prop("checked")){//如果选中。。。
			chooseEmpNum ++;
		
		}
	})
	
	if(chooseEmpNum != 0) {
		$("#el_empTrainDoc").modal();
		
	} else {
		alert("请先选择员工！")
	}
}

/*检修的树 */
$(function(){	

	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"#1机组大修", open:true},
			{ id:11, pId:1, name:"北京博齐", open:true},
			{ id:111, pId:11, name:"666"},
			
		];
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_permission"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = type;
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo_permission"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
})



/** *************************修改分配员工************************** */
function el_changeAllot(){
	$("#el_changeAllot").modal();
}
/*检修的树 */
$(function(){	

	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"#1机组大修", open:true},
			{ id:11, pId:1, name:"北京博齐", open:true},
			{ id:111, pId:11, name:"626"},
			
		];
		
		var code;
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_permission1"),
			py = $("#py").attr("checked")? "p":"",
			sy = $("#sy").attr("checked")? "s":"",
			pn = $("#pn").attr("checked")? "p":"",
			sn = $("#sn").attr("checked")? "s":"",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = type;
			showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		}
		function showCode(str) {
			if (!code) code = $("#code");
			code.empty();
			code.append("<li>"+str+"</li>");
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo_permission1"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
})





/** *************************页面加载函数************************** */

$(function() {
	initEmployeeTypeDic();
	findEmployeeOutBaseInfo();
});

/** ************************组合条件查询********************************** */
// 初始化工种信息
function initEmployeeTypeDic() {
	$.post(basePathUrl + '/dic_getDicNamesByUpid.action', {
		"upId" : "100"
	}, showEmployeeTypeDic, 'json');
}
function showEmployeeTypeDic(response) {
	if (response != null && response.names != null) {
		var names = response.names;// 获取字段返回的值
		$("#query_empType").append("<option value=''>--请选择--</option>")
		for (var i = 0; i < names.length; i++) {
			$("#query_empType").append(
					'<option value="' + names[i] + '">' + names[i]
							+ '</option>')
		}
	}
}

// 点击查询按钮执行的操作
function queryEmployeeOutBaseInfo() {
	// 执行查询操作前先将隐藏域中的值清空当前页清空或改为第一页
	$("#currentPage").val("");
	// 调用查询的方法
	findEmployeeOutBaseInfo();
}

function findEmployeeOutBaseInfo() {
	$.ajax({
		url : "employeeOutPerson_getEmployeeOutBaseInfoList.action",
		data : $("#form_findEmployeeOutBaseInfo").serialize(),
		type : "post",
		dataType : "json",
		success : showEmployeeBaseInfo,
		error : function() {
			alert("请求失败！")
		}
	})
}
// 显示员工的基本信息
function showEmployeeBaseInfo(data) {
	var employeeOutBaseInfoList = data.pageBean.productList;
	var showEmployeeOutBaseInfoList = '';
	for (var i = 0; i < employeeOutBaseInfoList.length; i++) {
		var index = i + 1;
		showEmployeeOutBaseInfoList += "<tr><td><input type='checkbox' name='el_chooseEmp' class='el_checks' vaule='"
				+ employeeOutBaseInfoList[i].idcard
				+ "'/></td><td>"
				+ (index + (data.pageBean.currentPage - 1) * 8)
				+ "<input class='find_employeeOutBirthday' type='hidden' value='"
				+ employeeOutBaseInfoList[i].birthday
				+ "'/>"
				+ "<input class='find_employeeOutPhoto' type='hidden' value='"
				+ employeeOutBaseInfoList[i].photo
				+ "'/>"
				+ "<input class='find_employeeOutId' type='hidden' value='"
				+ employeeOutBaseInfoList[i].employeeid
				+ "'/>"
				+ "<input class='find_bigEmployeeOutId' type='hidden' value='"
				+ employeeOutBaseInfoList[i].bigemployeeoutid
				+ "'/>"
				+ "<input class='find_bigId' type='hidden' value='"
				+ employeeOutBaseInfoList[i].bigid
				+ "'/>"
				+ "</td><td>"
				+ employeeOutBaseInfoList[i].name
				+ "</td><td>"
				+ (employeeOutBaseInfoList[i].sex > 1 ? '女' : '男')
				+ "</td><td>"
				+ employeeOutBaseInfoList[i].idcard
				+ "</td><td>"
				+ employeeOutBaseInfoList[i].departmentname
				+ "</td><td>"
				+ employeeOutBaseInfoList[i].emptype
				+ "</td><td onclick='el_breakRulesCase(this)' class='success'>"
				+ employeeOutBaseInfoList[i].minusnum + "</td>";
		if (employeeOutBaseInfoList[i].isblacklist == '是') {
			showEmployeeOutBaseInfoList += "<td style='color:red;'>"
					+ employeeOutBaseInfoList[i].isblacklist + "</td><td>";
		} else if (employeeOutBaseInfoList[i].isinblacklist == '是') {
			showEmployeeOutBaseInfoList += "<td style='color:blue;'>"
					+ employeeOutBaseInfoList[i].isblacklist + "</td><td>";
		} else {
			showEmployeeOutBaseInfoList += "<td>"
					+ employeeOutBaseInfoList[i].isblacklist + "</td><td>";
		}
		showEmployeeOutBaseInfoList += employeeOutBaseInfoList[i].trainstatus
				.toString().replace("0", "未参加培训").replace("1", "通过一级考试")
				.replace("2", "通过二级考试").replace("3", "通过三级考试")
				+ "</td><td>"
				+ "<a href='javascript:void(0)' onclick='allInfo(this)'>详细信息</a>&nbsp;";
		if (employeeOutBaseInfoList[i].bigstatus != "已结束" && hasOperatingEmpout) {
			showEmployeeOutBaseInfoList += "<a href='javascript:void(0)' onclick='el_modifyEmp(this)'>修改</a>&nbsp;"
					+ "<a class='el_delButton' onClick='delcfm(this)'>删除</a><br />"
					+ "</td></tr>";
		} else {
			showEmployeeOutBaseInfoList += "</td></tr>";
		}
	}
	/*$("#employeeOutBaseInfoList").empty();
	$("#employeeOutBaseInfoList").append(showEmployeeOutBaseInfoList);
*/
	// 当前页
	var currentPage = data.pageBean.currentPage;
	// 总条数
	var totalCount = data.pageBean.totalCount;
	// 页大小
	var currentCount = data.pageBean.currentCount;
	// 调用分页函数
	outdepartEmpManage_page(currentPage, totalCount, currentCount);
}

/** *******************************模态框*************************************** */



/** *******************************分页*************************************** */
// 员工基本信息的分页函数
function outdepartEmpManage_page(currentPage, totalCount, currentCount) {
	$('#paginationIDU').pagination(
			{
				// 组件属性
				"total" : totalCount,// 数字 当分页建立时设置记录的总数量
				"pageSize" : currentCount,// 数字 每一页显示的数量 10
				"pageNumber" : currentPage,// 数字 当分页建立时，显示的页数 1
				"pageList" : [ 8, 15, 20 ],// 数组 用户可以修改每一页的大小，
				// 功能
				"layout" : [ 'list', 'sep', 'first', 'prev', 'manual', 'next',
						'last', 'links' ],
				"onSelectPage" : function(pageNumber, b) {
					// alert("pageNumber=" + pageNumber);
					// alert("pageSize=" + b);
					// 向隐藏域中设置值
					$("#currentPage").val(pageNumber);
					$("#currentCount").val(b);
					// 调用查找函数
					findEmployeeOutBaseInfo();
				}
			});

}

/** *********************点击查询表单的清空按钮执行的操作********************* */
function clearQueryInfo() {
	// 清空隐藏域中的大修和部门信息
	$("#query_bigId").val('');
	$("#query_unitId").val('');
}


/** **************************************树的相关方法**************************************************************** */

$(function() {
	searchDepartmentAndOverHualTree();
})

/** *********************请求树信息********************* */

function searchDepartmentAndOverHualTree() {
	$.ajax({
		type : "post",
		target : "#departmentAndOverHaulTree",
		dataType : "json",
		url : "employeeOutPerson_getDepartmentAndOverHaulTree.action",
		success : getTree_1,
		error : function() {
			alert("请求树失败！");
		}
	});
}

/** *********************生成树信息********************* */
function getTree_1(treeList2) {
	var treeList3 = treeList2.departmentAndOverHaulTree;
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "upid",
				rootPId : null,
			},
			key : {
				name : "name",
			}
		},
		callback : {
			onClick : onClick
		}
	};
	var zNodes = treeList3;
	// 添加 树节点的 点击事件；
	var log, className = "dark";
	$.fn.zTree.init($("#departmentAndOverHaulTree"), setting, zNodes);
	var treeObj = $.fn.zTree.getZTreeObj("departmentAndOverHaulTree");
	treeObj.expandAll(false);
}

var clickRes = 0;
var clickBigAndDepTree = 0;
var selectedDepartmentName;
var selectedDepartmentID;
var selectedOverHaulId;
function onClick(event, treeId, treeNode) {
	// 清空页号:
	$("#currentPage").val("");
	clickBigAndDepTree = 1;
	selectedOverHaulId = treeNode.id;
	selectedDepartmentID = treeNode.upid;
	$("#query_bigId").val(selectedOverHaulId);
	$("#query_unitId").val(selectedDepartmentID);
	if (selectedDepartmentID == null) {
		// 调用查询员工信息的方法
		queryEmployeeOutBaseInfo();
	}
	// 如果树的等级是零则证明是部门，设置部门信息
	if (treeNode.level == 1) {
		clickRes = 1;
		// 设置部门名称
		$("#add_departmentName").val(treeNode.name);
		$("#add_departmentId").val(treeNode.id);
		$("#add_bigId").val(treeNode.upid);
		selectedDepartmentID = treeNode.id;
		selectedOverHaulId = treeNode.upid;
		$("#query_bigId").val(selectedOverHaulId);
		$("#query_unitId").val(selectedDepartmentID);
		// 调用查询员工信息的方法
		queryEmployeeOutBaseInfo();
	} else {
		clickRes = 0;
	}

	return (treeNode.click != false);
}
