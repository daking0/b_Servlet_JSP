package spms.gen;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet {
	
	ServletConfig config;

	@Override
	public void destroy() {
		System.out.println("HelloWorld-destroy() 호출");
	}

	@Override
	public ServletConfig getServletConfig() {
		System.out.println("HelloWorld-getServletConfig() 호출");
		return config;
	}

	@Override
	public String getServletInfo() {
		System.out.println("HelloWorld-getServletInfo() 호출");
		return "version=1.0;author=bitcamp;copyright=bitcamp 2019";
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("HelloWorld-init() 호출");
		this.config = config;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		System.out.println("HelloWorld-service() 호출");
	}
}





