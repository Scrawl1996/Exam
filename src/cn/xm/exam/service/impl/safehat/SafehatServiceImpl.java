package cn.xm.exam.service.impl.safehat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xm.exam.bean.employee.out.EmployeeOut;
import cn.xm.exam.bean.employee.out.EmployeeOutExample;
import cn.xm.exam.bean.haul.Haulemployeeout;
import cn.xm.exam.bean.safehat.Safehat;
import cn.xm.exam.bean.safehat.SafehatExample;
import cn.xm.exam.bean.safehat.SafehatExample.Criteria;
import cn.xm.exam.bean.system.User;
import cn.xm.exam.mapper.employee.out.EmployeeOutMapper;
import cn.xm.exam.mapper.haul.HaulemployeeoutMapper;
import cn.xm.exam.mapper.safehat.SafehatMapper;
import cn.xm.exam.mapper.safehat.custom.SafehatCustomMapper;
import cn.xm.exam.service.safehat.SafehatService;
import cn.xm.exam.utils.ExamSystemUtils;
import cn.xm.exam.utils.UUIDUtil;

@Service
public class SafehatServiceImpl implements SafehatService {
	private static final Logger log = LoggerFactory.getLogger(SafehatServiceImpl.class);

	@Autowired
	private SafehatMapper safehatMapper;

	@Autowired
	private SafehatCustomMapper safehatCustomMapper;
	@Autowired
	private HaulemployeeoutMapper haulemployeeoutMapper;
	@Autowired
	private EmployeeOutMapper employeeOutMapper;

	@Override
	public void saveSafeHatNums(String safeHatPrefix, List<String> haulEmpoutIds, List<String> haulEmpoutSafehatNums,
			List<String> empoutNames, String unitName, User user) {
		// 1.保存安全帽表
		String safeHatNumLength = StringUtils
				.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength), "3");
		for (int i = 0, length_1 = haulEmpoutSafehatNums.size(); i < length_1; i++) {
			String haulEmpoutSafehatNum = haulEmpoutSafehatNums.get(i);
			String haulEmpoutId = haulEmpoutIds.get(i);
			String empoutName = empoutNames.get(i);
			String safehatNum = safeHatPrefix
					+ String.format("%0" + safeHatNumLength + "d", NumberUtils.stringToInt(haulEmpoutSafehatNum));// 最终的编号

			createSafeHatAndAllocateToUser(haulEmpoutId, empoutName, unitName, safehatNum, user);
		}
	}

	/**
	 * 新增帽子并且分配给
	 * 
	 * @param useHaulEmpoutId
	 * @param useEmpoutName
	 * @param useUnitName
	 * @param safehatNum
	 * @param creator
	 */
	private void createSafeHatAndAllocateToUser(String useHaulEmpoutId, String useEmpoutName, String useUnitName,
			String safehatNum, User creator) {
		// 1.获取员工身份证号
		Haulemployeeout empOut = haulemployeeoutMapper.selectByPrimaryKey(useHaulEmpoutId);
		if (empOut == null) {
			return;
		}
		String userIdCard = empOut.getEmpoutidcard();

		// 1.1保存安全帽
		Safehat safehat = new Safehat();
		String id = UUIDUtil.getUUID2();
		safehat.setId(id);
		safehat.setUserhaulempid(useHaulEmpoutId);
		safehat.setCreatordepartid(creator.getDepartmentid());
		safehat.setIsDeleted(false);
		safehat.setCreatorfullname(creator.getUsername());
		safehat.setSafehatnum(safehatNum);
		safehat.setUseridcard(userIdCard);// 使用者身份证号
		String geneChangeLog = geneChangeLog(creator, "创建了安全帽" + safehatNum,
				"分配给" + useEmpoutName + "(" + useUnitName + ")", "【新建】");
		safehat.appendChangeLog(geneChangeLog);
		log.info("geneChangeLog -> {}", geneChangeLog);
		safehatMapper.insert(safehat);

		//
		if (empOut != null) {
			empOut.setSafehatnum(safehatNum);
			haulemployeeoutMapper.updateByPrimaryKeySelective(empOut);
		}
	}

	@Override
	public void updateSafehatNum(String originSafeHatNum, String newSafeHatNum, String safeHatPrefix, User user) {
		// 1.保存安全帽表
		String safeHatNumLength = StringUtils
				.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength), "3");

		SafehatExample example = new SafehatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andSafehatnumEqualTo(originSafeHatNum);
		// 调用大数据字段，否则获取不到变更日志
		List<Safehat> hats = safehatMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(hats)) {
			throw new RuntimeException("没有帽子");
		}
		Safehat safehat = hats.get(0);
		String safehatNum = safeHatPrefix
				+ String.format("%0" + safeHatNumLength + "d", NumberUtils.stringToInt(newSafeHatNum));// 新编号
		safehat.setSafehatnum(safehatNum);
		safehat.appendChangeLog(geneChangeLog(user, "将帽子编号改为" + safehatNum, "【修改】"));
		safehatMapper.updateByPrimaryKeySelective(safehat);

		String userhaulempid = safehat.getUserhaulempid();
		Haulemployeeout empOut = haulemployeeoutMapper.selectByPrimaryKey(userhaulempid);
		if (empOut != null) {
			empOut.setSafehatnum(safehatNum);
			haulemployeeoutMapper.updateByPrimaryKeySelective(empOut);
		}
	}

	@Override
	public String getSafehatChangelog(String originSafeHatNum) {
		SafehatExample example = new SafehatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andSafehatnumEqualTo(originSafeHatNum);
		List<Safehat> hats = safehatMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(hats)) {
			throw new RuntimeException("没有帽子");
		}
		Safehat safehat = hats.get(0);
		log.info(safehat.getChangelog());
		return safehat.getChangelog();
	}

	@Override
	public String getHatUserName(String safeHatNum) {
		// 1.查到帽子
		SafehatExample example = new SafehatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andSafehatnumEqualTo(safeHatNum);
		List<Safehat> hats = safehatMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(hats)) {
			throw new RuntimeException("没有帽子");
		}
		Safehat safehat = hats.get(0);
		String userhaulempid = safehat.getUseridcard();

		// 2.查使用者
		EmployeeOutExample employeeOutExample = new EmployeeOutExample();
		EmployeeOutExample.Criteria createCriteria2 = employeeOutExample.createCriteria();
		createCriteria2.andIdcodeEqualTo(userhaulempid);
		List<EmployeeOut> employeeOuts = employeeOutMapper.selectByExample(employeeOutExample);
		if (CollectionUtils.isEmpty(employeeOuts)) {
			throw new RuntimeException("帽子使用者已经删除");
		}

		return employeeOuts.get(0).getName() + "(" + employeeOuts.get(0).getIdcode() + ")";
	}

	@Override
	public void deleteSafehat(String originSafeHatNums, User user) {
		for (String originSafeHatNum : originSafeHatNums.split(",")) {
			// 1.查到帽子
			SafehatExample example = new SafehatExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andSafehatnumEqualTo(originSafeHatNum);
			List<Safehat> hats = safehatMapper.selectByExampleWithBLOBs(example);
			if (CollectionUtils.isEmpty(hats)) {
				continue;
			}
			Safehat safehat = hats.get(0);

			// 2.查使用者
			String userhaulempid = safehat.getUserhaulempid();
			Haulemployeeout empOut = haulemployeeoutMapper.selectByPrimaryKey(userhaulempid);
			if (empOut != null) {
				empOut.setSafehatnum("");
				haulemployeeoutMapper.updateByPrimaryKeySelective(empOut);
			}

			// 3.设置安全帽日志且取消记录使用者的信息
			safehat.appendChangeLog(geneChangeLog(user, "回收安全帽", "【回收】"));
			safehat.setUseridcard("");
			safehat.setUserhaulempid("");
			safehatMapper.updateByPrimaryKeySelective(safehat);
		}
	}

	@Override
	public void updateSafeHatNumBatch(String safeHatPrefix, List<String> haulEmpoutIds,
			List<String> haulEmpoutSafehatNums, List<String> empoutNames, String unitName, User user) {
		String safeHatNumLength = StringUtils
				.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength), "3");

		for (int i = 0, length_1 = haulEmpoutSafehatNums.size(); i < length_1; i++) {
			if (StringUtils.isBlank(haulEmpoutSafehatNums.get(i)) || StringUtils.isBlank(haulEmpoutIds.get(i))) {
				continue;
			}

			String haulEmpoutSafehatNum = haulEmpoutSafehatNums.get(i);
			String haulEmpoutId = haulEmpoutIds.get(i);
			String empoutName = empoutNames.get(i);
			String safehatNum = safeHatPrefix
					+ String.format("%0" + safeHatNumLength + "d", NumberUtils.stringToInt(haulEmpoutSafehatNum));// 最终的编号

			// 查询帽子，查到就换人,查不到就创建帽子并且分配给人
			// 1.查帽子查不到创建帽子
			SafehatExample example = new SafehatExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andSafehatnumEqualTo(safehatNum);
			List<Safehat> hats = safehatMapper.selectByExampleWithBLOBs(example);
			if (CollectionUtils.isEmpty(hats)) {
				log.debug("safehatNum -> {} is not exists!", safehatNum);
				// 创建帽子并且分配给用户
				createSafeHatAndAllocateToUser(haulEmpoutId, empoutName, unitName, safehatNum, user);
				continue;
			}

			// 2.查到帽子换人
			Safehat safehat = hats.get(0);
			// 2.1取消原来的使用者
			String oldUserhaulempid = safehat.getUserhaulempid();
			Haulemployeeout empOut = haulemployeeoutMapper.selectByPrimaryKey(oldUserhaulempid);
			if (empOut != null) {
				empOut.setSafehatnum("");
				haulemployeeoutMapper.updateByPrimaryKeySelective(empOut);
			}
			String oldUserIdCard = safehat.getUseridcard();

			// 2.2分给新人
			Haulemployeeout empOutNewUser = haulemployeeoutMapper.selectByPrimaryKey(haulEmpoutId);
			if (empOutNewUser == null) {
				continue;
			}
			String userIdCard = empOutNewUser.getEmpoutidcard();
			safehat.setUseridcard(userIdCard);
			safehat.setUserhaulempid(haulEmpoutId);

			safehat.appendChangeLog(geneChangeLog(user,
					"将帽子从 " + oldUserIdCard + " 换给 " + empoutName + "(" + userIdCard + ")", "【换人】"));
			safehatMapper.updateByPrimaryKeySelective(safehat);

			empOutNewUser.setSafehatnum(safehatNum);
			haulemployeeoutMapper.updateByPrimaryKeySelective(empOutNewUser);
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
		return safehatCustomMapper.getSafehatTaizhang(condition);
	}

}
