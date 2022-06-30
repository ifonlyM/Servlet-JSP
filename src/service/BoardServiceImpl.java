package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dao.BoardDao;
import vo.Attach;
import vo.Board;
import vo.Criteria;

public class BoardServiceImpl implements BoardService {
	BoardDao dao = new BoardDao();
	@Override
	// 트랜잭션으로 동작해야함.
	public Long write(Board board) {
		// 글작성 > 후 글 번호 반환
		Long bno = dao.insert(board);
		
		for(Attach attach: board.getAttachs()) {
			if(attach == null || attach.getUuid() == null) continue;
			attach.setBno(bno); // 각 첨부파일에 글번호 부여
			attach.setUploadTime(
					new SimpleDateFormat("HHmmssSSS").format(new Date(System.currentTimeMillis()))
			); // 업로드 시간 기록
			dao.writeAttach(attach); // 첨부 파일 작성
		}
		return bno; 
	}

	@Override
	public Board read(Long bno) {
		Board board = dao.read(bno);
		board.setAttachs(dao.readAttach(bno));
		return board;
	}

	@Override
	public List<Board> list() {
		return dao.list();
	}
	
	@Override
	public List<Board> list(Criteria cri) {
		// TODO Auto-generated method stub
		List<Board> list = dao.list(cri);
		list.forEach(b -> b.setAttachs(dao.readAttach(b.getBno())));
		return list;
	}

	@Override
	public void modify(Board board) {
		dao.update(board);
		
		// 기존에 있던 첨부파일 전부 삭제
		dao.deleteAttach(board.getBno());
		
		// 첨부파일 새로 작성
		for(Attach a : board.getAttachs()) {
			if(a == null || a.getUuid() == null) continue;
			a.setBno(board.getBno());
			a.setUploadTime(new SimpleDateFormat("HHmmssSSS").format(new Date(System.currentTimeMillis())));
			dao.writeAttach(a);
		}
	}

	@Override
	public void remove(Long bno) {
		// 업로드 폴더에있는 파일도 지워야 하나?
		dao.deleteAttach(bno);
		dao.delete(bno);
	}

	@Override
	public String findOriginBy(String uuid) {
		// TODO Auto-generated method stub
		return dao.findOriginBy(uuid);
	}

	@Override
	public int getCount(Criteria cri) {
		// TODO Auto-generated method stub
		return dao.getCount(cri);
	}

	@Override
	public List<Attach> readAttachByPath(String path) {
		// TODO Auto-generated method stub
		return dao.readAttachByPath(path);
	}

	@Override
	public void removeAttachBy(Long bno, String uuid) {
		// TODO Auto-generated method stub
		dao.deleteAttach(bno, uuid);
	}
	
	

}
