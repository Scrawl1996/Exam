package cn.xm.exam.action.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.utils.ExamSystemUtils;
import cn.xm.exam.utils.ShiroPermissionUtils;

@Controller
@Scope("prototype")
public class SettingsAction extends ActionSupport {

	private String safeHatNumLength;// 安全帽长度
	private String physicalStatus;// 身体状况
	private String educateBackground;// 教育背景
	private Map<String, Object> responseMap = new HashMap<String, Object>();// 返回的结果
	private String queryKey;// 需要查询的key的值

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5885555441378384728L;
	private static final Logger log = LoggerFactory.getLogger(ShiroPermissionUtils.class);

	public String settings() {
		ShiroPermissionUtils.checkPerissionAny("systemmanager:settings");
		safeHatNumLength = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength),
				"3");
		physicalStatus = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.physicalStatus),
				"健康,不健康");
		educateBackground = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.educateBackground),
				"大学,硕士");

		return "settings";
	}

	public String saveSettings() {
		ShiroPermissionUtils.checkPerissionAny("systemmanager:settings");

		try {
			if (StringUtils.isNoneBlank(safeHatNumLength)) {
				ExamSystemUtils.setProperty(ExamSystemUtils.safeHatNumLength, safeHatNumLength);
			}
			if (StringUtils.isNoneBlank(safeHatNumLength)) {
				ExamSystemUtils.setProperty(ExamSystemUtils.physicalStatus, physicalStatus);
			}
			if (StringUtils.isNoneBlank(safeHatNumLength)) {
				ExamSystemUtils.setProperty(ExamSystemUtils.educateBackground, educateBackground);
			}
		} catch (Exception e) {
			log.error("saveSettings error", e);
			responseMap.put("msg", "保存失败!");
			return "json";
		}
		responseMap.put("msg", "保存成功!");
		return "json";
	}

	public String getSettingsJSON() {
		try {
			safeHatNumLength = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength),
					"3");
			physicalStatus = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.physicalStatus),
					"健康,不健康");
			educateBackground = StringUtils
					.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.educateBackground), "大学,硕士");
		} catch (Exception e) {
			log.error("getSettingsJSON error");
			return "json";
		}
		responseMap.put("safeHatNumLength", safeHatNumLength);
		responseMap.put("physicalStatus", StringUtils.split(physicalStatus, ","));
		responseMap.put("educateBackground", StringUtils.split(educateBackground, ","));
		return "json";
	}

	public String getSafeHatNumLength() {
		return safeHatNumLength;
	}

	public void setSafeHatNumLength(String safeHatNumLength) {
		this.safeHatNumLength = safeHatNumLength;
	}

	public String getPhysicalStatus() {
		return physicalStatus;
	}

	public void setPhysicalStatus(String physicalStatus) {
		this.physicalStatus = physicalStatus;
	}

	public String getEducateBackground() {
		return educateBackground;
	}

	public void setEducateBackground(String educateBackground) {
		this.educateBackground = educateBackground;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLog() {
		return log;
	}

	public Map getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map responseMap) {
		this.responseMap = responseMap;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
}
