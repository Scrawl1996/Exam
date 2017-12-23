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
<title>添加考试</title>
<style>
.error{
	color:red;
}
</style>

<%@ include file="/public/cssJs.jsp"%>

    <!--分页-->
    <%-- <script src="<%=path %>/js/public/page.js"></script> --%>
    <script>
    	/* $(function(){
    		$('#paginationID').pagination({
    	        //			组件属性
    	        "total": 1000,//数字 当分页建立时设置记录的总数量 1
    	        "pageSize": 10,//数字 每一页显示的数量 10
    	        "pageNumber": 1,//数字 当分页建立时，显示的页数 1
    	        "pageList": [10, 20],//数组 用户可以修改每一页的大小，
    	        //功能
    	        "layout": ['list', 'sep', 'first', 'prev', 'manual', 'next', 'last', 'links'],
    	        "onSelectPage": function (pageNumber, b) {
    	            alert("pageNumber=" + pageNumber);
    	            alert("pageSize=" + b);
    	        }
    	    });

    	}) */
    	
   
    	
    	//************左侧的树的信息
    	function leftTree(){
    		$.ajax({
    			url:"${pageContext.request.contextPath}/project_findLeftTree.action",
    			data:{ 
    				"unitbigid":$("#unitBigHual").val(),//单位大修编号
    				"unitid":$("#unitBH").val(),//单位编号
    				"curPage":$("#yeHao").val(),//当前页页号
    				"curTotal":$("#jiLuShu").val()//每页显示的记录数
    				},
    			dataType:"json",
    			type:"POST",
    			async:true,
    			success:function(data){
    				//alert("进入回掉函数")
    				//alert(data.selProjectByLeftTreeList.length+"当前页显示的总条数")
    				
    				//数据显示之前 要先清空表格中的所有数据
					$("#tBody tr").remove();
					var tdStr = "";
					for(var i=0;i<data.projectList.length;i++){
					   /*  private String projectid;
					    private String unitbigid;//单位大修id
					    private String unitid;//所属单位编号
					    private String name;
					    private Date startdate;
					    private Date enddate;
					    private String contactperson;
					    private String phone;
					    private String status;
					    private String description; */
						//工程名称(项目名称)
						var fproName = data.projectList[i].name;
					    //项目id
					    var fproId = data.projectList[i].projectId;
						//开工时间
						var fproStartTime = data.projectList[i].startDate;
						//完工时间
						var fendTime = data.projectList[i].endDate;
						//联系人
						var fContactPerson = data.projectList[i].contactPerson;
						//联系方式
						var fPhone = data.projectList[i].phone;
						//工程状态
						var fStatus = data.projectList[i].status;
						//部门(单位id)名称
						var fUnitName = data.projectList[i].unitId;//部门id
						//部门名称
						var unitName = data.projectList[i].unitname;//部门名称
						//var fUnitName2 = null;
						//
						//通过部门(单位)id获取部门名称
						//$("#findUnitId").val(fUnitName);
						/* $.ajax({
							url:"${pageContext.request.contextPath}/project_selUnitNameByUnitId.action",
							data:{"findUnitId":$("#findUnitId").val()},
							dataType:"json",
							type:"POST",
							async:false,
							success:function(data2){
								$("#findUnitName").val(data2.unitName);//为单位名称的隐藏域赋值
							}
						}); */
						
						tdStr +="<tr>";
						//tdStr +="<td>"+fproId+"</td>";//隐藏一个项目id
						tdStr +="<td class='my'>"+fproName+"</td>";//项目名称
						tdStr +="<td class='my'>"+fproStartTime+"</td>";//开工时间
						tdStr +="<td class='my'>"+fendTime+"</td>";//完工时间
						tdStr +="<td class='my'>"+unitName+"</td>";//单位名称(这里是个单位编号)
						tdStr +="<td class='my'>"+fContactPerson+"</td>";//联系人
						tdStr +="<td class='my'>"+fPhone+"</td>";//联系方式
						tdStr +="<td class='my'>"+fStatus+"</td>";//项目状态
						//隐藏一个项目id
						//tdStr +="<input type='hidden' value="+fproId+"/>";
						tdStr +="<td>";
						//隐藏域，隐藏标记=》左侧的树的操作
						tdStr +="<input type='hidden' value='left'/>"
						tdStr +="<a href='javascript:void(0)' onclick='el_modifyProject(this)'><input type='hidden' value="+fproId+">修改</a>";
						tdStr +="<a class='el_delButton' onClick='delcfm(this)'><input type='hidden' value="+fproId+">删除</a>"
						tdStr +="</td>";
						
						tdStr +="</tr>"
					}
					
					$("#tBody").append(tdStr);
					
					/* //当前页页要显示的数据
					map.put("selProjectByLeftTreeList", selProjectByLeftTreeList);
					//当前页页号
					map.put("curPage", "curPage");
					//每页显示的记录数
					map.put("curTotal", "curTotal");
					//总记录数
					map.put("count", count); */
				     //当前页页号
					var curPage = data.curPage;
					//每页显示的记录数
					var curTotal = data.curTotal;
					//总记录数
					var count = data.count;
					//alert("当前页页号"+curPage+" 每页显示的记录数"+curTotal+"  总记录数"+count) 
					//参数1：总记录数   参数2：当前页页号  参数3：每页显示的记录条数
			        queryFy(count,curPage,curTotal);
    				
    			},
    			error:function(){
    				alert("查询失败，请从新查询")
    			}
    		});  
    		
    	}
    	
    	
    	//************左侧的树的信息  end
    	
    	
    	//这个是左侧的树专用的分页条
    	//新版的分页条(最底部的那个)  start
    	//参数1：总记录数   参数2：当前页页号  参数3：每页显示的记录条数
    	function queryFy(resultCount,currentPage,currentTotal){
    		//分页栏  start
    	    $('#paginationID').pagination({
    	    	 //			组件属性
    	        "total":resultCount,//数字 当分页建立时设置记录的总数量 1
    	        "pageSize":currentTotal ,//数字 每一页显示的数量 10
    	        "pageNumber": currentPage,//数字 当分页建立时，显示的页数 1
    	        "pageList": [10,20],//数组 用户可以修改每一页的大小，   
    	        //功能
    	        "layout": ['list', 'sep', 'first', 'prev', 'manual', 'next', 'last', 'links'],
    	        "onSelectPage": function (pageNumber, b) {
    	         //alert("pageNumber=" + pageNumber);//当前页页号
    	        // alert("pageSize=" + b);//每页显示的记录数
    	         //为两个隐藏域赋值  当前页页号   每页显示的记录数
    	         $("#yeHao").val(pageNumber);//当前页页号
    	         $("#jiLuShu").val(b);//每页显示的记录数
    	         
    	         //直接调用分页查询的方法
    	         leftTree();
            	}
        	});
    	}
    	//新版的分页条(最底部的那个) end
    	
    	
    	
    	//按工程名称、工程状态 与 左侧的树绑定之后 分页查询用的分页条
    	//新版的分页条(最底部的那个)  start
    	//参数1：总记录数   参数2：当前页页号  参数3：每页显示的记录条数
    	function queryFy2(resultCount,currentPage,currentTotal){
    		//分页栏  start
    	    $('#paginationID').pagination({
    	    	 //			组件属性
    	        "total":resultCount,//数字 当分页建立时设置记录的总数量 1
    	        "pageSize":currentTotal ,//数字 每一页显示的数量 10
    	        "pageNumber": currentPage,//数字 当分页建立时，显示的页数 1
    	        "pageList": [3,10,20],//数组 用户可以修改每一页的大小，   
    	        //功能
    	        "layout": ['list', 'sep', 'first', 'prev', 'manual', 'next', 'last', 'links'],
    	        "onSelectPage": function (pageNumber, b) {
    	         //alert("pageNumber=" + pageNumber);//当前页页号
    	        // alert("pageSize=" + b);//每页显示的记录数
    	         //为两个隐藏域赋值  当前页页号   每页显示的记录数
    	         $("#yeHao").val(pageNumber);//当前页页号
    	         $("#jiLuShu").val(b);//每页显示的记录数
    	         
    	         //直接调用分页查询的方法
    	         findBtn();
            	}
        	});
    	}
    	//新版的分页条(最底部的那个) end
    	
    	
    	 //新版的分页条(最底部的那个)  start  初始化页面数据用的
    	//参数1：总记录数   参数2：当前页页号  参数3：每页显示的记录条数
    	function queryFy4(resultCount,currentPage,currentTotal){
    		//分页栏  start
    	    $('#paginationID').pagination({
    	    	 //			组件属性
    	        "total":resultCount,//数字 当分页建立时设置记录的总数量 1
    	        "pageSize":currentTotal ,//数字 每一页显示的数量 10
    	        "pageNumber": currentPage,//数字 当分页建立时，显示的页数 1
    	        "pageList": [3,10,20],//数组 用户可以修改每一页的大小，   
    	        //功能
    	        "layout": ['list', 'sep', 'first', 'prev', 'manual', 'next', 'last', 'links'],
    	        "onSelectPage": function (pageNumber, b) {
    	         //alert("pageNumber=" + pageNumber);//当前页页号
    	        // alert("pageSize=" + b);//每页显示的记录数
    	         //为两个隐藏域赋值  当前页页号   每页显示的记录数
    	         $("#yeHao").val(pageNumber);//当前页页号
    	         $("#jiLuShu").val(b);//每页显示的记录数
    	         
    	         //直接调用分页查询的方法
    	        initPage();
            	}
        	});
    	}
    	//新版的分页条(最底部的那个) end
    	
    	
    	
    </script> 
    
   <%--  <!--左边的树-->
    <script src="<%=path %>/js/public/tree.js"></script> --%>

    <script src="<%=path %>/js/outDepart/projectManage.js"></script>
    <link rel="stylesheet" href="<%=path %>/css/outDepart/projectManage.css">
    
    <link rel="stylesheet" href="<%=path %>/css/outDepart/outdepartTree.css">
    <!--验证-->
	<script src="${pageContext.request.contextPath }/controls/validate/jquery.validate.js"></script>
	<script src="${pageContext.request.contextPath }/controls/validate/messages_zh.js"></script> 
 
</head>
<body>

	<!-- 隐藏域  start  -->
	
	
	
	<!-- 隐藏左侧选中的树的名字 -->
	<input type="hidden" id="leftTreeName" value=""/>
	
	
    
    <!-- 表单初始化用的单位编号 -->
    <input id="unitID" type="hidden" value="">
	
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
        <div class="panel-heading"><span>外来单位管理</span><span>>工程管理</span></div>
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
                    	<!-- 注意：当前页页号和每页显示的记录数都用同一个隐藏域， -->
                    	<!-- 隐藏当前页页号 -->
						<input type="hidden" id="yeHao" name="curpage" value="1"/>
						<!-- 隐藏每页显示的记录数 -->
						<input type="hidden" id="jiLuShu" name="curtotal" value="10"/>
                    
                    	<!-- 左侧的树、按条件查询的时候都要用到这两个 -->
						<!-- 隐藏域  隐藏单位大修编号 -->
					    <input id="unitBigHual" type="hidden" name="unitbigid"  value=""/>
					    <!-- 隐藏域  隐藏单位(部门)编号(所属单位的单位编号) -->
					    <input id="unitBH" type="hidden" name="unitid"  value=""/>
					    
                    	
                    <!-- mapTreeAndCondition.put("unitbigid", "10000");//大修单位编号
		mapTreeAndCondition.put("name", "");//工程名称
		mapTreeAndCondition.put("status", "完");//工程状态
		mapTreeAndCondition.put("unitid", "");//单位(部门id)id
		mapTreeAndCondition.put("curPage", curPage);//当前页页号
		mapTreeAndCondition.put("curTotal", curTotal);//每页显示的记录数 -->
                        <div class="row">
                            <div class="col-md-3 el_qlmQuery">
                                <div class="input-group" role="toolbar">
                                    <span class="el_spans">工程名称：</span>
                                    <input id="findProName" type="text" class="form-control" name="project.name" value=""/>
                                </div>
                            </div>

                            <div class="col-md-3 el_qlmQuery el_qlmQuery2" style="width:290px;">
                                <div class="input-group" role="toolbar">
                                    <span class="el_spans">工程状态：</span>
                                    <label class="el_radioBox">
                             			<input type="radio" name="project.status" class="myStatus" value="完工"> 完工</label>
                                    <label class=""><input type="radio" name="project.status" class="myStatus" value="未开始"> 未开始</label>
                                    <label class=""><input type="radio" name="project.status" class="myStatus" value="进行中"> 进行中</label>
                                </div>
                            </div>

                        </div>

                        <!--提交查询按钮-->
                        <button type="button" onclick="findBtn()" class="btn  btn-primary el_queryButton btn-sm " >查询</button>
                        <button type="button" onclick="clearBtn()" class="btn btn-default el_queryButton btn-sm"  style="right:7px;">清空</button>
                    </form>
                    <!-- 清空按钮的点击事件 -->
                    <script type="text/javascript">
                    	function clearBtn(){
                    		//alert("进入清空按钮的点击事件")
                    		$("#findProName").val("");
                    		$(".myStatus").attr("checked",false); 
                    	}
                    
                    </script>
					<!-- 隐藏一个部门(单位)id -->
					<input id="findUnitId" type="hidden" value=""/>
					<!-- 隐藏一个查询到的单位名称 -->
					<input id="findUnitName" type="hidden" value=""/>
					<!-- 查询按钮的点击事件   按条件分页查询   这个还有问题，应该和左侧的大修或者部门绑定？？？？？？？？ -->
					<script type="text/javascript">
						function findBtn(){
							//alert("工程名称："+$(".findProName").val())
							//alert("当前选中的状态"+$(".Findstatus checked").val())
							if($("#unitBigHual").val()==""){
								alert("请先选中大修或者部门")
							}else{
								$.ajax({
									url:"${pageContext.request.contextPath}/project_leftTreeAndConditionFy.action",
									data:$("#findForm").serialize(),
									dataType:"json",
									type:"POST",
									async:true,
									success:function(data){
										//alert("进入回掉函数")
										//数据显示之前 要先清空表格中的所有数据
										$("#tBody tr").remove();
										var tdStr = "";
										for(var i=0;i<data.projectList.length;i++){
										   /*  private String projectid;
										    private String unitbigid;//单位大修id
										    private String unitid;//所属单位编号
										    private String name;
										    private Date startdate;
										    private Date enddate;
										    private String contactperson;
										    private String phone;
										    private String status;
										    private String description; */
											//工程名称(项目名称)
											var fproName = data.projectList[i].name;
										    //项目id
										    var fproId = data.projectList[i].projectId;
											//开工时间
											var fproStartTime = data.projectList[i].startDate;
											//完工时间
											var fendTime = data.projectList[i].endDate;
											//联系人
											var fContactPerson = data.projectList[i].contactPerson;
											//联系方式
											var fPhone = data.projectList[i].phone;
											//工程状态
											var fStatus = data.projectList[i].status;
											//部门(单位id)名称
											var fUnitName = data.projectList[i].unitId;//部门id
											//部门名称
											var unitName = data.projectList[i].unitname;//部门名称
											//var fUnitName2 = null;
											//
											//通过部门(单位)id获取部门名称
											//$("#findUnitId").val(fUnitName);
											/* $.ajax({
												url:"${pageContext.request.contextPath}/project_selUnitNameByUnitId.action",
												data:{"findUnitId":$("#findUnitId").val()},
												dataType:"json",
												type:"POST",
												async:false,
												success:function(data2){
													$("#findUnitName").val(data2.unitName);//为单位名称的隐藏域赋值
												}
											}); */
											
											tdStr +="<tr>";
											//tdStr +="<td>"+fproId+"</td>";//隐藏一个项目id
											tdStr +="<td class='my'>"+fproName+"</td>";//项目名称
											tdStr +="<td class='my'>"+fproStartTime+"</td>";//开工时间
											tdStr +="<td class='my'>"+fendTime+"</td>";//完工时间
											tdStr +="<td class='my'>"+unitName+"</td>";//单位名称(这里是个单位编号)
											tdStr +="<td class='my'>"+fContactPerson+"</td>";//联系人
											tdStr +="<td class='my'>"+fPhone+"</td>";//联系方式
											tdStr +="<td class='my'>"+fStatus+"</td>";//项目状态
											//隐藏一个项目id
											//tdStr +="<input type='hidden' value="+fproId+"/>";
											tdStr +="<td>";
											//隐藏一个操作的标记=》是左侧的树还是顶部的按条件查询
											tdStr +="<input type='hidden' value='top'/>"
											tdStr +="<a href='javascript:void(0)' onclick='el_modifyProject(this)'><input type='hidden' value="+fproId+">修改</a>";
											tdStr +="<a class='el_delButton' onClick='delcfm(this)'><input type='hidden' value="+fproId+">删除</a>"
											tdStr +="</td>";
											
											tdStr +="</tr>"
										}
										
										$("#tBody").append(tdStr);
										
										/* //当前页页要显示的数据
										map.put("selProjectByLeftTreeList", selProjectByLeftTreeList);
										//当前页页号
										map.put("curPage", "curPage");
										//每页显示的记录数
										map.put("curTotal", "curTotal");
										//总记录数
										map.put("count", count); */
									     //当前页页号
										var curPage = data.curPage;
										//每页显示的记录数
										var curTotal = data.curTotal;
										//总记录数
										var count = data.count;
										//alert("当前页页号"+curPage+" 每页显示的记录数"+curTotal+"  总记录数"+count) 
										//参数1：总记录数   参数2：当前页页号  参数3：每页显示的记录条数
								        queryFy2(count,curPage,curTotal);
										
									},
									error:function(){
										alert("查询失败，请从新查询")
									}
								});  
								
							}//else的括号
						    
							
						}//findBtn()方法的括号
					
					</script> 
					
					
					
                </div>   <!--结束 查询表单提交-->

                <!--显示内容-->
                <h4 class="el_mainHead">工程信息</h4>
                <div class="panel panel-default el_Mainmain">

                    <!--按钮面板-->
                    <div class="panel-body">

                        <div class="panel panel-default">
                            <div class="panel-body el_MainxiaoMain">

                                <div class="el_topButton">
                                    <!-- 按钮触发模态框1 -->
                                    <div class="col-md-2">
                                        <button class="btn btn-primary" onclick="el_addDictinary()">添加工程
                                        </button>
                                    </div>

                                </div>

                            </div>
                        </div>
						

                        <!--表格
                            内容都提取到json里边
                        -->
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                            <th>工程名称</th>
                            <th>开工时间</th>
                            <th>完工时间</th>
                            <th>负责单位</th>
                            <th>工程负责人</th>
                            <th>联系方式</th>
                            <th>工程状态</th>
                            <th width="130">操作</th>
                            </tr>
                            </thead>
                            <tbody id="tBody">
                            <tr>
                                <td>工程名称</td>
                                <td>开工时间</td>
                                <td>完工时间</td>
                                <td>负责单位也就是部门名称</td>
                                <td>联系人</td>
                                <td>联系方式</td>
                                <td>工程状态</td>
                                <td>
                                    <a href="javascript:void(0)" onclick="el_modifyProject()">修改</a>&nbsp;&nbsp;
                                    <a class="el_delButton" onClick="delcfm(this)">删除</a><br/>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <!--分页-->
                        <div id="paginationID"></div>

                    </div>
                </div>

                <!-- 模态框   信息删除确认 -->
                <div class="modal fade" id="delcfmModel">
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
                
                <input id="delproid" type="hidden" value=""/>
                <!-- 删除的操作  start -->
				<script type="text/javascript">
					/*删除*/
					//点击删除按钮之后弹出模态框
					function delcfm(obj) {
						//获取到当前选中的数据的项目id
						var delProId = $(obj).children("input").val();//当前选中的项目的id
						//alert("该条信息的项目id=>"+delProId)
						$("#delproid").val(delProId);
						
						//获取标记，并未标记的input隐藏域赋值
						$("#allMark").val($(obj).parents("td").children("input").val());
						//alert("标记=》"+$("#allMark").val())
						//打开模态框
					    $('#delcfmModel').modal();
					}
					
					//点击删除的确定按钮的点击事件，将信息删除
					function urlSubmit() {
						//alert("进入确定按钮的点击事件")
						
						$.ajax({
							url:"${pageContext.request.contextPath}/project_delProject.action",
							data:{"proId":$("#delproid").val()},
							dataType:"json",
							type:"POST",
							async:true,
							success:function(data){
								alert(data.result)
								
								//删除成功之后，再次分页查询数据(当作数据的刷新)
								//操作成功之后，再次分页查询数据(当作数据的刷新)
								if($("#allMark").val()=="left"){
									//alert("left")
									leftTree();//左侧的树的分页查询
								}else if($("#allMark").val()=="top"){
									//alert("top")
									//顶部的按条件分页查询
									findBtn();
								}else if($("#allMark").val()=="initPage"){
									//页面初始化的分页
									initPage();
								}
							},
							error:function(){
								alert("删除失败，请从新删除")
							}
						});
						//关闭模态框
						$('#delcfmModel').modal("hide");
						
					    /* var url = $.trim($("#url").val());//获取会话中的隐藏属性URL
					    window.location.href = url; */
					}
				
				</script>
				
				
				<!-- 删除的操作 end -->
				
				
                <!-- 模态框 添加工程信息-->
                <div class="modal fade" id="el_addProject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button><!--关闭符号-->
                                <!--标题-->
                                <h4 class="modal-title" id="myModalLabel">添加工程信息</h4>
                            </div>
                            <form id="addForm">
                                <div class="modal-body">
                                    <span>负责单位：</span>
                                    <div class="el_threeScoreList">
                                        <table class="table table-bordered">
                                            <tr>
                                                <th >单位名称</th>
                                                <th >单位联系人</th>
                                                <th >联系方式</th>
                                                <th >违章记分</th>
                                                <th >单位地址</th>
                                            </tr>
                                            <tr>
                                                <td id="addDefaultDepart">asdf</td><!--单位名称  这个不用再设置了 -->
                                                <td id="proConcatPerson">asdf</td><!-- 联系人 -->
                                                <td id="proPhone">asdf</td><!-- 联系方式 -->
                                                <td id="proBreakScore">asdf</td><!-- 违章记分 -->
                                                <td id="proUnitAddress">asdf</td><!-- 单位地址 -->
                                            </tr>
                                        </table>
                                    </div>
                                    
                                     <!-- 隐藏域  隐藏单位大修编号 -->
								    <input id="bigXiuId" type="hidden" name="project.unitbigid" value=""/>
								    <!-- 隐藏域  隐藏单位编号(所属单位的单位编号) -->
								    <input id="unitBianHao" type="hidden" name="project.unitid" value=""/>
                                   
                                    <br/><br/>
                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">工程名称：</span>
                                        <input id="addInitProName" type="text" class="form-control" name="project.name"/>
                                    </div>

									<div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">开工时间：</span>
                                        <input type="text" class="form-control workinput wicon" id="inpstart0"  name="project.startdate" readonly/>
                                    </div>
                                    
                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">完工时间：</span>
                                        <input type="text" class="form-control workinput wicon" id="inpend"  name="project.enddate" readonly/>
                                    </div>

                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">工程状态：</span>
                                         <input id="addInitStatus" type="text" class="form-control workinput wicon" value="未开始"  name="project.status" readonly />
                                        <!-- <select class="selectpicker form-control" title="请选择" name="project.status">
                                            <option value="">--请选择--</option>
                                            <option value="完工">完工</option>
                                            <option value="未开始">未开始</option>
                                            <option value="进行中">进行中</option>
                                        </select>-->
                                    </div>

                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">工程负责人：</span>
                                        <input id="addInitPerson" type="text" class="form-control" name="project.contactperson"/>
                                    </div>

                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">联系方式：</span>
                                        <input id="addInitPhone" type="text" class="form-control" name="project.phone"/>
                                    </div>
                                    
                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">描述：</span>
                                        <textarea id="addInitContent" class="form-control" rows="3" name="project.description"></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <button type="button" class="btn btn-primary" onclick="saveBtn()">保存</button>
                                </div>
                            </form>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
				
				<!--添加工程  的 保存按钮的点击事件  -->
				<script type="text/javascript">
					function saveBtn(){
						//alert("进入保存按钮")
						//为隐藏域单位大修编号赋值    
						$("#bigXiuId").val($("#unitBigHual").val());
						//为隐藏域单位编号赋值
						$("#unitBianHao").val($("#unitBH").val());
						    
						
						//表单校验
						var isNotNull = $("#addForm").validate({
						ignore : [],
						rules : {
							"project.name" : "required",//工程名称
							"project.startdate" :"required",//开工时间
							"project.enddate" :"required",//完工时间
							"project.status" : {//工程状态
								required : true
							},//验证下拉框的
							"project.contactperson" : "required",//负责人
							"project.phone":{//联系方式
								required:true,
								isPhone:true
							},
							"project.description" : "required",  //描述
						},
						messages : {
							"project.name" : {//工程名称
								required : "不能为空"
							},//下边与上边对应
							"project.startdate" : {//开工时间
								required : "必须选择"
							},
							"project.enddate" : {//完工时间
								required : "必须选择"
							},
							"project.status" : {//工程状态
								required : "必须选择"
							},
							"project.status" : {//负责人
								required : "不能为空"
							},
							"project.phone" : {//联系方式
								required : "不能为空",
								isPhone:"请输入一个有效的联系电话"
							},
							"project.description":{
								required:"不能为空"  //描述
							}
						},
						focusInvalid: false,
						  onkeyup: false, 
						
					});
        			//以上是用于表单校验的
		        	if (isNotNull.form()) {
							$.ajax({
								url:"${pageContext.request.contextPath}/project_addProject.action",
								data:$("#addForm").serialize(),
								dataType:"json",
								type:"POST",
								async:true,
								success:function(data){
									alert(data.result)
									//添加成功之后刷新数据
									//操作成功之后，再次分页查询数据(当作数据的刷新)
									leftTree();//左侧的树的分页查询
								},
							    error:function(){
									alert("添加失败，请从新添加")
								}
							});
							//关闭模态框
							$('#el_addProject').modal('hide');
							//刷新一下页面
							//window.location.reload();
						}
					}
				</script>
				
                <!-- 模态框 修改工程信息-->
                <div class="modal fade" id="el_modifyProject" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button><!--关闭符号-->
                                <!--标题-->
                                <h4 class="modal-title" id="myModalLabel4">修改工程信息</h4>
                            </div>
                            <form id="updateForm">
                                <div class="modal-body">
                                    <div class="el_threeScoreList">
                                        <table  class="table table-bordered">
                                            <caption>负责单位</caption>
                                            <tbody>
                                            	<tr>
                                            	<th>单位名称</th>
                                            	 <th>单位地址</th>
                                              	<th>联系人</th>
                                              	<!--  <th>联系方式</th> -->
                                                <!-- <th>上级单位</th> -->
                                                 <th>违章记分</th>
                                                </tr>
                                                <tr>
                                                	<td id="upUnitName"></td>
                                                	<td id="upUnitAddress"></td>
                                                	<td id="upConcatPerson"></td>
                                                	<!-- <td id="upPhoneNumber"></td> -->
                                                	 <<!-- td id="upUpId"></td> -->
                                                	 <td id="upbreakScore"></td>
                                                </tr>
                                            <!-- <tr>
                                                <td>单位名称</td>
                                                <td id="upUnitName"></td>
                                                <td>单位地址</td>
                                                <td id="upUnitAddress"></td>
                                            </tr>
                                            <tr>
                                                <td>联系人</td>
                                                <td id="upConcatPerson"></td>
                                                <td>联系方式</td>
                                                <td id="upPhoneNumber"></td>
                                            </tr>
                                            <tr>
                                                <td>上级单位</td>
                                                <td id="upUpId"></td>
                                                <td>违章记分</td>
                                                <td id="upbreakScore"></td>
                                            </tr> -->
                                            </tbody>
                                        </table>
                                    </div>
                                    
                                    <!-- 隐藏域，隐藏项目的id -->
                                    <input id="upProId" type="hidden" name="project.projectid" value="">
                                    <br/><br/><br/>
                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">工程名称：</span>
                                        <input id="upProName" type="text" class="form-control" name="project.name"/>
                                    </div>

									<div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">开工时间：</span>
                                        <input  type="text" class="form-control workinput wicon" id="inpstart2"  name="" readonly/>
                                    </div>
                                    
                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">完工时间：</span>
                                        <input  type="text" class="form-control workinput wicon" id="inpend2"  name="" readonly/>
                                    </div>

                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">工程状态：</span>
                                        <!-- <input id="upProName" type="text" class="form-control" name="project.status"/> -->
                                        <label class="el_radioBox"><input type="radio" name="project.status" value="完工">完工</label>
                                        <label class="el_radioBox"><input type="radio" name="project.status"  value="未开始"> 未开始</label>
                                        <label class="el_radioBox"><input type="radio" name="project.status" value="进行中"> 进行中</label> 
                                    </div>

                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">负&nbsp;&nbsp;责&nbsp;人：</span>
                                        <!--<span class="input-group-addon">工程负责人</span>-->
                                        <input id="upPerson" type="text" class="form-control" name="project.contactperson"/>
                                    </div>

                                    <div class="input-group el_modellist" role="toolbar">
                                        <span class="el_spans">联系方式：</span>
                                        <!--<span class="input-group-addon">联系方式</span>-->
                                        <input id="upPhone" type="text" class="form-control" name="project.phone"/>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                	
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <button type="button" onclick="upSaveBtn()" class="btn btn-primary">保存</button>
                                </div>
                            </form>

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
                
                <!-- 隐藏域 隐藏添加、修改、删除操作的是左侧的树还是顶部的按条件查询的标记 -->
                <input id="allMark" type="hidden" value=""/>
                <!-- 点击修改按钮的点击事件 (为模态框初始化) -->
				<script type="text/javascript">
					//将模态框中的数据初始化
					function el_modifyProject(obj) {
						
						//获取是不是左侧的树的操作的标记
						//alert("操作是什么:"+$(obj).parents("td").children("input").val());
						
						//获取标记，并未标记的input隐藏域赋值
						$("#allMark").val($(obj).parents("td").children("input").val());
						/* if($("#allMark").val()=="left"){
							alert("left")
						}else if($("#allMark").val()=="top"){
							alert("top")
						} */
						
						
						
						//获取当前那条数据的项目id，并且将该项目id的值赋给隐藏域id=upProId的input隐藏域
						var initProId = $(obj).children("input").val();//当前选中的项目的id
						
						//alert("项目id"+initProId)
						$("#upProId").val(initProId);
						
						
						//先为修改模态框中的表单初始化
						//alert("有值吗？"+$(obj).parents('tr').children('td').eq(0).text());
						//工程名称
						$("#upProName").val($(obj).parents('tr').children('td').eq(0).text());
						//开工时间
						$("#inpstart2").val($(obj).parents('tr').children('td').eq(1).html());
						//完工时间
						$("#upEndTime").val($(obj).parents('tr').children('td').eq(2).html());
						//工程状态
						//为模态框 --》修改   工程状态 赋值
               			var isUse = $(obj).parents('tr').children('td').eq(6).text();//工程状态
               			$(".el_radioBox").each(function(){
               				var checkValue = $(this).children("input").attr("value");
               				//alert(checkValue);
               				
               				if(isUse == checkValue){
               					$(this).children("input").prop("checked","true");
               				}
               			});
						//负责人
						$("#upPerson").val($(obj).parents('tr').children('td').eq(4).text());
						//联系方式
						$("#upPhone").val($(obj).parents('tr').children('td').eq(5).text()); 
						
						
						//查询单位信息
						$.ajax({
							url:"${pageContext.request.contextPath}/project_findunitByProjectId.action",
							data:{"upProId":$("#upProId").val()},
							dataType:"json",
							type:"POST",
							async:true,
							success:function(data){
								//alert(data.unit)
								
							/* private String unitid;
						    private String upunitid;
						    private String name;
						    private String address;
						    private String contact;
						    private String phone; */
						    
						    //单位名称
						    $("#upUnitName").text(data.unit.name);
						    //单位地址
							$("#upUnitAddress").text(data.unit.address);
							//联系人
							$("#upConcatPerson").text(data.unit.contact);
							//联系方式
							/* $("#upPhoneNumber").text(data.unit.phone); */
							//违章记分
							$("#upbreakScore").text(data.unitminismum);
								
							},
							error:function(){
								alert("修改失败，请从新修改")
							}
						});
						
						
						
						//为修改模态框中的表格初始化
						/* <tr>
                                                <td>单位名称</td>
                                                <td id="upUnitName"></td>
                                                <td>单位地址</td>
                                                <td id="upUnitAddress"></td>
                                            </tr>
                                            <tr>
                                                <td>联系人</td>
                                                <td id="upConcatPerson"></td>
                                                <td>联系方式</td>
                                                <td id="upPhoneNumber"></td>
                                            </tr>
                                            <tr>
                                                <td>违章记分</td>
                                                <td id="upbreakScore"></td>
                                            </tr> */
						//单位名称
						$("#upUnitName").text();
						//单位地址
                        $("#upUnitAddress").text();
						//联系人
                        $("#upConcatPerson").text();
						//联系方式
                        $("#upUnitName").text();
						//上级单位
                       // $("#upUpId").text();
						//违章记分
                        $("#upbreakScore").text();
                                            
                                            
                         if(isUse=="完工"){
                        	 alert("已经完工的不能修改！")
                         }else{
                        	 $('#el_modifyProject').modal();
                         }                   
					}
					/* function el_modifyProject(){
						
						//根据单位编号查询单位信息，将单位信息初始化到模态框最顶端的位置
						
						
						/* //工程名称
						$("#upProName").val();
						//开工时间
						$("#inpstart2").val();
						//完工时间
						$("#upEndTime").val();
						//工程状态
						
						//负责人
						$("#upPerson").val();
						//联系方式
						$("#upPhone").val(); 
						
						//弹出模态框
						$('#el_modifyProject').modal();
					}*/
					 
					
					//将模态框中修改后的数据提交到后台保存
					function upSaveBtn(){
						
						//表单校验
						var isNotNull = $("#updateForm").validate({
						ignore : [],
						rules : {
							"project.name" : "required",//工程名称
							"project.startdate" :"required",//开工时间
							"project.enddate" :"required",//完工时间
							"project.status" : {//工程状态
								required : true
							},//验证下拉框的
							"project.contactperson" : "required",//负责人
							"project.phone":{//联系方式
								required:true,
								isPhone:true
							},
							"project.description" : "required",  //描述
						},
						messages : {
							"project.name" : {//工程名称
								required : "不能为空"
							},//下边与上边对应
							"project.startdate" : {//开工时间
								required : "必须选择"
							},
							"project.enddate" : {//完工时间
								required : "必须选择"
							},
							"project.status" : {//工程状态
								required : "必须选择"
							},
							"project.status" : {//负责人
								required : "不能为空"
							},
							"project.phone" : {//联系方式
								required : "不能为空",
								isPhone:"请输入一个有效的联系电话"
							},
							"project.description":{
								required:"不能为空"  //描述
							}
						}
	
					});
        			//以上是用于表单校验的
		        	if (isNotNull.form()) {
							$.ajax({
								url:"${pageContext.request.contextPath}/project_updateProject.action",
								data:$("#updateForm").serialize(),
								dataType:"json",
								type:"POST",
								async:true,
								success:function(data){
									alert(data.result)
									
									//操作成功之后，再次分页查询数据(当作数据的刷新)
									if($("#allMark").val()=="left"){
										//alert("left")
										leftTree();//左侧的树的分页查询
									}else if($("#allMark").val()=="top"){
										//alert("top")
										//顶部的按条件分页查询
										findBtn();
									}else if($("#allMark").val()=="initPage"){
										//页面初始化的分页
										initPage();
									}
								},
								error:function(){
									alert("修改失败，请从新修改")
								}
							});
							//关闭模态框
							$('#el_modifyProject').modal("hide");
						}
					}
				</script>
            </div>

        </div>
    </div>
</div>


    </div>
</div>


<!-- 验证电话号码  end -->
<!--放脚-->
<jsp:include page="/view/public/footer.jsp"></jsp:include>
</body>
</html>
