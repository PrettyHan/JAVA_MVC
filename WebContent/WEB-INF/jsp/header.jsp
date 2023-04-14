<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<link rel="stylesheet" href="css/main.css">
<style>
header {
	background-color: #FFB1B1;
	height: 100px;
}
body{
	background-color: #FFF0F0;
}
</style>
</head>
<header>
	<div class="left-box">オンラインショッピングサイト</div>
	<div class="right-box">
		<span id="clock" style="color: gray; font-size: 20px;">clock</span>
		<span id="apm" style="color: gray; font-size: 10px;">ampm</span>
		<c:if test="${admin.NAME eq null}">
			<c:out value="ログインしてください。" />
		</c:if>
		<c:if test="${admin.NAME ne null}">
			<c:out value="「${admin.NAME}」さん" />
		</c:if>
	</div>
	<script>
        var Target = document.getElementById("clock");
        var Target_apm = document.getElementById("apm");
        function clock() {
            var time = new Date();
            var hours = time.getHours();
            var minutes = time.getMinutes();
            var seconds = time.getSeconds();
            var AmPm ="AM";
            if(hours >= 12){
                var AmPm ="PM";
                hours %= 12;
            }

            Target.innerText =
            `\${hours < 10 ? `\${hours}` : hours}:\${minutes < 10 ? `\${minutes}` : minutes}:\${seconds < 10 ? `\${seconds}` : seconds}`;

            Target_apm.innerText = `\${AmPm}`;

        }
        clock();
        setInterval(clock, 1000);
</script>
</header>