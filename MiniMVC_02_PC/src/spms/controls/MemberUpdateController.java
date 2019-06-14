package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao)model.get("memberDao");
		// 누구 업데이트 해야 하나? jsp보여주기
		if(model.get("member")==null) {
			int no = (int)model.get("no");
			Member member = memberDao.selectOne(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		}
		// DB에 갱신
		else {
			Member member = (Member)model.get("member");
			memberDao.update(member);
			return "redirect:list.do";
		}
	}

}









