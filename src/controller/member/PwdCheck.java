package controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.PwdJbcrypt;
import vo.Member;

@WebServlet("/pwdCheck")
public class PwdCheck extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String inputPwd = req.getParameter("input");
		String userPwd =((Member)session.getAttribute("member")).getPwd();
		int result = new PwdJbcrypt().pwdCheck(inputPwd, userPwd) ? 1 : 0;
		resp.getWriter().print(result);
	}
	
	
}
