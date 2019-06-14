package spms.context;


import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

public class ApplicationContext {
	Hashtable<String, Object> objTable = 
						new Hashtable<String, Object>();
	
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	public ApplicationContext(String propertiesPath) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		// 프로퍼티 파일에 의한 객체 생성
		prepareObjects(props);
		// 애노테이션에 의해 객체를 생성하고 objTable에 등록
		prepareAnnotationObjects();
		// setter를 호출해서 memberDao를 주입
		injectDependency();
	}
	
	// 여기서는 오픈소스 Reflection API를 사용해서 객체를 만든다
	private void prepareAnnotationObjects() throws Exception{
		// classpath이하의 모든 class를 대상으로 삼는다
		// Properties -> Java Build Path -> Source
		Reflections reflector = new Reflections("");
		// Component애노테이션이 설정된 모든 클래스 정보를 가져온다
		Set<Class<?>> list = 
				reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list) {
			// 애노테이션의  value를 가져온다 key = /auth/login.do
			key = clazz.getAnnotation(Component.class).value();
			// objTable에 /auth/login.do와 
			// 연결된 클래스(LogInController)의 객체를 생성 후 등록한다
			objTable.put(key, clazz.newInstance());
		}
	}
	
	private void prepareObjects(Properties props) throws Exception{
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		for(Object item : props.keySet()) {
			key = (String)item;
			value = props.getProperty(key);
			if(key.startsWith("jndi.")) {	// Tomcat DataSource
				objTable.put(key, ctx.lookup(value));
			}else {	// 객체를 생성해야 하는 것들
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	private void injectDependency() throws Exception{
		for(String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object obj) throws Exception{
		Object dependency = null;
		Method[] methods = obj.getClass().getMethods();
		for(Method m : methods) {
			if(m.getName().startsWith("set")) {
				// memberDao객체를 objTable에서 찾아서 반환해
				dependency = findObjectByType(
								m.getParameterTypes()[0]);
				// ex) MemberAddController setMemberDao를 호출
				// obj == MemberAddController 객체
				// m == setMemberDao		  메서드
				// dependency == memberDao    객체
				m.invoke(obj, dependency);
			}
		}
	}
	
	private Object findObjectByType(Class<?> type) {
		for(Object obj : objTable.values()) {
			if(type.isInstance(obj)) {
				return obj;
			}
		}		
		return null;
	}
}






















