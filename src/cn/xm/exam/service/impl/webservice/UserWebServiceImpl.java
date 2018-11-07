package cn.xm.exam.service.impl.webservice;

import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.springframework.stereotype.Service;

import cn.xm.exam.bean.system.User;
import cn.xm.exam.mapper.system.UserMapper;
import cn.xm.exam.service.webservice.UserWebService;
import cn.xm.exam.utils.ValidateCheck;

@Service
@WebService(targetNamespace = "http://webservice.service.exam.xm.cn")
public class UserWebServiceImpl implements UserWebService {
	@Resource
	private UserMapper userMapper;

	@Override
	public User getUserByUseridcard(String useridcard) {
		if (ValidateCheck.isNull(useridcard)) {
			return null;
		}
		User user = null;
		try {
			user = userMapper.getUserByUseridcard(useridcard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Set<String> getRoleByUserid(String userid) {
		if (ValidateCheck.isNull(userid)) {
			return null;
		}
		Set<String> role = null;
		try {
			role = userMapper.getRoleByUserid(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

}
