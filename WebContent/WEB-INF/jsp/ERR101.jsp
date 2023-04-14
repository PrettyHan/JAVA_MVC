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
<script>
	function goBack() {
		let f = document.createElement('form');
		f.setAttribute('method', 'get');
		f.setAttribute('action', 'menu.html');
		document.body.appendChild(f);
		f.submit();
	}
</script>
</head>
<body>
	<c:out value="予想せぬエラーが発生しました。管理者にお問い合わせください。" />
	<br>
	<input type="button" value="メニューへ戻る" onClick="javascript:goBack()">
	<br>
	<c:out value="${error}"/>
</body>

</html>