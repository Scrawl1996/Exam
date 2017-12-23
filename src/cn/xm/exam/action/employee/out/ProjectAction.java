package cn.xm.exam.action.employee.out;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.bean.employee.out.Project;
import cn.xm.exam.bean.employee.out.Unit;
import cn.xm.exam.bean.haul.Haulunit;
import cn.xm.exam.service.employee.out.ProjectService;

@Controller
@Scope("prototype")
public class ProjectAction extends ActionSupport {

	@Resource
	private ProjectService projectService;

	// javabean
	private Project project;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	// 要转成json的Map集合
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	private String unitbigid;// 大修单位编号
	private String unitid;// 部门编号(单位编号)
	private String curpage;// 当前页页号
	private String curtotal;// 每页显示的记录数

	public String getUnitbigid() {
		return unitbigid;
	}

	public void setUnitbigid(String unitbigid) {
		this.unitbigid = unitbigid;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getCurpage() {
		return curpage;
	}

	public void setCurpage(String curpage) {
		this.curpage = curpage;
	}

	public String getCurtotal() {
		return curtotal;
	}

	public void setCurtotal(String curtotal) {
		this.curtotal = curtotal;
	}

	/**
	 * 添加项目(工程)
	 * 
	 * @return 视图
	 * @throws Exception
	 */
	public String addProject() throws Exception {
		System.out.println("进入action");
		// 实例化要转成json的map集合
		map = new LinkedHashMap<String, Object>();

		System.out.println(project);

		int result = projectService.insert(project);
		System.out.println("插入结果" + result);
		if (result > 0) {
			System.out.println("添加成功");
			map.put("result", "添加成功！");
		} else {
			map.put("result", "添加失败");
		}
		return "ok";
	}

	/**
	 * 修改项目(工程)
	 * 
	 * @return 视图
	 * @throws Exception
	 */
	public String updateProject() throws Exception {
		System.out.println("进入修改的action");
		System.out.println(project);
		// 实例化要转成json的map集合
		map = new LinkedHashMap<String, Object>();
		int result = projectService.updateByPrimaryKeySelective(project);
		System.out.println(result);
		if (result == 1) {
			map.put("result", "修改成功!");
		} else {
			map.put("result", "修改失败！");
		}

		return "ok";
	}

	/**
	 * 根据项目id 删除项目(工程)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delProject() throws Exception {
		// 实例化要转成json的map集合
		map = new LinkedHashMap<String, Object>();
		// 接收从jsp页面传过来的项目(工程)id
		String proId = ServletActionContext.getRequest().getParameter("proId");
		System.out.println("项目id：=>" + proId);
		int result = projectService.deleteByPrimaryKey(proId);
		if (result == 1) {
			map.put("result", "删除成功");
		} else {
			map.put("result", "删除失败,请重新操作");
		}

		// struts2会根据视图标记自动将对应的map集合转成json
		return "ok";
	}

	/**
	 * 
	 * 用于根据条件进行分页查询
	 * 
	 * @param map
	 *            分页条件=》 项目名称name 项目状态status 当前页页号currentPage
	 *            每页显示的记录数currentTotal
	 * @return 根据分页条件查询的所有项目信息
	 *
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String findPrjByFyCondition() throws Exception {
	 * System.out.println("进入分页action"); // 实例化要转成json的map集合 map = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * //以下的函数体代码有问题？？？？？？？？？？ //接收从jsp页面传过来的参数 HttpServletRequest request =
	 * ServletActionContext.getRequest(); //项目名称 String proName =
	 * project.getName(); //项目状态 String proStatus = project.getStatus(); //当前页页号
	 * String curPage2 = request.getParameter("curPage"); //每页显示的记录数 String
	 * curTotal2 = request.getParameter("curTotal");
	 * 
	 * 
	 * System.out.println("当前页页号:"+curPage2+"  每页显示的记录数:"+curTotal2);
	 * System.out.println("项目名:"+project.getName()+"  项目状态:"
	 * +project.getStatus());
	 * 
	 * // 用于封装分页条件的Map集合 Map<String, Object> mapFY = new LinkedHashMap<String,
	 * Object>(); // 当前页页号 int curPage = Integer.valueOf(curPage2); // 每页显示的记录数
	 * int curTotal = Integer.valueOf(curTotal2);
	 * 
	 * 
	 * mapFY.put("name", proName);//项目名称 mapFY.put("status",proStatus );//项目状态
	 * 
	 * //用于封装查询当前分页条件的总记录数的条件 Map<String, Object> mapFY2 = new
	 * LinkedHashMap<String, Object>(); mapFY2.put("name",
	 * project.getName());//项目名称 mapFY2.put("status",
	 * project.getStatus());//项目状态 //查询符合当前分页条件的总记录数 int count =
	 * projectService.findAllProjectCountByNameStatus(mapFY2);
	 * 
	 * System.out.println("总记录数:"+count);
	 * 
	 * mapFY.put("curPage", (curPage - 1) * curTotal);// 当前页页号 //
	 * (当前页页号-1)*每页显示的记录数 mapFY.put("curTotal", curTotal); List<Project>
	 * projectList = projectService.findAllProjectByNameStatusFy(mapFY);
	 * 
	 * if(projectList!=null){ //当前页要显示的数据 map.put("projectList", projectList);
	 * //当前页页号 map.put("curPage", curPage); //每页显示的记录数 map.put("curTotal",
	 * curTotal); //符合当前分页条件的总记录数 map.put("count", count); }else{
	 * map.put("result", "没有您要找的数据"); }
	 * 
	 * 
	 * return "ok"; }
	 */

	/**
	 * 加载左侧的树
	 * 
	 * @return
	 */
	public String unloadTree() throws Exception {
		List<Map<String, Object>> leftTree = projectService.findLeftTree();
		map = new HashMap<String, Object>();
		map.put("leftTree", leftTree);
		return "ok";
	}

	/**
	 * 根据单位名称获取单位信息 及 违章记分
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findUnitByUnitName() throws Exception {
		// 实例化要转成json的map集合
		map = new LinkedHashMap<String, Object>();

		// 接收从jsp页面中传过来的单位编号(单位id)
		String unitID = ServletActionContext.getRequest().getParameter("unitID");

		// 根据单位编号查询单位信息
		Unit unit = projectService.findUnitByUnitId(unitID);

		// 获取违章记分 根据单位表中的单位编号 从 单位大修表中获取积分
		// 获取单位编号
		String unitid = unit.getUnitid();
		Haulunit haulUnit = projectService.findHaulUnitByUnitId(unitid);
		// 获取违章记分
		int unitminismum = haulUnit.getUnitminismum();

		if (unit != null) {
			// 封装单位信息
			map.put("unit", unit);
			// 封装违章记分
			map.put("unitminismum", unitminismum);
		} else {
			map.put("result", "没有数据");
		}

		return "ok";
	}

	/**
	 * 获取单位信息 根据项目id获取单位大修编号，再根据单位大修编号从单位大修表中获取单位编号，再通过获取到的单位编号获取单位表unit中的信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findunitByProjectId() throws Exception {
		map = new LinkedHashMap<String, Object>();

		// 获取从jsp页面中传过来的项目id
		String upProId = ServletActionContext.getRequest().getParameter("upProId");
		System.out.println("项目id" + upProId);
		/**
		 * 根据项目id获取单位大修编号， 再根据单位大修编号从单位大修表中获取单位编号， 再通过获取到的单位编号获取单位表unit中的信息
		 */
		// 根据项目id获取单位信息
		Project selectByPrimaryKey = projectService.selectByPrimaryKey(upProId);// 根据项目id获取项目信息
		// 根据项目信息获取单位id
		String unitID = selectByPrimaryKey.getUnitid();
		System.out.println("单位id" + unitID);
		Unit findUnitByUnit = projectService.findUnitByUnitId(unitID);
		System.out.println("单位信息" + findUnitByUnit);
		// Unit unit = projectService.findUnitByProjectId(upProId);
		// System.out.println("单位信息"+unit);

		// 根据项目id获取大修信息中的记分(违章积分)
		// 获取违章记分 根据单位表中的单位编号 从 单位大修表中获取积分
		// 获取单位编号
		String unitid = findUnitByUnit.getUnitid();
		Haulunit haulUnit = projectService.findHaulUnitByUnitId(unitid);
		// 获取违章记分
		int unitminismum = haulUnit.getUnitminismum();

		if (findUnitByUnit != null) {
			map.put("unit", findUnitByUnit);
			map.put("unitminismum", unitminismum);
		} else {
			map.put("result", "没有信息");
		}

		return "ok";
	}

	// 根据单位(部门)id去单位表中找单位名称
	/*
	 * public String selUnitNameByUnitId() throws Exception{ map = new
	 * LinkedHashMap<String,Object>();
	 * 
	 * //获取上传的部门id String unitId =
	 * ServletActionContext.getRequest().getParameter("findUnitId"); Unit unit =
	 * projectService.findUnitByUnitId(unitId); String unitName =
	 * unit.getName();//获取部门名称
	 * 
	 * if(unitName!=null){ map.put("unitName", unitName); }else{
	 * map.put("unitName", " "); }
	 * 
	 * return "ok"; }
	 */

	/**
	 * 左侧的树的 根据大修id和单位id找到旗下的所有工程
	 * 
	 * @return
	 */
	public String findLeftTree() throws Exception {
		System.out.println("进入action");
		map = new LinkedHashMap<String, Object>();

		// 接收大修单位的id
		HttpServletRequest request = ServletActionContext.getRequest();
		String unitbigid = request.getParameter("unitbigid");// 大修单位编号
		String unitid = request.getParameter("unitid");// 部门编号
		String curPage = request.getParameter("curPage");// 当前页页号
		String curTotal = request.getParameter("curTotal");// 每页显示的记录数

		int curpage = (Integer.parseInt(curPage) - 1) * (Integer.parseInt(curTotal));// （当前页页号-1）*每页显示的记录数

		System.out.println("大修单位编号 " + unitbigid + " 部门编号 " + unitid + " 当前页页号 " + curPage + " 每页显示的记录数 " + curTotal);

		if (unitid == null) {
			unitid = "";
		}

		Map<String, Object> mapLeft = new LinkedHashMap<String, Object>();
		/*
		 * '%${unitbigid}%' AND unit.unitId like '%${unitid}%' ORDER BY
		 * breakrules.minusNum DESC LIMIT #{curPage}, #{curTotal}
		 */
		mapLeft.put("unitbigid", unitbigid);// 大修单位的id haulunit
		mapLeft.put("unitid", unitid);// unit 单位id
		mapLeft.put("curPage", curpage);// （当前页页号-1）*每页显示的记录数
		mapLeft.put("curTotal", Integer.parseInt(curTotal));// 每页显示的记录数
		List<Map<String, Object>> selProjectByLeftTreeList = projectService.selProjectByLeftTree(mapLeft);
		// System.out.println("条数"+selProjectByLeftTreeList.size());
		// System.out.println(selProjectByLeftTreeList);

		// 获取总记录数
		Map<String, Object> mapLeftCount = new LinkedHashMap<String, Object>();
		mapLeftCount.put("unitbigid", unitbigid);// 大修单位的id haulunit
		mapLeftCount.put("unitid", unitid);// unit 单位id
		int count = projectService.selProjectByLeftTreeCount(mapLeftCount);
		// System.out.println("总记录数"+count);

		if (selProjectByLeftTreeList != null) {
			// 当前页页要显示的数据
			map.put("projectList", selProjectByLeftTreeList);
			// 当前页页号
			map.put("curPage", curPage);
			// 每页显示的记录数
			map.put("curTotal", curTotal);
			// 总记录数
			map.put("count", count);
		}

		return "ok";
	}

	/**
	 * 左侧的树和查询条件绑定之后的查询结果 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String leftTreeAndConditionFy() throws Exception {
		// 实例化要转成json的map集合
		map = new LinkedHashMap<String, Object>();

		/*
		 * //接收从jsp页面传过来的参数 HttpServletRequest request =
		 * ServletActionContext.getRequest(); //大修单位编号 String unitbigid =
		 * request.getParameter("unitbigid"); //工程名称
		 * 
		 * //工程状态 //单位id(项目id) String unitid = request.getParameter("unitid");
		 * //当前页页号 String curPage2 = request.getParameter("curPage"); //每页显示的记录数
		 * String curTotal2 = request.getParameter("curTotal");
		 */

		/*
		 * private String unitbigid;//大修单位编号 private String unitid;//部门编号(单位编号)
		 * private String curpage;//当前页页号 private String curtotal;//每页显示的记录数
		 */
		String proName = project.getName();// 工程名称
		if (proName == null) {
			proName = "";
		}
		String status = project.getStatus();
		if (status == null) {
			status = "";
		}

		System.out.println("大修单位编号" + unitbigid + " 部门编号" + unitid + " 当前页页号" + curpage + " 每页显示的记录数 " + curtotal
				+ "工程名称:" + proName + " 工程状态：" + status);
		// 下面的注释待会儿要打开
		Map<String, Object> mapTreeAndCondition = new LinkedHashMap<String, Object>();
		int curPage = (Integer.parseInt(curpage) - 1) * (Integer.parseInt(curtotal));
		int curTotal = Integer.parseInt(curtotal);// 每页显示的记录数

		mapTreeAndCondition.put("unitbigid", unitbigid);// 大修单位编号
		mapTreeAndCondition.put("name", proName);// 工程名称
		mapTreeAndCondition.put("status", status);// 工程状态
		mapTreeAndCondition.put("unitid", unitid);// 单位(部门id)id
		mapTreeAndCondition.put("curPage", curPage);// 当前页页号
		mapTreeAndCondition.put("curTotal", curTotal);// 每页显示的记录数

		List<Map<String, Object>> selProjectByLeftTreeAndFindCondition = projectService
				.selProjectByLeftTreeAndFindCondition(mapTreeAndCondition);
		System.out.println("条数" + selProjectByLeftTreeAndFindCondition.size());
		System.out.println(selProjectByLeftTreeAndFindCondition);

		// 获取总记录数
		Map<String, Object> mapTreeAndConditionCount = new LinkedHashMap<String, Object>();
		mapTreeAndConditionCount.put("unitbigid", unitbigid);// 大修单位编号
		mapTreeAndConditionCount.put("name", proName);// 工程名称
		mapTreeAndConditionCount.put("status", status);// 工程状态
		mapTreeAndConditionCount.put("unitid", unitid);// 单位(部门id)id

		int count = projectService.selProjectByLeftAndFindConditionCount(mapTreeAndConditionCount);
		System.out.println("总条数" + count);

		if (selProjectByLeftTreeAndFindCondition != null) {
			// 当前页要显示的数据
			map.put("projectList", selProjectByLeftTreeAndFindCondition);
			// 当前页页号
			map.put("curPage", curpage);
			// 每页显示的记录数
			map.put("curTotal", curTotal);
			// 总记录数
			map.put("count", count);
		}

		return "ok";
	}
	/**
	 * 初始化页面的方法，加入页面的时候加载一些数据出来
	 * @return
	 */
	public String initPage() throws Exception{
		//实例化要转成json的map集合
		map = new LinkedHashMap<String,Object>();
		//接收从jsp页面上传的数据
		HttpServletRequest request = ServletActionContext.getRequest();
		//当前页页号
		String curPage = request.getParameter("curPage");
		//每页显示的记录数
		String curTotal =request.getParameter("curTotal");
		Map<String,Object> map2 = new LinkedHashMap<String,Object>();
		
		map2.put("curPage", (Integer.parseInt(curPage)-1)*(Integer.parseInt(curTotal)));// (当前页页号-1)*每页显示的记录数
		map2.put("curTotal", Integer.parseInt(curTotal));//当前页页号
		List<Map<String, Object>> projectList = projectService.selInitPage(map2);
		
		//获取总记录数
		int count = projectService.selInitPageCount();
		if(projectList!=null){
			map.put("projectList", projectList);//封装当前页要显示的数据
			map.put("curPage", curPage);//封装当前页页号
			map.put("curPage", curTotal);//每页显示的记录数
			map.put("count", count);//封装总记录数
		}
		
		return "ok";
	}
}
