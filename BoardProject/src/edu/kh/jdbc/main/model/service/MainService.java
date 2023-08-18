package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

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
	
	/** 회원 목록 조회 서비스
	 * 
	 */
	public List<Member> getMemberList() throws Exception {
	    Connection conn = getConnection();
	    List<Member> memberList = null;

	    try {
	        memberList = dao.selectMemberList(conn);
	    } finally {
	        close(conn);
	    }

	    return memberList;
	}

	/** 내 정보 수정 서비스
	 * @param memberId
	 * @param newName
	 * @param newGender
	 * @return
	 */
	public boolean updateMyInfo(String memberId, String newName, String newGender) throws Exception {
	    Connection conn = getConnection();
	    boolean success = false;

	    try {
	        success = dao.updateMemberInfo(conn, memberId, newName, newGender);
	    } finally {
	        close(conn);
	    }

	    return success;
	}

	/** 비밀번호 변경 서비스
	 * 
	 */
	public boolean changePassword(String memberId, String currentPassword, String newPassword, String newPasswordConfirm) throws Exception {
	    Connection conn = getConnection();
	    boolean success = false;

	    try {
	        success = dao.updateMemberPassword(conn, memberId, currentPassword, newPassword, newPasswordConfirm);
	    } finally {
	        close(conn);
	    }

	    return success;
	}

	/** 회원 탈퇴 서비스
	 * 
	 */
	public boolean withdrawMember(String memberId, String securityCode, String password) throws Exception {
	    Connection conn = getConnection();
	    boolean success = false;

	    try {
	        success = dao.deleteMember(conn, memberId, securityCode, password);
	    } finally {
	        close(conn);
	    }

	    return success;
	}
	
	

}
