package cn.xm.exam.action.system;

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
	private String safeHatNumLength;

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5885555441378384728L;
	private static final Logger log = LoggerFactory.getLogger(ShiroPermissionUtils.class);

	public String settings() {
		ShiroPermissionUtils.checkPerissionAny("systemmanager:settings");
		safeHatNumLength = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty("safeHatNumLength"), "3");

		return "settings";
	}

	public String saveSettings() {
		ShiroPermissionUtils.checkPerissionAny("systemmanager:settings");
		if (StringUtils.isNoneBlank(safeHatNumLength)) {
			ExamSystemUtils.setProperty("safeHatNumLength", safeHatNumLength);
		}
		safeHatNumLength = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty("safeHatNumLength"), "3");

		return "settings";
	}

	public String getSafeHatNumLength() {
		return safeHatNumLength;
	}

	public void setSafeHatNumLength(String safeHatNumLength) {
		this.safeHatNumLength = safeHatNumLength;
	}
}
