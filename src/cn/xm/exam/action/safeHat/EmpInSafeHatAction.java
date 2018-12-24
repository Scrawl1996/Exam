package cn.xm.exam.action.safeHat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.bean.system.User;
import cn.xm.exam.service.safehat.EmpInSafehatService;
import cn.xm.exam.utils.DefaultValue;
import cn.xm.exam.utils.ValidateCheck;

@Controller
@Scope("prototype")
public class EmpInSafeHatAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(EmpInSafeHatAction.class);
	private Map<String, Object> response = new HashMap<>();

	private List<String> safeHatNums;
	private List<String> userIdCards;

	private String safeHatNum;
	private String newSafeHatNum;
	private String safeHatNumsArr;// 逗号分隔的多个安全帽编号

	@Autowired
	private EmpInSafehatService empInSafehatService;

	public String allocateSafeHatNum() {
		if (CollectionUtils.isEmpty(safeHatNums) || CollectionUtils.isEmpty(userIdCards)) {
			log.error("departmentIds or safeHatNums or userIdCards is empty");
		}

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			empInSafehatService.saveSafeHatNums(safeHatNums, userIdCards, user);
		} catch (Exception e) {
			log.error("allocateSafeHatNum error!", e);
			response.put("msg", "已经存在相同编号的安全帽,请重新输入!");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	public String getSafehatChangelog() {
		String changeLog = "";
		try {
			changeLog = empInSafehatService.getSafehatChangelog(safeHatNum);
		} catch (Exception e) {
			log.error("getSafehatChangelog error", e);
		}

		response.put("data", StringUtils.split(changeLog, ","));
		return "json";
	}

	public String recoverSafehat() {
		// 批量回收，因此originSafeHatNum是逗号分隔的多个安全编号
		if (StringUtils.isBlank(safeHatNumsArr)) {
			response.put("msg", "请选择带安全帽的人员");
			return "json";
		}

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			empInSafehatService.deleteSafehat(safeHatNumsArr, user);
		} catch (Exception e) {
			log.error("recoverSafehat error", e);
			response.put("msg", "回收失败");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	/**
	 * 通过安全帽编号查询正在使用者
	 * 
	 * @return
	 */
	public String getSafehatUserNameBySafehatName() {
		if (StringUtils.isBlank(safeHatNum)) {
			response.put("hatUserName", "");
			return "json";
		}

		String hatUserName = "";
		try {
			hatUserName = empInSafehatService.getHatUserName(safeHatNum);
		} catch (Exception e) {
			log.error("getSafehatChangelog error", e);
			hatUserName = "";
		}

		response.put("hatUserName", hatUserName);
		return "json";
	}

	public String updateSafeHatNumBatch() {
		if (CollectionUtils.isEmpty(safeHatNums) || CollectionUtils.isEmpty(userIdCards)) {
			log.error("departmentIds or safeHatNums or userIdCards is empty");
		}

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			empInSafehatService.updateSafeHatNumBatch(safeHatNums, userIdCards, user);
		} catch (Exception e) {
			log.error("allocateSafeHatNum error!", e);
			response.put("msg", "安全帽换人失败!");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	public String deleteSafeHat() {
		try {
			empInSafehatService.deleteSafeHat(safeHatNum);
		} catch (Exception e) {
			log.error("deleteSafeHat error!", e);
			response.put("msg", "删除失败");
			return "json";
		}

		response.put("msg", "删除成功");
		return "json";
	}

	public String updateSafeNum() {
		try {
			empInSafehatService.updateSafeNum(safeHatNum, newSafeHatNum);
		} catch (Exception e) {
			log.error("updateSafeNum error!", e);
			response.put("msg", "修改失败");
			return "json";
		}

		response.put("msg", "修改成功");
		return "json";
	}

	private String userName;
	private String idCard;
	private String currentPage;
	private String currentCount;
	private String departName;

	public String getSafehatTaizhang() {
		Map condition = new HashMap<>();
		if (ValidateCheck.isNotNull(userName)) {
			condition.put("userName", userName);
		}
		if (ValidateCheck.isNotNull(idCard)) {
			condition.put("idCard", idCard);
		}
		if (ValidateCheck.isNotNull(safeHatNum)) {
			condition.put("safeHatNum", safeHatNum);
		}
		if (ValidateCheck.isNotNull(departName)) {
			condition.put("departName", departName);
		}
		int pageNum = 1;
		if (StringUtils.isNotBlank(currentPage)) { // 如果不为空的话改变当前页号
			pageNum = Integer.parseInt(currentPage);
		}
		int pageSize = NumberUtils.stringToInt(DefaultValue.PAGE_SIZE);
		if (StringUtils.isNotBlank(currentCount)) { // 如果不为空的话改变当前页大小
			pageSize = Integer.parseInt(currentCount);
		}
		// 开始分页
		List<Map<String, Object>> results = null;
		PageHelper.startPage(pageNum, pageSize, "safehat_in.safehatnum");
		try {
			results = empInSafehatService.getSafehatTaizhang(condition);
		} catch (Exception e) {
			log.error("getSafehatTaizhang error", e);
			results = new ArrayList<>();
			return "json";
		}

		if (CollectionUtils.isNotEmpty(results)) {
			disPoseResults(results);
		}
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(results);
		response.put("pageInfo", pageInfo);
		return "json";
	}

	private void disPoseResults(List<Map<String, Object>> results) {
		for (Map map : results) {
			// 处理性别
			if (StringUtils.isNotBlank(MapUtils.getString(map, "sex"))) {
				if ("1".equals(map.get("sex").toString())) {
					map.put("sex", "男");
				}
				if ("2".equals(map.get("sex").toString())) {
					map.put("sex", "女");
				}
			}
			// 处理年龄
			String birthday = MapUtils.getString(map, "birthday");
			if (StringUtils.isNotBlank(birthday)) {
				Date date = null;
				try {
					date = DateUtils.parseDateStrictly(birthday, "yyyy-MM-dd");
				} catch (ParseException e) {
					date = new Date();
				}
				int birhYear = date.getYear();
				int nowYear = (new Date()).getYear();
				map.put("age", (nowYear - birhYear) + 1);
			}
		}
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}

	public List<String> getUserIdCards() {
		return userIdCards;
	}

	public void setUserIdCards(List<String> userIdCards) {
		this.userIdCards = userIdCards;
	}

	public List<String> getSafeHatNums() {
		return safeHatNums;
	}

	public void setSafeHatNums(List<String> safeHatNums) {
		this.safeHatNums = safeHatNums;
	}

	public String getSafeHatNum() {
		return safeHatNum;
	}

	public void setSafeHatNum(String safeHatNum) {
		this.safeHatNum = safeHatNum;
	}

	public String getSafeHatNumsArr() {
		return safeHatNumsArr;
	}

	public void setSafeHatNumsArr(String safeHatNumsArr) {
		this.safeHatNumsArr = safeHatNumsArr;
	}

	public EmpInSafehatService getEmpInSafehatService() {
		return empInSafehatService;
	}

	public void setEmpInSafehatService(EmpInSafehatService empInSafehatService) {
		this.empInSafehatService = empInSafehatService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getNewSafeHatNum() {
		return newSafeHatNum;
	}

	public void setNewSafeHatNum(String newSafeHatNum) {
		this.newSafeHatNum = newSafeHatNum;
	}

	public static Logger getLog() {
		return log;
	}
}