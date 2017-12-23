package cn.xm.exam.service.employee.out;

import cn.xm.exam.bean.employee.out.Project;

//完

/**
 * 查询单位的工程
 * 
 * @author QizoLiQiang
 * @time 2017年8月7日下午11:49:58
 */
public interface UnitProjectService {

	/**
	 * 根据单位编号查询出下次要插入的工程的ID(为添加做准备)
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	public String getNextProjectId(String unitId) throws Exception;

	/**
	 * 增加工程信息。在实现层应该先增加工程信息，然后在单位工程中也添加一条记录(刚添加进去的工程id与单位id)。
	 * 
	 * @param project
	 *            需要添加的工程对象
	 * @return 是否添加成功，(影响的行数)
	 * @throws Exception
	 */
	public boolean addProject(Project project) throws Exception;

	/**
	 * 根据id删除工程，实现层应该先删除项目工程中的记录，然后删除工程信息
	 * 
	 * @param id
	 *            要删除的工程的id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteProject(String id) throws Exception;

	/**
	 * 修改工程的信息
	 * 
	 * @param project
	 *            修改后的工程对象
	 * @return
	 * @throws Exception
	 */
	public boolean updateProject(Project project) throws Exception;

	// 查询的放在单位service接口，因为工程通过单位Id查询

}
