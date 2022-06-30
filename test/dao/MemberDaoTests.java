package dao;

import vo.Member;

public class MemberDaoTests {
	MemberDao dao = new MemberDao();
	public static void main(String[] args) {
		MemberDaoTests test = new MemberDaoTests();
		test.testUpdate();
	}
	
	// 회원정보수정 테스트:  clear
	public void testUpdate() {
		dao.update(new Member("javaman", "123456", "ifonly@naver.com", "문현석"));
	}
}
