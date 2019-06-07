import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL_JDBC {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 1. mysql jdbc driver 등록
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			// 2. mysql server와 연결
			// 예전 5.x 에서는 jdbc:mysql://localhost/studydb 설정함
			// 현재 8.x 에서는 아래처럼 하거나 mysql 설정을 변경해야 함
			// jdbc-url, id, password
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/studydb?serverTimezone=UTC",
					"study",
					"study");
			// 3. sql문을 실행할 대화 객체를 생성
			stmt = conn.createStatement();
			// 4. 실행 후 결과값 받기
			rs = stmt.executeQuery(
					"SELECT mno,mname,email,cre_date" +
					" FROM members" +
					" ORDER BY mno ASC");
			// 5. 화면에 출력하기
			while(rs.next()) {
				System.out.println(rs.getInt("mno") + ", " +
								   rs.getString("mname") + ", " +
								   rs.getString("email") + ", " +
								   rs.getDate("cre_date"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
				if(conn!=null)
					conn.close();
			}catch(Exception e) {
				
			}
		}
	}
}





