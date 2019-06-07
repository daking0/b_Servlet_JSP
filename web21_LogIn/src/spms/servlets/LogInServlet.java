package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Member;

@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet{
	
	// http://localhost:9999/web21_LogIn/auth/login
	// 로 입력하면 doGet으로 들어온다
	// LogInForm.jsp로 forward로 위임한다
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(
									"/auth/LogInForm.jsp");
		rd.forward(request, response);
	}
	
	// /auth/LogInForm.jsp에서 보내온 email, password를 추출해서
	// MySQL에 요청해서 member VO객체를 만들고
	// HttpSession 공간에 저장한다
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection)sc.getAttribute("conn");
			stmt = conn.prepareStatement(
					"SELECT mname, email FROM members" +
			        " WHERE email=? AND pwd=?");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			rs = stmt.executeQuery();
			// DB에 사용자가 존재하면
			if(rs.next()) {
				// HttpSession에 저장할 VO객체를 생성한다
				Member member = new Member()
								.setEmail(rs.getString("email"))
								.setName(rs.getString("mname"));
				// HttpSession 공유공간에 저장한다
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				// 인증 후 /member/list로 보낸다
				response.sendRedirect("../member/list");
			}else {
				RequestDispatcher rd = 
						request.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(request, response);
			}
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			
		}
	}
}











