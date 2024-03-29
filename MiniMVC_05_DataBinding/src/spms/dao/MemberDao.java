package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.vo.Member;

public interface MemberDao {	
	public List<Member> selectList() throws Exception;
	public int insert(Member member) throws Exception;
	public int delete(int no) throws Exception;
	public Member selectOne(int no) throws Exception;
	public int update(Member member) throws Exception;
	public Member exist(String email, String password) 
										throws Exception;
}



