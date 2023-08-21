package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.model.dto.Member;

public class MemberService {

	private MemberDAO dao = new MemberDAO();
	
	/** 회원 목록 조회 서비스
	 * 
	 */
	public List<Member> selectMemberList() throws Exception {
	   
		Connection conn = getConnection();
	    
	    List<Member> memberList = dao.selectMemberList(conn);

	    close(conn);
	    
	    return memberList;
	}

	/** 내 정보 수정 서비스
	 * 
	 */
	public int updateMember(String memberName, String memberGender, int memberNo) throws Exception {
	    Connection conn = getConnection();
	    
	    int result = dao.updateMember(conn, memberName, memberGender, memberNo);	    
	    
	    // 트랜잭션 처리
	    if(result > 0) commit(conn);
	    else 			rollback(conn);
	    
	    close(conn);

	    return result;
	}

	/** 비밀번호 변경 서비스
	 * 
	 */
	public boolean updatePassword(String memberId, String currentPassword, String newPassword, String newPasswordConfirm) throws Exception {
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
	public boolean deleteMember(String pwcheck) throws Exception {
	    Connection conn = getConnection();
	    boolean success = false;

	    try {
	        success = dao.deleteMember(conn, pwcheck);
	    } finally {
	        close(conn);
	    }

	    return success;
	}
	
	
}
