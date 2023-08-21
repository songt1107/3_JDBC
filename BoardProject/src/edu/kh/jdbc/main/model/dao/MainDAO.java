package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;
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
import edu.kh.jdbc.member.model.dto.Member;

public class MainDAO {

	// 필드
	// JDBC 객체 참조 변수
	private Statement stmt; // SQL 수행, 결과 반환
	private PreparedStatement pstmt; // placeholder를 포함한 SQL 세팅/수행
	private ResultSet rs; // SELECT 수행 결과 저장
	
	private Properties prop;
	// - Map<String, String> 형태
	// - XML 파일 입/출력 메서드를 제공
	
	// 기본생성자
	public MainDAO() {
		
		// DAO 객체가 생성될 때 Xml 파일을 읽어와 Properties 객체에 저장
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-sql.xml"));
			
			// -> prop.getProperty("key") 호출
			// --> value (SQL) 반환
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/** 로그인 DAO
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		Member member = null;
		
		try {
			
			String sql = prop.getProperty("login");
			
			// 2. SQL 작성 후 수행
			/*
			 * SELECT MEMBER_NO, MEMBER_ID, MEMBER_NM, MEMBER_GENDER,
				TO_CHAR(ENROLL_DT, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') ENROLL_DT
				FROM MEMBER
				WHERE MEMBER_ID = ?
				AND MEMBER_PW = ?
				AND UNREGISTER_FL = 'N'
			 * */
			
			pstmt = conn.prepareStatement(sql);
			
			// placeholder에 알맞은값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			rs = pstmt.executeQuery(); // SELECT 수행 후 결과 반환 받기
			
			// 3. 조회 결과를 1행씩 접근해서 얻어오기
			if(rs.next()) {
				int memberNo = rs.getInt("MEMBER_NO");
				
				//String memberId = rs.getString("MEMBER_ID");
				// 입력받은 아이디 == 조회한 아이디
				// -> DB에서 얻어올 필요가 없음
				
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String enrollDate = rs.getString("ENROLL_DT");
				
				member = new Member();
				
				member.setMemberNo(memberNo);
				member.setMemberId(memberId);
				member.setMemberName(memberName);
				member.setMemberGender(memberGender);
				member.setEnrollDate(enrollDate);
				
				
			}
			
		} finally {
			// 4. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
			
		}
		// 5. 결과반환
		return member;
	}

	/** 회원가입 DAO
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public Member signup(Connection conn, String memberId, String memberPw, String memberName, String memberGender) throws Exception{
		
		Member member = null;

	    try {
	        String sql = prop.getProperty("signup");
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, memberId);
	        pstmt.setString(2, memberPw);
	        pstmt.setString(3, memberName);
	        pstmt.setString(4, memberGender);
	       
            rs = pstmt.executeQuery();
	            if (rs.next()) {
	               
	                member = new Member();
	                member.setMemberId(memberId);
	                member.setMemberName(memberName);
	                member.setMemberGender(memberGender);
	            }

	    } finally {
	        close(rs);
	        close(pstmt);
	    }

	    return member;
	}
	
    /** 아이디 중복 검사 DAO
     * @param conn
     * @param memberId
     * @return
     */
	public int idDuplicationCheck(Connection conn, String memberId) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("idDuplicationCheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	
	/** 회원가입 SQL 수행( INSERT )
	 * @param conn
	 * @param member
	 * @return
	 */
	public int signUp(Connection conn, Member member) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
}
