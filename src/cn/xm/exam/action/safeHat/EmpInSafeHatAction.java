package cn.xm.exam.action.safeHat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.bean.system.User;
import cn.xm.exam.service.safehat.EmpInSafehatService;

@Controller
@Scope("prototype")
public class EmpInSafeHatAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(EmpInSafeHatAction.class);
	private Map<String, Object> response = new HashMap<>();

	private List<String> safeHatNums;
	private List<String> userIdCards;
	
	private String safeHatNum;
	private String safeHatNumsArr;//逗号分隔的多个安全帽编号

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
}