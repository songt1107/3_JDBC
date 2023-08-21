package edu.kh.jdbc.board.model.dto;

import java.util.List;

public class Board {
	private int boardNo;
    private String boardTitle;
    private String createDate;
    private int readCount;
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    
    public Board() {}
    
    
    public Board(int boardNo, String boardTitle, String createDate, int readCount, List<Comment> comments) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.createDate = createDate;
		this.readCount = readCount;
		this.comments = comments;
	}


	public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

	@Override
	public String toString() {
		final int maxLen = 10;
		return "Board [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", createDate=" + createDate
				+ ", readCount=" + readCount + ", comments="
				+ (comments != null ? comments.subList(0, Math.min(comments.size(), maxLen)) : null) + "]";
	}
   
    
    
}
