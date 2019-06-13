package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	String url;
	String username;
	String password;
	// 사용하고 반환된 여유 Connection 객체들
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String url, String username, String password) throws Exception{
		this.url = url;
		this.username = username;
		this.password = password;
		
		Class.forName(driver); //드라이버 객체 로딩
	}
	
	public Connection getConnection() throws Exception{
		//  사용하지 않는 여유 객체가 있다면
		if(connList.size() > 0) {
			Connection conn = connList.get(0);
			// DB가 일정시간 사용을 안하면 강제로 종료하곤 한다 
			// 그러므로 이미 생성된 객체라도 연결되었는지 확인하고 
			// 서블릿에 빌려준다
			if(conn.isValid(10)) {
				return conn; 
			}
		}
		return DriverManager.getConnection(url,username, password); //여유분 없으면 그냥 새로 만들기
		
	}
	
	// 반납 메서드 - 서블릿을 다 사용했으면 반납한다	
	public void returnConnection(Connection conn) throws Exception{
		connList.add(conn);
	}
	
	// 서비스가 종료되면 모두 연결 해제한다
	public void closeAll() {
		for(Connection conn : connList) {
			try {
				conn.close();
			}catch(Exception e) {
				
			}
		}
	}
}
