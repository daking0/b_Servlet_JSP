<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// _jspService에 복사되는 자바 코드
	String v1 = "";
	String v2 = "";
	String result = "";
	String[] selected = {"", "", "", ""};
	
	// 브라우저로부터의 요청에 v1값이 있을 때만 실행
	if(request.getParameter("v1")!=null){
		v1 = request.getParameter("v1");
		v2 = request.getParameter("v2");
		String op = request.getParameter("op");
		result = calculate(Integer.parseInt(v1),
							Integer.parseInt(v2),
							op);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산기</title>
</head>
<body>
	<h2>JSP 계산기</h2>
	<form action="Calculator.jsp" method="get">
		<input type="text" name="v1" size="4" value="<%=v1%>">
		<select name="op">
			<option value="+" <%=selected[0]%>>+</option>
			<option value="-" <%=selected[1]%>>-</option>
			<option value="*" <%=selected[2]%>>*</option>
			<option value="/" <%=selected[3]%>>/</option>
		</select>
		<input type="text" name="v2" size="4" value="<%=v2%>">
		<input type="submit" value="=">
		<input type="text" size="8" value="<%=result%>"><br>
	</form>
</body>
</html>
<%!
	// 필드(멤버변수), 메서드, 내부클래스 선언되는 영역
	// _jspService() 바깥에 선언됨
	// 즉, Calculator_jsp 클래스의 멤버 메서드로 선언됨
	private String calculate(int a, int b, String op){
		int r = 0;
		if("+".equals(op)){
			r = a + b;
		}else if("-".equals(op)){
			r = a - b;
		}else if("*".equals(op)){
			r = a * b;
		}else if("/".equals(op)){
			r = a / b;
		}
		return Integer.toString(r);
	}
%>












