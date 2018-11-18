package cn.xm.exam.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExamSystemUtils {
	private static final Logger log = LoggerFactory.getLogger(ExamSystemUtils.class);
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

	private ExamSystemUtils() {
	}

	public static String getProperty(String key) {
		return PropertiesFileUtils.getPropertyValueByFilePath(systemPropertyName, key);
	}

	public static void setProperty(String key, String value) {
		PropertiesFileUtils.saveOrUpdatePropertyByFilePath(systemPropertyName, key, value);
	}

	public static File getTmpFile() {
		// 获取到当前系统的临时工作目录
		String tempDirectoryPath = FileUtils.getTempDirectoryPath();
		String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		String tmpFileDir = tempDirectoryPath + date;
		FileUtils.deleteQuietly(new File(tmpFileDir));

		// 创建目录(以日期格式命名)
		File file2 = new File(tmpFileDir);
		file2.mkdir();

		// 创建临时文件,UUID产生名称
		String fileName = UUIDUtil.getUUID2();
		String tmpFileName = (tmpFileDir + "/" + fileName).replace("\\", "/");
		File file = new File(tmpFileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			log.error("getTmpFile error", e);
		}
		log.info("gene tempFile -> {}", file.getAbsolutePath());
		return file;
	}

}
