package cn.xm.exam.service.safehat;

import java.util.List;
import java.util.Map;

import cn.xm.exam.bean.system.User;

public interface EmpInSafehatService {

	String getSafehatChangelog(String originSafeHatNum);

	String getHatUserName(String string);

	void deleteSafehat(String originSafeHatNum, User user);

	List<Map<String, Object>> getSafehatTaizhang(Map condition);

	void saveSafeHatNums(List<String> safeHatNums, List<String> userIdCards, User user);

	void updateSafeHatNumBatch(List<String> safeHatNums, List<String> userIdCards, User user);

}
