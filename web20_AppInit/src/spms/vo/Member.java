package spms.vo;

import java.util.Date;

// DTO, VO 데이터 객체
// 데이터베이스의 테이블이나 뷰와 대응되는 객체

//setter메서드에 자신의 객체를 반환하게 하면 다음과 같은 표현이 가능하다
//new Member().setNo(1)
//			.setName("홍길동")
//			.setEmail("hong@test.com");

//setter메서드를 void로 하면 아래처럼 해야 한다
//Member member = new Member();
//member.setNo(1);
//member.setName("홍길동");
//member.setEmail("hong@test.com");

public class Member {
	protected int no;
	protected String name;
	protected String email;
	protected String password;
	protected Date createdDate;
	protected Date modifiedDate;
	
	public int getNo() {
		return no;
	}
	public Member setNo(int no) {
		this.no = no;
		return this;
	}
	public String getName() {
		return name;
	}
	public Member setName(String name) {
		this.name = name;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public Member setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public Member setPassword(String password) {
		this.password = password;
		return this;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Member setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public Member setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
		return this;
	}
}








