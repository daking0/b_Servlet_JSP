package spms.controls;

import java.util.Map;

//DispatcherServlet이 일반 객체인 PageController한테
//위임하기 위한 약속으로 정의한 인터페이스
//Map객체에는 공유한 정보를 저장해서 넘긴다
public interface Controller {
	String execute(Map<String, Object> model) throws Exception;
}
