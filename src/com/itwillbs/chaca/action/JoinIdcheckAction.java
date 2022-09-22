package com.itwillbs.chaca.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.chaca.db.UserDAO;
import com.itwillbs.chaca.db.UserDTO;

public class JoinIdcheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("M : JoinIdcheckAction_execute() 호출 ");		
		
		// 한글 인코딩 처리
		request.setCharacterEncoding("UTF-8");

		// 전달된 정보 저장
		String id = request.getParameter("id");
			
		final String DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DBURL = "jdbc:mysql://localhost:3306/jspdb?useUnicode=true&serverTimezone=Asia/Seoul";
		final String DBID = "root";
		final String DBPW = "1234";
		
		// 1. 드라이버 로드
		Class.forName(DRIVER);
		System.out.println("드라이버 연결 성공!");
		
		// 2. 디비 연결
		Connection con = DriverManager.getConnection(DBURL, DBID, DBPW);
		System.out.println("디비 연결 성공!");
		
		// 3. SQL & pstmt
		// sql - 전체 회원목록 조회
		String sql = "select * from userinfo where id_email = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		
		// 4. SQL 실행
		ResultSet rs =  pstmt.executeQuery();
		
		// 5. 데이터 처리(select)
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("입력 ID : <strong>" + id + "</stong>");
		if(rs.next()) {
			out.println("<p style='color: red'>해당 아이디는 사용하실 수 없습니다.</p>");
		} else {
			out.println("<p>사용 가능한 아이디입니다.</p>");
			out.println("<a href='javascript:apply(\"" + id + "\")'>[적용]</a>");
			out.println("<script>");
			out.println("function apply(" + id +") {");
			out.println("opener.document.form.id_email.value = " + id + ";");
			out.println("window.close(); }");
			out.println("</script><hr>");
		}
		out.println("<a href='javascript:history.back();'>[다시시도]</a>");
		out.println("&nbsp; &nbsp;");
		out.println("<a href='javascript:window.close()'>[창닫기]</a>");
		
		return null;
		
	}

}
