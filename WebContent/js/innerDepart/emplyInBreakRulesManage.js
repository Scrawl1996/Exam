/**
 * Created by yorge on 2017/9/14.
 */

/* 添加违章信息 */
function el_addBreakInfo() {
	// 累计选择的个数，若等于1，才执行，否则提示
	var nums = 0;
	$.each($(".el_checks"), function(i, el_check) {
		if ($(this).prop("checked")) {
			nums++;
		}
	});
	if (nums != 1) {
		alert("请选中员工之后再添加违章信息！");
		return false;
	} else {
		// 为模态框表格中的数据初始化

		// 获取被选中按钮的值
		// 姓名
		var empName = $('input[name="el_chooseBreakRules"]:checked ').parents("tr").children("td").eq(1).text();// 姓名
		// 联系方式
		var phone = $('input[name="el_chooseBreakRules"]:checked ').val();
		// 违章记分
		var score = $('input[name="el_chooseBreakRules"]:checked ').parents("tr").children("td").eq(4).text();
		// 所属单位
		var unitName = $('input[name="el_chooseBreakRules"]:checked ').parents("tr").children("td").eq(6).text();
		// 黑名单
		var isBreak = $('input[name="el_chooseBreakRules"]:checked ').parents("tr").children("td").eq(5).text();
		// 性别
		var sex = $('input[name="el_chooseBreakRules"]:checked ').parents("tr").children("td").eq(2).text();
		

		// 为表格中的数据初始化
		// 姓名
		$("#addName").text(empName);
		// 联系方式
		$("#addPhone").text(phone);
		// 违章记分
		$("#addbreakScore").text(score);
		// 所属单位
		$("#addunitName").text(unitName);
		// 是否黑名单
		$("#addIsBreak").text(isBreak);
		// 性别
		$("#addSex").text(sex);

		// 获取职工id
		var empId = $('input[name="el_chooseBreakRules"]:checked').parents("tr").children('input[name="el_ycy"]').eq(0).val();
		// 操作的标记 是左侧的树还是顶部的按条件分页查询
		var allmark = $('input[name="el_chooseBreakRules"]:checked').parents("tr").children('input[name="el_ycy"]').eq(1).val();
		// 部门id
		var departmentid = $('input[name="el_chooseBreakRules"]:checked').parents("tr").children('input[name="el_ycy"]').eq(2).val();
		// 职工的身份证号码
		var idcode = $('input[name="el_chooseBreakRules"]:checked').parents("tr").children('input[name="el_ycy"]').eq(3).val();

		// 为员工id的隐藏域赋值
		$("#addEmpID").val(empId);
		// 为操作标记赋值
		$("#allMark").val(allmark);
		// 为身份证赋值
		$("#addIdCard").val(idcode);
		// 为隐藏域赋值
		// 职工id

		// 为添加前的违章总积分隐藏域赋值
		$("#breakScoreSum").val(score);

		// 为违章记分、违章内容初始化，也就是初始化添加违章信息的表单
		// 初始化违章记分
		$("#breakScoreID").val("");
		// 初始化违章内容
		$("#addBreakContent").val("");
		

		if ($("#addIsBreak").text() == "是") {
			alert("处于永久黑名状态的外来职工不能添加违章积分")
		} else {
			// 打开模态框
			$("#el_addBreakInfo").modal();
		}

	}
}

/* 选择树 */
function el_selectTree() {
	// 获得的树的名字： getName
}

/* 员工详情 */
/*
 * function el_breakRulesInfo() { $('#el_breakRulesInfo').modal(); }
 */
/*
 * 修改违章信息 function modifyBreak() {
 * 
 * //弹出模态框 $('#modifyBreak').modal(); }
 */
/* 删除 */
/*
 * function delcfm() { $('#delcfmModel').modal(); }
 */
function urlSubmit() {
	var url = $.trim($("#url").val());// 获取会话中的隐藏属性URL
	window.location.href = url;
}

/** ******************lixianyuan start *********** */
// 初始化部门树
function searchDepartmentTree_2() {
	$.ajax({
		type : "post",
		target : "#treeDemo",
		dataType : "json",
		url : "empInbreakrules_initDepartment.action",
		success : getTree_2,
		error : function() {
			alert("请求树失败！");
		}
	});
}

/** *********************生成树信息********************* */
function getTree_2(treeList2) {
	var treeList3 = treeList2.departmentTree;
	var setting = {
		data : {
			simpleData : {
				enable : true,
				idKey : "departmentId",
				pIdKey : "upDepartmentId",
				rootPId : null,
			},
			key : {
				name : "departmentName",
			}
		},
		callback : {
			beforeClick : beforeClick
		}
	};
	var zNodes = treeList3;
	// 添加 树节点的 点击事件；
	var log, className = "dark";
	function onClick(event, treeId, treeNode, clickFlag) {
		clickOnTree(event, treeId, treeNode, clickFlag);
		
	}
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	// var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	// treeObj.expandAll(false);
}

/** **************************************树的相关方法**************************************************************** */

$(function() {
	// 初始化树
	searchDepartmentTree_2();

	jQuery.validator.addMethod("isNumber", function(value, element) {
		var length = value.length;
		var tel = /(^\d+$)/;
		return this.optional(element) || (tel.test(value));
	}, "请输入数字");

	// 页面加载的时候初始化一些数据出来
	findSaveBtn();
	//initData();
})

/** *********************请求树信息********************* */

function showIconForTree(treeId, treeNode) {
	return !treeNode.isParent;
}

var clickRes = 0;
var getName;
var selectedDepartmentID;
var selectedOverHaulId;
function beforeClick(treeId, treeNode, clickFlag) {
	clearBtn();
	$("#yeHao").val("1");// 清空页号
	clickRes = 1;
	getName = treeNode.departmentName;// 当前选中的树的名字
	selectedDepartmentID = treeNode.departmentId;// 当前选中的树的id
	selectedOverHaulId = treeNode.upDepartmentId;// 当前选中的树的上级id



	// 隐藏当前选中的部门的部门id
	$("#departmentidTree").val(selectedDepartmentID);
	

	// 点击部门显示其及其子孙部门下的所有内部员工信息及其违章信息
	leftBtn();

	// console.log(selectedOverHaulId);
	return (treeNode.click != false);
}

// *********************页面加载初始化一些数据出来
// 页面初始化的
/*function initData() {

		$.ajax({
				url : contextPath+"/empInbreakrules_initPageDate.action",
				data : {
					"curPage" : $("#yeHao").val(),// 当前页页号
					"curTotal" : $("#jiLuShu").val()
				// 每页显示的记录数
				},
				dataType : "json",
				type : "POST",
				async : true,
				success : function(data) {
					// alert("执行了左侧的树的回掉函数")
					// 每次查询之前都先清空表格中的数据
					$("#tbody tr").remove();
					var opStr = "";
					for (var i = 0; i < data.empInMsgByDepIdLeft.length; i++) {

						var name = data.empInMsgByDepIdLeft[i].name;// 姓名
						var sex = data.empInMsgByDepIdLeft[i].sex;// 性别
						if (sex == "1") {
							sex = "男";
						} else if (sex == "2") {
							sex = "女";
						}
						var idCode = data.empInMsgByDepIdLeft[i].idCode;// 身份证
						var sumBreakScore = data.empInMsgByDepIdLeft[i].sumBreakScore;// 违章总记分
						var phone = data.empInMsgByDepIdLeft[i].phone;// 电话
						var duty = data.empInMsgByDepIdLeft[i].duty;// 职务
						var unitName = data.empInMsgByDepIdLeft[i].departmentName;// 部门名称
						var employeeId = data.empInMsgByDepIdLeft[i].employeeId;// 内部员工id
						var departmentid = data.empInMsgByDepIdLeft[i].departmentId;// 部门id
						var blackStatus = data.empInMsgByDepIdLeft[i].status;// 黑名单状态

						opStr += "<tr>";
						// 隐藏域
						opStr += "<input name='el_ycy' type='hidden' value="+ employeeId + ">";// 隐藏域，隐藏一个职工id employeeId
						opStr += "<input name='el_ycy' type='hidden' value='initData'>";// 隐藏域，隐藏一个标记，是左侧的树还是顶部的按条件分页查询
						opStr += "<input name='el_ycy' type='hidden' value="+ departmentid + ">";// 隐藏部门id
						opStr += "<input name='el_ycy' type='hidden' value="+ idCode + ">";// 隐藏身份证

						opStr += "<td><input type='radio' name='el_chooseBreakRules' class='el_checks' value="+ phone + "></td>";
						opStr += "<td>" + name + "</td>";// 姓名
						opStr += "<td>" + sex + "</td>";// 性别
						opStr += "<td>" + idCode + "</td>";// 身份证
						opStr += "<td>" + sumBreakScore + "</td>";// 违章记分
						// 为隐藏职工id的隐藏域赋值
						if (blackStatus == "1") {
							opStr += "<td><font color='red'>" + "是"	+ "</font></td>";
						} else {
							if (sumBreakScore >= 12) {
								opStr += "<td><font color='blue'>" + "否"+ "</font></td>";
							} else {
								opStr += "<td>" + "否" + "</td>";
							}
						}

						opStr += "<td>" + unitName + "</td>";// 所属单位
						opStr += " <th>" + duty + "</th>";// 职务

						opStr += "<td>";

						opStr += "<input type='hidden' value=" + employeeId	+ ">";// 隐藏域，隐藏一个职工id employeeId
						opStr += "<input type='hidden' value='initData'/>";// 隐藏域，隐藏式操作左边的树的还是顶部的分页条的"&empOutphone="+empOutphone+
						opStr += "<input type='hidden' value=" + unitName + ">";// 隐藏域，隐藏部门名称
						
						opStr += "<a href='javascript:void(0)' onclick='detailInBtn(this)'>详情</a>";
						opStr += "</td>";

						opStr += "</tr>";
					}
					$("#tbody").append(opStr);

					
					var count = data.count;// 总记录数
					var curPage = data.curPage;// 当前页页号
					var curTotal = data.curTotal;// 每页显示的记录数
					
					// 参数1：总记录数 参数2：当前页页号 参数3：每页显示的记录条数
					queryFy4(count, curPage, curTotal);
				},
				error : function() {
					alert("error");
				}
			});
}*/

// 与左侧的树相关的，点击左侧的树之后，把其及其旗下的部门的员工显示出来

// 与查看详情相关的方法
function detailInBtn(obj) {
	// 向隐藏的时间文本框赋值
	$("#q_starttime").val($(".query_dep_starttime").val());
	$("#q_endtime").val($(".query_dep_endtime").val());
	$("#detailemployeeId").val($(obj).parents('td').children('input').eq(0).val());// 职工id
	$("#detailunitName").val($(obj).parents('td').children('input').eq(2).val());// 部门名称
	$("#detail_breakInfoType").val($("#el_breakType").val());
	$("#detailInForm").submit();

}

// 新版的分页条(这个分页条是左边的树专用的啊) start
// 参数1：总记录数 参数2：当前页页号 参数3：每页显示的记录条数
function queryFy(resultCount, currentPage, currentTotal) {
	// 分页栏 start
	$('#paginationIDU').pagination({
			// 组件属性
			"total" : resultCount,// 数字 当分页建立时设置记录的总数量 1
			"pageSize" : currentTotal,// 数字 每一页显示的数量 10
			"pageNumber" : parseInt(currentPage),// 数字 当分页建立时，显示的页数 1
			"pageList" : [ 8, 15, 20 ],// 数组 用户可以修改每一页的大小，
			// 功能
			"layout" : [ 'list', 'sep', 'first', 'prev', 'manual', 'next','last', 'links' ],
			"onSelectPage" : function(pageNumber, b) {
				// 为两个隐藏域赋值 当前页页号 每页显示的记录数
				$("#yeHao").val(pageNumber),// 当前页页号
				$("#jiLuShu").val(b)// 每页显示的记录数
				// 执行左边的树的查询
				leftBtn();
			}
		});
}
// 新版的分页条(最底部的那个) end

// 新版的分页条(这个分页条是左边的树与分页条件绑定之后专用的 记住：这个是点击黑名单为"是"的情况) start
// 参数1：总记录数 参数2：当前页页号 参数3：每页显示的记录条数
function queryFy3(resultCount, currentPage, currentTotal) {
	// 分页栏 start
	$('#paginationIDU').pagination({
		// 组件属性
		"total" : resultCount,// 数字 当分页建立时设置记录的总数量 1
		"pageSize" : currentTotal,// 数字 每一页显示的数量 10
		"pageNumber" : parseInt(currentPage),// 数字 当分页建立时，显示的页数 1
		"pageList" : [ 8, 15, 20 ],// 数组 用户可以修改每一页的大小，
		// 功能
		"layout" : [ 'list', 'sep', 'first', 'prev', 'manual', 'next','last', 'links' ],
		"onSelectPage" : function(pageNumber, b) {
			$("#yeHao").val(pageNumber),// 当前页页号
			$("#jiLuShu").val(b)// 每页显示的记录数
			findSaveBtn();
		}
	});
}
// 新版的分页条(最底部的那个) end

// 新版的分页条(用于页面初始化) start
// 参数1：总记录数 参数2：当前页页号 参数3：每页显示的记录条数
/*function queryFy4(resultCount, currentPage, currentTotal) {
	// 分页栏 start
	$('#paginationIDU').pagination({
		// 组件属性
		"total" : resultCount,// 数字 当分页建立时设置记录的总数量 1
		"pageSize" : currentTotal,// 数字 每一页显示的数量 10
		"pageNumber" : parseInt(currentPage),// 数字 当分页建立时，显示的页数 1
		"pageList" : [ 8, 15, 20 ],// 数组 用户可以修改每一页的大小，
		// 功能
		"layout" : [ 'list', 'sep', 'first', 'prev', 'manual', 'next','last', 'links' ],
		"onSelectPage" : function(pageNumber, b) {
			// 设置当前页号
			$("#yeHao").val(pageNumber);
			// 设置每页显示的记录数
			$("#jiLuShu").val(b);
			// 刷新数据
			initData();
		}
	});
}*/
// 新版的分页条(最底部的那个) end

// 点击左侧的树之后的 分页查询
function leftBtn() {
	$.ajax({
		url : contextPath+"/empInbreakrules_clickLeftTreeShowMsg.action",
		data : {
			"departmentid" : $("#departmentidTree").val(),// 部门id
			"curPage" : $("#yeHao").val(),// 当前页页号
			"curTotal" : $("#jiLuShu").val(),// 每页显示的记录数
			"empBreakInfoType":$("#el_breakType").val()
		},
		dataType : "json",
		type : "POST",
		async : true,
		success : function(data) {
			// 每次查询之前都先清空表格中的数据
			$("#tbody tr").remove();
			var opStr = "";
			// alert("总数量"+data.selectLeftToTable.length)
			for (var i = 0; i < data.empInMsgByDepIdLeft.length; i++) {

				var name = data.empInMsgByDepIdLeft[i].name;// 姓名
				var sex = data.empInMsgByDepIdLeft[i].sex;// 性别
				if (sex == "1") {
					sex = "男";
				} else if (sex == "2") {
					sex = "女";
				}
				var idCode = data.empInMsgByDepIdLeft[i].idCode;// 身份证
				var sumBreakScore = data.empInMsgByDepIdLeft[i].sumBreakScore;// 违章总记分
				var phone = data.empInMsgByDepIdLeft[i].phone;// 电话
				var duty = data.empInMsgByDepIdLeft[i].duty;// 职务
				var unitName = data.empInMsgByDepIdLeft[i].departmentName;// 部门名称
				var employeeId = data.empInMsgByDepIdLeft[i].employeeId;// 内部员工id
				var departmentid = data.empInMsgByDepIdLeft[i].departmentId;// 部门id
				var blackStatus = data.empInMsgByDepIdLeft[i].status;// 黑名单状态

				opStr += "<tr>";
				// 隐藏域
				opStr += "<input name='el_ycy' type='hidden' value="+ employeeId + ">";// 隐藏域，隐藏一个职工id employeeId
				opStr += "<input name='el_ycy' type='hidden' value='left'>";// 隐藏域，隐藏一个标记，是左侧的树还是顶部的按条件分页查询
				opStr += "<input name='el_ycy' type='hidden' value="+ departmentid + ">";// 隐藏部门id
				opStr += "<input name='el_ycy' type='hidden' value="+ idCode + ">";// 隐藏身份证

				opStr += "<td><input type='radio' name='el_chooseBreakRules' class='el_checks' value="+ phone + "></td>";
				opStr += "<td>" + name + "</td>";// 姓名
				opStr += "<td>" + sex + "</td>";// 性别
				opStr += "<td>" + idCode + "</td>";// 身份证
				opStr += "<td>" + sumBreakScore + "</td>";// 违章记分
				// 为隐藏职工id的隐藏域赋值
				if (blackStatus == "1") {
					opStr += "<td><font color='red'>" + "是"	+ "</font></td>";
				} else {
					if (sumBreakScore >= 12) {
						opStr += "<td><font color='blue'>" + "否"+ "</font></td>";
					} else {
						opStr += "<td>" + "否" + "</td>";
					}
				}

				opStr += "<td>" + unitName + "</td>";// 所属单位
				opStr += " <th>" + duty + "</th>";// 职务

				opStr += "<td>";

				opStr += "<input type='hidden' value=" + employeeId	+ ">";// 隐藏域，隐藏一个职工id employeeId
				opStr += "<input type='hidden' value='left'/>";// 隐藏域，隐藏式操作左边的树的还是顶部的分页条的"&empOutphone="+empOutphone+
				opStr += "<input type='hidden' value=" + unitName + ">";// 隐藏域，隐藏部门名称
				
				opStr += "<a href='javascript:void(0)' onclick='detailInBtn(this)'>详情</a>";
				opStr += "</td>";

				opStr += "</tr>";
			}
			$("#tbody").append(opStr);

			var count = data.count;// 总记录数
			var curPage = data.curPage;// 当前页页号
			var curTotal = data.curTotal;// 每页显示的记录数
			// 参数1：总记录数 参数2：当前页页号 参数3：每页显示的记录条数
			queryFy(count, curPage, curTotal);
		},
		error : function() {
			alert("error");
		}
	});
}





/**
 * 与左边的树绑定之后的点击查询按钮的点击事件  未点击黑名单  和点击了黑名单的情况
 */
function clearPagenum() {
	$("#yeHao").val("1");
	findSaveBtn();
}
function findSaveBtn() {
		$.ajax({
			url : contextPath+"/empInbreakrules_leftTreeAndConditionShowMsg.action",
			data : $("#findForm").serialize(),
			dataType : "json",
			type : "POST",
			async : true,
			success : function(data) {

				$("#tbody tr").remove();
				var opStr = "";
				for (var i = 0; i < data.empInMsgByDepIdLeft.length; i++) {

					var name = data.empInMsgByDepIdLeft[i].name;//姓名
					var sex = data.empInMsgByDepIdLeft[i].sex;//性别
					if (sex == "1") {
						sex = "男";
					} else if (sex == "2") {
						sex = "女";
					}
					var idCode = data.empInMsgByDepIdLeft[i].idCode;//身份证
					var sumBreakScore = data.empInMsgByDepIdLeft[i].sumBreakScore;//违章总记分
					var phone = data.empInMsgByDepIdLeft[i].phone;//电话
					var duty = data.empInMsgByDepIdLeft[i].duty;//职务
					var unitName = data.empInMsgByDepIdLeft[i].departmentName;//部门名称
					var employeeId = data.empInMsgByDepIdLeft[i].employeeId;//内部员工id
					var departmentid = data.empInMsgByDepIdLeft[i].departmentId;//部门id
					var blackStatus = data.empInMsgByDepIdLeft[i].status;//黑名单状态

					opStr += "<tr>";
					//隐藏域
					opStr += "<input name='el_ycy' type='hidden' value="+employeeId+">";//隐藏域，隐藏一个职工id employeeId
					opStr += "<input name='el_ycy' type='hidden' value='top'>";//隐藏域，隐藏一个标记，是左侧的树还是顶部的按条件分页查询
					opStr += "<input name='el_ycy' type='hidden' value="+departmentid+">";//隐藏部门id
					opStr += "<input name='el_ycy' type='hidden' value="+idCode+">";//隐藏身份证

					opStr += "<td><input type='radio' name='el_chooseBreakRules' class='el_checks' value="+phone+"></td>";
					opStr += "<td>" + name+ "</td>";//姓名
					opStr += "<td>" + sex+ "</td>";//性别
					opStr += "<td>"+ idCode+ "</td>";//身份证
					opStr += "<td>"+ sumBreakScore+ "</td>";//违章记分
					//为隐藏职工id的隐藏域赋值
					//为隐藏职工id的隐藏域赋值
					if (blackStatus == "1") {
						opStr += "<td><font color='red'>"+ "是"+ "</font></td>";
					} else {
						if (sumBreakScore >= 12) {
							opStr += "<td><font color='blue'>"+ "否"+ "</font></td>";
						} else {
							opStr += "<td>"+ "否"+ "</td>";
						}
					}

					opStr += "<td>"	+ unitName+ "</td>";//所属单位
					opStr += " <th>" + duty+ "</th>";//职务

					opStr += "<td>";

					opStr += "<input type='hidden' value="+employeeId+">";//隐藏域，隐藏一个职工id employeeId
					opStr += "<input type='hidden' value='top'/>";//隐藏域，隐藏式操作左边的树的还是顶部的分页条的"&empOutphone="+empOutphone+
					opStr += "<input type='hidden' value="+unitName+">";//隐藏域，隐藏部门名称
					
					opStr += "<a href='javascript:void(0)' class='el_delButton' onclick='detailInBtn(this)'>详情</a>";
					opStr += "</td>";

					opStr += "</tr>";
				}
				$("#tbody").append(opStr);

				
				var count = data.count;//总记录数
				var curPage = data.curPage;//当前页页号
				var curTotal = data.curTotal;//每页显示的记录数
				
				//参数1：总记录数   参数2：当前页页号  参数3：每页显示的记录条数
				queryFy3(count, curPage,curTotal);

			},//success回掉函数的括号
			error : function() {
				alert("error");
			}//error的括号
		});//ajax的括号
}//方法的括号


/**
 * 清空按钮的点击事件 
 */
function clearBtn() {
	$("#inpstart2").val("");//清空姓名
	$("#inpend2").val("");//清空身份证
	$("#initName").val("");//清空姓名
	$("#initIdCard").val("");//清空身份证
	$("#el_breakSelect option:first").prop("selected", 'selected');//清空违章记分  
	$(".initsex").attr("checked", false); //清空性别
	$(".initBlack").attr("checked", false);//清空是否黑名单
	//清空时间段
	$("#inpstart2").val("");
	//清空时间段
	$("#inpend2").val("");
	//清空之后让黑名单选项可以选中
	$("#el_blackCheckbox").find("input").attr("disabled", false);
	
	//
	$("#departmentidTree").val("");
}

/**
 * 添加违章信息的保存按钮的点击事件及关联操作 start
 */
//<!-- 添加违章信息的保存按钮的点击事件-->
function addSaveBtn(){
	//添加前的违章总积分
	var addBeforeBreakScore = $("#breakScoreSum").val();
	//alert(addBeforeBreakScore+"添加前")
	
	if($("#breakScoreID").val()>=12){
		//弹出警告的模态框
		$("#addyAlertModel2").modal();
	}else{
		if(parseInt(addBeforeBreakScore)+parseInt($("#breakScoreID").val())>=12){
			alert("该员工的违章记分累计超过12分");
		}
		//如果本次添加的违章记分<12分，就不给警告提示
		addSaveAfterBtn();
	} 
}

/**
 * 给出"正式员工积分12分为厂级下岗，8分为部门内部下岗，"的提醒 的方法
 */
function addAlertMsg(){
	//$("#addEmpID").val(empId);//当天添加记分的员工id
	$.ajax({
		url:contextPath+"/empInbreakrules_getSingleEmplyInBreakScoreSum.action",
		data:{"employeeid":$("#addEmpID").val()},
		dataType:"json",
		type:"POST",
		async:true,
		success:function(data){
			//给出提示
			if((data.result>=8) && (data.result<12)){
				alert("该员工部门内部下岗");
			}else if(data.result>=12){
				alert("该员工厂级下岗");
			}
		},
	});
}

//点击了警告提示框 的确定按钮之后的事件
function addAlertModelBtn2(){
	//执行添加违章记分的后续操作
	addSaveAfterBtn();
}


//进入添加模态框 之后点击确定时的后续操作
function addSaveAfterBtn(){
	//表单校验
	var isNotNull = $("#addForm").validate({
		ignore : [],
			rules : {
				"emplyinBreakrules.empinbreaktime" : "required",//违章时间
				"emplyinBreakrules.empinminusnum" : {//违章记分
					"required":true,
					"isNumber":true
				},
				"emplyinBreakrules.empinbreakcontent" : "required"//违章内容
			},
			messages : {
				"emplyinBreakrules.empinbreaktime" : {//违章时间
					required : "不能为空"
				},//下边与上边对应
				"emplyinBreakrules.empinminusnum" : {//违章记分
					required : "不能为空",
					isNumber:"请输入一个数字"
				},
				"emplyinBreakrules.empinbreakcontent" : {
					required : "不能为空"
				}
			}
		});
	//表单校验结束						
	if (isNotNull.form()) {
		$.ajax({
			url:contextPath+"/empInbreakrules_addEmpInBreakrules.action",
			data:$("#addForm").serialize(),
			dataType:"json",
			type:"POST",
			async:true,
			success:function(data){
				alert(data.result);
				//操作成功之后，再次分页查询数据(当作数据的刷新)
				if($("#allMark").val()=="left"){
					//alert("left")
					//执行左边的树的查询
					leftBtn();
				}else if($("#allMark").val()=="top"){
					//执行顶部的分页的查询
					findSaveBtn();
				}/*else if($("#allMark").val()=="initData"){
					initData();
				}*/
				//记分添加完成之后，给出"正式员工积分12分为厂级下岗，8分为部门内部下岗，"的提醒
				addAlertMsg();
			},
			error:function(){
				alert("添加失败，请从新添加")
			}
		});
		//关闭模态框
		$('#el_addBreakInfo').modal("hide");
	}

}
/**
 * 添加违章信息的保存按钮的点击事件及关联操作 end
 */


/** *****************lixianyuan end ********* */
//查看历史违章信息
function historyBreakInfoFind(){
	var type = $("#el_breakType").val();
	$("#breakInfo_Type").val(type);
	$("#yeHao").val("1");
	findSaveBtn();
}


