<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="eCommerce.DTO.Rec101DTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<jsp:useBean id="now" class="java.util.Date" />
<link rel="stylesheet" href="css/main.css">
<%
Rec101DTO rec101DTO = (Rec101DTO) session.getAttribute("searchQuery");
String setDay_start = rec101DTO.getStart_day();
String setDay_end = rec101DTO.getEnd_day();
%>
<script>
	function goBack() {
		let f = document.createElement('form');
		f.setAttribute('method', 'get');
		f.setAttribute('action', 'menu.html');
		document.body.appendChild(f);
		f.submit();
	}
	function resetBtn() {
		let f = document.createElement('form');
		f.setAttribute('method', 'get');
		f.setAttribute('action', 'showOrderList.html');
		document.body.appendChild(f);
		f.submit();
	}


	function setDays(prefix) {
	    var setDay_start = parseInt("<%=setDay_start%>");
	    var setDay_end = parseInt("<%=setDay_end%>");
	    var year = document.getElementsByName(prefix + "_year")[0].value;
	    var month = document.getElementsByName(prefix + "_month")[0].value;
	    var daySelect = document.getElementsByName(prefix + "_day")[0];
	    daySelect.innerHTML = "";

	    var daysInMonth = 31;
	    if (month == 2) {
	        daysInMonth = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0 ? 29 : 28;
	    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
	        daysInMonth = 30;
	    }

	    var startOption = document.createElement("option");
	    startOption.text = "-";
	    startOption.value = "";
	    daySelect.add(startOption);

	    for (var i = 1; i <= daysInMonth; i++) {
	        var option = document.createElement("option");
	        option.text = i;
	        option.value = i;
	        if (setDay_start == i && prefix == "start") {
	            option.selected = true;
	        }
	        if (setDay_end == i && prefix == "end") {
	            option.selected = true;
	        }
	        daySelect.add(option);
	    }
	}

	window.onload = function() {
	    setDays('start');
	    setDays('end');
	};
</script>
</head>
<body>
	<br>
	<h4>検索条件を入力してください。</h4>
	<form action="resultOrderList.html" method="get">
		<form:errors cssClass="error" path="rec101DTO.*" />
		<div>
			<label for="member_no">会員NO</label>
			<input id="member_no" name="member_no" value="${rec101DTO.member_no}">
		</div>
		<div>
			<label for="member_name">会員名</label>
			<input id="member_name" name="member_name" value="${rec101DTO.member_name}">
		</div>
		<div>
			<label>注文日（開始年月日）</label>
			<select name="start_year">
				<option value="" selected>-</option>
				<c:forEach var="i" begin="2016" end="2025">
					<option value="${i}" <c:if test="${rec101DTO.start_year eq i}">selected="selected"</c:if>>${i}</option>
				</c:forEach>
			</select>

			<select name="start_month" onchange="setDays('start')">
				<option value="">-</option>
				<c:forEach var="i" begin="1" end="12">
					<option value="${i}" <c:if test="${rec101DTO.start_month eq i}">selected="selected"</c:if>>${i}</option>
				</c:forEach>
			</select>

			<select name="start_day">
			</select>
		</div>
		<div>
			<label>注文日（終了年月日）</label>
			<select name="end_year">
				<option value="">-</option>
				<c:forEach var="i" begin="2016" end="2025">
					<option value="${i}" <c:if test="${rec101DTO.end_year eq i}">selected="selected"</c:if>>${i}</option>
				</c:forEach>
			</select>

			<select name="end_month" onchange="setDays('end')">
				<option value="">-</option>
				<c:forEach var="i" begin="1" end="12">
					<option value="${i}" <c:if test="${rec101DTO.end_month eq i}">selected="selected"</c:if>>${i}</option>
				</c:forEach>
			</select>
			<select name="end_day">
			</select>
		</div>
		<div>
			<label>金額上限</label>
			<input name="upper_limit" value="${rec101DTO.upper_limit}">
		</div>
		<div>
			<label>金額下限</label>
			<input name="lower_limit" value="${rec101DTO.lower_limit}">
		</div>
		<input type="submit" value="検索">
		<input type="button" value="クリア" onClick="javascript:resetBtn()">
		<input type="button" value="戻る" onClick="javascript:goBack()">
	</form>
	<c:if test="${fn:length(orderList) > 0}">
		<h4>購入履歴一覧</h4>
		<div>
			<form>
				<input type="hidden" name="returnPage" value="${returnPage}" />
				<input type="hidden" name="nextPage" value="${nextPage}" />
				<c:choose>
					<c:when test="${currentPage eq 1}">
						<input formmethod="get" formaction="frontPage.html" type="submit" value="＜＜" disabled="disabled" />
						<input formmethod="get" formaction="return.html" type="submit" value="＜" disabled="disabled" />
					</c:when>
					<c:otherwise>
						<input formmethod="get" formaction="frontPage.html" type="submit" value="＜＜" />
						<input formmethod="get" formaction="return.html" type="submit" value="＜" />
					</c:otherwise>
				</c:choose>
				<c:out value="[${currentPage} / ${endPage}]" />
				<c:choose>
					<c:when test="${currentPage eq endPage || endPage eq 1}">
						<input formmethod="get" formaction="next.html" type="submit" value="＞" disabled="disabled" />
						<input formmethod="get" formaction="backPage.html" type="submit" value="＞＞" disabled="disabled" />
					</c:when>
					<c:otherwise>
						<input formmethod="get" formaction="next.html" type="submit" value="＞" />
						<input formmethod="get" formaction="backPage.html" type="submit" value="＞＞" />
					</c:otherwise>
				</c:choose>
			</form>
		</div>
		<table border="1">
			<tr>
				<th>注文取りまとめ番号</th>
				<th>会員NO</th>
				<th>会員名</th>
				<th>注文日</th>
				<th>小計</th>
				<th>消費税</th>
				<th>合計</th>
				<th>削除</th>
			</tr>
			<c:forEach var="order" items="${orderList}">
				<tr>
					<td>
						<a href="showOrder.html?ORDER_NO=${order.ORDER_NO}">${order.ORDER_NO}</a>
					</td>
					<td>
						<c:out value="${order.MEMBER_NO}"></c:out>
					</td>
					<td>
						<c:out value="${order.MEMBER_NAME}"></c:out>
					</td>
					<td>
						<c:out value="${order.ORDER_DATE}"></c:out>
					</td>
					<td align="right">
						<p>
							￥
							<fmt:formatNumber value="${order.TOTAL_MONEY}" pattern="###,###" />
						</p>
					</td>
					<td align="right">
						<p>
							￥
							<fmt:formatNumber value="${order.TOTAL_TAX}" pattern="###,###" />
						</p>
					</td>
					<td align="right">
						<p>
							￥
							<fmt:formatNumber value="${order.TOTAL_RESULT}" pattern="###,###" />
						</p>
					</td>
					<td>
						<a href="deleteOrder.html?ORDER_NO=${order.ORDER_NO}">削除</a>
					</td>
				</tr>
			</c:forEach>

		</table>
	</c:if>
	<c:if test="${fn:length(orderList) == 0 and error eq null and orderList ne null}">
		<c:out value="条件に該当する購入履歴は０件です。"></c:out>
	</c:if>
</body>
</html>