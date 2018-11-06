package cn.xm.exam.utils;

import java.util.Properties;

public class ExamSystemUtils {
	private static final String systemPropertyFileName = "settings.properties";

	private ExamSystemUtils() {

	}

	public static String getProperty(String key) {
		return PropertiesFileUtils.getPropertyValue(systemPropertyFileName, key);
	}

	public static void setProperty(String key, String value) {
		PropertiesFileUtils.saveOrUpdateProperty(systemPropertyFileName, key, value);
	}

	public static Properties getAllSystemProperties() {
		return PropertiesFileUtils.getProperties(systemPropertyFileName);
	}
}
