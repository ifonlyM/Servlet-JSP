package service;

import java.util.List;

import dao.MemberDao;
import vo.Member;

public class MemberServiceImpl implements MemberService{
	private MemberDao dao = new MemberDao();
	
	@Override
	public boolean login(String id, String pwd) {
		// TODO Auto-generated method stub
		return dao.login(id, pwd);
	}

	@Override
	public void join(Member member) {
		// TODO Auto-generated method stub
		dao.join(member);
		
	}

	@Override
	public List<Member> getMembers() {
		// TODO Auto-generated method stub
		return dao.getMembers();
	}

	@Override
	public Member findBy(String SELECT, String param) {
		// TODO Auto-generated method stub
		return dao.findBy(SELECT, param);
	}

	@Override
	public void modify(Member member) {
		// TODO Auto-generated method stub
		dao.update(member);
	}

	@Override
	public void signOut(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}
}
