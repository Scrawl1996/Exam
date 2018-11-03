package cn.xm.exam.exception;

/**
 * 没有权限异常
 * 
 * @author QiaoLiQiang
 * @time 2018年11月3日下午9:34:12
 */
public class NoPermissionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4442982597754920924L;

	public NoPermissionException(String msg) {
		super(msg);
	}
}
