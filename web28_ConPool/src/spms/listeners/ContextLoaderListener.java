package spms.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spms.dao.MemberDao;
import spms.util.DBConnectionPool;

//@WebListener
public class ContextLoaderListener implements ServletContextListener {
	DBConnectionPool connPool;
	
	// 웹 어플리케이션이 종료할 때 마무리 작업
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed 호출 - 종료");
		try {
			connPool.closeAll();
		}catch(Exception e) {}
	}

	// 웹 어플리케이션이 시작할 때 초기화 작업
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("contextInitialized 호출 - 시작");
		try {
			ServletContext sc = event.getServletContext();
			connPool = new DBConnectionPool(sc.getInitParameter("driver"),
											sc.getInitParameter("url"),
											sc.getInitParameter("username"),
											sc.getInitParameter("password"));
			
			// MemberDao객체를 생성해서 등록하자
			MemberDao memberDao = new MemberDao();
			memberDao.setDBConnectionPool(connPool);
			// ServletContext영역에 memberDao객체를 등록
			sc.setAttribute("memberDao", memberDao);	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}









