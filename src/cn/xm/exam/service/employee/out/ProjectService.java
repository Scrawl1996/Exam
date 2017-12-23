package cn.xm.exam.service.employee.out;

import java.util.List;
import java.util.Map;

import cn.xm.exam.bean.employee.out.Project;
import cn.xm.exam.bean.employee.out.Unit;
import cn.xm.exam.bean.haul.Haulunit;

public interface ProjectService {
	
	
    /**
     * 添加项目信息
     * @param record 要添加的项目javabean
     * @return 是否添加成功
     */
    int insert(Project record);
    
    /**
     * 根据项目javabean修改项目信息(必须要带上项目的id)
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Project record);
    
    /**
     * 根据项目的主键id删除项目
     * @param projectid
     * @return
     */
    int deleteByPrimaryKey(String projectid);

    
    /**
     * 通过主键id查询项目(工程)信息
     * @param projectid
     * @return 是否添加成功
     */
    Project selectByPrimaryKey(String projectid);
    
    /**
  	 * 用于根据条件进行分页查询
  	 * @param map 分页条件=》  项目名称name  项目状态status   当前页页号currentPage  每页显示的记录数currentTotal
  	 * @return 根据分页条件查询的所有项目信息
  	 */
  	List<Project> findAllProjectByNameStatusFy(Map map);
  	
  	/**
  	 * <!-- 根据分页的条件查询一共有多少条记录数 -->
  	 * @param map  项目名称name  项目状态status
  	 * @return 符合条件的总记录数
  	 */
  	int findAllProjectCountByNameStatus(Map map);
    
  	/**
  	 * 加载树的service
  	 * @return
  	 */
  	List<Map<String,Object>> findLeftTree();

  	
  	/**
  	 * 根据单位id查询单位信息
  	 * @return
  	 */
  	Unit findUnitByUnitId(String unitid);
  	
  	
  	/**
  	 * 根据单位id查询大修单位表中的信息
  	 * @param unitid
  	 * @return
  	 */
  	Haulunit findHaulUnitByUnitId(String unitid);
  	
  	/**
  	 * 根据项目编号获取单位信息
  	 * 思路：
	 * 根据项目id获取单位大修编号，
	 * 再根据单位大修编号从单位大修表中获取单位编号，
	 * 再通过获取到的单位编号获取单位表unit中的信息
	 */
  	Unit findUnitByProjectId(String projectId);
  	
  	/**
  	 * 根据左侧的树的 haulunit 和unit 的id获取旗下对应的所有工程信息
  	 */
  	List<Map<String,Object>> selProjectByLeftTree(Map<String,Object> map);
  	
  	/**
  	 * <!-- 根据左侧的树的单位大修id和部门编号获取旗下的工程信息的总条数 -->
  	 */
	public int selProjectByLeftTreeCount(Map<String, Object> map);
	
	/**
  	 * <!-- 根据工程名称、工程状态 与 左边的树绑定一起进行分页查询 -->
  	 */
  	List<Map<String,Object>> selProjectByLeftTreeAndFindCondition(Map<String,Object>map);
  	
  	/**
  	 * <!-- 根据工程名称、工程状态 与 左边的树绑定一起进行分页查询的总条数 -->
  	 */
  	int selProjectByLeftAndFindConditionCount(Map<String,Object> map);
  	/**
  	 * 用于页面加载初始化的，也就是进入页面的时候加载一些数据出来
  	 */
  	List<Map<String,Object>> selInitPage(Map<String,Object> map);
  	
	/**
  	 * 用于页面加载初始化的数据的总条数
  	 */
  	int selInitPageCount();
  	
}
