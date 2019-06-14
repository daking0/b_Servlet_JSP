package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller, DataBinding {
	
	MemberDao memberDao;
	
	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	// 객체를 자동으로 생성하기 위한 메서드
	// 등록이름, 클래스정보
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"member", spms.vo.Member.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// 입력폼을 요청할 때(doGet)
		//if(model.get("member")==null) {
		Member member = (Member)model.get("member");
		if(member.getEmail() == null) {
			return "/member/MemberForm.jsp";
		}
		// 회원 등록을 요청할 때(doPost)
		else {							
			memberDao.insert(member);			
			return "redirect:list.do";
		}
	}

}






