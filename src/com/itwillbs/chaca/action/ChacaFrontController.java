package com.itwillbs.chaca.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ChacaFrontController extends HttpServlet{

	//URL : http://localhost:8088/Model2/test.bo
	//URI : /Model2/test.bo
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("GET, POST방식 모두 호출 - doProcess() 실행 \n");
			
			// 1. 가상주소 계산------------------------------------------------
			System.out.println("------------------------");
			System.out.println("1. 가상주소 계산 - 시작");
			String requestURI = request.getRequestURI();
			System.out.println("C : requestURI : "+requestURI);
			
			String ctxPath = request.getContextPath();
			System.out.println("C : ctxPath : "+ctxPath);
			
			String command = requestURI.substring(ctxPath.length());
			System.out.println("C : command : "+command);						
			System.out.println("1. 가상주소 계산 - 끝 \n");
			// 1. 가상주소 계산 ------------------------------------------------
			
			
			// 2. 가상주소 매핑 ------------------------------------------------
			System.out.println("2. 가상주소 매핑 - 시작");
			Action action = null;
			ActionForward forward = null;
			
			// 메인
			if(command.equals("/Main.bo")){
				System.out.println("C : 메인 페이지");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./view/main.jsp");
				forward.setRedirect(false);
			}
			
			// 회원가입 페이지
			else if(command.equals("/Join.bo")){
				System.out.println("C : 회원가입 페이지");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./user/join.jsp");
				forward.setRedirect(false);
			}

			// 회원가입 - 아이디 중복확인 페이지
			else if(command.equals("/Idcheck.bo")){
				System.out.println("C : 아이디 중복확인 페이지");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./user/idCheckForm.jsp");
				forward.setRedirect(false);
			}
			
			// 회원가입 - 아이디 중복 확인 - 작업 - 임시
			else if(command.equals("/idCheckAction.bo")){
				System.out.println("C : 회원가입 - 아이디 중복확인 - 작업");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./user/idCheckPro.jsp");
				forward.setRedirect(false);
			}
			
			
//			// 회원가입 - 아이디 중복 확인 - 작업
//			else if(command.equals("/idCheckAction.bo")){
//				System.out.println("C : 회원가입 - 아이디 중복 확인 - 작업");
//				// System.out.println("C : DB작업 o, 페이지 이동 o");
//				
//				// JoinAction() 객체 생성
//				action = new JoinIdcheckAction(); // 자동 형변환
//				try {
//					forward = action.execute(request, response);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
			// 회원가입 - 작업
			else if(command.equals("/JoinAction.bo")){
				System.out.println("C : 회원가입 작업");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				// JoinAction() 객체 생성
				action = new JoinAction(); // 자동 형변환
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 회원가입 - 완료
			else if(command.equals("/JoinCompletion.bo")){
				System.out.println("C : 회원가입 완료");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./user/joinresult.jsp");
				forward.setRedirect(false);				
			}
			
			// 로그인 페이지
			else if(command.equals("/Login.bo")){
				System.out.println("C : 로그인 페이지");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./user/login.jsp");
				forward.setRedirect(false);				
			}
			
			// 로그인 - 완료
			else if(command.equals("/LoginAction.bo")){
				System.out.println("C : 로그인 완료");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				// LoginAction() 객체 생성
				action = new LoginAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 로그아웃
			else if(command.equals("/Logout.bo")){
				System.out.println("C : 로그아웃");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				// LogoutAction() 객체 생성
				action = new LogoutAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 이용안내
			else if(command.equals("/Information.bo")){
				System.out.println("C : 이용안내");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./view/info.jsp");
				forward.setRedirect(false);				
			}
			
			// 요금안내
			else if(command.equals("/Service.bo")){
				System.out.println("C : 요금안내");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./view/services.jsp");
				forward.setRedirect(false);
			}
			
			// 예약하기
			else if(command.equals("/Reservation.bo")){
				System.out.println("C : 예약하기");
				// System.out.println("C : DB정보가 필요없음 - view 페이지로 이동");
				
				forward = new ActionForward();
				forward.setPath("./reservation/pricing.jsp");
				forward.setRedirect(false);
			}
			
			// 차량 조회			
			else if(command.equals("/ReservationAction.bo")){
				System.out.println("C : 예약하기 - 차량 조회");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				// JoinAction() 객체 생성
				action = new CarReservation(); // 자동 형변환
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 예약하기 - 결제정보 페이지
			else if(command.equals("/Payment.bo")){
				System.out.println("C : 예약하기 - 결제정보 페이지");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				// JoinAction() 객체 생성
				action = new CarPayment(); // 자동 형변환
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 예약하기 - 결제정보 작업
			else if(command.equals("/PaymentAction.bo")){
				System.out.println("C : 예약하기 - 결제정보 작업");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				// JoinAction() 객체 생성
				action = new CarPaymentAction(); // 자동 형변환
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// --------------------- 예약하기 진행하면 추가해야함 ---------------------
			
			
			
			// 리뷰 구간 시작-----------------------------------------------------------
			else if(command.equals("/BoardWrite.bo")){
				// 패턴1
				System.out.println("(from BoardFrontController_doProcess) C: /BoardWrite.bo 주소 호출됨");
				System.out.println("(from BoardFrontController_doProcess) C: DB 정보 필요 X -> view 페이지(writeForm.jsp)로 이동시킬거");
				
				forward = new ActionForward();
				forward.setPath("./review/reviewWriteForm.jsp");
				forward.setRedirect(false); // forward 방식으로 이동
				
			}// if --- 패턴1 끝 --- /BoardWrite.bo

			else if(command.equals("/BoardWriteAction.bo")){
				// 패턴2
				System.out.println("(from BoardFrontController_doProcess) C: /BoardWriteAction.bo 주소 호출됨");
				System.out.println("(from BoardFrontController_doProcess) C: 패턴2= DB 정보 필요 O + 페이지 이동 O");
				
				action = new ReviewWriteAction(); 
				// 上              下             upcasting
				
				try {
					forward = action.execute(request, response); // execute 메서드 실행 결과 forward 갖고 오니까
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// else if  -- 패턴2 끝 -- /BoardWriteAction.bo
			
			else if(command.equals("/BoardList.bo")){
				// 패턴3
				System.out.println("(from BoardFrontController_doProcess) C: /BoardList.bo 주소 호출됨");
				System.out.println("(from BoardFrontController_doProcess) C: 패턴3= DB 정보 필요 O, 페이지 이동 X, 기존 페이지에 출력은 O");
				
				action = new ReviewListAction();
				// 上              下             upcasting
				
				try {
					System.out.println("(from BoardFrontController_doProcess) C: 해당 Model 객체 호출할 거");
					forward = action.execute(request, response);
							// execute 메서드 리턴 타입이 ActionForward니까, AF 타입인 forward 변수로 받아주기
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}// else if  -- 패턴3 끝 -- /BoardList.bo
			
			else if(command.equals("/BoardContent.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /BoardContent.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: 패턴3= DB 정보 필요 O, 페이지 이동 X, 기존 페이지에 출력은 O");
				
				action = new ReviewContentAction();
				// 上              下             upcasting
				
				try {
					System.out.println("(from BoardFrontController_doProcess) C: 해당 Model 객체 호출할 거");
					forward = action.execute(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}// else if --- 패턴3 끝 --- /BoardContent.bo 
			
			else if(command.equals("/BoardUpdate.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /BoardUpdate.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: DB 정보 필요 O, 해당 정보 view 페이지에 출력");
				
				action = new ReviewUpdateAction();
				
				try {
					forward = action.execute(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}// else if ---  /BoardUpdate.bo 
			
			else if(command.equals("/BoardUpdatePro.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /BoardUpdatePro.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: DB 사용 O, 페이지 이동 O");
				
				action = new ReviewUpdateProAction();
				
				try {
					forward = action.execute(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}// else if ---  /BoardUpdatePro.bo	
			
			else if(command.equals("/BoardDelete.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /BoardDelete.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: DB 사용 O, 해당 정보 view 페이지에 출력");
				
				action = new ReviewDeleteAction();
				try {
					forward = action.execute(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// else if ---  /BoardDelete.bo	
			
			else if(command.equals("/BoardDeletePro.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /BoardDeletePro.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: DB 사용 O, 페이지 이동 O");
				
				action = new ReviewDeleteProAction();
				
				try {
					forward = action.execute(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// else if ---  /BoardDeletePro.bo	
			
			else if(command.equals("/FileBoardWrite.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /FileBoardWrite.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: DB 사용 X, 페이지 이동 O");
				
				
				forward = new ActionForward();
				forward.setPath("./reviewWriteForm.jsp");
				forward.setRedirect(false); // forward 방식으로 이동

			}// else if ---  /FileBoardWrite.bo	

			else if(command.equals("/FileBoardWriteAction.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /FileBoardWriteAction.bo 호출");
				System.out.println("(from BoardFrontController_doProcess) C: DB 사용 O, 페이지 이동 O");
				
				
				action = new ReviewFileWriteAction();
				
				try {
					forward = action.execute(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}// else if ---  /FileBoardWrite.bo	
			
			else if(command.equals("/FileBoardUpdate.bo")){
				System.out.println(" C : /FileBoardUpdate.bo 호출 ");
				System.out.println(" C : DB작업 o, 페이지 이동");
			
				// FileBoardUpdateAction() 객체 생성
				action = new ReviewFileUpdateAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}// else if ---  /FileBoardUpdate.bo	
				
			else if(command.equals("/FileBoardUpdateActionPro.bo")){
				System.out.println(" C : /FileBoardUpdateActionPro.bo 호출 ");
				System.out.println(" C : DB작업 o, 페이지 이동");
				
				// FileBoardUpdateActionPro() 객체 생성
				action = new ReviewFileUpdateProAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// else if ---  /FileBoardUpdate.bo	
			
			// 댓글 구현 시작////////////////////////////////////////
			else if (command.equals("/CommentWrite.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /CommentWrite.bo 호출");
						
				action = new ReviewCommentWriteAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
						
			} // CommentWrite.bo 끝
					
			else if (command.equals("/CommentUpdate.bo")){
				System.out.println("(from BoardFrontController_doProcess) C: /CommentUpdate.bo 호출");
					
				action = new ReviewCommentUpdateAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} // CommentUpdate.bo 끝
			
			// 리뷰 구간 끝---------------------------------------------------------
			
			
//			
//			
//			
			//ActionForward 자바 페이지 생성하여 사용 (페이지 이동을 위한 정보를 저장하는 객체)
			else if(command.equals("/ContactWrite.bo")){ //만약 주소가 맞아서 실행된다면 
				System.out.println("C : /ContactWrite.bo 호출 ");
				System.out.println("C: DB정보가 필요없음 -view페이지로 이동");
				
				//글쓰기 페이지 보여주기(DB정보 필요없음 -> Model2방식에서 view로 보내야함) 
				//(DB정보 필요하면 -> M ionForward(); //객체 생성
				forward = new ActionForward();
				forward.setPath("./contact/writeForm.jsp"); //어디로 갈것인지 
				forward.setRedirect(false); //어떤 방식으로 갈 것인지 설정 
				//false니까 forward방식으로 이동 
				
				
			}else if(command.equals("/ContactWriteAction.bo")){
				System.out.println("C:/ContactWriteAction.bo호출");
				System.out.println("C:DB정보가 필요없음-view페이지로 이동");
				
				//ContactWriteAction bwAction = new ContactWriteAction();
				action = new ContactWriteAction();//인터페이스를 통해 자동형변환  -> 응집도를 떨어트리기 위해 이렇게 구현 (객체지향)
				try {
					System.out.println("11111111111111111111111111");
					//forward = bwAction.execute(req, resp);
					forward = action.execute(request, response); 
					System.out.println("22222222222222222222222222222222");
				} catch (Exception e) {
					e.printStackTrace();
				}                   
			}else if(command.equals("/ContactList.bo")){
				System.out.println("C : /ContactList.bo 호출");
				System.out.println("C : DB정보가 필요, 페이지 이동X, 페이지 출력O");
				action = new ContactListAction();
				//contactListAction 객체 생성
				//ContactListAction listAction = new ContactListAction();
				try {
					//forward = listAction.execute(req, resp); //예외처리해야 오류 안뜸 
					forward = action.execute(request, response);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(command.equals("/ContactContent.bo")){
				//게시판의 글을 누르면 글내용으로 이동하는 주소로 이동 			
				System.out.println(" C: /ContactContent.cot호출");
				System.out.println("C: DB정보 사용, 출력 ");
				
				//contactContentAction 객체 생성
				action = new ContactContentAction();
				//action에서 상속받은 execute재정의 메서드 이용~
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if(command.equals("/ContactUpdate.bo")){
				System.out.println("C: /ContactUpdate.bo 호출");
				System.out.println("C: DB정보 사용, 출력 ");
				
				action = new ContactUpdateAction();
				//action에서 상속받은 execute재정의 메서드 이용~
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if(command.equals("/ContactUpdatePro.bo")){
				System.out.println("C: /ContactUpdatePro.bo 호출");
				System.out.println("C: DB가서 수정,페이지 이동 ");
				
				action = new ContactUpdateProAction();
				//action에서 상속받은 execute재정의 메서드 이용~
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (command.equals("/ContactDelete.bo")) {
				System.out.println("(from ContactFrontController_doProcess) C: /BoardDelete.bo 호출");
				System.out.println("(from ContactFrontController_doProcess) C: DB 사용 O, 해당 정보 view 페이지에 출력");

				action = new ContactDeleteAction();
				try {
					forward = action.execute(request, response);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} // else if --- /BoardDelete.bo

			else if (command.equals("/ContactDeletePro.bo")) {
				System.out.println("(from ContactFrontController_doProcess) C: /BoardDeletePro.bo 호출");
				System.out.println("(from ContactFrontController_doProcess) C: DB 사용 O, 페이지 이동 O");

				action = new ContactDeleteProAction();

				try {
					forward = action.execute(request, response);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
			// 마이페이지 - 회원정보 조회
			else if(command.equals("/UserInfo.bo")){
				System.out.println("C : 회원정보 조회");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				action = new UserUpdate();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 마이페이지 - 회원정보 수정
			else if(command.equals("/UserUpdate.bo")){
				System.out.println("C : 회원정보 수정");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				action = new UserUpdateAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 마이페이지 - 회원정보 삭제 페이지
			else if(command.equals("/UserDelete.bo")){
				System.out.println("C : 회원정보 삭제 페이지");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				action = new UserDelete();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 마이페이지 - 회원정보 삭제 완료
			else if(command.equals("/UserDeleteAction.bo")){
				System.out.println("C : 회원정보 삭제 완료");
				// System.out.println("C : DB작업 o, 페이지 이동");
				
				action = new UserDeleteAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 관리자 페이지
			else if(command.equals("/Admin.bo")){
				System.out.println("C : /Admin.bo 호출");
				// System.out.println("C : DB정보가 필요, 페이지 이동X, 페이지 출력O");
				action = new CarReservationManagement();
				
				try {
					//forward = listAction.execute(req, resp); //예외처리해야 오류 안뜸 
					forward = action.execute(request, response);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			// 마이페이지 - 예약정보 조회
			else if(command.equals("/ReservationInfo.bo")){
				System.out.println("C : /ReservationInfo.bo 호출");
				// System.out.println("C : DB정보가 필요, 페이지 이동X, 페이지 출력O");
				action = new CarReservationList();
				
				try {
					//forward = listAction.execute(req, resp); //예외처리해야 오류 안뜸 
					forward = action.execute(request, response);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
				
			System.out.println("2. 가상주소 매핑 - 끝 \n");
			// 2. 가상주소 매핑 ------------------------------------------------
			
			System.out.println("3. 가상주소 이동 - 시작");
			// 3. 가상주소 이동 ------------------------------------------------
			if(forward != null){
				// 페이지 이동정보가 있을때
				if(forward.isRedirect()) {
					// true - sendRedirect() 방식으로 이동
					System.out.println("C : true-" + forward.getPath() + "이동, sendRedirect() 방식");
					response.sendRedirect(forward.getPath());
				} else {
					// false - forward() 방식으로 이동	
					System.out.println("C : false-" + forward.getPath() + "이동, forward() 방식");
					RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
					dis.forward(request, response);					
				}
			}			
			System.out.println("3. 가상주소 이동 - 끝");
			System.out.println("------------------------");
			// 3. 가상주소 이동 ------------------------------------------------		
	}
	// -------------------------------------------- doProcess() -------------------------------------------- 
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("GET방식 호출 - doGet() 실행");
			doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("POST방식 호출 - doPost() 실행 ");
			doProcess(request, response);
	}

}
