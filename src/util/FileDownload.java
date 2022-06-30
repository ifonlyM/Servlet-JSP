package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BoardService;
import service.BoardServiceImpl;
import vo.Attach;
import vo.Board;

@WebServlet("/download")
public class FileDownload extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 파일 인풋을 위한 단계
		String fileName = req.getParameter("filename");
		String saveDirectory = CommonConst.UPLOAD_PATH;
		String filePath = saveDirectory + File.separator + fileName;
		
		
		FileInputStream fis = new FileInputStream(filePath);
		String mimeType = getServletContext().getMimeType(filePath); // 파라미터(경로)에 위치한 파일의 mimeType을 가져오기
		
		if(mimeType == null) {
			mimeType = "application/octet-stream"; // 8비트로 된 데이터라는 뜻. 해당파일을 다룰 특별한 프로그램이 없다는 뜻도 될수 있다.
		}
		
		// 사용자의 브라우저
		String userAgent = req.getHeader("User-Agent");
		
		
		//파일 아웃풋을위한 단계
		BoardService service = new BoardServiceImpl();
		fileName = service.findOriginBy(fileName.substring(fileName.indexOf("/")+1)); // uuid를 통해서 파일의 원본 이름 가져오기
		
		// 인터넷 익스플로러일 경우
		if(userAgent.toLowerCase().contains("trident")) {
			fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
		}
		// 다른 브라우저일경우에따른 파일이름 변경
		else {
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		
		// 응답헤더 설정
		resp.setContentType(mimeType);
		resp.setHeader("Content-DisPosition", "attachment; filename=" + fileName); // response body에 오는 값을 다운로드 받으라는 뜻
		
		// 출력 스트림 지정
		ServletOutputStream sos = resp.getOutputStream();
		int b;
		
		while((b = fis.read()) != -1) {
			sos.write(b);
		}
		
		fis.close();
		sos.close();
	}
	
}
