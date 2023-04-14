<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<link rel="stylesheet" href="css/main.css">
</head>
<body>
	<div class="centerDiv">
		<h3>★管理者用★</h3>
		<c:out value="日付: " />
		<c:set var="ymd" value="<%=new java.util.Date()%>" />
		<fmt:formatDate value="${ymd}" pattern="yyyy-MM-dd" />
		<br>
		<c:out value=" ログイン者名：${admin.NAME}" />
		<br>
		<a href="showOrderList.html">購入履歴</a>
		<br>
		<input type="button" onclick="location.href='./logout.html'" value="ログアウト">
	</div>

</body>
</html>