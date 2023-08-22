package edu.kh.jdbc.member.view;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.model.service.MemberService;

public class MemberView {
	
	private MemberService service = new MemberService();

	private Scanner sc = new Scanner(System.in);
	/** 회원 기능 메뉴
	 * 
	 */
	public void memberMenu() {
		 
	    int input = 0;

	    do {
	        try {
	            System.out.println("\n =======회원 기능======= \n");
	            System.out.println("1. 내 정보 조회");
	            System.out.println("2. 회원 목록 조회(아이디, 이름, 성별)");
	            System.out.println("3. 내 정보 수정(이름, 성별)");
	            System.out.println("4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
	            System.out.println("5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
	            System.out.println("9. 메인 메뉴로 돌아가기");
	            System.out.println("0. 프로그램 종료");

	            System.out.println("\n메뉴선택 : ");
	            input = sc.nextInt();
	            sc.nextLine();

	            switch (input) {
	                case 1: selectMyInfo(); break;
	                case 2: selectMemberList(); break;
	                case 3: updateMember(); break;
	                case 4: updatePassword(); break;
	                case 5: 
	                	if( unRegisterMember() ) {
	                		return;
	                	}
	                	break;
	                case 9: 
	                    System.out.println("\n === 메인 메뉴로 돌아갑니다 === \n");
	                    break;
	                case 0: System.out.println("====프로그램 종료====");
	                	// JVM 강제 종료 구문
	                	System.exit(0);
	                default:
	                    System.out.println("\n***메뉴 번호만 입력해 주세요***\n");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } while (input != 9);
	}
	
	/** 내 정보 조회
	 * 
	 */
	private void selectMyInfo() {
	        System.out.println("\n === 내 정보 조회 ===");
	        
	        // Session.loginMember 이용
	        
	        System.out.println("회원번호 : " + Session.loginMember.getMemberNo());
	        System.out.println("아이디 : " + Session.loginMember.getMemberId());
	        System.out.println("이름 : " + Session.loginMember.getMemberName());
	        
	        if(Session.loginMember.getMemberGender().equals("M")){
	        	System.out.println("성별 : 남");
	        } else {
	        	System.out.println("성별 : 여");
	        }
	        System.out.println("가입일 : " + Session.loginMember.getEnrollDate());
	
	}
	
	/** 회원 목록 조회
	 * 
	 */ 
	private void selectMemberList() {
		System.out.println("\n=== 회원 목록 조회 ===");
	   
		try {
			// 회원 목록 조회 서비스 호출 후 결과 반환받기.
			
	        List<Member> memberList = service.selectMemberList();
	        
	        if(memberList.isEmpty()) {
	        	System.out.println("\n=== 조회 결과가 없습니다===\n");
	        } else {
	        	for(int i=0; i<memberList.size(); i++) {
	        	
	        		System.out.printf("%d\t\t%s\t\t%s\t\t%s \n", i+1,
	        				memberList.get(i).getMemberId(),
	        				memberList.get(i).getMemberName(),
	        				memberList.get(i).getMemberGender()
	        				);
	        	}
	        }
	        
	    } catch (Exception e) {
	    	System.out.println("\n=== 회원 목록 조회 중 예외 발생 ===");
	        e.printStackTrace();
	    }
		
	}

	/** 내 정보 수정
	 * 
	 */
	private void updateMember() {
	  
	    System.out.println("\n === 내 정보 수정 ===");
	   
	    System.out.print("수정할 이름 : ");
	    String memberName = sc.next();
	    String memberGender = null;

	    while(true) {
	    	System.out.println("수정할 성별(M/F) : ");
	    	
	    	memberGender = sc.next().toUpperCase();
	    	
	    	if(memberGender.equals("M") || memberGender.equals("F")){
	    		break;
	        }
	    	
	        System.out.println("[M또는 F를 입력]");
	    }
	    
	    try {
	    	int result = service.updateMember(memberName, memberGender, Session.loginMember.getMemberNo());
	    	
	    	if(result > 0) {
	    		System.out.println("\n==수정되었습니다==\n");
	    		
	    		// DB와 Java프로그램 데이터 동기화 필요!!
	    		Session.loginMember.setMemberName(memberName);
	    		Session.loginMember.setMemberGender(memberGender);
	    	
	    	}else {
	    		System.out.println("\n==수정 실패==\n");
	    	}
	    	
	    } catch(Exception e) {
	    	System.out.println("\n***내정보 수정 중 예외발생***\n");
	    	e.printStackTrace();
	    }
	}

	/** 비밀번호 변경
	 * 
	 */
	private void updatePassword() {
	        System.out.println("\n === 비밀번호 변경 ===");
	        
	        System.out.println("현재 비밀번호 : ");
	        String current = sc.next();

	        String newPw1 = null;
	        
	        while(true) {
	        // 새 비밀번호
	        System.out.println("새 비밀번호 : ");
	        newPw1 = sc.next();
	        
	        // 새 비밀번호 확인
	        System.out.println("새 비밀번호 확인 : ");
	        String newPw2 = sc.next();
	        
	        // 같을 때 까지 무한반복
	        if(newPw1.equals(newPw2)) {
	        	break;
	        }
	        // 아닐 때
	        System.out.println("\n***새 비밀번호가 일치하지 않습니다***\n");
	    }
	    try {
	        	
	        // 서비스 호출 ( 현재 비밀번호, 새 비밀번호, 로그인한 회원 번호 )
	    	int result = service.updatePassword(current, newPw1, Session.loginMember.getMemberNo());
	    	// 성공하면 -> 1 / 실패하면 -> 0
	    	
	    	if(result > 0) {
	    		System.out.println("\n===비밀번호가 변경되었습니다===\n");
	    	}else {
	    		System.out.println("\n===현재 비밀번호가 일치하지 않습니다===\n");
	    	}
	        	
	        } catch(Exception e) {
	        	System.out.println("\n***비밀번호 변경 중 예외 발생***\n");
	        	e.printStackTrace();
	    }
	    
	}
	
	/** 회원 탈퇴
	 * @return true / false
	 */
    private boolean unRegisterMember() {

    		System.out.println("\n=== 회원 탈퇴 ===\n");
    		
            System.out.println("현재 비밀번호 : ");
            String memberPw = sc.next();

            String code = service.createSecurityCode();
            System.out.printf("보안문자 입력 [%s] :", code); // 보안문자 입력 [574021] : 574021
            String input = sc.next();
            
            // 보안 문자 일치여부 확인
            if (!input.equals(code)) {
    	         System.out.println("\n***보안 문자가 일치하지 않습니다***\n");
    	         return false;
    	    }
            while(true) {
            	System.out.println("정말 탈퇴 하시겠습니까?(Y/N) : ");
            	char check = sc.next().toUpperCase().charAt(0);
            	
            	if(check == 'N') {
            		System.out.println("\n==탈퇴 취소==\n");
            		return false; // 메서드 종료
            	}
            	
            	if(check == 'Y') {
            		break; // 반복문 종료
            	}
            	
            	// 'Y' 'N'이 아닌 경우
            	System.out.println("\n***잘못 입력하셨습니다***\n");
            	
            }
            
            try {
            	// 회원 탈퇴 서비스 호출
            	int result = service.unRegisterMember(memberPw, Session.loginMember.getMemberNo()
            			);
            	
            	if(result > 0) {
            		System.out.println("\n=== 탈퇴되었습니다 ===\n");
            	
            		// 로그아웃
            		Session.loginMember = null;
            		
            		return true;
            		
            	} else {
            		System.out.println("\n***비밀번호가 일치하지 않습니다***\n");
            	}
            	
            } catch(Exception e) {
            	System.out.println("\n***회원 탈퇴 중 예외 발생***\n");
            	e.printStackTrace();
            }
       return false;
    }
	
}
