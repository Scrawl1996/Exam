package cn.xm.exam.action.safeHat;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class SafeHatAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(SafeHatAction.class);
	private Map<String, Object> response;

	public SafeHatAction() {
		log.info("SafeHatAction init.........");
	}

	public String test1() {
		response = new HashMap();
		response.put("ttt", "tttttt");
		return SUCCESS;
	}
	
	public String test2() {
		response = new HashMap();
		response.put("ttt", "tttttt");
		int i = 1/0;
		return SUCCESS;
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}

}
