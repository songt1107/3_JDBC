package edu.kh.jdbc.board.view;

import java.util.List;
import java.util.Scanner;
import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;
import edu.kh.jdbc.board.model.service.BoardService;

public class BoardView {
	
	private BoardService service = new BoardService();
	
	private Scanner sc = new Scanner(System.in);
	/** 게시판 기능
	 * 
	 */
	public void boardMenu() {
		int input = 0;
		do {
			try {
	            System.out.println("\n ==========게시판 기능========== \n");
	            System.out.println("1. 게시글 목록 조회");
	            System.out.println("2. 게시글 상세 조회(+ 댓글 기능)");
	            System.out.println("3. 게시글 작성");
	            System.out.println("4. 게시글 검색");
	            System.out.println("9. 메인 메뉴로 돌아가기");
	            System.out.println("0. 프로그램 종료");

	            System.out.println("\n메뉴선택 : ");
	            input = sc.nextInt();
	            sc.nextLine();

	            switch (input) {
	                case 1: getBoardList(); break;
	                case 2: 
	                	System.out.println("게시글 번호 조회");
	                	int boardNo = sc.nextInt();
	                	selectBoard(boardNo); break;
	                case 3: break;
	                case 4: break;
	                case 9: break;
	                case 0:
	                    System.out.println("\n====프로그램 종료====\n");
	                    System.exit(0);
	                    break;
	                default:
	                    System.out.println("\n***메뉴 번호만 입력해 주세요***\n");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } while (input != 0);
	}
	
	
	/** 게시글 목록 조회
	 * 
	 */
	private void getBoardList() {
	    try {
	        List<Board> boardList = service.getBoardList();

	        if (boardList.isEmpty()) {
	            System.out.println("\n*** 등록된 게시글이 없습니다. ***\n");
	        } else {
	            System.out.println("\n=====게시글 목록=====");
	            for (Board board : boardList) {
	                System.out.printf("%d. %s / %s / %s\n", board.getBoardNo(), board.getBoardTitle(), board.getCreateDate(), board.getReadCount());
	            }
	            System.out.println("=====================\n");
	        }
	    } catch (Exception e) {
	        System.out.println("\n*** 게시글 목록 조회 중 예외 발생 ***\n");
	        e.printStackTrace();
	    }
	}
	
	/** 게시글 상세 조회(+ 댓글 기능)
	 * 
	 */
	private void selectBoard(int boardNo) {
	    try {
	        Board board = service.getBoardDetail(boardNo);
	        if (board != null) {
	            System.out.println("=== 게시글 상세 내용 ===");
	            System.out.println("글번호: " + board.getBoardNo());
	            System.out.println("제목: " + board.getBoardTitle());
	            System.out.println("작성일: " + board.getCreateDate());
	            System.out.println("조회수: " + board.getReadCount());
	            
	            List<Comment> comments = board.getComments();
	            if (comments != null && !comments.isEmpty()) {
	                System.out.println("\n=== 댓글 목록 ===");
	                for (Comment comment : comments) {
	                    System.out.println("댓글번호: " + comment.getCommentNo());
	                    System.out.println("내용: " + comment.getCommentContent());
	                    System.out.println("작성일: " + comment.getCreateDate());
	                    System.out.println("------------");
	                }
	            } else {
	                System.out.println("\n댓글이 없습니다.");
	            }
	            
	            // 댓글 추가 기능 호출
	            addComment(boardNo);
	        } else {
	            System.out.println("해당 게시글이 존재하지 않습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// 댓글 추가 기능 추가
	private void addComment(int boardNo) {
	    System.out.print("\n댓글 작성: ");
	    String content = sc.nextLine();
	    
	    Comment comment = new Comment();
	    comment.setBoardNo(boardNo);
	    comment.setCommentContent(content);
	    
	    try {
	        service.insertComment(comment);
	        System.out.println("댓글이 추가되었습니다.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("댓글 추가 중 오류가 발생했습니다.");
	    }
	}
	
}
