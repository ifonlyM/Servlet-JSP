package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.oreilly.servlet.MultipartRequest;

import lombok.Data;
import net.coobird.thumbnailator.Thumbnailator;
import vo.Attach;

@Data
public class FileUpload {
	private MultipartRequest multiPartRequest;
	private List<Attach> attachs;
	
	/**
	 * upload과정을 거쳐야 multipartRequest, attachs 객체를 외부에서 호출할때 접근가능
	 * @param req (HttpServletRequest)
	 */
	public FileUpload(HttpServletRequest req){
		try {
			this.upload(req);
		} catch (IOException e) {e.printStackTrace();}
	}

	private void upload(HttpServletRequest req) throws IOException {
		
		multiPartRequest = new MultipartRequest(
				req, 										// HttpServletRequest
				uploadPath().getAbsolutePath(), // 파일 업로드 절대 경로
				10 * 1024 * 1024, 					// 파일 최대 사이즈
				"utf-8", 									// 인코딩 타입
				new MyFileRenamePolicy()			// FileRenamePolicy 커스텀(UUID.randomUUID()를 이용)
		);
		
		@SuppressWarnings("unchecked")
		Enumeration<String> files = multiPartRequest.getFileNames(); // 글작성시 첨부한 파일명들을 Enumeration<String> 객체로 받음
		attachs = new ArrayList<>();
		
		while(files.hasMoreElements()) {
			String file = files.nextElement();
			String uuid = multiPartRequest.getFilesystemName(file); // renamepolicy로 변경된 이름
			// 파일이 없는 경우
			if(uuid == null) {
				attachs.add(0, new Attach());
				continue;													
			}
			
			String origin = multiPartRequest.getOriginalFileName(file); 	// 원래 파일 이름
			Long fileSize = multiPartRequest.getFile(file).length(); 		// 파일 사이즈
			
			Attach attach = new Attach(uuid, origin, null, new SimpleDateFormat("yyMMdd").format(new Date()), fileSize, null);
			attachs.add(0, attach);
			
			// 파일 확장자가 이미지에 해당하는 경우 썸네일 생성하기
			String fileName = multiPartRequest.getFile(file).getName();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1); //이미지 확장자 추출
			boolean isImg = false;
			
			
			// 이미지 확장자와 파일의 확장자 비교후 맞으면 isImg = true
			for(String imgExt : CommonConst.imgExts) {
				if(ext.equals(imgExt)) {
					isImg = true;
					break;
				}
			}
			
			// 썸네일 이미지 생성
			if(isImg) {
				FileInputStream fis = new FileInputStream(uploadPath() + "\\" + uuid);
				FileOutputStream fos = new FileOutputStream(uploadPath() + "\\s_" + uuid);
				Thumbnailator.createThumbnail(fis, fos, 250, 250);
			}
		}
	}
	
	/**
	 * 파일 저장 경로를 가지고 생성된 File객체 반환,
	 * @return ex) "c:\\upload\\220520"
	 */
	private File uploadPath() {
		File uploadPath =new File(CommonConst.UPLOAD_PATH + File.separator + getPath());
		
		//지정한 경로에 디렉토리가 없을경우 생성함
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		return uploadPath;
	}
	
	/**
	 * 오늘 날짜로 이루어진 문자열을 반환,
	 * 첨부파일을 저장하는 경로를 생성하는데 쓰인다
	 * @return ex) "220520"
	 */
	private String getPath() {
		return new SimpleDateFormat("yyMMdd").format(new Date());
	}
}
