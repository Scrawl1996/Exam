package cn.xm.exam.service.impl.employee.out;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xm.exam.bean.employee.out.Employeeoutdistribute;
import cn.xm.exam.bean.system.User;
import cn.xm.exam.mapper.employee.out.custom.EmpoutDistributeCustomMapper;
import cn.xm.exam.service.employee.out.EmpoutDistributeService;
import cn.xm.exam.utils.PageBean;
import cn.xm.exam.utils.ValidateCheck;

@Service
@SuppressWarnings("all")
public class EmpoutDistributeServiceImpl implements EmpoutDistributeService {
	@Autowired
	private EmpoutDistributeCustomMapper empoutDistributeCustomMapper;

	@Override
	public List<Map<String, Object>> getHaulunitTreeByDepartmentId(String departmentId) throws SQLException {
		return empoutDistributeCustomMapper.getHaulunitTreeByDepartmentId(departmentId);
	}

	@Override
	public PageBean<Map<String, Object>> getDistributeInfoWithCondition(int currentPage, int currentCount,
			Map condition) throws SQLException {
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>();
		// 1.设置当前页
		pageBean.setCurrentPage(currentPage);
		// 2.设置页大小
		pageBean.setCurrentCount(currentCount);
		// 3.查询总数并设置总数
		int totalCount = empoutDistributeCustomMapper.getEmpoutDistributeInfoCountByCondition(condition);
		pageBean.setTotalCount(totalCount);
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);

		// 4.计算索引并请求数据
		int index = (currentPage - 1) * currentCount;
		// 5.将索引放入条件查询数据
		condition.put("index", index);
		condition.put("currentCount", currentCount);
		List<Map<String, Object>> productList = empoutDistributeCustomMapper.getEmpoutDistributeInfo(condition);
		pageBean.setProductList(productList);
		return pageBean;
	}

	@Override
	public Map<String, Object> getUintInfoByHaulIdAndUnitId(String bigId, String unitId) throws SQLException {
		return empoutDistributeCustomMapper.getUintInfoByHaulIdAndUnitId(bigId, unitId);
	}

	@Override
	public List<Map<String, Object>> getDepartmentTreeForFenpei(String departmentId) throws SQLException {
		return empoutDistributeCustomMapper.getDepartmentTreeForFenpei(departmentId);
	}

	@Override
	public boolean addFenpeiInfoBatch(List<Employeeoutdistribute> list) throws SQLException {
		if (list == null) {
			return false;
		}
		for (int i = 0, length_1 = list.size(); i < length_1; i++) {
			if (list.get(i).getDepartmentid().length() == 5 && !"01002".equals(list.get(i).getDepartmentid())) {
				list.get(i).setEmpoutexamstatus("0");
				list.get(i).setEmpouttraingrade("2");
			}
			if (list.get(i).getDepartmentid().length() == 8) {
				list.get(i).setEmpoutexamstatus("0");
				list.get(i).setEmpouttraingrade("3");
			}
		}
		return empoutDistributeCustomMapper.addFenpeiInfoBatch(list) > 0 ? true : false;
	}

	@Override
	public boolean updateFenpeiInfo(String departmentIds, Employeeoutdistribute employeeoutdistribute)
			throws SQLException {
		String[] departmentId_str = null;
		if (ValidateCheck.isNotNull(departmentIds)) {
			departmentId_str = departmentIds.split(",");
		}
		int departmentLength = 0;
		if (departmentId_str != null && departmentId_str.length > 0) {
			departmentLength = departmentId_str[0].length();
		}
		String departmentLevel = departmentLength == 5 ? "2" : "3";
		// 删掉
		Map condition = new HashMap();
		condition.put("haulempid", employeeoutdistribute.getHaulempid());
		condition.put("deplevel", departmentLevel);
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		String departmentIdSession = user == null ? null : user.getDepartmentid();// 获取到session部门ID
		condition.put("departmentId", departmentIdSession);
		empoutDistributeCustomMapper.deleteFenpeiInfoByHaulempIdAndDepartmentLevel(condition);
		// 重新添加
		for (int i = 0; i < departmentId_str.length; i++) {
			employeeoutdistribute.setDepartmentid(departmentId_str[i]);
			List fenpeis = new ArrayList();
			fenpeis.add(employeeoutdistribute);
			this.addFenpeiInfoBatch(fenpeis);
		}
		return true;
	}

}
