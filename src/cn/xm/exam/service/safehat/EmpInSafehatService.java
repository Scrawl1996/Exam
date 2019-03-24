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

	void deleteSafeHat(String safeHatNum);

	void updateSafeNum(String safeHatNum, String newSafeHatNum);

	/**
	 * 根据内部员工ID回收帽子
	 * 
	 * @param empId
	 */
	public void deleteSafehatByEmpId(String empId);

}
