package cn.xm.exam.utils;

import java.util.Properties;

public class ExamSystemUtils {
	private static final String systemPropertyName = "settings.properties";

	private ExamSystemUtils() {

	}

	public static String getProperty(String key) {
		return PropertiesFileUtils.getPropertyValue(systemPropertyName, key);
	}

	public static void setProperty(String key, String value) {
		PropertiesFileUtils.saveOrUpdateProperty(systemPropertyName, key, value);
	}

	public static Properties getAllSystemProperties() {
		return PropertiesFileUtils.getProperties(systemPropertyName);
	}
}
