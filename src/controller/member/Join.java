package controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberServiceImpl;
import service.MemberService;
import vo.Member;
import util.PwdJbcrypt;

@WebServlet("/join")
public class Join extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/member/join.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		pwd = new PwdJbcrypt().pwdJbcrypt(pwd);
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		
		Member member  = new Member(id, pwd, email, name);
		MemberService service = new MemberServiceImpl();
		service.join(member);
		
		resp.sendRedirect("login");
	}
	
}
