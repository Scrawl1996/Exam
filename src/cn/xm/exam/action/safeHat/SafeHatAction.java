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
import cn.xm.exam.service.safehat.SafehatService;
import cn.xm.exam.utils.DefaultValue;
import cn.xm.exam.utils.ExamSystemUtils;
import cn.xm.exam.utils.ValidateCheck;

@Controller
@Scope("prototype")
public class SafeHatAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(SafeHatAction.class);
	private Map<String, Object> response = new HashMap<>();

	private List<String> haulEmpoutId;
	private List<String> haulEmpoutSafehatNum;
	private List<String> empoutNames;
	private String safeHatPrefix;
	private String unitName;
	private String originSafeHatNum;
	private String newSafeHatNum;

	private String userName;
	private String idCard;
	private String safeHatNum;
	private String currentPage;
	private String currentCount;

	@Autowired
	private SafehatService safehatService;

	public String allocateSafeHatNum() {
		if (CollectionUtils.isEmpty(haulEmpoutId) || CollectionUtils.isEmpty(haulEmpoutSafehatNum)
				|| StringUtils.isBlank(safeHatPrefix) || StringUtils.isBlank(unitName)) {
			log.error("haulEmpoutId or haulEmpoutSafehatNum is empty");
		}

		log.info("haulEmpoutId ->{} ", haulEmpoutId);
		log.info("haulEmpoutSafehatNum ->{} ", haulEmpoutSafehatNum);

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			safehatService.saveSafeHatNums(safeHatPrefix, haulEmpoutId, haulEmpoutSafehatNum, empoutNames, unitName,
					user);
		} catch (Exception e) {
			log.error("allocateSafeHatNum error!", e);
			response.put("msg", "已经存在相同编号的安全帽,请重新输入!");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	public String modifySafeHatNum() {
		if (CollectionUtils.isEmpty(haulEmpoutId) || CollectionUtils.isEmpty(haulEmpoutSafehatNum)
				|| StringUtils.isBlank(safeHatPrefix) || StringUtils.isBlank(unitName)) {
			log.error("haulEmpoutId or haulEmpoutSafehatNum is empty");
		}

		log.info("haulEmpoutId ->{} ", haulEmpoutId);
		log.info("haulEmpoutSafehatNum ->{} ", haulEmpoutSafehatNum);

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			safehatService.saveSafeHatNums(safeHatPrefix, haulEmpoutId, haulEmpoutSafehatNum, empoutNames, unitName,
					user);
		} catch (Exception e) {
			log.error("allocateSafeHatNum error!", e);
			response.put("msg", "已经存在相同编号的安全帽,请重新输入!");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	public String updateSafehatNum() {
		if (StringUtils.isBlank(newSafeHatNum) || StringUtils.isBlank(originSafeHatNum)
				|| StringUtils.isBlank(safeHatPrefix)) {
			log.error("allocateSafeHatNum error!");
			return "json";
		}

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			safehatService.updateSafehatNum(originSafeHatNum, newSafeHatNum, safeHatPrefix, user);
		} catch (Exception e) {
			log.error("updateSafehatNum error", e);
			response.put("msg", "已经存在相同编号的安全帽,请重新输入!");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	public String getSafehatChangelog() {
		String changeLog = "";
		try {
			changeLog = safehatService.getSafehatChangelog(originSafeHatNum);
		} catch (Exception e) {
			log.error("getSafehatChangelog error", e);
		}

		response.put("data", StringUtils.split(changeLog, ","));
		return "json";
	}

	/**
	 * 通过安全帽编号查询正在使用者
	 * 
	 * @return
	 */
	public String getSafehatUserNameBySafehatName() {
		if (StringUtils.isBlank(safeHatPrefix) || StringUtils.isBlank(originSafeHatNum)) {
			response.put("hatUserName", "");
			return "json";
		}

		// 故意转换一次编号，转换失败证明输入的是编号全称
		String safehatNum = "";
		try {
			Integer.valueOf(originSafeHatNum);
			String safeHatNumLength = StringUtils
					.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength), "");
			safehatNum = safeHatPrefix
					+ String.format("%0" + safeHatNumLength + "d", NumberUtils.stringToInt(originSafeHatNum));// 新编号
		} catch (NumberFormatException e1) {
			safehatNum = originSafeHatNum;
		}

		String hatUserName = "";
		try {
			hatUserName = safehatService.getHatUserName(safehatNum);
		} catch (Exception e) {
			log.error("getSafehatChangelog error", e);
			hatUserName = "";
		}

		response.put("hatUserName", hatUserName);
		return "json";
	}

	public String recoverSafehat() {
		// 批量回收，因此originSafeHatNum是逗号分隔的多个安全编号
		if (StringUtils.isBlank(originSafeHatNum)) {
			response.put("msg", "请选择带安全帽的人员");
			return "json";
		}

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			safehatService.deleteSafehat(originSafeHatNum, user);
		} catch (Exception e) {
			log.error("recoverSafehat error", e);
			response.put("msg", "回收失败");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

	public String updateSafeHatNumBatch() {
		if (CollectionUtils.isEmpty(haulEmpoutId) || CollectionUtils.isEmpty(haulEmpoutSafehatNum)) {
			log.error("haulEmpoutId or haulEmpoutSafehatNum is empty");
			response.put("msg", "请输入编号后保存");
			return "json";
		}

		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");
		try {
			safehatService.updateSafeHatNumBatch(safeHatPrefix, haulEmpoutId, haulEmpoutSafehatNum, empoutNames,
					unitName, user);
		} catch (Exception e) {
			log.error("allocateSafeHatNum error!", e);
			response.put("msg", "安全帽换人失败!");
			return "json";
		}

		response.put("msg", "ok");
		return "json";
	}

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
		PageHelper.startPage(pageNum, pageSize, "safehat.safehatnum");
		try {
			results = safehatService.getSafehatTaizhang(condition);
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

	public List<String> getHaulEmpoutId() {
		return haulEmpoutId;
	}

	public void setHaulEmpoutId(List<String> haulEmpoutId) {
		this.haulEmpoutId = haulEmpoutId;
	}

	public List<String> getHaulEmpoutSafehatNum() {
		return haulEmpoutSafehatNum;
	}

	public void setHaulEmpoutSafehatNum(List<String> haulEmpoutSafehatNum) {
		this.haulEmpoutSafehatNum = haulEmpoutSafehatNum;
	}

	public String getSafeHatPrefix() {
		return safeHatPrefix;
	}

	public void setSafeHatPrefix(String safeHatPrefix) {
		this.safeHatPrefix = safeHatPrefix;
	}

	public List<String> getEmpoutNames() {
		return empoutNames;
	}

	public void setEmpoutNames(List<String> empoutNames) {
		this.empoutNames = empoutNames;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOriginSafeHatNum() {
		return originSafeHatNum;
	}

	public void setOriginSafeHatNum(String originSafeHatNum) {
		this.originSafeHatNum = originSafeHatNum;
	}

	public String getNewSafeHatNum() {
		return newSafeHatNum;
	}

	public void setNewSafeHatNum(String newSafeHatNum) {
		this.newSafeHatNum = newSafeHatNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSafeHatNum() {
		return safeHatNum;
	}

	public void setSafeHatNum(String safeHatNum) {
		this.safeHatNum = safeHatNum;
	}

	public SafehatService getSafehatService() {
		return safehatService;
	}

	public void setSafehatService(SafehatService safehatService) {
		this.safehatService = safehatService;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
	}
}