<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>500</title>
<meta http-equiv="refresh" content="60;url=index.jsp">
<!-- content="600，即600秒后返回主页，可根据需要修改或者删除这段代码 -->
<link href="css/500.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<!-- 代码 开始 -->
	<div id="container">
		<img class="png" src="image/500.png" />
		<p>
			<a href="${pageContext.request.contextPath }/index.jsp"><img
				class="png" src="image/404_to_index.png" /></a>
		</p>
	</div>
	<div id="cloud" class="png"></div>
	<!-- 代码 结束 -->


</body>
</html>