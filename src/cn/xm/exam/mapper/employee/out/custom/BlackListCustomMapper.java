package cn.xm.exam.mapper.employee.out.custom;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BlackListCustomMapper {
	
	/**
	 * 根据条件查询黑名单员工信息
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> getBlackEmployeeListInfo(Map<String,Object> condition) throws SQLException;
	
	/**
	 * 短委员工
	 * 根据员工ID删除当前年的违章信息
	 * @param employeeId
	 * @return
	 * @throws SQLException
	 */
	public int deleteBreakRulesInfo(String employeeId) throws SQLException;
	
	/**
	 * 长委员工
	 * 根据员工ID删除当前年的违章信息
	 * @param employeeId
	 * @return
	 * @throws SQLException
	 */
	public int deleteEmpInBreakRulesInfo(String employeeId) throws SQLException;
	
}
