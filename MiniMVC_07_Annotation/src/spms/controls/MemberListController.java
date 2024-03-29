package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MemberDao;

@Component("/member/list.do")
public class MemberListController implements Controller {
	
	MemberDao memberDao;
	
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// 직접 dao를 주입받겠다
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}

}















