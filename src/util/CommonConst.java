package util;

public class CommonConst {
	public static final String UPLOAD_PATH = "c:\\upload"; // 파일 업로드 경로 root 
	public static final String[] imgExts = {"png", "jpg", "gif", "webp"}; // 이미지에 해당하는 파일 확장자 모음
	
	/**
	 * 
	 * @param category
	 * 게시판의 카테고리값을 Long타입으로 주고받음.
	 * @param URLs 
	 * URL배열, 인덱스 0번째는 기본URL값이 위치해야함.
	 * @return
	 * 카테고리 값으로 정해지는 URL을 문자열 형태로 반환함.
	 */
	public static String categorySequence(long category, String[] URLs) {
		String url = URLs[0];
		
//		if(category == 0L) url = "/notice/list";		
//		else if(category == 1L) url = "/board/list";
//		else if(category == 2L) url = "/gallery/list";
		if(category == 0L) url = URLs[1];		
		else if(category == 1L) url = URLs[2];
		else if(category == 2L) url = URLs[3];
		
		return url;
	}
}
