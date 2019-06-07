<%@ page import="spms.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 로그인시 생성한 member객체를 HttpSession으로부터 가져온다
	Member member = (Member)session.getAttribute("member");
%>
<div style="background-color:#00008b;color:#ffffff;height:20px;padding:5px;">
SPMS(Simple Project Management System)
<span style="float:right;">
<%=member.getName()%>
<a style="color:white;" 
	href="<%=request.getContextPath()%>/auth/logout">가지마세요</a>
</span>
</div>









