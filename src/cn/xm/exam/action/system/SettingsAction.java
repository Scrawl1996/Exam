package cn.xm.exam.action.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.xm.exam.utils.ShiroPermissionUtils;

@Controller
@Scope("prototype")
public class SettingsAction extends ActionSupport {

	/**
	 * serial
	 */
	private static final long serialVersionUID = -5885555441378384728L;
	private static final Logger log = LoggerFactory.getLogger(ShiroPermissionUtils.class);

	public String settings() {
		ShiroPermissionUtils.checkPerissionAny("systemmanager:settings");

		return "settings";
	}

}
