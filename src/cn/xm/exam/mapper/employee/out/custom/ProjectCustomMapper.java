package cn.xm.exam.mapper.employee.out.custom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.xm.exam.bean.employee.out.Project;
import cn.xm.exam.bean.employee.out.ProjectExample;
import cn.xm.exam.bean.employee.out.Unit;
import cn.xm.exam.bean.haul.Haulunit;

public interface ProjectCustomMapper {
	
    //========lixianyuan  start
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
  	 * 加载左边的树
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
  	 * 根据左侧的树的 haulunit 和unit 的id获取旗下对应的所有工程信息
  	 */
  	List<Map<String,Object>> selProjectByLeftTree(Map<String,Object> map);
  	
  	/**
  	 * <!-- 根据左侧的树的单位大修id和部门编号获取旗下的工程信息的总条数 -->
  	 */
  	int selProjectByLeftTreeCount(Map<String,Object> map);
  	
  	
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
    //========lixianyuan  end
}