package spms.servlets;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppInitServlet extends HttpServlet{
	 
	// 웹어플리케이션이 시작될 때 하는 작업
	// 데이터베이스 연결 준비
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("AppInitServlet 준비...");
		super.init(config);
		
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			Connection conn = DriverManager.getConnection(
								sc.getInitParameter("url"),
								sc.getInitParameter("username"),
								sc.getInitParameter("password"));
			// 모든 서블릿이 공유할 수 있도록 ServletContext 공간에 저장
			sc.setAttribute("conn", conn);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	// 웹 어플리케이션이 종료시 해야할 작업
	@Override
	public void destroy() {
		System.out.println("AppInitServlet 종료...");
		super.destroy();
		ServletContext sc = this.getServletContext();
		Connection conn = (Connection)sc.getAttribute("conn");
		try {
			if(conn!=null && conn.isClosed() == false)
				conn.close();
		}catch(Exception e) {}
	}
}











