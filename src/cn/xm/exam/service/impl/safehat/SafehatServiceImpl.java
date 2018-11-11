package cn.xm.exam.service.impl.safehat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xm.exam.bean.haul.Haulemployeeout;
import cn.xm.exam.bean.safehat.Safehat;
import cn.xm.exam.bean.safehat.SafehatExample;
import cn.xm.exam.bean.safehat.SafehatExample.Criteria;
import cn.xm.exam.bean.system.User;
import cn.xm.exam.mapper.employee.out.EmployeeOutMapper;
import cn.xm.exam.mapper.haul.HaulemployeeoutMapper;
import cn.xm.exam.mapper.safehat.SafehatMapper;
import cn.xm.exam.service.safehat.SafehatService;
import cn.xm.exam.utils.ExamSystemUtils;
import cn.xm.exam.utils.UUIDUtil;

@Service
public class SafehatServiceImpl implements SafehatService {
	private static final Logger log = LoggerFactory.getLogger(SafehatServiceImpl.class);

	@Autowired
	private SafehatMapper safehatMapper;
	@Autowired
	private HaulemployeeoutMapper haulemployeeoutMapper;
	@Autowired
	private EmployeeOutMapper employeeOutMapper;

	@Override
	public void saveSafeHatNums(String safeHatPrefix, List<String> haulEmpoutIds, List<String> haulEmpoutSafehatNums,
			List<String> empoutNames, String unitName, User user) {
		// 1.保存安全帽表
		String spliter = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumSpliter), "");
		String safeHatNumLength = StringUtils
				.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength), "");
		for (int i = 0, length_1 = haulEmpoutSafehatNums.size(); i < length_1; i++) {
			String haulEmpoutSafehatNum = haulEmpoutSafehatNums.get(i);
			String haulEmpoutId = haulEmpoutIds.get(i);
			String empoutName = empoutNames.get(i);
			String safehatNum = safeHatPrefix + spliter
					+ String.format("%0" + safeHatNumLength + "d", NumberUtils.stringToInt(haulEmpoutSafehatNum));// 最终的编号

			// 1.1保存安全帽
			Safehat safehat = new Safehat();
			String id = UUIDUtil.getUUID2();
			safehat.setId(id);
			safehat.setUseridcard(user.getUseridcard());
			safehat.setUserhaulempid(haulEmpoutId);
			safehat.setCreatordepartid(user.getDepartmentid());
			safehat.setIsDeleted(false);
			safehat.setCreatorfullname(user.getUsername());
			safehat.setSafehatnum(safehatNum);
			String geneChangeLog = geneChangeLog(user, "创建了安全帽" + safehatNum,
					"分配给" + empoutName + "(" + unitName + ")");
			safehat.setChangelog(geneChangeLog);
			log.info("geneChangeLog -> {}", geneChangeLog);
			safehatMapper.insert(safehat);
			// 2.保存修改检修员工表
			Haulemployeeout empOut = haulemployeeoutMapper.selectByPrimaryKey(haulEmpoutId);
			if (empOut != null) {
				empOut.setSafehatnum(safehatNum);
				haulemployeeoutMapper.updateByPrimaryKeySelective(empOut);
			}
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
	public void updateSafehatNum(String originSafeHatNum, String newSafeHatNum, String safeHatPrefix, User user) {
		// 1.保存安全帽表
		String spliter = StringUtils.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumSpliter), "");
		String safeHatNumLength = StringUtils
				.defaultIfBlank(ExamSystemUtils.getProperty(ExamSystemUtils.safeHatNumLength), "");

		SafehatExample example = new SafehatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andSafehatnumEqualTo(originSafeHatNum);
		// 调用大数据字段，否则获取不到变更日志
		List<Safehat> hats = safehatMapper.selectByExampleWithBLOBs(example);
		if (CollectionUtils.isEmpty(hats)) {
			throw new RuntimeException("没有帽子");
		}
		Safehat safehat = hats.get(0);
		String safehatNum = safeHatPrefix + spliter
				+ String.format("%0" + safeHatNumLength + "d", NumberUtils.stringToInt(newSafeHatNum));// 新编号
		safehat.setSafehatnum(safehatNum);
		String changeLog = safehat.getChangelog() + "," + geneChangeLog(user, "将帽子编号改为" + safehatNum);
		log.info(changeLog);
		safehat.setChangelog(changeLog);
		safehatMapper.updateByPrimaryKeySelective(safehat);

		String userhaulempid = safehat.getUserhaulempid();
		Haulemployeeout empOut = haulemployeeoutMapper.selectByPrimaryKey(userhaulempid);
		if (empOut != null) {
			empOut.setSafehatnum(safehatNum);
			haulemployeeoutMapper.updateByPrimaryKeySelective(empOut);
		}
	}

}
