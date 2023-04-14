<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		f.setAttribute('action', 'resultOrderList.html');
		document.body.appendChild(f);
		f.submit();
	}
</script>
<style>
.tr {
	display: block;
	float: left;
}

.th, .td {
	display: block;
}
</style>
</head>
<body>
	<h4>・会員情報</h4>
	<table border="1">
		<tr class="tr">
			<th class="th">会員NO</th>
			<th class="th">氏名</th>
			<th class="th">電話番号</th>
		</tr>
		<tr class="tr">
			<td class="td">
				<c:out value="${orderInfo.MEMBER_NO}"></c:out>
			</td>
			<td class="td">
				<c:out value="${orderInfo.MEMBER_NAME}"></c:out>
			</td>
			<td class="td">
				<c:out value="${orderInfo.TEL}"></c:out>
			</td>
		</tr>
	</table>

	<h4>・注文情報</h4>
	<table border="1">
		<tr class="tr">
			<th class="th">注文取りまとめ番号</th>
			<th class="th">注文日</th>
			<th class="th">小計</th>
			<th class="th">消費税</th>
			<th class="th">合計</th>
		</tr>
		<tr class="tr">
			<td class="td">
				<c:out value="${orderInfo.ORDER_NO}"></c:out>
			</td>
			<td class="td">
				<c:out value="${orderInfo.ORDER_DATE}"></c:out>
			</td>
			<td class="td" align="right">
				￥
				<fmt:formatNumber value="${orderInfo.TOTAL_MONEY}" pattern="###,###" />
			</td>
			<td class="td" align="right">
				￥
				<fmt:formatNumber value="${orderInfo.TOTAL_TAX}" pattern="###,###" />
			</td>
			<td class="td" align="right">
				￥
				<fmt:formatNumber value="${orderInfo.TOTAL_RESULT}" pattern="###,###" />
			</td>
		</tr>
	</table>

	<h4>・注文商品一覧</h4>
	<table border="1">
		<tr>
			<th>商品コード</th>
			<th>商品名</th>
			<th>販売元</th>
			<th>価格</th>
			<th>購入数</th>
		</tr>
		<c:forEach var="order" items="${orderListInfo}">
			<tr>
				<td>
					<c:out value="${order.PRODUCT_CODE}"></c:out>
				</td>
				<td>
					<c:out value="${order.PRODUCT_NAME}"></c:out>
				</td>
				<td>
					<c:out value="${order.MAKER}"></c:out>
				</td>
				<td align="right">
					￥
					<fmt:formatNumber value="${order.ORDER_PRICE}" pattern="###,###" />
				</td>
				<td>
					<c:out value="${order.ORDER_COUNT}"></c:out>
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="button" value="戻る" onClick="javascript:goBack()">
</body>
</html>