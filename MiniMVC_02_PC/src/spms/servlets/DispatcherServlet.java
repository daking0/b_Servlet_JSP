package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.controls.Controller;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.vo.Member;

// 프론트 컨트롤러(제갈량의 역할)

// 요청 URL 맨 뒤에 .do 가 붙으면 여기에서 받겠다
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String servletPath = request.getServletPath();
		try {
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = 
					new HashMap<String, Object>();
			model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			Controller pageController = null;
			
			if("/member/list.do".equals(servletPath)) {
				pageController = new MemberListController();
			}else if("/member/add.do".equals(servletPath)) {
				pageController = new MemberAddController();
				if(request.getParameter("email")!=null) {
					model.put("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));		
				}
			}else if("/member/update.do".equals(servletPath)) {
				pageController = new MemberUpdateController();
				if(request.getParameter("email")!=null) {
					model.put("member", new Member()
						.setNo(Integer.parseInt(request.getParameter("no")))
						.setEmail(request.getParameter("email"))
						.setName(request.getParameter("name")));
				}else {
					model.put("no", 
							Integer.parseInt(request.getParameter("no")));
				}
			}else if("/member/delete.do".equals(servletPath)) {
				pageController = new MemberDeleteController();
				if(request.getParameter("no")!=null) {
					model.put("no", 
							Integer.parseInt(request.getParameter("no")));
				}
			}else if("/auth/login.do".equals(servletPath)) {
				pageController = new LogInController();
				if(request.getParameter("email")!=null) {
					model.put("loginInfo", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")));
				}
			}else if("/auth/logout.do".equals(servletPath)) {
				pageController = new LogOutController();
			}
			
			// 담당 pageController 객체에 업무 위임
			// 후 이동할 경로를 리턴받는다
			String viewUrl = pageController.execute(model);
			
			// request 공유공간에 model데이터들을 저장한다
			for(String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")) {
				// redirect: 이후 문자열 경로로 이동
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {	// redirect:로 시작하지 않는다면
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = 
					request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}







