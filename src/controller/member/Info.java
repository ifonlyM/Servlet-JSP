package controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.google.gson.Gson;

import service.MemberServiceImpl;
import util.PwdJbcrypt;
import service.MemberService;
import vo.Member;

@WebServlet("/info")
public class Info extends HttpServlet{
	private MemberService service = new MemberServiceImpl();
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/member/info.jsp").forward(req, resp);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Member loginMember = (Member)session.getAttribute("member");
		
		String jsonData = req.getParameter("jsonData");
		Member member = gson.fromJson(jsonData, Member.class);
		
		// jbcrypt를 통해 비밀번호 암호화
		member.setPwd(new PwdJbcrypt().pwdJbcrypt(member.getPwd()));
		
		if(member.getId().isEmpty())		member.setId(loginMember.getId());
		if(member.getPwd().isEmpty())		member.setPwd(loginMember.getPwd());
		if(member.getEmail().isEmpty())		member.setEmail(loginMember.getEmail());
		if(member.getName().isEmpty())		member.setName(loginMember.getName());
		
		service.modify(member);
		
		// 수정한 정보의 유저로 member어트리뷰트를 다시 설정
		session.setAttribute("member", member); 
	}
}
