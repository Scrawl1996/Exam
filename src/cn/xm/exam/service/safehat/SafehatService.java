package cn.xm.exam.service.safehat;

import java.util.List;
import java.util.Map;

import cn.xm.exam.bean.system.User;

public interface SafehatService {

	void saveSafeHatNums(String safeHatPrefix, List<String> haulEmpoutId, List<String> haulEmpoutSafehatNum,
			List<String> empoutNames, String unitName, User user);

	void updateSafehatNum(String originSafeHatNum, String newSafeHatNum, String safeHatPrefix, User user);

	String getSafehatChangelog(String originSafeHatNum);

	String getHatUserName(String string);

	void deleteSafehat(String originSafeHatNum, User user);

	void updateSafeHatNumBatch(String safeHatPrefix, List<String> haulEmpoutId, List<String> haulEmpoutSafehatNum,
			List<String> empoutNames, String unitName, User user);

	List<Map<String, Object>> getSafehatTaizhang(Map condition);

	/**
	 * 根据大修员工ID回收安全帽
	 * 
	 * @param bigEmployeeOutId
	 */
	void deleteSafehatBybigEmployeeOutId(String bigEmployeeOutId);

}
