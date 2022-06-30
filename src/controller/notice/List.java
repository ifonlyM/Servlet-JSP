package controller.notice;

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

@WebServlet("/notice/list")
public class List extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardService service =  new BoardServiceImpl();
		String pn = req.getParameter("pageNum");
		String am = req.getParameter("amount");
		if(pn==null) pn = "1";
		if(am==null) am = "15";
		
		Criteria cri = new Criteria(Integer.parseInt(pn), Integer.parseInt(am), 0);
		req.setAttribute("list", service.list(cri));
		req.setAttribute("page", new PageDTO(service.getCount(cri), cri));
		req.getRequestDispatcher("/WEB-INF/jsp/notice/list.jsp").forward(req, resp);
	}

}
