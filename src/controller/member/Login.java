package controller.member;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.MemberServiceImpl;
import service.MemberService;
import vo.Member;

@WebServlet("/login")
public class Login extends HttpServlet{
	List<Member> members = new ArrayList<Member>();
	{
		// 이곳에 멤버추가
		members.add(new Member("javaman", "1234"));
	}
	
	//로그인 폼 화면 forwarding
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/member/login.jsp").forward(req, resp);
	}

	// 로그인 처리
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		String msg = "";
		String redirectUrl = "login";
		
		MemberService service = new MemberServiceImpl();
		boolean success = service.login(id, pwd);
		
		if(success) {
			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(1800); //세션 유효시간 1시간으로 설정
			session.setAttribute("member", service.findBy("ID", id));
			msg = "로그인 성공";
			
			// 아이디 저장 체크박스 기능 로직
			Cookie cookie = new Cookie("savedId", id);
			// 삼항연산자를 이용해서 쿠키가 없을땐 유효기간을 0으로 쿠키가 존재할땐 1년으로 시간을 셋
			cookie.setMaxAge(req.getParameter("savedId") == null ? 0 : 60 * 60 * 24 * 365); 
			resp.addCookie(cookie);
			
			redirectUrl = "index.html";
		}
		else {
			msg = "로그인 실패";
		}
		resp.sendRedirect(redirectUrl + "?msg=" + URLEncoder.encode(msg, "utf-8"));
	}
	
	private Member findBy(String id) {
		Member member = null;
		for(Member m : members) {
			if(m.getId().equals(id)) {
				member = m;
				break;
			}
		}
		return member;
	}
	
	private Member findBy(String id, String pwd) {
		Member member = null;
		for(Member m : members) {
			if(m.getId().equals(id) && m.getPwd().equals(pwd)) {
				member = m;
				break;
			}
		}
		return member;
	}
	
}
