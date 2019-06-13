package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import spms.vo.Member;

//MVC중에 데이터베이스와 입출력을 진행하는 역할을 Model이라고 하고
//그 객체를 Dao객체라고 한다.
//데이터베이스와의 입출력을 일원화 함으로써 에러발생시 쉽게 찾을 수 있게 하고
//향후 유연한 아키텍처의 적용이 가능하다
public class MemberDao {
	Connection connection;

	// 의존성 주입(DI : Dependency Injection)
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// MemberListServlet이 사용할 메서드
	public List<Member> selectList() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT mno,mname,email,cre_date" + " FROM members" + " ORDER BY mno ASC");
			ArrayList<Member> members = new ArrayList<Member>();
			while (rs.next()) {
				members.add(new Member().setNo(rs.getInt("mno")).setName(rs.getString("mname"))
						.setEmail(rs.getString("email")).setCreatedDate(rs.getDate("cre_date")));
			}
			return members;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	// 회원등록
	public int insert(Member member) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)" + " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	// 회원삭제
	public int delete(int no) throws Exception {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			return stmt.executeUpdate("DELETE FROM MEMBERS WHERE MNO=" + no);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	// 회원상세정보조회(Update-doGet())
	public Member selectOne(int no) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT MNO,EMAIL,MNAME,CRE_DATE FROM MEMBERS" + " WHERE MNO=" + no);
			if (rs.next()) {
				return new Member().setNo(rs.getInt("MNO")).setEmail(rs.getString("EMAIL"))
						.setName(rs.getString("MNAME")).setCreatedDate(rs.getDate("CRE_DATE"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	// 회원 정보 변경(Update-doPost())
	public int update(Member member) throws Exception {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()" + " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	// 있으면 Member객체, 없으면 null (로그인)
	public Member exist(String email, String password) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement("SELECT MNAME,EMAIL FROM MEMBERS" + " WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();

			if (rs.next()) {
				Member member = new Member().setEmail(rs.getString("EMAIL")).setName(rs.getString("MNAME"));
				return member;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}
}
