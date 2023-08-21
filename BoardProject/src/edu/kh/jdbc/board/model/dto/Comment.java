package edu.kh.jdbc.board.model.dto;

public class Comment {
	    private int commentNo;
	    private String commentContent;
	    private String createDate;
	    private int boardNo;
	    
	    
		public Comment(int commentNo, String commentContent, String createDate, int boardNo) {
			super();
			this.commentNo = commentNo;
			this.commentContent = commentContent;
			this.createDate = createDate;
			this.boardNo = boardNo;
		}
		
		
		public Comment() {}


		public int getCommentNo() {
			return commentNo;
		}
		public void setCommentNo(int commentNo) {
			this.commentNo = commentNo;
		}
		public String getCommentContent() {
			return commentContent;
		}
		public void setCommentContent(String commentContent) {
			this.commentContent = commentContent;
		}
		public String getCreateDate() {
			return createDate;
		}
		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}
		public int getBoardNo() {
			return boardNo;
		}
		public void setBoardNo(int boardNo) {
			this.boardNo = boardNo;
		}


		@Override
		public String toString() {
			return "Comment [commentNo=" + commentNo + ", commentContent=" + commentContent + ", createDate="
					+ createDate + ", boardNo=" + boardNo + "]";
		}

		
	    
}
