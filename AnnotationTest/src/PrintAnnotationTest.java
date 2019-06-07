import java.lang.reflect.Method;

public class PrintAnnotationTest {
	public static void main(String[] args) {
		// Service클래스로부터 메서드 정보를 얻음
		Method[] dMethods = 
				Service.class.getDeclaredMethods();
		for(Method method : dMethods) {
			// 메서드에 어노테이션 적용되어 있니?
			if(method.isAnnotationPresent(
					PrintAnnotation.class)) {
				// PrintAnnotation 객체 얻기
				PrintAnnotation printAnnotation = 
						method.getAnnotation(
								PrintAnnotation.class);
				// 정보 출력
				// 해당 메서드 이름 출력
				System.out.println("[" + method.getName() + "]");
				// 구분선 출력
				for(int i=0;i<printAnnotation.number();i++) {
					System.out.print(printAnnotation.value());
				}
				System.out.println();
				
				// 메서드 호출
				try {
					method.invoke(new Service());
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}	
	}
}




