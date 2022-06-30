package controller.board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;

import service.BoardService;
import service.BoardServiceImpl;
import util.CommonConst;
import util.FileUpload;
import vo.Attach;
import vo.Board;
import vo.Reply;

@WebServlet("/board/modify")
public class Modify extends HttpServlet{
	BoardService service = new BoardServiceImpl();
	List<Attach> oldAttachs;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long bno = Long.parseLong(req.getParameter("bno"));
		Board board = new BoardServiceImpl().read(bno);
		req.setAttribute("board", board);
		
		oldAttachs = board.getAttachs();
		if(oldAttachs != null)
			req.setAttribute("oldAttachs", oldAttachs);
		
		req.getRequestDispatcher("/WEB-INF/jsp/board/modify.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//첨부파일 업로드 처리와 multipartRequest, attachs(DB에 저장할 첨부파일 데이터 리스트) 생성후 get
		FileUpload fileUpload = new FileUpload(req);	
		MultipartRequest multi = fileUpload.getMultiPartRequest(); //request를 대신하여 기능 수행
		List<Attach> attachs = fileUpload.getAttachs();
		
		// 기존 첨부파일과 새로 추가된 첨부파일을 비교해서 수정안된 파일의 경우 기존 첨부파일 사용
		for(int i = 0; i < attachs.size(); i++) {
			Attach newAtc = attachs.get(i);
			Attach oldAtc = null;
			// oldAttachs 널 체크, 사이즈 체크
			if(oldAttachs != null && oldAttachs.size()-1 >= i) {
				oldAtc = oldAttachs.get(i);
			}
			
			// 수정 안하고 기존 첨부파일이 있을땐 기존 파일 사용
			if(newAtc.getUuid() == null && oldAtc != null) {
				attachs.remove(i);
				attachs.add(i, oldAtc);
			}
		}
		
		Long bno = Long.valueOf(multi.getParameter("bno"));
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		
		Board board = new Board(bno, title, content); // 수정된 내용으로 게시글 데이터 생성
		board.setAttachs(attachs); // 첨부파일 리스트 set
		
		service.modify(board); // 게시글과 첨부파일 DB에서 수정처리
		
		// 글 수정후 카테고리에 알맞는 게시판으로 이동처리
		Long category = Long.valueOf(multi.getParameter("category"));
		String[] URLs = {"index.html", "/notice/detail?bno=", "/board/detail?bno=", "/gallery/detail?bno="};
		resp.sendRedirect(req.getContextPath() + CommonConst.categorySequence(category, URLs) + bno);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 과정에서 기존 첨부파일 삭제시 ajax통신으로 실행됨
		String uuid = req.getParameter("uuid");
		Long bno = Long.parseLong(req.getParameter("bno"));
		int index = Integer.parseInt(req.getParameter("index"));
		oldAttachs.remove(index);
		oldAttachs.add(index, null);
		service.removeAttachBy(bno, uuid);
	}
	
	
}
