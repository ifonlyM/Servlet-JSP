package controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardService;
import service.BoardServiceImpl;
import vo.Criteria;
import vo.PageDTO;

@WebServlet("/index.html")
public class Index extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardService service =  new BoardServiceImpl();
		req.setAttribute("list", service.list(new Criteria(1, 8, 1)));
		req.setAttribute("gallList", service.list(new Criteria(1, 6, 2)));
		req.getRequestDispatcher("/WEB-INF/jsp/common/index.jsp").forward(req, resp);
	}
}

