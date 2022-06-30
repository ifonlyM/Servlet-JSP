package controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardServiceImpl;

@WebServlet("/board/detail")
public class Detail extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long bno = Long.parseLong(req.getParameter("bno"));
		req.setAttribute("board", new BoardServiceImpl().read(bno));
		req.getRequestDispatcher("/WEB-INF/jsp/board/detail.jsp").forward(req, resp);
	}
	
}
