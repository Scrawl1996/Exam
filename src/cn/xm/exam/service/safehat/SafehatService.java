package cn.xm.exam.service.safehat;

import java.util.List;

import cn.xm.exam.bean.system.User;

public interface SafehatService {

	void saveSafeHatNums(String safeHatPrefix, List<String> haulEmpoutId, List<String> haulEmpoutSafehatNum,
			List<String> empoutNames, String unitName, User user);

	void updateSafehatNum(String originSafeHatNum, String newSafeHatNum, String safeHatPrefix, User user);

	String getSafehatChangelog(String originSafeHatNum);

}
