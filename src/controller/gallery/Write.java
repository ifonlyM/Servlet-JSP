//package controller.gallery;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import com.oreilly.servlet.MultipartRequest;
//
//import service.BoardServiceImpl;
//import util.CommonConst;
//import util.FileUpload;
//import vo.Attach;
//import vo.Board;
//import vo.Member;
//
////@WebServlet("/gallery/write")
//@WebServlet(urlPatterns = {"/board/write","/gallery/write"})
//public class Write extends HttpServlet{
//
//	// 글쓰기 폼
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Long category = Long.valueOf(req.getParameter("category"));
//		String[] URLs = {"board", "notice", "board", "gallery"};
//		String menu = CommonConst.categorySequence(category, URLs);
//		req.getRequestDispatcher("/WEB-INF/jsp/" + menu +"/write.jsp").forward(req, resp);
//	}
//
//	// 글쓰기 후 프로세스
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String url = req.getContextPath();
//		String[] URLs = {"index.html", "/notice/list", "/board/list", "/gallery/list"};
//		
//		FileUpload fileUpload = new FileUpload(req); 				// 반드시 생성자에 req파라미터를 넘겨주어야함
//		MultipartRequest multi = fileUpload.getMultiPartRequest();	// 기존 request를 대체하는 multipartRequest 가져오기
//		List<Attach> attachs = fileUpload.getAttachs();				// DB에 첨부파일 데이터를 Insert하기 위해서 필요함
//		
//		String title = multi.getParameter("title");
//		String content = multi.getParameter("content");
//		Long category = Long.valueOf(multi.getParameter("category"));
//		Member member = ((Member)req.getSession().getAttribute("member"));
//		
//		if(member == null) {
//			resp.getWriter().print("<script>alert('로그인 세션이 만료되었습니다.')</script>");
//			url += CommonConst.categorySequence(category, URLs);
//			resp.sendRedirect(url);
//		}
//		else {
//			// DB에 게시글과 첨부파일 Insert
//			Board board = new Board(null, title, content, null, member.getId(), category);
//			board.setAttachs(attachs);
//			new BoardServiceImpl().write(board);
//			
//			url += CommonConst.categorySequence(category, URLs);
//			resp.sendRedirect(url);
//		}
//	}
//}
