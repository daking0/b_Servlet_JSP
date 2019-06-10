<%@ page import="spms.vo.Member" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가지마세요</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>
	<h1>가지마세요</h1>
	<p><a href='add'>신규 회원</a></p>
	<jsp:useBean id="members"
				 scope = "request" 
				 class = "java.util.ArrayList"
				 type = "java.util.ArrayList<spms.vo.Member>"/>
	 <!-- scope는 영역 / class에 type에 해당하는 요소를 만드다-->
	<%
	System.out.println("MemberList.jsp에서 위임받아 화면 처리...");
/*	ArrayList<Member> members = 
		(ArrayList<Member>)request.getAttribute("members"); */
	for(Member member : members){
	%>
	<%=member.getNo()%>,
	<a href='update?no=<%=member.getNo()%>'><%=member.getName()%></a>,
	<%=member.getEmail()%>,
	<%=member.getCreatedDate()%>
	<a href='delete?no=<%=member.getNo()%>'>[삭제]</a><br>
	<%
	}
	%>
	<jsp:include page="/Tail.jsp"/>
</body>
</html>









