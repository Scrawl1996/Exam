/***  大修信息管理JS*******/
/**
 * 页面初始化函数
 */
$(function() {
	// 页面初始化查询大修信息
	queryHaulFun();
	// 查询的点击事件
	$("#haulQueryButton").click(function() {
		$("#currentPage").val("");// 清空页数
		queryHaulFun();
	});
	// 修改大修的修改按钮的点击事件
	$("#update_haul_btn").click(function() {
		// ajax异步修改
		updateHaulinfo();
	});
	// 删除大修模态框的确定按钮的点击事件
	$("#deleteHaulBtn").click(function() {
		// ajax异步修改
		deleteHaulinfo();
	});

});

// (创建大修的点击事件)打开添加大修模态框
var el_addOverhaul = function() {
	$(".clearAdd").val("");// 清空模态框
	$("#el_addOverhaul").modal({
		backdrop : 'static',
		keyboard : false
	});
}
/** *S 验证保存大修信息*** */
// 大修创建的提交事件
function addNewsSibmitButton() {
	// addHaulForm是表单的ID
	var isNotNull = $("#addHaulForm").validate({
		ignore : [],
		rules : {
			"bigname" : {
				"required" : true,
				"maxlength" : 50
			},
			"bigbegindate" : {
				required : true
			},
			"bigenddate" : "required",// 验证文本框的。前边是 name 属性
			"bigdescription" : {
				required : true
			}
		},
		messages : {
			"bigname" : {
				"required" : "名字不能为空",
				"maxlength" : "最长不能超过五十个字符"
			},// 发现日期
			"bigbegindate" : {
				"required" : "开始日期不能为空"
			},
			"bigenddate" : {
				"required" : "结束日期不能为空"
			},// 验证文本框的。前边是 name 属性
			"bigdescription" : {
				required : "大修描述不能为空"
			}
		}

	});
	if (isNotNull.form()) {
		if (confirm("确认保存?")) {
			$.ajax({
				url : contextPath + '/addHaul.action',
				type : "POST",
				async : true,
				data : $("#addHaulForm").serialize(),
				dataType : "json",
				success : function(response) {
					alert(response.result)
					// 添加成功重新加载页面
					if (response.result == "添加成功!") {
						window.location.reload();
					}
				},
				error : function() {
					alert("添加大修失败!!!");
				}
			});
		}
	}
}
/** *E 保存大修信息*** */

/** *S 分页查询大修信息*** */
// 查询大修
var queryHaulFun = function() {
	$.ajax({
		url : contextPath + "/haul_queryPageHaul.action",
		data : $("#haulQueryForm").serialize(),
		dataType : "JSON",
		async : true,
		type : "POST",
		success : showHaulTable,
		error : function() {
			alert("查询大修失败!!!");
		}

	});
}
// 大修分页信息
function showHaulTable(response) {
	$("#haulTbody").html("");// 清空表格
	var haulinfos = response.pageBean.productList;// 获取到大修JSON对象
	var currentCount = response.pageBean.currentCount;// 页大小
	var totalCount = response.pageBean.totalCount;// 页总数
	var currentPage = response.pageBean.currentPage;// 当前页
	for (var i = 0, length_1 = haulinfos.length; i < length_1; i++) {
		var tr = '<tr><td>'
				+ '<input type="hidden" value="'
				+ haulinfos[i].bigid
				+ '"/>'
				+ +(parseInt(currentCount) * parseInt(currentPage - 1) + (i + 1))
				+ '</td><td>' + haulinfos[i].bigname + '</td><td>'
				+ haulinfos[i].bigbegindate + '  到  ' + haulinfos[i].bigenddate
				+ '</td><td>' + haulinfos[i].bigstatus + '</td><td>'
				+ haulinfos[i].bigdescription + '</td><td>';

		tr += '<a  title="点击查看具体的检修单位信息" href="' + contextPath
				+ '/view/overhaul/overhaulInfo.jsp?haulId='
				+ haulinfos[i].bigid + '">详情</a>';
		if (hasOperatingJianxiu) {
			tr += '<a href="javascript:void(0)" onclick="el_modifyOverhaul(this)">修改</a>'
			tr += '<a href="javascript:void(0)" onclick="showDeleteModal(this)">删除</a>';
		}
		tr += '</td></tr>';
		// 填充表格
		$("#haulTbody").append(tr);
	}
	// 动态开启分页组件
	page(currentPage, totalCount, currentCount);
}
// 显示分页
function page(currentPage, totalCount, currentCount) {
	// 修改分页的基本属性
	$('#paginationIDU').pagination(
			{
				// 组件属性
				"total" : totalCount,// 数字 当分页建立时设置记录的总数量 1
				"pageSize" : currentCount,// 数字 每一页显示的数量 10
				"pageNumber" : currentPage,// 数字 当分页建立时，显示的页数 1
				"pageList" : [ 8, 15, 20 ],// 数组 用户可以修改每一页的大小，
				// 功能
				"layout" : [ 'list', 'sep', 'first', 'prev', 'manual', 'next',
						'last', 'links' ],
				"onSelectPage" : function(pageNumber, pageSize) {
					$("#currentPage").val(pageNumber);
					$("#currentCount").val(pageSize);
					// 查询大修
					queryHaulFun();
				}
			});
}
/** *E 分页查询大修信息*** */

/** **S 修改大修***** */
// 初始化修改大修模态框信息
function el_modifyOverhaul(obj) {
	var $tds = $(obj).parent().parent().children();// 获取到所有列
	var up_id = $($tds[0]).children("input").val();// 获取隐藏的ID
	var up_name = $($tds[1]).text();// 获取名字
	var up_startTime = $($tds[2]).text().toString().substring(0, 10);// 取开始时间
	var up_endTime = $($tds[2]).text().toString().substring(15);// 获取结束时间
	var up_desc = $($tds[4]).text();// 获取描述
	$("#update_bigid").val(up_id);// 设置隐藏的ID值
	$("#update_haulName").val(up_name);// 设置大写名字
	$("#inpstart1").val(up_startTime);// 设置开始时间
	$("#inpend1").val(up_endTime);// 设置结束时间
	$("#update_desc").val(up_desc);// 设置描述
	$("#el_modifyOverhaul").modal({
		backdrop : 'static',
		keyboard : false
	});
}

// 保存修改的信息
function updateHaulinfo() {
	var isNotNull = $("#modifyHaulForm").validate({
		ignore : [],
		rules : {
			"bigname" : {
				"required" : true,
				"maxlength" : 50
			},
			"bigbegindate" : {
				required : true
			},
			"bigenddate" : "required",// 验证文本框的。前边是 name 属性
			"bigdescription" : {
				required : true
			}
		},
		messages : {
			"bigname" : {
				"required" : "名字不能为空",
				"maxlength" : "最长不能超过五十个字符"
			},// 发现日期
			"bigbegindate" : {
				"required" : "开始日期不能为空"
			},
			"bigenddate" : {
				"required" : "结束日期不能为空"
			},// 验证文本框的。前边是 name 属性
			"bigdescription" : {
				required : "大修描述不能为空"
			}
		}

	});
	if (isNotNull.form()) {
		if (confirm("确认修改?")) {
			// 开始修改.......
			$.ajax({
				url : contextPath + '/updateHaul.action',
				data : $("#modifyHaulForm").serialize(),
				async : true,
				dataType : '',
				type : 'POST',
				success : function(response) {
					alert(response.result)
					// 添加成功重新加载页面
					if (response.result == "修改成功!") {
						window.location.reload();
					}
				},
				error : function() {
					alert("修改大修基本信息出错!!!")
				}
			});
		}
	}

}

/** **E 修改大修***** */
/** **S 删除大修***** */
// 打开询问是否删除的模态框并设置需要删除的大修的ID
function showDeleteModal(obj) {
	var $tds = $(obj).parent().parent().children();// 获取到所有列
	var delete_id = $($tds[0]).children("input").val();// 获取隐藏的ID
	$("#deleteHaulId").val(delete_id);// 将模态框中需要删除的大修的ID设为需要删除的ID
	$("#delcfmOverhaul").modal({
		backdrop : 'static',
		keyboard : false
	});
}
// 传到数据库进行删除
function deleteHaulinfo() {
	var deleteBigid = $("#deleteHaulId").val();
	$.post(contextPath + '/deleteHaul.action', {
		"bigId" : deleteBigid
	}, function(response) {
		if (response != null && response.deleteResult != null) {
			alert(response.deleteResult);
			if (response.deleteResult == "删除成功!") {
				window.location.reload();
			}
		}
	}, 'json');
}

/** **E 删除大修***** */

