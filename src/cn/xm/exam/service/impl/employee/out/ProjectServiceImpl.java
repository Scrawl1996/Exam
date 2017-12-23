package cn.xm.exam.service.impl.employee.out;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.xm.exam.bean.employee.out.Project;
import cn.xm.exam.bean.employee.out.Unit;
import cn.xm.exam.bean.employee.out.UnitExample;
import cn.xm.exam.bean.haul.Haulunit;
import cn.xm.exam.bean.haul.HaulunitExample;
import cn.xm.exam.mapper.employee.out.ProjectMapper;
import cn.xm.exam.mapper.employee.out.UnitMapper;
import cn.xm.exam.mapper.employee.out.custom.ProjectCustomMapper;
import cn.xm.exam.mapper.haul.HaulunitMapper;
import cn.xm.exam.service.employee.out.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectMapper projectMapper;
	@Resource
	private ProjectCustomMapper projectCustomMapper;

	@Resource
	private UnitMapper unitMapper;

	@Resource
	private HaulunitMapper haulunitMapper;

	/**
	 * 添加项目信息
	 */
	@Override
	public int insert(Project record) {
		int result = projectMapper.insert(record);
		if (result > 0) {
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * 根据项目javabean修改项目信息
	 */
	@Override
	public int updateByPrimaryKeySelective(Project record) {
		int result = projectMapper.updateByPrimaryKeySelective(record);
		return result;
	}

	/**
	 * 根据项目id删除项目信息
	 */
	@Override
	public int deleteByPrimaryKey(String projectid) {
		int result = projectMapper.deleteByPrimaryKey(Integer.valueOf(projectid));
		return result;
	}

	/**
	 * 根据项目id查询项目信息
	 */
	@Override
	public Project selectByPrimaryKey(String projectid) {
		Project project = projectMapper.selectByPrimaryKey(Integer.valueOf(projectid));
		if (project == null) {
			return null;
		} else {
			return project;
		}

	}

	/**
	 * /** 用于根据条件进行分页查询
	 * 
	 * @param map
	 *            分页条件=》 项目名称name 项目状态status 当前页页号currentPage
	 *            每页显示的记录数currentTotal
	 * @return 根据分页条件查询的所有项目信息
	 */
	/*
	 * List<Project> findAllProjectByNameStatusFy(Map map);
	 * 
	 *//**
		 * <!-- 根据分页的条件查询一共有多少条记录数 -->
		 * 
		 * @param map
		 *            项目名称name 项目状态status
		 * @return 符合条件的总记录数
		 *//*
		 * int findAllProjectCountByNameStatus(Map map);
		 */

	/**
	 * 用于根据条件进行分页查询
	 * 
	 * @param map
	 *            分页条件=》 项目名称name 项目状态status 当前页页号currentPage
	 *            每页显示的记录数currentTotal
	 * @return 根据分页条件查询的所有项目信息
	 */
	@Override
	public List<Project> findAllProjectByNameStatusFy(Map map) {
		List<Project> projectList = projectCustomMapper.findAllProjectByNameStatusFy(map);
		if (projectList.size() > 0) {
			return projectList;
		} else {
			return null;
		}
	}

	/**
	 * <!-- 根据分页的条件查询一共有多少条记录数 -->
	 * 
	 * @param map
	 *            项目名称name 项目状态status
	 * @return 符合条件的总记录数
	 */
	@Override
	public int findAllProjectCountByNameStatus(Map map) {
		int count = projectCustomMapper.findAllProjectCountByNameStatus(map);
		return count;
	}

	/**
	 * 加载树的service
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findLeftTree() {
		List<Map<String, Object>> leftTree = projectCustomMapper.findLeftTree();
		return leftTree;
	}

	/**
	 * 根据单位id查询单位信息
	 * 
	 * @return
	 */
	@Override
	public Unit findUnitByUnitId(String unitId) {
		Unit unit = projectCustomMapper.findUnitByUnitId(unitId);
		if (unit != null) {
			return unit;
		} else {
			return null;
		}
	}

	/**
	 * 根据单位id查询大修单位表中的信息
	 * 
	 * @param unitid
	 * @return
	 */
	@Override
	public Haulunit findHaulUnitByUnitId(String unitid) {
		Haulunit haulUnit = projectCustomMapper.findHaulUnitByUnitId(unitid);
		if (haulUnit != null) {
			return haulUnit;
		} else {
			return null;
		}
	}

	/**
	 * 根据项目编号获取单位信息 思路： 根据项目id获取单位大修编号， 再根据单位大修编号从单位大修表中获取单位编号，
	 * 再通过获取到的单位编号获取单位表unit中的信息
	 */
	@Override
	public Unit findUnitByProjectId(String projectId) {
		/**
		 * 根据项目id获取单位大修编号， 再根据单位大修编号从单位大修表中获取单位编号， 再通过获取到的单位编号获取单位表unit中的信息
		 */
		// 1、根据项目id获取单位大修编号
		Project selectByPrimaryKey = projectMapper.selectByPrimaryKey(Integer.valueOf(projectId));
		String unitbigid = selectByPrimaryKey.getUnitbigid();// 单位大修编号

		/*
		 * // 2、根据隐患编号删除复查表信息 RechecktableExample rechecktableExample = new
		 * RechecktableExample(); RechecktableExample.Criteria criteria2 =
		 * rechecktableExample.createCriteria();
		 * criteria2.andDangeridEqualTo(id); List<Rechecktable> rechList =
		 * rechecktableMapper.selectByExample(rechecktableExample); if
		 * (rechList.size() > 0) { int countRechDel =
		 * rechecktableMapper.deleteByExample(rechecktableExample); }
		 */
		// 2.1根据大修单位编号获取大修单位信息
		HaulunitExample haulunitExample = new HaulunitExample();
		HaulunitExample.Criteria criteria = haulunitExample.createCriteria();
		// 2.2封装条件
		criteria.andBigidEqualTo(unitbigid);
		List<Haulunit> haulUnitList = haulunitMapper.selectByExample(haulunitExample);
		String unitid = haulUnitList.get(0).getUnitid();// 获取到单位编号

		// 根据单位编号获取单位信息
		UnitExample unitExample = new UnitExample();
		UnitExample.Criteria criteria2 = unitExample.createCriteria();
		// 封装条件
		criteria2.andUnitidEqualTo(unitid);
		List<Unit> unitList = unitMapper.selectByExample(unitExample);
		if (unitList.size() > 0) {
			return unitList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 根据左侧的树的 haulunit 和unit 的id获取旗下对应的所有工程信息
	 */
	@Override
	public List<Map<String, Object>> selProjectByLeftTree(Map<String, Object> map) {
		List<Map<String, Object>> selProjectByLeftTreeList = projectCustomMapper.selProjectByLeftTree(map);
		if (selProjectByLeftTreeList.size() > 0) {
			return selProjectByLeftTreeList;
		} else {
			return null;
		}
	}

	/**
	 * <!-- 根据左侧的树的单位大修id和部门编号获取旗下的工程信息的总条数 -->
	 */
	@Override
	public int selProjectByLeftTreeCount(Map<String, Object> map) {
		int result = projectCustomMapper.selProjectByLeftTreeCount(map);
		if (result > 0) {
			return result;
		} else {
			return 0;
		}
	}

	/**
	 * <!-- 根据工程名称、工程状态 与 左边的树绑定一起进行分页查询 -->
	 */
	@Override
	public List<Map<String, Object>> selProjectByLeftTreeAndFindCondition(Map<String, Object> map) {
		List<Map<String, Object>> selProjectByLeftTreeAndFindCondition = projectCustomMapper
				.selProjectByLeftTreeAndFindCondition(map);
		if (selProjectByLeftTreeAndFindCondition != null) {
			return selProjectByLeftTreeAndFindCondition;
		} else {
			return null;
		}
	}

	/**
	 * <!-- 根据工程名称、工程状态 与 左边的树绑定一起进行分页查询的总条数 -->
	 */
	@Override
	public int selProjectByLeftAndFindConditionCount(Map<String, Object> map) {
		int count = projectCustomMapper.selProjectByLeftAndFindConditionCount(map);
		if (count > 0) {
			return count;
		} else {
			return 0;
		}
	}
	/**
	 * 用于页面加载初始化的数据条数
	 * @return
	 */
	@Override
	public int selInitPageCount() {
		int count = projectCustomMapper.selInitPageCount();
		return count;
	}

	/**
  	 * 用于页面加载初始化的，也就是进入页面的时候加载一些数据出来
  	 */
	@Override
	public List<Map<String, Object>> selInitPage(Map<String, Object> map) {
		List<Map<String, Object>> projectList = projectCustomMapper.selInitPage(map);
		if(projectList!=null){
			return projectList;
		}else{
			return null;
		}
	}
}
