package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		{
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				// 1. mysql jdbc driver 등록
				//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
				// 2. mysql server와 연결
				// 예전 5.x 에서는 jdbc:mysql://localhost/studydb 설정함
				// 현재 8.x 에서는 아래처럼 하거나 mysql 설정을 변경해야 함
				// jdbc-url, id, password
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/studydb?serverTimezone=UTC",
						"study",
						"study");
				// 3. sql문을 실행할 대화 객체를 생성
				stmt = conn.createStatement();
				// 4. 실행 후 결과값 받기
				rs = stmt.executeQuery(
						"SELECT mno,mname,email,cre_date" +
						" FROM members" +
						" ORDER BY mno ASC");
				// 5. 화면에 출력하기
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<html><head><title>회원목록</title></head>");
				// http://localhost:9999/web06_add/member/add
				// get 요청
				out.println("<p><a href='add'>신규 회원</a></p>");
				out.println("<body><h1>회원목록</h1>");
				while(rs.next()) {
					out.println(rs.getInt("mno") + ", " +
									   rs.getString("mname") + ", " +
									   rs.getString("email") + ", " +
									   rs.getDate("cre_date") + "<br>");
				}
				out.println("</body></html>");
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(rs!=null)
						rs.close();
					if(stmt!=null)
						stmt.close();
					if(conn!=null)
						conn.close();
				}catch(Exception e) {
					
				}
			}
		}	
	}
}












