package controller.reply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;

import com.google.gson.Gson;

/*import jdk.nashorn.internal.parser.JSONParser;*/
import service.ReplyService;
import service.ReplyServiceImpl;
import vo.Reply;

@WebServlet("/reply")
public class ReplyController extends HttpServlet{
	private ReplyService service = new ReplyServiceImpl();
	private Gson gson = new Gson();
	
	// 댓글 단일 조회
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long rno = Long.parseLong(req.getParameter("rno"));
		Reply reply = service.get(rno);
		resp.setContentType("allication/json");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(gson.toJson(reply));
	}

	// 댓글 작성
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonData = req.getParameter("jsonData");
		Reply reply = gson.fromJson(jsonData, Reply.class);
		service.write(reply);
	}

	// 댓글 수정
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		BufferedReader br = req.getReader();
//		String jsonData = "";
//		String parseStr = null;
//		while((parseStr = br.readLine()) != null) {
//			jsonData += parseStr;
//		}
//		System.out.println(jsonData);
//
//		
//		Reply reply = gson.fromJson(req.getReader(), Reply.class);
//		System.out.println(reply);
//		Reply reply = gson.fromJson(jsonData, Reply.class);
//		service.modiify(reply);
	}
	
	// 댓글 삭제
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long rno = Long.parseLong(req.getParameter("rno"));
		service.remove(rno);
	}
	
}
