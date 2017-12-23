package cn.xm.exam.mapper.haul.custom;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.xm.exam.bean.haul.Haulinfo;

/**
 * 大修基本信息mapper
 * 
 * @author QiaoLiQiang
 * @time 2017年11月10日下午12:23:15
 */
public interface HaulinfoCustomMapper {

	/**
	 * 查询满足条件的大修总数
	 * 
	 * @param condition
	 *            条件
	 * @return
	 * @throws SQLException
	 */
	public int getHaulinfoTotalByCondition(Map<String, Object> condition) throws SQLException;

	/**
	 * 组合条件查询大修信息用于分页显示大修
	 * 
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public List<Haulinfo> getHaulinfoslByCondition(Map<String, Object> condition) throws SQLException;

	/**
	 * 获取大修树
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getOverHaulInfoTree() throws SQLException;

	/**
	 * 获取部门树
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getDepartInfoTree() throws SQLException;

	/**
	 * 根据大修ID查询所有的大修部门ID
	 * 
	 * @param haulId
	 * @return
	 * @throws SQLException
	 */
	public List<String> getHaulUnitByHaulid(String haulId) throws SQLException;

	/**
	 * 查询未结束的大修的名字与ID
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getHaulNameAndIdsForExam() throws SQLException;

}
