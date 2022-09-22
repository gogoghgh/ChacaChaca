package com.itwillbs.chaca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class ReviewDAO {
	// Data Access Object 데이터 처리 객체!!  DB 쓰는 작업들 여기서 다
	
	// 공통 변수 선언 (인스턴스 변수)
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	private ReviewDTO dto;
	
	// 기본 생성자
	public ReviewDAO(){
		System.out.println("(from BoardDAO) DB 연결에 관한 모든 준비 완^^");
	}
	
	// getConnect --- DB 연결 메서드 (用 커넥션풀) 
	private Connection getConnect() throws Exception {
		// 프로젝트 정보를 초기화 함 해서 -> initCTX에 넣겠다
		Context initCTX = new InitialContext();
		
		// 초기화된 프로젝트 정보 중에서 데이터 불러오기 + ds에 저장 + 다운캐스팅
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/ChacaChaca");
		
		// 연결된 정보를 바탕으로 connection 정보를 리턴
		con = ds.getConnection();
		
		return con;
	}
	// getConnect --- DB 연결 메서드 (用 커넥션풀) 끝
	
	
	// closeDB() --- 자원 해제 메서드
	public void closeDB(){
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
			System.out.println("(from BoardDAO_closeDB) 자원 해제 완 ㅂ2ㅂ2");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// closeDB() --- 자원 해제 메서드 끝
	
	// 1. boardWrite --- 글쓰기 메서드
	public void boardWrite(ReviewDTO dto){
		System.out.println("\n(from BoardDAO_1.boardWrite) C: boardWrite() 호출");
		
		int bno = 0;
		
		try {
			// 1+2 드라이버 로드, DB 연결  + 6 자원해제
			con = getConnect();
			
			// 3. sql & pstmt & ?
			//    insert 하기 전에~ bno 글 번호(pk) 계산 먼저
			sql = "select max(bno) from review";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리 (글 번호 계산)
			if(rs.next()){
				bno = rs.getInt(1) + 1;
			}
			
			System.out.println("(from BoardDAO_1.boardWrite) 글번호 bno: " + bno);
			
			// 글 번호 계산 완 -> 다시 3단계~~ insert 하러~ + pstmt + ?
			sql = "insert into review(bno, name, id, password, rate, content,"
					+ "readcount, re_ref, re_lev, re_seq, date, ip, file) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
													// date는 직접 지정 안 하고 now로 할게요,,,,,^^,,
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getPassword());
			pstmt.setInt(5, dto.getRate());
			pstmt.setString(6, dto.getContent());
			pstmt.setInt(7, 0);
			pstmt.setInt(8, bno);
			pstmt.setInt(9, 0);
			pstmt.setInt(10, 0);
			pstmt.setString(11, dto.getIp());
			pstmt.setString(12, dto.getFile());
			
			// 4. sql 실행
			pstmt.executeUpdate();
			System.out.println("(from BoardDAO_1.boardWrite) 글 작성 완  bno: " + bno);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	// 1. boardWrite --- 글쓰기 메서드 끝
	
	
	// 2. getBoardList() --- 글 목록 조회(all) 메서드
	public List<ReviewDTO> getBoardList(){
		System.out.println("\n(from BoardDAO_2.getBoardList) C: getBoardList() 호출");
		
		List<ReviewDTO> boardList = new ArrayList<ReviewDTO>();
		
		try {
			// 1+2
			con = getConnect();

			// 3 sql & pstmt & ?
			sql = "select * from review";
			pstmt = con.prepareStatement(sql);
			
			// 4 sql 실행 & 날려서 가져온 결과값 rs에 담기
			rs = pstmt.executeQuery();
			
			// 5 데이터 처리
			while(rs.next()){
				ReviewDTO dto = new ReviewDTO();
				dto.setBno(rs.getInt(1));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString(4));
				dto.setRate(rs.getInt("rate"));
				dto.setContent(rs.getString(6));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt(8));
				dto.setRe_lev(rs.getInt(9));
				dto.setRe_seq(rs.getInt(10));
				dto.setDate(rs.getDate(11));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				boardList.add(dto);
				
			} // while
			System.out.println("(from BoardDAO_2.getBoardList) C: List에 저장 완");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 6
			closeDB();
		}
		return boardList;
	}
	// 2. getBoardList() --- 글 목록 조회(all) 메서드 끝
	
	
	// 2-1. getBoardList(startRow, pageSize) 오버로딩 --- 글 목록 조회(all 아니고 내가 원하는 만큼만) 
	public List<ReviewDTO> getBoardList(int startRow, int pageSize){
		System.out.println("\n(from BoardDAO_2-1.getBoardList) C: getBoardList(startRow, pageSize) 호출");

		List<ReviewDTO> boardList = new ArrayList<ReviewDTO>();
		
		try {
			// 1+2
			con = getConnect();

			// 3 sql & pstmt & ?
			sql =     "select * from review "
					+ "order by re_ref desc, re_seq asc "
					+ "limit ?, ?";
			
			pstmt = con.prepareStatement(sql);
			
			// ? 처리
			pstmt.setInt(1, startRow-1); // DB에서 인덱스 0부터 시작하니까,, -1 --> 시작행-1
			pstmt.setInt(2, pageSize); // 몇 개씩 보여줄거? 매개변수로 받아온 pageSize만큼
			// 이 밑에부터는 똑같음~~~~~~~~~
			
			// 4 sql 실행 & 날려서 가져온 결과값 rs에 담기
			rs = pstmt.executeQuery();
			
			// 5 데이터 처리
			while(rs.next()){
				ReviewDTO dto = new ReviewDTO();
				dto.setBno(rs.getInt(1));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString(4));
				dto.setRate(rs.getInt("rate"));
				dto.setContent(rs.getString(6));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt(8));
				dto.setRe_lev(rs.getInt(9));
				dto.setRe_seq(rs.getInt(10));
				dto.setDate(rs.getDate(11));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				boardList.add(dto);
				
			} // while
			System.out.println("(from BoardDAO_2-1.getBoardList) C: List에 저장 완");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 6
			closeDB();
		}
		return boardList;
	}	
	// 2-1. getBoardList(startRow, pageSize) 오버로딩 --- 글 목록 조회(all 아니고 내가 원하는 만큼만) 끝 
	
	
	// 3. getBoardCount() --- 글 개수 조회(all) 
	public int getBoardCount(){
		System.out.println("\n(from BoardDAO_3.getBoardCount) C: getBoardCount() 호출");
		int cnt = 0;
		
		try {
			// 1+2. 디비 연결(커넥션 풀 이용) + 6. closeDB
			con = getConnect();
			
			// 3. sql 작성 & pstmt & ?
			sql = "select count(*) from review";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행 + rs
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리 (select날리니까)
			if(rs.next()){ 
				cnt = rs.getInt(1); // 1번 컬럼(=count(*)) 값을 cnt에 저장쓰
			}
			
			System.out.println("(from BoardDAO_3.getBoardCount) C: 글 개수는 총 " + cnt + "개^^");
			
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return cnt;
	}
	// 3. getBoardCount() --- 글 개수 조회(all) 끝
	
	
	// 4. updateReadcount(bno) --- 글 조회수 1 증가 
	public void updateReadcount(int bno){
		System.out.println("(from BoardDAO_4.updateReadcount) C: updateReadcount(int bno) 호출 완");
		
		try {
			// 1+2.    + 6. 자원해제 미리
			con = getConnect();
			
			// 3. sql & pstmt & ?
			sql =    "update review "
					+ "set readcount = readcount+1 "
					+ "where bno=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bno);
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println("(from BoardDAO_4.updateReadcount) C: 글번호 " + bno + " 조회수 +1 완");
			
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			closeDB();
		}

	}
	// 4. updateReadcount(bno) --- 글 조회수 1 증가 끝 
	
	
	// 5. getBoard(bno) --- 특정 글 1개의 정보 조회 
	public ReviewDTO getBoard(int bno){
		System.out.println("(from BoardDAO_5.getBoard) C: getBoard(bno) 호출");

		ReviewDTO dto = null;
		
		try {
			// 1+2.    +6.
			con = getConnect();
			
			// 3.
			sql = "select * from review where bno=?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			// 4. 
			rs = pstmt.executeQuery();
			
			// 5.
			
			if(rs.next()){ // 데이터 결과값: 하나니까 if
				// rs에 담겨 있는 DB 데이터 -> DTO에 넣기
				dto = new ReviewDTO(); // 인제 new 하기1!!!
				
				dto.setBno(rs.getInt(1));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString(4));
				dto.setRate(rs.getInt("rate"));
				dto.setContent(rs.getString(6));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt(8));
				dto.setRe_lev(rs.getInt(9));
				dto.setRe_seq(rs.getInt(10));
				dto.setDate(rs.getDate(11));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
			}// if
			
			System.out.println("(from BoardDAO_5.getBoard) C: 게시글 " + bno + "번 정보 dto에 저장 완");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return dto;
	}
	// 5. getBoard(bno) --- 특정 글 1개의 정보 조회 끝 
	
	
	// 6. updateBoard(dto) --- 글 정보 수정 메서드
	public int updateBoard(ReviewDTO dto){
		int result = -1;
		
		try {
			// 1. 2. DB 연결   + 6. closeDB
			con = getConnect();
			
			// 3. sql 작성 & pstmt & ?
			sql = "select password from review where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			
			// 4. sql 실행 & rs에 담기
			rs = pstmt.executeQuery();
			
			// 5. rs에 담긴 데이터 처리
			if(rs.next()){
				// 데이터 있을 때~ 
				// 비번 다시 확인
				if(dto.getPassword().equals(rs.getString("password"))){
					// 3. sql & pstmt & ?
					sql = "update review "
							+ "set name=?, content=?, rate=?, file=? "
							+ "where bno=? ";
					
					pstmt = con.prepareStatement(sql);
					
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getContent());
					pstmt.setInt(3, dto.getRate());
					pstmt.setString(4, dto.getFile());
					pstmt.setInt(5, dto.getBno());
					
					// 4. sql 실행
					result = pstmt.executeUpdate(); 
					
				} else {
					// 비번 다름
					result = 0;
					
				}// 안에 if-else

			} else {
				result = -1;
			}
			System.out.println("(from BoardDAO_6.updateBoard) bno: " + dto.getBno() + "번 글 수정 완 result: " + result);
			System.out.println("(from BoardDAO_6.updateBoard) 수정 내용: " + dto);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			closeDB();
		}
		
		return result;
		
	} // 6. updateBoard(dto) --- 글 정보 수정 메서드 끝
	
	
	// 7. deleteBoard(dto) --- 특정 글 1개 삭제 메서드
	public int deleteBoard(ReviewDTO dto) {
		int result=-1;
		
		try {
			con=getConnect();
			sql = "select password from review where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(dto.getPassword().equals(rs.getString("password"))){
					sql = "delete from review where bno=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, dto.getBno());
					result = pstmt.executeUpdate();
					System.out.println("글 삭제 성공");
				} else {
					result = 0;
					System.out.println("비번 오류");
				}
			} else {
				result = -1;
				System.out.println("select 오류");
			}
			System.out.println("(from BoardDAO_7.deleteBoard) bno: " + dto.getBno() + "번 글 삭제 완");
			System.out.println("(from BoardDAO_7.deleteBoard) 삭제 내용: " + dto);	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}
	// 7. deleteBoard(dto) --- 특정 글 1개 삭제 메서드

	// 댓글 구현 //////////////////////////////////////////////////////////////
	// 8. getOne(c_bno) 메서드  comment 테이블의 c_bno로 데이터 한 개 가져오기 
	public ReviewCmtDTO getOneComment(int c_bno) {
		System.out.println("(from BoardDAO_8.getOneComment) getOneComment 메서드 호출됨");
		
		ReviewCmtDTO cdto = new ReviewCmtDTO();
		
		try {
			// 1+2.   +6
			con = getConnect();
			
			// 3. sql & pstmt & ?
			sql = "select * from board_comment where c_bno=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, c_bno);
			
			// 4. sql 실행 & rs에 담기
			rs = pstmt.executeQuery();
			
			// 5. rs에 담긴 데이터 처리
			if (rs.next()){
				
				cdto.setC_bno(rs.getInt("c_bno"));
				cdto.setBno(rs.getInt("bno"));
				cdto.setName(rs.getString("name"));
				cdto.setContent(rs.getString("content"));
				cdto.setDate(rs.getTimestamp("date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return cdto;
	}
	// 8. getOneComment(bno) 메서드  comment 테이블의 bno로 데이터 한 개 가져오기 끝
	
	
	// 8-1. updateComment
	public void updateComment(ReviewCmtDTO cdto) {
		System.out.println("(from BoardDAO_8-1.updateComment) updateComment 메서드 호출됨");
		
		try {
			// 1+2.   +6
			con = getConnect();
			
			// 3. sql & pstmt & ?
			sql = "update board_comment "
					+ "set name=?, content=?, where c_bno=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, cdto.getName());
			pstmt.setString(2, cdto.getContent());
			pstmt.setInt(3, cdto.getC_bno());
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println("(from BoardDAO_8-1.updateComment) 댓글 수정 완 " + cdto);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// 8-1. updateComment 끝
	
	
	// 8-2. deleteComment
	public void deleteComment(int c_bno) {
		System.out.println("(from BoardDAO_8-2.deleteComment) deleteComment 메서드 호출됨");
		
		try {
			// 1+2.   +6
			con = getConnect();
			
			// 3. sql & pstmt & ?
			sql = "delete from board_comment "
					+ "where c_bno=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, c_bno);
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println("(from BoardDAO_8-2.deleteComment) 댓글 삭제 완 c_bno:" + c_bno);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// 8-2. deleteComment 끝
	
	
	// 8-3. insertComment
	public void insertComment(ReviewCmtDTO cdto) {
		System.out.println("(from BoardDAO_8-3.insertComment) insertComment 메서드 호출됨");
		
		try {
			// 1+2.   +6
			con = getConnect();
			
			// 3. sql & pstmt & ?
			sql = "insert into board_comment (name, content, bno) "
					+ "values(?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, cdto.getName());
			pstmt.setString(2, cdto.getContent());
			pstmt.setInt(3, cdto.getBno());
			
			// 4. sql 실행
			pstmt.executeUpdate();
			
			System.out.println("(from BoardDAO_8-3.insertComment) 댓글 추가 완 " + cdto);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// 8-3. insertComment 끝
	
	
	// 8-4. getCommentCount 한 게시물의 댓글 개수 구하는 메서어드
	public int getCommentCount(int bno){
		int cnt = 0;
		
		try {
			// 1+2.  6.
			con = getConnect();
			
			// 3. sql & pstmt & ?
			sql = "select count(*) from board_comment where bno=?"; // board Table의 bno에 속한 -> comment Table의 bno니까,,
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			// 4. sql 실행 + rs에 담기
			rs = pstmt.executeQuery();
			
			// 5. rs에 담긴 데이터 처리
			if(rs.next()){
				cnt = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		System.out.println("(from BoardDAO_8-4.getCommentCount) C: 덧글 개수는 총 " + cnt + "개^^");
		return cnt;
		
	}
	// 8-4. getCommentCount 한 게시물의 댓글 개수 구하는 메서어드 끝
	
	
	// 8-5. getCommentList 한 게시물의 댓글 리스트 출력하는 메서드
	public List<ReviewCmtDTO> getCommentList(int bno){
		System.out.println("\n(from BoardDAO_8-5.getCommentList) C: getCommentList() 호출됨");

		List<ReviewCmtDTO> cmtList = new ArrayList<ReviewCmtDTO>();
		
		try {
			// 1+2
			con = getConnect();

			// 3 sql & pstmt & ?
			sql = "select * from board_comment where bno=? order by c_bno desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			
			// 4 sql 실행 & 날려서 가져온 결과값 rs에 담기
			rs = pstmt.executeQuery();
			
			// 5 데이터 처리
			while(rs.next()){
				// 이 정보를 바로 배열로는 못 넣고,, DB에 저장된 정보 -> DTO 필통에 저장해서!! -> List에 저장 
				ReviewCmtDTO cdto = new ReviewCmtDTO();
				cdto.setC_bno(rs.getInt("c_bno"));
				cdto.setBno(rs.getInt("bno"));
				cdto.setName(rs.getString("name"));
				cdto.setContent(rs.getString("content"));
				cdto.setDate(rs.getTimestamp("date"));
				
				// DTO필통을 -> List에 저장쓰
				cmtList.add(cdto);
				
			} // while
			System.out.println("(from BoardDAO_8-5.getCommentList) List에 저장 완");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 6
			closeDB();
		}
		
		return cmtList;
		
	}	
	// 8-5. getCommentList 한 게시물의 댓글 리스트 출력하는 메서드 끝
	
	
}// ReviewDAO class
