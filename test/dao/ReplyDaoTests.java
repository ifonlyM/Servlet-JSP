package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConn;
import vo.Reply;

public class ReplyDaoTests {
	ReplyDao dao = new ReplyDao();
	
	public static void main(String[] args) {
		ReplyDaoTests daoTest = new ReplyDaoTests();
//		daoTest.testUpdate();
		daoTest.testDelete();
	}
	
	public void testUpdate() {
		Reply reply =  dao.select(1L);
		reply.setTitle("수정된타이틀");
		dao.update(reply);
	}
	
	public void testDelete() {
		// 글삭제할때 해당글에 종속된 댓글 모두 삭제
		dao.deleteBy(8L);
	}
}
