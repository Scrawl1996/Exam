package cn.xm.exam.action.employee.out;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class BlackUnitAction extends ActionSupport{
	private static final long serialVersionUID = 1L;

	/**
	 * 添加单位黑名单信息
	 * @return
	 */
	public String addBlackUnitInfo(){
		return SUCCESS;
	}
	
	/**
	 * 修改单位黑名单信息
	 * @return
	 */
	public String updateBlackUnitInfo(){
		return SUCCESS;
		
	}
	
	/**
	 * 删除单位黑名单信息
	 * @return
	 */
	public String deleteBlackUnitInfo(){
		return SUCCESS;
		
	}
	
	/**
	 * 查询单位黑名单信息
	 * @return
	 */
	public String findBlackUnitInfos(){
		return SUCCESS;
		
	}
	
}
