package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dto.Board;
import edu.kh.jdbc.board.model.dto.Comment;
import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.model.dto.Member;

public class MainService {

	private MainDAO dao = new MainDAO();
	
	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member login(String memberId, String memberPw) throws Exception{
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. DAO 호출
		Member member = dao.login(conn, memberId, memberPw);
		
		// 3. Connection 반환
		close(conn);
		
		return member;
	}

	/** 회원가입 서비스
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member signup(String memberId, String memberPw, String memberName, String memberGender) throws Exception{

		Connection conn = getConnection();
		
		try {
	        // 회원가입 DAO 호출
	        Member member = dao.signup(conn, memberId, memberPw, memberName, memberGender);

	        // commit 실행
	        commit(conn);

	        return member;
	    } catch (Exception e) {
	        rollback(conn);
	        throw e;
	    } finally {
	        close(conn);
	    }
	}
	
	
	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return result
	 */
	public int idDuplicationCheck(String memberId) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.idDuplicationCheck(conn, memberId);
		
		close(conn);
		
		return result;
	}

	/** 회원가입 서비스
	 * @param member
	 * @return
	 */
	public int signUp(Member member) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.signUp(conn, member); // -> INSERT 수행
		
		// 트랜잭션 처리
		if(result > 0) commit(conn);
		else 			rollback(conn);
		
		return result;
	}
	

}
