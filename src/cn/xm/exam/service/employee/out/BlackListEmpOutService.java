package cn.xm.exam.service.employee.out;

import java.sql.SQLException;
import java.util.Map;

import cn.xm.exam.utils.PageBean;

/**
 * 外部员工黑名单service
 * 
 * @author leilong
 * @time 2018年1月25日下午7:16:00
 */
public interface BlackListEmpOutService {
	
	/**
	 * 分页查询黑名单人员信息
	 * @param currentPage
	 * @param currentCount
	 * @return
	 * @throws SQLException
	 */
	public  PageBean<Map<String,Object>> getBlackEmployeePage(int currentPage,int currentCount)throws SQLException;
	
	/**
	 * 根据黑名单ID删除黑名单信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public  boolean deleteBlackListInfoById(String id) throws SQLException;
}
