package edu.kh.jdbc.member.view;

import java.util.List;
import java.util.Random;
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
	            System.out.println("\n ==========회원 기능========== \n");
	            System.out.println("1. 내 정보 조회");
	            System.out.println("2. 회원 목록 조회");
	            System.out.println("3. 내 정보 수정");
	            System.out.println("4. 비밀번호 변경");
	            System.out.println("5. 회원 탈퇴");
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
	                case 5: deleteMember(); break;
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
	    if (Session.loginMember != null) {
	        Member member = Session.loginMember;
	        System.out.println("\n === 비밀번호 변경 ===");
	        System.out.println("현재 비밀번호 : ");
	        String currentPassword = sc.next();

	        System.out.println("새 비밀번호 : ");
	        String newPassword = sc.next();

	        System.out.println("새 비밀번호 확인 : ");
	        String newPasswordConfirm = sc.next();

	        try {
	            boolean success = service.updatePassword(member.getMemberId(), currentPassword, newPassword, newPasswordConfirm);
	            if (success) {
	                System.out.println("비밀번호 변경이 완료되었습니다.");
	            } else {
	                System.out.println("비밀번호 변경에 실패하였습니다.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("\n로그인이 필요한 서비스입니다.");
	    }
	}
	

    // 6자리의 랜덤 숫자로 구성된 보안 코드 생성
    private static String generateSecurityCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();
    }
    
    public static boolean checkCurrentPassword(String password) {
        return true;
    }
	/** 회원 탈퇴
	 * 
	 */
    private void deleteMember() {

    		Member member = Session.loginMember;
    		Scanner sc = new Scanner(System.in);
            String securityCode = generateSecurityCode();

            System.out.println("회원 탈퇴를 진행하시려면 보안 코드를 입력하세요.");
            System.out.println("보안 코드 : " + securityCode);

            System.out.print("보안 코드 입력: ");
            String inputCode = sc.nextLine();

            System.out.println("비밀번호 확인 : ");
            String pwcheck = sc.nextLine();
            
            // 입력한 보안 코드와 생성한 보안 코드 비교
            if (securityCode.equals(inputCode) && member.getMemberPw() != null && member.getMemberPw().equals(pwcheck)) {
            	try {
    	            boolean success = service.deleteMember(pwcheck);
    	            System.out.println("회원 탈퇴 성공");
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
            } else {
                System.out.println("보안코드 혹은 비밀번호가 일치하지 않습니다.");
            }
        }
	
}
