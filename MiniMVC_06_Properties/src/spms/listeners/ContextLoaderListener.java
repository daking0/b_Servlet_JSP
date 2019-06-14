package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import spms.context.ApplicationContext;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

//@WebListener
public class ContextLoaderListener implements ServletContextListener {

	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	// 웹 어플리케이션이 종료할 때 마무리 작업
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed 호출 - 종료");
		
//		Tomcat의 context.xml에서 closeMethod="close"을 했기 때문에
//		별도로 해제할 필요가 없다
	}

	// 웹 어플리케이션이 시작할 때 초기화 작업
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("contextInitialized 호출 - 시작");
		try {
			ServletContext sc = event.getServletContext();

			// application-contetxt.properties 파일의 경로를 얻기
			String propertiesPath = sc.getRealPath(
					sc.getInitParameter("contextConfigLocation"));
			// 프로퍼티 파일에 등록된 pageController객체들을 자동으로 생성하기
			applicationContext = new ApplicationContext(propertiesPath);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}









