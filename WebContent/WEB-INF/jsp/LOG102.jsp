<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/main.css">
<jsp:include page="/WEB-INF/jsp/header.jsp" />
</head>
<body>
	<div class="centerDiv">
		<form method="post" action="login.html">
			<div>
				<label for="member_no">ログインID</label>
				<input type="text" id="member_no" name="member_no">
			</div>
			<div>
				<label for="password">パスワード</label>
				<input type="password" id="password" name="password">
			</div>
			<input type="submit" value="ログイン">
			<input type="reset" value="クリア">
		</form>
		<form:errors cssClass="error" path="log102DTO.*" />
		<c:out value="${error}"/>
	</div>
</body>

</html>