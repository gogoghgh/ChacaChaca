package com.itwillbs.chaca.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.chaca.db.ReviewCmtDTO;
import com.itwillbs.chaca.db.ReviewDAO;


public class ReviewCommentWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("(from ReviewCommentWriteAction) Model: execute() 호출 완");
		
		// 댓글 입력 폼에서 넘어온 값이 한글일 수 있으니 한글 처리 먼저
		request.setCharacterEncoding("UTF-8");
		
//		// 사용자 응답 웹페이지 문자 인코딩
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();
		
		// 전달된 정보 저장(이름, 내용, bno, pageNum..)
		ReviewCmtDTO cdto = new ReviewCmtDTO();
		cdto.setBno(Integer.parseInt(request.getParameter("bno")));
		cdto.setName(request.getParameter("name"));
		cdto.setContent(request.getParameter("content"));
//		String pageNum = request.getParameter("pageNum"); // DB에 저장할 정보 아니니까,, 걍 String으로 받아도 됨 
		
		System.out.println("(from ReviewCommentWriteAction) M: 저장할 댓글 정보:" + cdto);
		
		// DB에 댓글 입력 정보 저장
		ReviewDAO dao = new ReviewDAO();
		dao.insertComment(cdto);
		
		// BoardContent.bo 가려니까 bno== null이라고 자꾸 오류나네 ㄱ- 세션에 넣을까,,,, 주소줄에 붙ㅌ여서 보내야 하나
		// bno 변수에 넣어서 주소줄에 같에 보내자
//		int bno = Integer.parseInt(request.getParameter("bno"));
		
		// 페이지 이동 정보 저장, forward 리턴
		ActionForward forward = new ActionForward();
		
		forward.setPath("./ReviewContent.bo?bno="+cdto.getBno()); // 되긴 되네???? .. pageNum은 굳이 필요 없는 듯,, 빼자  + "&pageNum="+pageNum
		forward.setRedirect(true); // 가상주소로 이동하니까 sendRedirect 방식으로 이동
		
		return forward;		
	}

}
