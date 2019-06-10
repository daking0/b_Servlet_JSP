package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/auth/logout")
public class LogOutServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpSession객체를 삭제한다(member객체도 삭제됨)
		HttpSession session = request.getSession();
		session.invalidate();	// 세션객체 무효화(삭제)
		
		// login으로 이동한다
		response.sendRedirect("login");
	}
}











