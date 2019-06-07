package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 현재 테스트 할 것은 
// 서블릿의 외부 초기 변수 설정이므로
// web.xml에 등록하겠다.
public class MemberUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// web.xml의 해당 서블릿 아래에 init-param으로 설정한
		// 초기화값을 this.getInitParameter("이름")으로 얻는다.
		try {
			Class.forName(this.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					this.getInitParameter("url"),
					this.getInitParameter("username"),
					this.getInitParameter("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT mno,email,mname,cre_date FROM members" +
					" WHERE mno=" + request.getParameter("no"));
			rs.next(); // 어차피 1개 데이터니까 그냥 이렇게 호출
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원 정보</title></head>");
			out.println("<body><h1>회원 정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" +
						request.getParameter("no") +"' readonly><br>");
			out.println("이름: *<input type='text' name='name'" +
						" value='" + rs.getString("mname") + "'><br>");
			out.println("이메일: <input type='text' name='email'" +
						" value='" + rs.getString("email") + "'><br>");
			out.println("가입일: " + rs.getDate("cre_date") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='취소'" +
						" onclick='location.href=\"list\"'>");
			out.println("</form>");
			out.println("</body></html>");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(Exception e) {}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		// web.xml의 해당 서블릿 아래에 init-param으로 설정한
		// 초기화값을 this.getInitParameter("이름")으로 얻는다.
		try {
			Class.forName(this.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					this.getInitParameter("url"),
					this.getInitParameter("username"),
					this.getInitParameter("password"));
			stmt = conn.prepareStatement(
					"UPDATE members SET email=?,mname=?,mod_date=NOW()"
					+ " WHERE mno=?");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
			stmt.executeUpdate();
			response.sendRedirect("list");
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











