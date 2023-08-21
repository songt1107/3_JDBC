package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;

public class BoardService {

	private BoardDAO dao = new BoardDAO();
	
	
	/** 게시글 목록 조회 서비스
	 * 
	 */
	public List<Board> getBoardList() throws Exception {
        Connection conn = getConnection();
        List<Board> boardList = dao.getBoardList(conn); // MainDAO의 메서드를 호출하여 게시글 목록 조회
        close(conn);
        return boardList;
    }
	
	/** 게시글 상세 조회(+ 댓글 기능) 서비스
	 * 
	 */
	public Board getBoardDetail(int boardNo) throws Exception {
	    Connection conn = getConnection();

	    try {
	        Board board = dao.selectBoard(conn, boardNo);
	        if (board != null) {
	            List<Comment> comments = dao.selectComments(conn, boardNo);
	            board.setComments(comments);
	        }
	        return board;
	    } finally {
	        close(conn);
	    }
	}

	// 댓글 추가 기능 추가
	public int insertComment(Comment comment) throws Exception {
	    Connection conn = getConnection();
	    try {
	        return dao.insertComment(conn, comment);
	    } finally {
	        close(conn);
	    }
	}
}
