<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- EL과 함께 사용되어 JSP화면을 깔끔하게 정리해준다
EL이 가지지 못한 조건문/반복문의 사용이 특히 중요하다 
MAP, List, 배열을 forEach으로 접근할 수 있다
if문도 사용가능, switch~case문(choose) 사용도 가능하다
jstl 태그 라이브러리 선언이 필요하다-->
<title>JSTL(JSP Standard Tag Library) - 확장태그</title>
</head>
<body>
	<!-- 문법 -->
	<!-- c:out은 value값이 출력되고 null이면 문자열 출력 -->
<%-- 	
	<c:out value="안녕하세요"/><br>
	<c:out value="${null}">반갑습니다</c:out><br>
	<c:out value="안녕하세요">반갑습니다</c:out><br>
	<c:out value="${null}"/><br>
 --%>
 
 	<!-- set tag -->
 	<!-- 공유저장공간을 지정해주지 않으면 jspContext에 저장
 	pageScope임 --> 	
<%--  	
 	<h3>값 설정 방식</h3>
 	<c:set var="username1" value="홍길동"/>
 	<c:set var="username2">임꺽정</c:set>
 	${username1}<br>
 	${username2}<br>
 	 
 	 <h3>기본 보관소 - page</h3>
 	 ${pageScope.username1}<br>
 	 ${requestScope.username1}<br>
 	 
 	 <h3>보관소 지정 - scope 속성</h3>
 	 <c:set var="username3" scope="request">일지매</c:set>
 	 ${pageScope.username3}<br>
 	 ${requestScope.username3}<br>
 	 
 	 <h3>기존의 값 덮어씀</h3>
 	 <% 
 	 pageContext.setAttribute("username4", "아이유"); 
 	 %>
 	 기존 값=${username4}<br>
 	 <c:set var="username4" value="하이유"/>
 	 새 값=${username4}<br>
 	  --%>
 	  
 	<!-- if 태그 -->
<%--  	
 	<c:if test="${10 > 20}" var="result1">
 		10은 20보다 크다<br>
 	</c:if>
 	${result1}<br>
 	
 	<c:if test="${10 < 20}" var="result2">
 		10은 20보다 작다<br>
 	</c:if>
 	${result2}<br>
 	 --%>
 	 
 	<!-- choose 태그 : switch~case문 -->
<%--  	
 	<c:set var="userid" value="admin"/>
 	<c:choose>
 		<c:when test="${userid=='hong'}">
 			홍길동님 반갑습니다.
 		</c:when>
 		<c:when test="${userid=='leem'}">
 			임꺽정님 반갑습니다.
 		</c:when>
 		<c:when test="${userid=='admin'}">
 			관리자 전용 페이지입니다.
 		</c:when>
 		<c:otherwise>
 			등록되지 않은 사용자입니다.
 		</c:otherwise>
 	</c:choose>
 	 --%>
 	 
 	<!-- forEach 태그 -->
 	<!-- java.util.Collection구현체
 	     (ArrayList, LinkedList, Vector, EnumSet 등 
 	     java.util.Iterator 구현체
 	     java.util.Enumeration 구현체
 	     java.util.Map 구현체
 	     콤마(,) 구분자로 나열된 문자열 예)홍길동,임꺽정,아이유-->
<%--  	     
 	  <%
 	  request.setAttribute("nameList", 
 			  new String[]{"홍길동", "임꺽정", "아이유"});
 	  %>
 	  <ul>
 	  <c:forEach var="name" items="${nameList}">
 	  	<li>${name}</li>
 	  </c:forEach>
 	  </ul>
 	   --%>
<%--  	   
 	  <%
 	  request.setAttribute("nameList", 
 			  new String[]{"혜영", "양희", "강일", "수지", "은진"});
 	  %>
 	  <ul>
 	  <c:forEach var="name" items="${nameList}" begin="1" end="3">
 	  	<li>${name}</li>
 	  </c:forEach>
 	  </ul>
 	   --%>
<%--  	   
 	   <%
 	   ArrayList<String> nameList3 = new ArrayList<String>();
 	   nameList3.add("류혜영"); 	   nameList3.add("하양희");
 	   nameList3.add("서강일"); 	   nameList3.add("이은진");
 	   nameList3.add("한소미");
 	   request.setAttribute("nameList3", nameList3);
 	   %>
 	   <ul>
 	   <c:forEach var="name" items="${nameList3}">
 	   		<li>${name}</li>
 	   </c:forEach>
 	   </ul>
 	    --%>
 	    
 	  <!-- redirect 태그 -->
 	  <c:redirect url="http://www.daum.net"/>
</body>
</html>



























