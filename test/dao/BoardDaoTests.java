package dao;

import util.CommonConst;
import vo.Board;
import vo.Criteria;

public class BoardDaoTests {
	BoardDao dao = new BoardDao();
	public static void main(String[] args) {
		BoardDaoTests tests = new BoardDaoTests();
//		tests.testRead();
//		tests.testList();
//		tests.testUpdate();
//		tests.testAttachDelete();
//		tests.testAttachList();
		String path = "";
		path = CommonConst.UPLOAD_PATH.replace("\\", "\\\\");
		System.out.println(path);
	}
	
	public void testRead() {
		Board board = dao.read(10L);
		System.out.println(board);
	}
	
	public void testList() {
		dao.list(new Criteria(1, 20)).forEach(System.out::println);
	}
	
	public void testUpdate() {
		Board board = dao.read(7L);
		board.setTitle("수정테스트");
		dao.update(board);
	}
	
	public void testAttachList() {
		dao.readAttach(1553L);
	}
	
	public void testAttachDelete() {
		dao.deleteAttach(6L);
	}
}
