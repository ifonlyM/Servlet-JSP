package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBConn;
import util.PwdJbcrypt;
import vo.Board;
import vo.Member;

public class MemberDao {
	public List<Member> getMembers() {
		// 1. Connection 취득
		// 2. 문장(Statement) 생성
		// 3. Select >> 결과 집합(ResultSet)
		// >> RS 순회

		Connection conn = DBConn.getConnection();
		List<Member> list = new ArrayList<Member>();

		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT ID, PWD, EMAIL, NAME FROM TBL_MEMBER");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("ID");
				String pwd = rs.getString("PWD");
				String email = rs.getString("EMAIL");
				String name = rs.getString("NAME");

				Member member = new Member(id, pwd, email, name);
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	
//	  public boolean login(String id, String pwd) { 
//		  Connection conn =  DBConn.getConnection(); 
//		  boolean success = false;
//	  
//		  PreparedStatement pstmt = null; 
//		  try { 
//			  pstmt = conn.prepareStatement("SELECT ID, PWD, EMAIL, NAME FROM TBL_MEMBER WHERE ID=? AND PWD=?");
//		  
//			  int idx = 1; 
//			  pstmt.setString(idx++, id); 
//			  pstmt.setString(idx++, pwd);
//		  
//			  ResultSet rs = pstmt.executeQuery(); 
//			  success = rs.next(); 
//			  pstmt.close();
//			  conn.close(); 
//			  } 
//	  		catch (SQLException e) { e.printStackTrace(); } 
//		  return  success; 
//	  }
	 

	// jbcrypt 버전 로그인
	  public boolean login(String id, String pwd) { 
		  Connection conn = DBConn.getConnection(); 
		  boolean success = false;
	  
		  PreparedStatement pstmt = null; 
		  try { 
			  pstmt = conn. prepareStatement("SELECT ID, PWD, EMAIL, NAME FROM TBL_MEMBER WHERE ID=?");
	  
			  pstmt.setString(1, id); 
			  ResultSet rs = pstmt.executeQuery();
			  success = rs.next();
			  
			  if(success) success = new PwdJbcrypt().pwdCheck(pwd, rs.getString("PWD"));
			  
			  pstmt.close();
			  conn.close(); 
		  }
		  catch (SQLException e) { e.printStackTrace();} 
		  return success; 
	}
	 

	public void join(Member member) {
		Connection conn = DBConn.getConnection();

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("INSERT INTO TBL_MEMBER VALUES (?, ?, ?, ?)");
			int idx = 1;
			pstmt.setString(idx++, member.getId());
			pstmt.setString(idx++, member.getPwd());
			pstmt.setString(idx++, member.getEmail());
			pstmt.setString(idx++, member.getName());

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			// select : executeQuery
			// insert, update, delete : executeUpdate
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(String id) {
		Connection conn = DBConn.getConnection();

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("UPDATE TBL_BOARD SET ID='탈주닌자' WHERE ID=?");
			int idx = 1;
			pstmt.setString(idx++, id);
			pstmt.executeUpdate();
			conn.commit();

			pstmt = conn.prepareStatement("UPDATE TBL_REPLY SET ID='탈주닌자', NAME='탈주닌자' WHERE ID=?");
			idx = 1;
			pstmt.setString(idx++, id);
			pstmt.executeUpdate();
			conn.commit();

			pstmt = conn.prepareStatement("DELETE TBL_MEMBER WHERE ID=?");
			idx = 1;
			pstmt.setString(idx++, id);
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void update(Member member) {
		Connection conn = DBConn.getConnection();

		// 글 작성
		try {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE TBL_MEMBER SET PWD=?, EMAIL=?, NAME=? WHERE ID=?");
			int idx = 1;
			pstmt.setString(idx++, member.getPwd());
			pstmt.setString(idx++, member.getEmail());
			pstmt.setString(idx++, member.getName());
			pstmt.setString(idx++, member.getId());

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Member findBy(String SELECT, String param) {
		Connection conn = DBConn.getConnection();
		Member member = null;

		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT ID, PWD, EMAIL, NAME FROM TBL_MEMBER WHERE " + SELECT + "=?");
			pstmt.setString(1, param);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int idx = 1;
				member = new Member(rs.getString(idx++), rs.getString(idx++), rs.getString(idx++), rs.getString(idx++));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
}
