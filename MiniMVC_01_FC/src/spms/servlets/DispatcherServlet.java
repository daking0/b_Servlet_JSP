package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Member;

// 프론트 컨트롤러(제갈량의 역할)

// 요청 URL 맨 뒤에 .do 가 붙으면 여기에서 받겠다
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		// /member/list.do  , /auth/login.do
		String servletPath = request.getServletPath();
		try {
			// 누가 담당할까(어떤 서블릿이?) 
			String pageControllerPath = null;
			
			if("/member/list.do".equals(servletPath)) {
				pageControllerPath = "/member/list";
			}else if("/member/add.do".equals(servletPath)) {
				pageControllerPath = "/member/add";
				if(request.getParameter("email")!=null) {
					request.setAttribute("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));		
				}
			}else if("/member/update.do".equals(servletPath)) {
				pageControllerPath = "/member/update";
				if(request.getParameter("email")!=null) {
					request.setAttribute("member", new Member()
						.setNo(Integer.parseInt(request.getParameter("no")))
						.setEmail(request.getParameter("email"))
						.setName(request.getParameter("name")));
				}else {
					request.setAttribute("no", 
							Integer.parseInt(request.getParameter("no")));
				}
			}else if("/member/delete.do".equals(servletPath)) {
				pageControllerPath = "/member/delete";
				if(request.getParameter("no")!=null) {
					request.setAttribute("no", 
							Integer.parseInt(request.getParameter("no")));
				}
			}else if("/auth/login.do".equals(servletPath)) {
				pageControllerPath = "/auth/login";
				if(request.getParameter("email")!=null) {
					request.setAttribute("member", new Member()
							.setEmail(request.getParameter("email"))
							.setPassword(request.getParameter("password")));
				}
			}else if("/auth/logout.do".equals(servletPath)) {
				pageControllerPath = "/auth/logout";
			}
			
			// pageControllerPath를 처리하는 서블릿(페이지 컨트롤러)에 
			//위임한 후 다시 진행한다
			RequestDispatcher rd = 
					request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);
			
			// 페이지 컨트롤러가 다음에 진행할 정보를 request공간에 담아놓음
			// 페이지 컨트롤러들은 viewUrl이라는 이름으로 request공간에
			// 다음에 진행해야할 경로를 DispatcherController에 알려준다
			String viewUrl = (String)request.getAttribute("viewUrl");
			// viewUrl의 시작 문자열이 redirect:로 시작하면 바로 이동
			if(viewUrl.startsWith("redirect:")) {
				// redirect: 이후 문자열 경로로 이동
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {	// redirect:로 시작하지 않는다면
				rd = request.getRequestDispatcher(viewUrl);
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







