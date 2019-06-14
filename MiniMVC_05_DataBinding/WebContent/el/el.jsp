<%@page import="java.util.List" %>
<%@page import="java.util.LinkedList" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%@page import="spms.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 용도는 서블릿에서 jsp로 데이터를 전달할 때 쉽게 꺼내기
     memberList : 여러 사람 -> Map, ArrayList, 배열
     login      : 1사람 -> 객체 
 -->
<title>EL(Expression Language)</title>
</head>
<body>
	<!-- EL 표현식 -->
<%-- 	
	문자열 : ${"test"}<br>
	문자열 : ${'test'}<br>
	정수 : ${20}<br>
	실수 : ${3.14}<br>
	참거짓 : ${true}<br>
	null : ${null}<br>
	 --%>
	 
	 <!-- 연산자 -->
<%-- 	 
	 \${10+20} = ${10+20}<br>
	 \${10-20} = ${10-20}<br>
	 \${10*20} = ${10*20}<br>
	 \${10/20} = ${10/20}<br>
	 \${10 div 20} = ${10 div 20}<br>
	 \${10%20} = ${10%20}<br>
	 \${10 mod 20} = ${10 mod 20}<br>
	  --%>
	  
	  <!-- 배열에서 값 꺼내기 -->
	  <!-- 아래처럼 jsp에서 스크립트릿을 작성하는 것이 아니라
	  서블릿에서 jsp에 넘겨줄 객체를 공유공간에 저장하는 것을 가정함 -->
	  <!-- el에서 공유공간을 접근할 때 scope를 지정하지 않으면
	  pageScope, requestScope, sessionScope, applicationScope순으로
	  접근한다 -->
<%-- 	  
	  <%
	  request.setAttribute("scores", new int[]{90,80,70,100});
	  %>
	  ${requestScope.scores[2]}<br>
	  ${scores[2]}<br>
	   --%>
	   
	   <!-- List 객체에서 값 꺼내기 -->
<%-- 	   
	   <%
	   List<String> nameList = new LinkedList<String>();
	   nameList.add("홍길동");
	   nameList.add("임꺽정");
	   nameList.add("일지매");
	   request.setAttribute("nameList", nameList);
	   %>
	   ${nameList[1]}<br>
	   ${nameList[2]}<br>
	    --%>
	    
	   <!-- Map 객체에서 값 꺼내기 -->
<%-- 	   
	   <%
	   Map<String, String> map = new HashMap<String, String>();
	   map.put("s01", "홍길동");
	   map.put("s02", "임꺽정");
	   map.put("s03", "일지매");
	   request.setAttribute("map", map);
	   %>
	   ${map.s01}<br>
	   ${map.s02}<br>
	   ${map.s03}<br>
	    --%>
	    
	  <!-- 자바 객체에서 프로퍼티 값 꺼내기 -->
	  <!-- 1명의 정보를 넘겨줄 때 사용 -->
	  <%
	  session.setAttribute("member", 
			  		new Member().setNo(100)
			  					.setName("아이유")
			  					.setEmail("iu@test.com"));
	  %>
	  ${member.no}<br>
	  ${member.name}<br>
	  ${member.email}<br>
</body>
</html>














