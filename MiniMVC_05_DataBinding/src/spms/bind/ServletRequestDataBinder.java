package spms.bind;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

// 리플렉션 API를 이용해서 객체를 자동으로 생성해주는 역할을 담당
// 리플렉션 API란 ? 객체를 이용해서 클래스의 정보를 추출하거나
//                클래스의 이름(문자열)로 객체를 생성 등...
public class ServletRequestDataBinder {
	public static Object bind(ServletRequest request,
			Class<?> dataType, String dataName) throws Exception {
		// 기본 자료형일 경우
		if(isPrimitiveType(dataType)) {
			return createValueObject(dataType,
						request.getParameter(dataName));
		}
		// 기본 자료형이 아닌 일반 클래스 객체라면
		// 클라이언트가 보낸 모든 parameter의 이름을 추출한다
		Set<String> paramNames = request.getParameterMap().keySet();
		Object dataObject = dataType.newInstance(); // 객체 생성
		Method m = null;	//메서드를 호출하기 위한 변수
		for(String paramName : paramNames) {
			// 해당 parameter의 setter함수를 찾아서 반환한다
			// ex) no, setNo    email, setEmail
			m = findSetter(dataType, paramName);
			if(m!=null) {	// setter메서드를 찾았다면
				// 해당 setter메서드를 호출한다
				// m.getParameterTypes()[0] : 첫번째 인자의 자료형
				// ex ) ((member)dataObject).setNo(new Integer(10)); 호출한것과 같음
				m.invoke(dataObject, 
						createValueObject(m.getParameterTypes()[0],
								request.getParameter(paramName)));
			}
		}
		return dataObject;
	}
	
	// 기본형인지 아닌지
	private static boolean isPrimitiveType(Class<?> type) {
		if(type.getName().equals("int") || type==Integer.class ||
		   type.getName().equals("long") || type==Long.class ||
		   type.getName().equals("float") || type==Float.class ||
		   type.getName().equals("double") || type==Double.class ||
		   type.getName().equals("boolean") || type==Boolean.class ||
		   type == Date.class || type == String.class) {
			return true;
		}
		return false;
	}
	
	private static Object createValueObject(Class<?> type, String value) {
		if(type.getName().equals("int") || type==Integer.class) {
			return new Integer(value);
		}else if(type.getName().equals("float") || type==Float.class) {
			return new Float(value);
		}else if(type.getName().equals("double") || type==Double.class) {
			return new Double(value);
		}else if(type.getName().equals("long") || type==Long.class) {
			return new Long(value);
		}else if(type.getName().equals("boolean") || type==Boolean.class) {
			return new Boolean(value);
		}else if(type == Date.class) {
			return java.sql.Date.valueOf(value);
		}else {// 기본타입인 아닌경우는 다른 방식으로 만들거다
			return value;
		}
	}
	
	// name에 해당하는 setter메서드 정보를 찾아서 반환하는 메서드
	// no -> setNo,   name -> setName, email -> setEmail
	private static Method findSetter(Class<?> type, String name) {
		// 일단 해당 클래스의 모든 메서드 정보를 추출한다
		Method[] methods = type.getMethods();
		String propName = null;
		for(Method m : methods) {
			// 메서드 이름의 시작이 set이 아니면 다시 처음으로
			if(!m.getName().startsWith("set"))
				continue;
			// 만약 set으로 시작한다면 set은 제외한 이름을 추출한다
			// setNo -> No
			propName = m.getName().substring(3);
			// 메서드의 이름을 소문자로, 인자이름을 소문자로 비교한다
			// 같은 이름이면 no == no
			if(propName.toLowerCase().equals(name.toLowerCase())) {
				return m;	// 찾았다
			}
		}
		return null;
	}
}


















