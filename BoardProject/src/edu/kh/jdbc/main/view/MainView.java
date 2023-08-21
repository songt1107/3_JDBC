package edu.kh.jdbc.main.view;

import java.util.Scanner;

import edu.kh.jdbc.board.view.BoardView;
import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.view.MemberView;

public class MainView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MainService service = new MainService();
	
	private MemberView memberView = new MemberView();
	
	private BoardView boardView = new BoardView();
	
	/** 메인 메뉴 출력
	 * 
	 */
	public void mainMenu() {
		
		int input = 0;
		
		do {
			
			try {
				
				if(Session.loginMember == null) { // 로그인 X
					
					System.out.println("\n ==========회원제 게시판 프로그램========== \n");
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("0. 프로그램 종료");
					
					System.out.println("\n메뉴선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: login(); break;
					case 2: signUp(); break;
					case 0: System.out.println("\n====프로그램 종료====\n"); break;
					default: System.out.println("\n***메뉴 번호만 입력해 주세요***\n");
					}
					
				} else { // 로그인 O
					
					System.out.println("\n ==========로그인 메뉴========== \n");
					System.out.println("1. 회원 기능");
					System.out.println("2. 게시판 기능");
					System.out.println("3. 로그아웃");
					System.out.println("0. 프로그램 종료");
					
					System.out.println("\n메뉴선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: memberView.memberMenu(); break;
					case 2: boardView.boardMenu(); break;
					case 3: 
						System.out.println("\n ===로그아웃 되었습니다=== \n");
						
						Session.loginMember = null; // 참조하고 있던 로그인 회원 객체를 없앰
						
						break;
					case 0: System.out.println("\n====프로그램 종료====\n"); break;
					default: System.out.println("\n***메뉴 번호만 입력해 주세요***\n");
					
					}
					
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}while(input != 0);
		
		
	}

	/** 로그인
	 * 
	 */
	private void login() {
		System.out.println("\n[로그인]\n");
		
		System.out.println("아이디 : ");
		String memberId = sc.next();
		
		System.out.println("비밀번호 : ");
		String memberPw = sc.next();
		
		try {
			
			// 로그인 서비스 호출 후 결과 반환받기
			// -> 반환 받은 결과는 Session.loginMember에 저장
			Session.loginMember = service.login(memberId, memberPw);
			
			if(Session.loginMember == null) { // 로그인 실패
				System.out.println("\n** 아이디 / 비밀번호가 일치하지 않습니다. **\n");
			}else {
				System.out.printf("\n === %s 님 환영합니다 === \n\n",
								Session.loginMember.getMemberName()
						);
								
			}
			
		}catch(Exception e) {
			System.out.println("\n*******로그인 중 예외 발생*******\n");
			e.printStackTrace();
		}
		
	}
	
	/** 회원가입
	 * 
	 */
	private void signUp() {
			System.out.println("\n[회원가입]\n");
			
	        // *참고* 시퀀스 할 때 NOCACHY
			// 아이디, 비밀번호, 이름, 성별(M/F)을 저장할 변수 선언
			String memberId = null;
			String memberPw = null;
			String pwConfirm = null; // 비밀번호 확인용 변수
			String memberName = null;
			String memberGender = null;
			
	        try {
	        	
	        	// 아이디 입력
	        	while(true) {
	        		
	        		System.out.print("아이디 입력 : ");
	        		memberId = sc.next();
	        		
	        		// 아이디 중복 확인 서비스 호출
	        		// -> 중복인 경우 1, 아니면 0 반환
	        		int result = service.idDuplicationCheck(memberId);
	        		
	        		if(result == 0) {
	        			System.out.println("\n=== 사용 가능한 아이디 입니다 ===\n");
	        			break;
	        		}else {
	        			System.out.println("\n=== 이미 사용중인 아이디 입니다 ===\n");
	        		}
	        	
	        	}
	        	
	        	// 비밀번호, 비밀번호 확인 입력을 받아서 둘이 같을 때 까지 무한반복
	        	while(true) {
	        		System.out.print("비밀번호 입력 : ");
	        		memberPw = sc.next();
	        		
	        		System.out.print("비밀번호 확인 : ");
	        		pwConfirm = sc.next();
	        		
	        		if(memberPw.equals(pwConfirm)) {
	        			System.out.println("===비밀번호 일치===");
	        			break;
	        		}else {
	        			System.out.println("===비밀번호가 일치하지 않습니다===");
	        		}
        		
	        	}
	        	
	        	// 이름 입력
	        	System.out.println("이름 : ");
	        	memberName = sc.next();
	        	
	        	// 성별 입력
	        	// M또는 F가 입력될 때까지 무한 반복
	        	while(true) {
	        		System.out.println("성별 : ");
	        		memberGender = sc.next().toUpperCase();
	        		
	        		if(memberGender.equals("M") || memberGender.equals("F")) {
	        			break;
	        		} else {
	        			System.out.println("***M 또는 F만 입력해주세요***");
	        		}
	        	}
	        
	        	// Member 객체 생성하여 입력받은 값 세팅
	        	Member member = new Member();
	        	member.setMemberId(memberId);
	        	member.setMemberPw(memberPw);
	        	member.setMemberName(memberName);
	        	member.setMemberGender(memberGender);
	        	
	        	// 회원 가입 서비스 호출
	        	int result = service.signUp(member);
	        	
	        	if(result > 0) {
	        		System.out.println("\n====회원가입 성공====\n");
	        	} else {
	        		System.out.println("\n====회원가입 실패====\n");
	        	}
	        	
	        } catch (Exception e) {
	        	System.out.println("\n****회원 가입 중 예외 발생****\n");
	        	e.printStackTrace();
	        }
		
	}
	
	
	
}
