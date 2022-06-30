package controller.reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.ReplyService;
import service.ReplyServiceImpl;
import vo.Reply;

@WebServlet("/reply/modify")
public class ReplyModify extends HttpServlet{
	ReplyService service = new ReplyServiceImpl();
	private Gson gson = new Gson();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonData = req.getParameter("jsonData");
		Reply reply = gson.fromJson(jsonData, Reply.class);
		service.modiify(reply);
	}
	
}
