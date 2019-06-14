package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MemberDao;

//@WebListener
public class ContextLoaderListener implements ServletContextListener {

	
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
			
			//Tomcat의 context.xml에 접근해서 context태그의 정보를 가져오기
			InitialContext initialContext = 
					new InitialContext();
			// context정보중에 jdbc/studydb를 찾아와라
			// java:comp/env 는 JNDI 이름
			// JNDI란? Tomcat에서 원하는 정보를 찾을 때 사용하는 이름 형식
			DataSource ds = (DataSource)initialContext.lookup(
						"java:comp/env/jdbc/studydb");

			// MemberDao객체를 생성해서 등록하자
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);
			
			// 우리가 사용하는 모든 pageController를 
			// ServletContext에 저장한다
			// 자동화를 위한 과도기 코딩
			sc.setAttribute("/auth/login.do", 
					new LogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do",
					new LogOutController());
			sc.setAttribute("/member/list.do", 
					new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do",
					new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do",
					new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do",
					new MemberDeleteController().setMemberDao(memberDao));
			
			// 더이상 memberDao를 ServletContext에 저장하지 않는다
			// 앞으로 DB교체나 자동화를 위한 포석이다
			//sc.setAttribute("memberDao", memberDao);	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}









