package cn.xm.exam.service.impl.safehat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctc.wstx.util.StringUtil;

import cn.xm.exam.bean.employee.in.Department;
import cn.xm.exam.bean.employee.in.EmployeeIn;
import cn.xm.exam.bean.safehat.SafehatIn;
import cn.xm.exam.bean.safehat.SafehatInExample;
import cn.xm.exam.bean.system.User;
import cn.xm.exam.mapper.employee.in.DepartmentMapper;
import cn.xm.exam.mapper.employee.in.EmployeeInMapper;
import cn.xm.exam.mapper.employee.in.custom.EmployeeInCustomMapper;
import cn.xm.exam.mapper.safehat.SafehatInMapper;
import cn.xm.exam.service.safehat.EmpInSafehatService;
import cn.xm.exam.utils.UUIDUtil;

@Service
public class EmpInSafehatServiceImpl implements EmpInSafehatService {
	private static final Logger log = LoggerFactory.getLogger(EmpInSafehatServiceImpl.class);

	@Autowired
	private SafehatInMapper safehatInMapper;

	@Autowired
	private EmployeeInMapper employeeInMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private EmployeeInCustomMapper employeeInCustomMapper;

	@Override
	public String getSafehatChangelog(String originSafeHatNum) {
		SafehatInExample example = new SafehatInExample();
		cn.xm.exam.bean.safehat.SafehatInExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andSafehatnumEqualTo(originSafeHatNum);
		List<SafehatIn> hats = safehatInMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(hats)) {
			throw new RuntimeException("没有帽子");
		}
		SafehatIn safehat = hats.get(0);
		log.info(safehat.getChangelog());
		return safehat.getChangelog();
	}

	@Override
	public String getHatUserName(String safeHatNum) {
		// 1.查到帽子
		SafehatInExample example = new SafehatInExample();
		cn.xm.exam.bean.safehat.SafehatInExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andSafehatnumEqualTo(safeHatNum);
		List<SafehatIn> hats = safehatInMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(hats)) {
			throw new RuntimeException("没有帽子");
		}
		SafehatIn safehat = hats.get(0);
		String userempid = safehat.getUserempid();

		// 2.查使用者
		EmployeeIn hatUser = employeeInMapper.selectByPrimaryKey(userempid);
		if (hatUser == null) {
			throw new RuntimeException("帽子使用者已经删除");
		}

		return hatUser.getName() + "(" + hatUser.getIdcode() + ")";
	}

	@Override
	public void deleteSafehat(String originSafeHatNums, User user) {
		for (String originSafeHatNum : originSafeHatNums.split(",")) {
			// 1.查到帽子
			SafehatInExample example = new SafehatInExample();
			cn.xm.exam.bean.safehat.SafehatInExample.Criteria createCriteria = example.createCriteria();
			createCriteria.andSafehatnumEqualTo(originSafeHatNum);
			List<SafehatIn> hats = safehatInMapper.selectByExampleWithBLOBs(example);
			if (CollectionUtils.isEmpty(hats)) {
				continue;
			}
			SafehatIn safehat = hats.get(0);

			// 2.查使用者
			String useridcard = safehat.getUseridcard();
			EmployeeIn hatUser = employeeInMapper.selectByPrimaryKey(safehat.getUserempid());
			if (hatUser != null) {
				hatUser.setSafehatnum("");
				employeeInMapper.updateByPrimaryKeySelective(hatUser);
			}

			// 3.设置安全帽日志且取消记录使用者的信息
			safehat.appendChangeLog(geneChangeLog(user, "回收安全帽", "【回收】"));
			safehat.setUseridcard("");
			safehat.setUserempid("");
			safehatInMapper.updateByPrimaryKeySelective(safehat);
		}
	}

	private String geneChangeLog(User user, String... others) {
		String username = user.getUsername();
		String useridcard = user.getUseridcard();
		String departmentname = user.getDepartmentname();
		String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
		List<String> result = new ArrayList<>();
		result.add(departmentname);
		result.add(username + "(" + useridcard + ")");
		result.add(date);
		for (String other : others) {
			result.add(other);
		}
		return StringUtils.join(result, "-");
	}

	@Override
	public List<Map<String, Object>> getSafehatTaizhang(Map condition) {
		return null;
	}

	@Override
	public void saveSafeHatNums(List<String> safeHatNums, List<String> userIdCards, User user) {
		// 1.保存安全帽表
		for (int i = 0, length_1 = safeHatNums.size(); i < length_1; i++) {
			String userIdCard = userIdCards.get(i);
			String safeHatNum = safeHatNums.get(i);
			createSafeHatAndAllocateToUser(safeHatNum, userIdCard, user);
		}
	}

	/**
	 * 保存安全帽且修改使用者
	 * 
	 * @param safeHatNum
	 * @param userIdCard
	 * @param creator
	 */
	private void createSafeHatAndAllocateToUser(String safeHatNum, String userIdCard, User creator) {
		EmployeeIn hatUser = employeeInCustomMapper.getEmployeeInByIdcode(userIdCard);// 使用者
		if (hatUser == null) {
			return;
		}
		Department userDepartment = departmentMapper.selectByPrimaryKey(hatUser.getDepartmentid());// 使用者部门

		SafehatIn safehat = new SafehatIn();
		String id = UUIDUtil.getUUID2();
		safehat.setId(id);
		safehat.setCreatordepartid(creator.getDepartmentid());
		safehat.setIsDeleted(false);
		safehat.setCreatorfullname(creator.getUsername());
		safehat.setSafehatnum(safeHatNum);
		safehat.setUserempid(hatUser.getEmployeeid());
		safehat.setUseridcard(userIdCard);// 使用者身份证号
		String geneChangeLog = geneChangeLog(creator, "创建了安全帽" + safeHatNum,
				"分配给" + hatUser.getName() + "(" + userDepartment.getDepartmentname() + ")", "【新建】");
		safehat.appendChangeLog(geneChangeLog);
		log.info("geneChangeLog -> {}", geneChangeLog);
		safehatInMapper.insert(safehat);

		if (hatUser != null) {
			hatUser.setSafehatnum(safeHatNum);
			employeeInMapper.updateByPrimaryKeySelective(hatUser);
		}
	}

	@Override
	public void updateSafeHatNumBatch(List<String> safeHatNums, List<String> userIdCards, User user) {

		for (int i = 0, length_1 = safeHatNums.size(); i < length_1; i++) {
			String safeHatNum = safeHatNums.get(i);
			String userIdCard = userIdCards.get(i);
			if (StringUtils.isBlank(safeHatNum) || StringUtils.isBlank(userIdCard)) {
				continue;
			}

			// 查询帽子，查到就换人,查不到就创建帽子并且分配给人
			// 0.新人信息
			EmployeeIn hatUser = employeeInCustomMapper.getEmployeeInByIdcode(userIdCard);// 使用者
			if (hatUser == null) {
				continue;
			}

			// 1.查帽子查不到创建帽子
			SafehatInExample example = new SafehatInExample();
			cn.xm.exam.bean.safehat.SafehatInExample.Criteria createCriteria = example.createCriteria();
			createCriteria.andSafehatnumEqualTo(safeHatNum);
			List<SafehatIn> hats = safehatInMapper.selectByExampleWithBLOBs(example);
			if (CollectionUtils.isEmpty(hats)) {
				log.debug("safehatNum -> {} is not exists!", safeHatNum);
				// 创建帽子并且分配给用户
				createSafeHatAndAllocateToUser(safeHatNum, userIdCard, user);
				continue;
			}

			// 2.查到帽子换人
			SafehatIn safehat = hats.get(0);
			// 2.1取消原来的使用者(将其安全帽编号改为换人信息)
			String oldUserIdCode = safehat.getUseridcard();
			if (StringUtils.isNotBlank(oldUserIdCode)) {
				EmployeeIn oldUser = employeeInCustomMapper.getEmployeeInByIdcode(oldUserIdCode);// 使用者
				StringBuilder oldSafeHatInfo = new StringBuilder(safeHatNum + "【已换人】");
				if (oldUser != null) {
					oldSafeHatInfo.append(
							geneChangeLog(user, "将帽子换给 " + hatUser.getName() + " (" + hatUser.getIdcode() + ")"));
					oldUser.setSafehatnum(oldSafeHatInfo.toString());
					employeeInMapper.updateByPrimaryKeySelective(oldUser);
					// 变更实录设为换人
					safehat.appendChangeLog(geneChangeLog(user, "将帽子从 " + oldUser.getName() + "(" + oldUser.getIdcode()
							+ ")换给 " + hatUser.getName() + "(" + hatUser.getIdcode() + ")", "【换人】"));
				}
			} else {// 变更记录设为分配
				safehat.appendChangeLog(
						geneChangeLog(user, "将帽子分配给" + hatUser.getName() + "(" + hatUser.getIdcode() + ")", "【换人】"));
			}

			safehat.setUseridcard(hatUser.getIdcode());
			safehat.setUserempid(hatUser.getEmployeeid());
			hatUser.setSafehatnum(safeHatNum);

			// 保存帽子信息和使用者信息
			employeeInMapper.updateByPrimaryKeySelective(hatUser);
			safehatInMapper.updateByPrimaryKeySelective(safehat);
		}
	}

}
