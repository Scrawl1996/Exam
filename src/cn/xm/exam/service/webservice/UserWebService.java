package cn.xm.exam.service.webservice;

import java.util.Set;

import javax.jws.WebService;

import cn.xm.exam.bean.system.User;

@WebService
public interface UserWebService {

	/**
	 * 根据用户身份证号码查询用户信息及其角色信息及其权限信息
	 * 
	 * @param useridcard
	 * @return user
	 */
	public User getUserByUseridcard(String useridcard) throws Exception;

	/**
	 * 根据用户编号查询角色code的集合
	 * 
	 * @param userid
	 * @return 角色集合
	 */
	public Set<String> getRoleByUserid(String userid) throws Exception;

}
