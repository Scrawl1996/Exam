package cn.xm.exam.utils;

public class ExamSystemUtils {
	/**
	 * 设置文件的位置
	 */
	private static final String systemPropertyName = ResourcesUtil.getValue("path", "settingsPropertiesPath");

	/**
	 * 安全帽长度
	 */
	public static final String safeHatNumLength = "safeHatNumLength";

	/**
	 * 身体状况
	 */
	public static final String physicalStatus = "physicalStatus";

	/**
	 * 教育背景
	 */
	public static final String educateBackground = "educateBackground";

	/**
	 * 安全帽分隔符
	 */
	public static final String safeHatNumSpliter = "safeHatNumSpliter";

	private ExamSystemUtils() {
	}

	public static String getProperty(String key) {
		return PropertiesFileUtils.getPropertyValueByFilePath(systemPropertyName, key);
	}

	public static void setProperty(String key, String value) {
		PropertiesFileUtils.saveOrUpdatePropertyByFilePath(systemPropertyName, key, value);
	}
}
