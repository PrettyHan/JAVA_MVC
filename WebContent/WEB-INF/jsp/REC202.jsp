<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<link rel="stylesheet" href="css/main.css">
<script>
	function goBack() {
		let f = document.createElement('form');
		f.setAttribute('method', 'get');
		f.setAttribute('action', 'menu.html');
		document.body.appendChild(f);
		f.submit();
	}
	function goSearch() {
		let f = document.createElement('form');
		f.setAttribute('method', 'get');
		f.setAttribute('action', 'resultOrderList.html');
		document.body.appendChild(f);
		f.submit();
	}
</script>
</head>
<body>
<h4>購入履歴の削除が完了しました</h4>
<input type="button" value="検索結果へ戻る" onClick="javascript:goSearch()">
<br>
<br>
<input type="button" value="メニューへ戻る" onClick="javascript:goBack()">
</body>
</html>