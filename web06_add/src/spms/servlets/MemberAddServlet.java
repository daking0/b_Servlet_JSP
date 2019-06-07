package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.Driver;

/*
Servlet은 interface
  ; 모든 메서드 overriding
GenericServlet은 Servlet을 상속받은 추상 클래스
  ; service() overriding
HttpServlet은 GenericServlet을 상속받은 클래스
  ; service()내에서 get -> doGet()
                   post -> doPost()
*/
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1></body>");
		// http://localhost:9999/web06_add/member/add
		// post 요청
		// doPost() 처리
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'<br>");
		out.println("암호: <input type='password' name='password'<br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 이곳에 한글이 깨지지 않도록 인코딩 형식을 설정해야 한다.
		// 이 메서드가 호출되기 전에 getParameter()가 호출되면 소용이 없다
		// web의 기본 인코딩이 utf-8이라는 것을 알려줘야 한다.
		// post일 때는 이곳에 설정을 하면 되지만 
		// get일때는 tomcat의 server.xml에 설정을 한다.
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/studydb?serverTimezone=UTC",
					"study",
					"study");
			stmt = conn.prepareStatement(
					"INSERT INTO members(email,pwd,mname,cre_date,mod_date)" +
					" VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			stmt.executeUpdate();
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다</p>");
			out.println("</body></html>");			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(Exception e) {}
		}
	}
}












