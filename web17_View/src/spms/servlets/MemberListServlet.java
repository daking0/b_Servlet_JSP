package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password")); 
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			
			response.setContentType("text/html; charset=UTF-8");
			
//			데이터베이스에서 가져온 회원정보를 Member클래스의
//			객체로 만들어 JSP에 전달한다
			ArrayList<Member> members = new ArrayList<Member>();
			while(rs.next()) {
				members.add(new Member().setNo(rs.getInt("mno"))
										.setName(rs.getString("mname"))
										.setEmail(rs.getString("email"))
										.setCreatedDate(rs.getDate("cre_date")));
			}
			
//			member객체의 전달을 위해 request공간에 저장한다
			request.setAttribute("members", members);
			System.out.println("MemberListServlet에서 전달...");
			
//			기존의 Servlet 자바 코드의 화면 출력코드를 
//			JSP를 만들어 전달한다.
//			JSP로 출력을 위임한다
			RequestDispatcher rd = request.getRequestDispatcher(
					"/member/MemberList.jsp");
			rd.include(request, response);			
			
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
