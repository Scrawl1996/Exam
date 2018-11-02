package cn.xm.exam.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ExceptionInterception implements Interceptor {
	private static final Logger log = LoggerFactory.getLogger(ExceptionInterception.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2268867259828199826L;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		log.info("enter ExceptionInterception intercept ... ");
		String result = "";
		try {
			result = arg0.invoke();
			log.info("result -> {}", result);
		} catch (Throwable e) {
			log.error("未处理的异常在拦截器被拦截,class:{}", arg0.getAction().getClass(), e);
			return "interceptorError";
		}
		log.debug("exit ExceptionInterception intercept ... ");
		return result;
	}

}
