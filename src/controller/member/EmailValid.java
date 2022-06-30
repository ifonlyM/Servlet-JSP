package controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberServiceImpl;
import vo.Member;

@WebServlet("/emailValid")
public class EmailValid extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		Member member = new MemberServiceImpl().findBy("EMAIL", email);
		int result =  member == null ? 1 : 0;
		resp.getWriter().print(result);
	}
}
