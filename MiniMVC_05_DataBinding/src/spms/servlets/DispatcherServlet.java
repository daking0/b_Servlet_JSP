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

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.controls.Controller;

// 프론트 컨트롤러(제갈량의 역할)

// 요청 URL 맨 뒤에 .do 가 붙으면 여기에서 받겠다
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		//     /member/list.do를 얻을 수 있다
		String servletPath = request.getServletPath();
		try {
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = 
					new HashMap<String, Object>();
			// ServletContext에 memberDao가 없으니까 제거
			//model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			// 경로에 알맞은 객체를 얻어서 저장한다
			Controller pageController = 
					(Controller)sc.getAttribute(servletPath);
			
			// pageController가 DataBinding의 상속을 받았다면
			// prepareRequestData라는 함수를 호출해서
			// request에서 넘어온 파라미터와
			// pageController에 등록된 자동화 정보를 사용해서
			// model에 자동으로 필요한 객체를 만들어서 등록해줘!
			if(pageController instanceof DataBinding) {
				prepareRequestData(request, model, 
							(DataBinding)pageController);
			}		

			String viewUrl = pageController.execute(model);

			for(String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
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
	
	private void prepareRequestData(HttpServletRequest request,
			HashMap<String, Object> model,
			DataBinding dataBinding) throws Exception{
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		// 만약에
		// /member/add.do요청이 들어왔을 때
		// dataBinding에는 MemberAddController가 전달된다
		// dataBinders에는 ["member", spms.vo.Member.class] 가 전달
		for(int i=0; i<dataBinders.length; i+=2) {
			// "member"
			dataName = (String)dataBinders[i];	
			// spms.vo.Member.class
			dataType = (Class<?>)dataBinders[i+1]; 
			// request의 파라미터를 추출하고
			// 객체를 만들어서
			// 객체에 추출값을 저장하고
			// 반환한다
			dataObj = ServletRequestDataBinder.bind(
					request, dataType, dataName);
			// model Map객체에 dataName으로 객체를 등록한다
			model.put(dataName, dataObj);
		}
	}
}



















