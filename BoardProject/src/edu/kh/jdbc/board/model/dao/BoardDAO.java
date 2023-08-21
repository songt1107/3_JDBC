package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;

public class BoardDAO {
	
	private Statement stmt; // SQL 수행, 결과 반환
	private PreparedStatement pstmt; // placeholder를 포함한 SQL 세팅/수행
	private ResultSet rs; // SELECT 수행 결과 저장
	
	private Properties prop;
	
	public BoardDAO() {
		
		// DAO 객체가 생성될 때 Xml 파일을 읽어와 Properties 객체에 저장
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-sql.xml"));
			
			// -> prop.getProperty("key") 호출
			// --> value (SQL) 반환
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/** 게시글 목록 조회 DAO
	 * 
	 */
	public List<Board> getBoardList(Connection conn) throws Exception {
        List<Board> boardList = new ArrayList<>();
        try {
            String sql = prop.getProperty("getBoardList");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int boardNo = rs.getInt("BOARD_NO");
                String title = rs.getString("BOARD_TITLE");
                String createDate = rs.getString("CREATE_DT");
                int readCount = rs.getInt("READ_COUNT");

                Board board = new Board();
                board.setBoardNo(boardNo);
                board.setTitle(title);
                board.setCreateDate(createDate);
                board.setReadCount(readCount);

                boardList.add(board);
            }
        } finally {
            close(rs);
            close(pstmt);
        }
        return boardList;
    }
	
	
	/** 게시글 상세 조회(+ 댓글 기능) DAO
	 * 
	 */
    public Board selectBoard(Connection conn, int boardNo) throws Exception {
        Board board = null;
        String sql = prop.getProperty("selectBoard");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new Board();

                board.setBoardNo(rs.getInt("BOARD_NO"));
                board.setTitle(rs.getString("BOARD_TITLE"));
                board.setCreateDate(rs.getString("CREATE_DATE"));
                board.setReadCount(rs.getInt("READ_COUNT"));

                // 기타 필요한 필드 설정
            }

        } finally {
            close(rs);
            close(pstmt);
        }

        return board;
    }

    // 댓글 조회 기능
    public List<Comment> selectComments(Connection conn, int boardNo) throws Exception {
        List<Comment> commentList = new ArrayList<>();
        String sql = prop.getProperty("selectComments");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();

                comment.setCommentNo(rs.getInt("COMMENT_NO"));
                comment.setCommentContent(rs.getString("COMMENT_CONTENT"));
                comment.setCreateDate(rs.getString("CREATE_DATE"));

                // 기타 필요한 필드 설정

                commentList.add(comment);
            }

        } finally {
            close(rs);
            close(pstmt);
        }

        return commentList;
    }

    // 댓글 추가 기능
    public int insertComment(Connection conn, Comment comment) throws Exception {
        int result = 0;
        String sql = prop.getProperty("insertComment");

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getCommentContent());
            pstmt.setInt(2, comment.getBoardNo());

            result = pstmt.executeUpdate();

        } finally {
            close(pstmt);
        }

        return result;
    }

}
