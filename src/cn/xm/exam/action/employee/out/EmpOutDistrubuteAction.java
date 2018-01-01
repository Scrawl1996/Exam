package cn.xm.exam.action.employee.out;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.bean.employee.out.Employeeoutdistribute;
import cn.xm.exam.bean.system.User;
import cn.xm.exam.service.employee.out.EmpoutDistributeService;
import cn.xm.exam.utils.DefaultValue;
import cn.xm.exam.utils.PageBean;
import cn.xm.exam.utils.ValidateCheck;

/**
 * 分配员工controller
 * 
 * @author QiaoLiQiang
 * @time 2017年12月30日下午2:41:11
 */
@Controller
@Scope("prototype")
@SuppressWarnings("all")
public class EmpOutDistrubuteAction extends ActionSupport {

	private Logger logger = Logger.getLogger(EmpOutDistrubuteAction.class);// 日志记录器
	private Map<String, Object> response;
	@Resource
	private EmpoutDistributeService empoutDistributeService;

	/**
	 * 跟句当前用户的ID查询大修单位数
	 * 
	 * @return
	 */
	public String getHaulunitTreeByDepartmentId() {
		response = new HashMap<String, Object>();
		List<Map<String, Object>> haulunitTree = null;
		try {
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
			String departmentIdSession = user == null ? null : user.getDepartmentid();// 获取到session部门ID
			haulunitTree = empoutDistributeService.getHaulunitTreeByDepartmentId(departmentIdSession);
		} catch (SQLException e) {
			logger.error("查询分配单位大修树出错", e);
		}
		if (haulunitTree != null) {
			response.put("haulunitTree", haulunitTree);
		}
		return SUCCESS;
	}

	// 大修ID和單位ID
	private String bigId;
	private String unitId;

	/**
	 * 根据单位id和大修id查询单位信息
	 * 
	 * @return
	 */
	public String getUnitInfoByBigIdAndUnitId() {
		response = new HashMap<String, Object>();
		Map<String, Object> unitInfo = null;
		try {
			unitInfo = empoutDistributeService.getUintInfoByHaulIdAndUnitId(bigId, unitId);
		} catch (SQLException e) {
			logger.error("根据大修ID和单位ID查询单位信息失败", e);
		}
		if (unitInfo != null) {
			response.put("unitInfo", unitInfo);
		}
		return SUCCESS;
	}

	private String currentPage;
	private String currentCount;
	private String distributeStatus;
	private String employeeOutName;
	private String employeeOutIdCard;
	private String employeeOutSex;

	/**
	 * 分页查询分配信息
	 * 
	 * @return
	 */
	public String getDistributeInfoWithCondition() {
		response = new HashMap<String, Object>();
		Map condition = generateCondition();
		PageBean<Map<String, Object>> pageBean = null;
		try {
			pageBean = empoutDistributeService.getDistributeInfoWithCondition(Integer.parseInt(currentPage),
					Integer.parseInt(currentCount), condition);
		} catch (NumberFormatException | SQLException e) {
			logger.error("查询员工分配信息出错", e);
		}
		response.put("pageBean", pageBean);
		return SUCCESS;
	}

	private Map generateCondition() {
		Map<String, Object> condition = new HashMap<String, Object>();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		String departmentIdSession = user == null ? null : user.getDepartmentid();// 获取到session部门ID
		if (ValidateCheck.isNotNull(departmentIdSession)) {
			condition.put("departmentId", departmentIdSession);
		}
		if (ValidateCheck.isNotNull(distributeStatus)) {
			condition.put("distributeStatus", distributeStatus);
		}
		if (ValidateCheck.isNotNull(employeeOutIdCard)) {
			condition.put("employeeOutIdCard", employeeOutIdCard);
		}
		if (ValidateCheck.isNotNull(unitId)) {
			condition.put("unitId", unitId);
		}
		if (ValidateCheck.isNotNull(bigId)) {
			condition.put("bigId", bigId);
		}
		if (ValidateCheck.isNotNull(employeeOutName)) {
			condition.put("employeeOutName", employeeOutName);
		}
		if (ValidateCheck.isNotNull(employeeOutSex)) {
			condition.put("employeeOutSex", employeeOutSex);
		}
		if (ValidateCheck.isNull(currentCount)) {
			currentCount = DefaultValue.PAGE_SIZE;
		}
		if (ValidateCheck.isNull(currentPage)) {
			currentPage = "1";
		}
		return condition;
	}

	/**
	 * 查詢分配树
	 * 
	 * @return
	 */
	public String getDepartmentTreeForFenpei() {
		// 获取Session中的用户信息
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		String departmentIdSession = user.getDepartmentid();// 获取部门ID
		String departmentId = "01002".equals(departmentIdSession) ? "01" : departmentIdSession;
		response = new HashMap<String, Object>();
		List<Map<String, Object>> departmentTree = null;
		try {
			departmentTree = empoutDistributeService.getDepartmentTreeForFenpei(departmentId);
		} catch (SQLException e) {
			logger.error("查询分配树出错", e);
		}
		if (departmentTree != null) {
			response.put("departmentTree", departmentTree);
		}
		return SUCCESS;
	}

	/**
	 * 添加分配信息
	 * 
	 * @return
	 */
	private String departmentids;
	private List<Employeeoutdistribute> employeeoutdistributes;

	public String addFenpeiInfo() {
		response = new HashMap<String, Object>();
		String[] departmentId_str = null;
		if (ValidateCheck.isNotNull(departmentids)) {
			departmentId_str = departmentids.split(",");
		}
		String result = "";
		try {
			for (int i = 0; i < departmentId_str.length; i++) {
				for (Employeeoutdistribute employeeoutdistribute : employeeoutdistributes) {
					employeeoutdistribute.setDepartmentid(departmentId_str[i]);
				}
				result = empoutDistributeService.addFenpeiInfoBatch(employeeoutdistributes) ? "添加成功" : "添加失败";
			}
		} catch (SQLException e) {
			result = "添加失败";
			logger.error("添加分配信息失败", e);
		}
		response.put("result", result);
		return SUCCESS;
	}

	/**
	 * 添加分配信息
	 * 
	 * @return
	 */
	private Employeeoutdistribute employeeoutdistribute;

	public String updateFenpeiInfo() {
		response = new HashMap<String, Object>();
		String result = "";
		try {
			result = empoutDistributeService.updateFenpeiInfo(departmentids, employeeoutdistribute) ? "修改成功" : "修改失败";
		} catch (SQLException e) {
			result = "修改失败";
			logger.error("修改分配信息失败", e);
		}
		response.put("result", result);
		return SUCCESS;
	}

	// get,set
	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}

	public String getBigId() {
		return bigId;
	}

	public void setBigId(String bigId) {
		this.bigId = bigId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(String currenCount) {
		this.currentCount = currenCount;
	}

	public List<Employeeoutdistribute> getEmployeeoutdistributes() {
		return employeeoutdistributes;
	}

	public void setEmployeeoutdistributes(List<Employeeoutdistribute> employeeoutdistributes) {
		this.employeeoutdistributes = employeeoutdistributes;
	}

	public String getDepartmentids() {
		return departmentids;
	}

	public void setDepartmentids(String departmentids) {
		this.departmentids = departmentids;
	}

	public Employeeoutdistribute getEmployeeoutdistribute() {
		return employeeoutdistribute;
	}

	public void setEmployeeoutdistribute(Employeeoutdistribute employeeoutdistribute) {
		this.employeeoutdistribute = employeeoutdistribute;
	}

	public String getDistributeStatus() {
		return distributeStatus;
	}

	public void setDistributeStatus(String distributeStatus) {
		this.distributeStatus = distributeStatus;
	}

	public String getEmployeeOutName() {
		return employeeOutName;
	}

	public void setEmployeeOutName(String employeeOutName) {
		this.employeeOutName = employeeOutName;
	}

	public String getEmployeeOutIdCard() {
		return employeeOutIdCard;
	}

	public void setEmployeeOutIdCard(String employeeOutIdCard) {
		this.employeeOutIdCard = employeeOutIdCard;
	}

	public String getEmployeeOutSex() {
		return employeeOutSex;
	}

	public void setEmployeeOutSex(String employeeOutSex) {
		this.employeeOutSex = employeeOutSex;
	}

}
