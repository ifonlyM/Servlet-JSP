package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardServiceImpl;
import service.ReplyServiceImpl;

@WebServlet("/board/remove")
public class Remove extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long bno = Long.valueOf(req.getParameter("bno"));
		// 댓글삭제를 jsp에서 했는데 이곳에서 처리할것
		new ReplyServiceImpl().removeBy(bno);
		
		// 글 삭제
		new BoardServiceImpl().remove(bno);
	}
	
}
