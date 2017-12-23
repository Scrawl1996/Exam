package cn.xm.exam.action.haul;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.xm.exam.bean.haul.Haulinfo;
import cn.xm.exam.service.haul.HaulinfoService;

/**
 * 修改大修Action
 * 
 * @author QiaoLiQiang
 * @time 2017年11月11日下午6:58:48
 */
@Controller
@Scope("prototype")
@SuppressWarnings("all")
public class UpdateHaulAction extends ActionSupport implements ModelDriven<Haulinfo> {
	private Logger logger = Logger.getLogger(UpdateHaulAction.class);// 日志记录器
	private Haulinfo haulinfo = new Haulinfo();
	@Resource
	private HaulinfoService haulinfoService;
	private Map<String, Object> response;

	public String execute() {
		response = new HashMap<String, Object>();
		boolean update_result = false;
		String result = "";
		try {
			update_result = haulinfoService.updateHaulinfoById(haulinfo);
		} catch (SQLException e) {
			response.put("result", "修改失败!");
			logger.error("修改大修基本信息出错!", e);
		}
		result = update_result ? "修改成功!" : "修改失败!";
		response.put("result", result);
		return SUCCESS;
	}

	@Override
	public Haulinfo getModel() {
		// TODO Auto-generated method stub
		return haulinfo;
	}

	// get,set
	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}
}
