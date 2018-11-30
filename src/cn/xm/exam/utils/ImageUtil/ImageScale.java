package cn.xm.exam.utils.ImageUtil;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import cn.xm.exam.utils.ResourcesUtil;

public class ImageScale {
	/**
	 * 对指定的文件进行缩放
	 * 
	 * @param imageFileName
	 * @param scaledWidth
	 * @param scaledHeight
	 */
	public static void scale(String imageFileName, String path, int scaledWidth, int scaledHeight) {
		String dir = FilenameUtils.separatorsToSystem(ServletActionContext.getServletContext().getRealPath(path) + "/");
		String fileType = FilenameUtils.getExtension(imageFileName);

		File sourceImageFile = new File(dir + imageFileName);
		File destImageFile = new File(dir + "temp." + fileType);
		ImageUtils.fromFile(sourceImageFile).size(scaledWidth, scaledHeight).quality(0.8f).fixedGivenSize(true)
				.toFile(destImageFile);
		try {
			FileUtils.copyFile(destImageFile, sourceImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对磁盘中的指定文件进行缩放
	 * 
	 * @param imageFileName
	 *            磁盘中的图片文件名
	 * @param pathKey
	 *            资源文件中的键
	 * @param scaledWidth
	 * @param scaledHeight
	 */
	public static void scaleToDisk(String imageFileName, String pathKey, int scaledWidth, int scaledHeight) {
		String dir = ResourcesUtil.getValue("path", pathKey);
		String fileType = FilenameUtils.getExtension(imageFileName);

		File sourceImageFile = new File(dir + imageFileName);
		File destImageFile = new File(dir + "temp." + fileType);
		ImageUtils.fromFile(sourceImageFile).size(scaledWidth, scaledHeight).quality(0.8f).fixedGivenSize(true)
				.toFile(destImageFile);
		try {
			FileUtils.copyFile(destImageFile, sourceImageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		File sourceImageFile = new File("e:/test.jpg");
		File destImageFile = new File("e:/test11.jpg");
		ImageUtils.fromFile(sourceImageFile).size(18, 18).quality(0.8f).fixedGivenSize(true).toFile(destImageFile);
	}
}