<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (request.getHeader("X-Requested-With") != null
			&& "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		response.setContentType("application/javascript;charset=utf-8");
		out.write("错误提醒:系统发生错误,系统已经记录错误日志");
	} else {
		response.setContentType("text/html;charset=\"utf-8\"");
		out.write(
				"<html><head><meta charset=\"utf-8\" /><title>错误提醒</title></head><body><br /><span style=\"font-weight: bold;font-size: 20px;margin: 20px;\">系统发生错误!日志已经记录!</span>	<br /></body></html>");
	}
%>