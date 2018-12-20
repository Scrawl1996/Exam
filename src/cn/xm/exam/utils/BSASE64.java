package cn.xm.exam.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xm.exam.utils.ImageUtil.ImageUtils;
import sun.misc.BASE64Decoder;

/**
 * 
 * 项目名称：Exam 类名称：BSASE64 类描述：图片解密 创建人：wangyueyue 创建时间：2017年11月15日 下午9:20:51
 * 
 * @version
 * 
 */
public class BSASE64 {
	private static final Logger log = LoggerFactory.getLogger(BSASE64.class);

	public static boolean generateImage(String imgStr, String path) {
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			//
			for (int i = 0; i < b.length; i++) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();

			// 调整图片大小为指定的大小为102*126像素
			ImageUtils.fromFile(new File(path)).size(102, 126).quality(0.8f).fixedGivenSize(true)
					.toFile(new File(path));

			return true;
		} catch (Exception e) {
			log.error("[保存员工头像]generateImage error", e);
			return false;
		}
	}

	public static void main(String[] args) {
		String code = "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";
		BSASE64.generateImage(code, "e:/test.jpg");
	}
}
