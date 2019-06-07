package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
		urlPatterns="/*",
		initParams= {
				@WebInitParam(name="encoding",value="UTF-8")
		})
public class CharacterEncodingFilter implements Filter {
	
	FilterConfig config;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, 
			ServletResponse response, 
			FilterChain nextFilter)
			throws IOException, ServletException {
		System.out.println("doFilter()");
		// web.xml의 정보를 가져와서 적용하자
		// 한글이 안깨지도록
		request.setCharacterEncoding(
				config.getInitParameter("encoding"));
		// 다음 필터 or 서블릿에 넘겨준다
		nextFilter.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;		
	}

}







