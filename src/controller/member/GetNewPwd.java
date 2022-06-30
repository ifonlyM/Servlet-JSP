package controller.member;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberServiceImpl;
import util.PwdJbcrypt;
import vo.Member;

@WebServlet("/getNewPwd")
public class GetNewPwd extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		Member member = new MemberServiceImpl().findBy("EMAIL", email);
		// 새로운 임시 비밀번호로 업데이트 후 resp으로 보내기
		UUID uuid = UUID.randomUUID();
		String bcrypt_uuid = new PwdJbcrypt().pwdJbcrypt(uuid.toString());
		new MemberServiceImpl().modify(new Member(member.getId(), bcrypt_uuid, member.getEmail(), member.getName()));
		resp.getWriter().print(uuid.toString());
	}
	
}
