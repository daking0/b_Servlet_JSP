package spms.gen;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

/* GenericServlet은 Servlet 인터페이스를 상속받은
 * 추상클래스이다.
 * service()를 제외하고 나머지는 default로 구현해놓고
 * service() 메서드만 구현하면 된다.
 * */

// Servlet 3.0부터는 애노테이션으로 배치 설정 가능
@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		/* request : 브라우저가 보낸 요청
		 * response : 서버가 보낼 응답
		 * http://localhost/web03_GenericServlet/calc?a=10&b=20
		*/
		int a = Integer.parseInt(request.getParameter("a"));
		int b = Integer.parseInt(request.getParameter("b"));
		
		response.setContentType("text/plain");  // 평문 데이터
		response.setCharacterEncoding("UTF-8"); // UTF-8로 전송
		PrintWriter writer = response.getWriter();
		writer.println("a=" + b + "," + "b=" + b + "의 계산결과");
		writer.println("a + b = " + (a+b));
		writer.println("a - b = " + (a-b));
		writer.println("a * b = " + (a*b));
		writer.println("a / b = " + ((float)a/b));
		writer.println("a % b = " + (a % b));
	}
}









