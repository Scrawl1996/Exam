package cn.xm.exam.action.safeHat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.bean.system.User;
import cn.xm.exam.service.safehat.SafehatService;
import cn.xm.exam.utils.ExamSystemUtils;

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
}