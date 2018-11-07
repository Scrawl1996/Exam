<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>错误提醒</title>
</head>
<body>
	<br />
	<span style="font-weight: bold; font-size: 20px; margin: 20px;">对不起,您没有权限访问该请求!</span>
	<br />
</body>
</html>