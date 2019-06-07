<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL 사용법</title>
</head>
<body>
	<!-- EL 표현식 -->
<%-- 	문자열 : ${"test"}<br>
	문자열 : ${'test'}<br>
	정수 : ${20}<br>
	부동소수점 : ${3.14}<br>
	참거짓: ${true}<br>
	null: ${null}<br> --%>
	
	<!-- 배열에서 값꺼내기 -->
	<%
	pageContext.setAttribute("scores", new int[]{90,80,70,100});
	%>
	${scores[2]}
</body>
</html>














