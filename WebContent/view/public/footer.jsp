<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/public/tag.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- 脚 -->
<link rel="stylesheet" href="<%=path%>/css/home/footer.css" />
<script type="text/javascript" src="${baseurl}/js/public/footer.js"></script>
<script type="text/javascript">
	/* 记录项目名字 /Exam */
	var contextPath = "${pageContext.request.contextPath}";
</script>
</head>
<body>
	<!-- 缩进菜单的图标 -->
	<img src="${baseurl}/image/show.png"
		style="position: fixed; left: 4px; bottom: 50px; width: 40px;"
		title="点此隐藏与显示菜单栏" id="toggleMenu" />

	<footer class="footer">
	<div class="center">Copyright © 2017-2018
		&nbsp;&nbsp;大唐集团&nbsp;阳城国际发电有限公司 &nbsp;&nbsp; All Rights Reserved.</div>
	</footer>

</body>
</html>