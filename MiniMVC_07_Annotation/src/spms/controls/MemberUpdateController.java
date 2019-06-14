package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {
	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class,
			"member", spms.vo.Member.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		// 누구 업데이트 해야 하나? jsp보여주기
		Member member = (Member)model.get("member");
		//if(model.get("member")==null) {
		if(member.getEmail() == null) {
			int no = (int)model.get("no");
			Member detailInfo = memberDao.selectOne(no);
			model.put("member", detailInfo);
			return "/member/MemberUpdateForm.jsp";
		}
		// DB에 갱신
		else {
			memberDao.update(member);
			return "redirect:list.do";
		}
	}

}









