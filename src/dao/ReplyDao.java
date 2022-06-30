package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBConn;
import vo.Board;
import vo.Reply;

public class ReplyDao {

	public void insert(Reply reply) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		Long rno = null;
		try {
			conn.setAutoCommit(false);
			
			// 글 번호를 먼저 발급
//			ResultSet rs = conn.prepareStatement("SELECT SEQ_REPLY.NEXTVAL FROM DUAL").executeQuery();
//			rs.next();
//			rno = rs.getLong(1);
			
			// 글작성
			pstmt = conn.prepareStatement("INSERT INTO TBL_REPLY(RNO, TITLE, CONTENT, ID, NAME, BNO) VALUES ( SEQ_REPLY.NEXTVAL, ?, ?, ?, ?, ?)");
			int idx = 1;
//			pstmt.setLong(idx++, rno);
			pstmt.setString(idx++, reply.getTitle());
			pstmt.setString(idx++, reply.getContent());
			pstmt.setString(idx++, reply.getId());
			pstmt.setString(idx++, reply.getName());
			pstmt.setLong(idx++, reply.getBno());
			
			pstmt.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			pstmt.close();
			conn.close();
			// select : executeQuery
			// insert, update, delete : executeUpdate
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Reply> list(Long bno) {
		Connection conn = DBConn.getConnection();
		List<Reply> list = new ArrayList<Reply>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT RNO, TITLE, CONTENT, TO_CHAR(REGDATE, 'YY/MM/DD') REGDATE, ID, NAME FROM TBL_REPLY WHERE RNO > 0 AND BNO=? ORDER BY RNO");
			pstmt.setLong(1, bno);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int idx = 1;
				Long rno = rs.getLong(idx++);
				String title = rs.getString(idx++);
				String content = rs.getString(idx++);
				String regDate = rs.getString(idx++);
				String id = rs.getString(idx++);
				String name = rs.getString(idx++);
				
				Reply reply = new Reply(rno, title, content, regDate, id, name, bno);
				list.add(reply);
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Reply select(Long rno) {
		Connection conn = DBConn.getConnection();
		Reply reply = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT TITLE, CONTENT, TO_CHAR(REGDATE, 'YY/MM/DD') REGDATE, ID, NAME, BNO "+
					"FROM TBL_REPLY WHERE RNO > 0 AND RNO=?");
			pstmt.setLong(1, rno);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int idx = 1;
				reply = new Reply(
						rno, 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++),
						rs.getLong(idx++)
				);
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reply;
	}
	
	public void delete(Long rno) {
		Connection conn = DBConn.getConnection();
		
		try {
			// rno에 해당하는 댓글 삭제
			PreparedStatement pstmt = conn.prepareStatement("DELETE TBL_REPLY WHERE RNO=?");
			int idx = 1;
			pstmt.setLong(idx++, rno);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBy(Long bno) {
		Connection conn = DBConn.getConnection();
		
		try {
			// bno에 해당하는 댓글 모두 삭제
			PreparedStatement pstmt = conn.prepareStatement("DELETE TBL_REPLY WHERE BNO=?");
			int idx = 1;
			pstmt.setLong(idx++, bno);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Reply reply) {
		// TODO Auto-generated method stub
		Connection conn = DBConn.getConnection();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE TBL_REPLY SET TITLE=?, CONTENT=? WHERE RNO=?");
			int idx = 1;
			pstmt.setString(idx++, reply.getTitle());
			pstmt.setString(idx++, reply.getContent());
			pstmt.setLong(idx++, reply.getRno());
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
