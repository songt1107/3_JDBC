package edu.kh.jdbc.main.view;

import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.model.dto.Member;

public class MainView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MainService service = new MainService();
	
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
					case 2: signup(); break;
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
					case 1: memberMenu(); break;
					case 2: break;
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
	private void signup() {
		System.out.println("\n[회원가입]\n");
		
		try {
	        // 입력 받기
	        System.out.println("아이디 : ");
	        String memberId = sc.next();

	        System.out.println("비밀번호 : ");
	        String memberPw = sc.next();

	        System.out.println("비밀번호 확인 : ");
	        String memberPw2 = sc.next();

	        if (!memberPw.equals(memberPw2)) {
	            System.out.println("비밀번호가 다릅니다.");
	            return;
	        }

	        System.out.println("이름 : ");
	        String memberName = sc.next();

	        System.out.println("성별(M/F) : ");
	        String memberGender = sc.next();

	        // 회원가입 서비스 호출
	        Session.loginMember = service.signup(memberId, memberPw, memberName, memberGender);

	        if (Session.loginMember != null) {
	            System.out.println("회원 가입 성공!");
	        } else {
	            System.out.println("회원 가입 실패...");
	        }

	    } catch (Exception e) {
	        System.out.println("회원 가입 중 예외 발생");
	        e.printStackTrace();
	    }
		
	}
	
	/** 회원 기능
	 * 
	 */
	private void memberMenu() {
	    int input = 0;

	    do {
	        try {
	            System.out.println("\n ==========회원 기능========== \n");
	            System.out.println("1. 내 정보 조회");
	            System.out.println("2. 회원 목록 조회");
	            System.out.println("3. 내 정보 수정");
	            System.out.println("4. 비밀번호 변경");
	            System.out.println("5. 회원 탈퇴");
	            System.out.println("0. 이전 메뉴로 돌아가기");

	            System.out.println("\n메뉴선택 : ");
	            input = sc.nextInt();
	            sc.nextLine();

	            switch (input) {
	                case 1:
	                    displayMyInfo();
	                    break;
	                case 2:
	                    displayMemberList();
	                    break;
	                case 3:
	                    updateMyInfo();
	                    break;
	                case 4:
	                    changePassword();
	                    break;
	                case 5:
	                    withdrawMember();
	                    break;
	                case 0:
	                    System.out.println("\n === 이전 메뉴로 돌아갑니다 === \n");
	                    break;
	                default:
	                    System.out.println("\n***메뉴 번호만 입력해 주세요***\n");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } while (input != 0);
	}
	
	/** 내 정보 조회
	 * 
	 */
	private void displayMyInfo() {
	    if (Session.loginMember != null) {
	        Member member = Session.loginMember;
	        System.out.println("\n === 내 정보 조회 ===");
	        System.out.println("아이디 : " + member.getMemberId());
	        System.out.println("이름 : " + member.getMemberName());
	        System.out.println("성별 : " + member.getMemberGender());
	    } else {
	        System.out.println("\n로그인이 필요한 서비스입니다.");
	    }
	}
	
	/** 회원 목록 조회
	 * 
	 */ 
	private void displayMemberList() {
	    try {
	        List<Member> memberList = service.getMemberList();
	        System.out.println("\n === 회원 목록 조회 ===");
	        System.out.println("아이디\t이름\t성별");
	        for (Member member : memberList) {
	            System.out.printf("%s\t%s\t%s%n", member.getMemberId(), member.getMemberName(), member.getMemberGender());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/** 내 정보 수정
	 * 
	 */
	private void updateMyInfo() {
	    if (Session.loginMember != null) {
	        Member member = Session.loginMember;
	        System.out.println("\n === 내 정보 수정 ===");
	        System.out.println("이름 (기존 이름: " + member.getMemberName() + ") : ");
	        String newName = sc.nextLine();

	        System.out.println("성별 (기존 성별: " + member.getMemberGender() + ") : ");
	        String newGender = sc.nextLine();

	        try {
	            boolean success = service.updateMyInfo(member.getMemberId(), newName, newGender);
	            if (success) {
	                System.out.println("내 정보 수정이 완료되었습니다.");
	                member.setMemberName(newName);
	                member.setMemberGender(newGender);
	            } else {
	                System.out.println("내 정보 수정에 실패하였습니다.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("\n로그인이 필요한 서비스입니다.");
	    }
	}

	/** 비밀번호 변경
	 * 
	 */
	private void changePassword() {
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
	            boolean success = service.changePassword(member.getMemberId(), currentPassword, newPassword, newPasswordConfirm);
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

	/** 회원 탈퇴
	 * 
	 */
	private void withdrawMember() {
	    if (Session.loginMember != null) {
	        Member member = Session.loginMember;
	        System.out.println("\n === 회원 탈퇴 ===");
	        System.out.println("보안 코드 : ");
	        String securityCode = sc.next();

	        System.out.println("비밀번호 : ");
	        String password = sc.next();

	        try {
	            boolean success = service.withdrawMember(member.getMemberId(), securityCode, password);
	            if (success) {
	                System.out.println("회원 탈퇴가 완료되었습니다.");
	                Session.loginMember = null;
	            } else {
	                System.out.println("회원 탈퇴에 실패하였습니다.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("\n로그인이 필요한 서비스입니다.");
	    }
	}
	
}
