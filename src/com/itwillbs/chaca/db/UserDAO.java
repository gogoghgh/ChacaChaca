package com.itwillbs.chaca.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class UserDAO {
	// DAO (Data Access Object) : 데이터 처리 객체
	
	// 공통변수 (인스턴스 변수)
	private Connection con = null;   // 디비 연결정보 저장 객체
	private PreparedStatement pstmt = null; //디비에 SQL 실행 처리 객체
	private ResultSet rs = null;	//select 실행 결과 저장 객체
	private String sql = "";		// SQL쿼리 구문 저장
	
	public UserDAO() {
		System.out.println(" DAO : DB연결에 관한 모든정보를 준비 완료! ");
	}
	
	
	// 디비 연결
//	private Connection getConnect() throws Exception{
//		// 디비 연결정보
//		String DRIVER = "com.mysql.cj.jdbc.Driver";
//		String DBURL = "jdbc:mysql://localhost:3306/jspdb";
//		String DBID = "root";
//		String DBPW = "1234";
//		
//		// 1. 드라이버로드
//		Class.forName(DRIVER);
//		System.out.println(" 드라이버로드 성공 ");
//		// 2. 디비연결
//		con = DriverManager.getConnection(DBURL, DBID, DBPW);
//		System.out.println(" 디비연결 성공 ");
//		System.out.println(" con : " + con);
//		
//		return con;
//	}
	
	
	// 디비 연결
	private Connection getConnect() throws Exception {
		// 디비 연결정보 - context.xml
		
		// 프로젝트 정보를 초기화
		Context initCTX = new InitialContext();
		// 초기화된 프로젝트 정보중 데이터 불러오기
		DataSource ds
		   = (DataSource)initCTX.lookup("java:comp/env/jdbc/ChacaChaca");
		// 연결된 정보를 바탕으로 connection 정보를 리턴
		con = ds.getConnection();
		
		return con;
	} // 디비 연결
	
	
	// 자원 해제
	public void closeDB(){
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
			
			System.out.println("DAO : 자원해제 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // 자원 해제
	
	
	// 회원가입 - joinUser()
	public void joinUser(UserDTO dto){
		System.out.println("DAO : joinUser() 호출 ");
		
		try {
			// 1.2. 디비 연결
			con = getConnect();
			
			// 3. sql작성 & pstmt 객체
			sql = "insert into userinfo(id_email, password, name, birth, phone, address) values(?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId_email());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());
			pstmt.setDate(4, dto.getBirth());
			pstmt.setString(5, dto.getPhone());
			pstmt.setString(6, dto.getAddress());
			
			// 4. SQL 실행
			pstmt.executeUpdate();
			
			System.out.println("DAO : joinUser() 완료!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	} // 회원가입 - joinUser()
	
	// 회원정보 조회 시작 - getMember(id)
		public UserDTO getMember(String id) {
			// 회원정보를 저장할 객체
			UserDTO dto = new UserDTO();
			
			
			// 1. 드라이버 로드 2. 디비 연결
			try {
				con = getConnect();
				
				// 3. SQL(select) & pstmt
				sql = "select * from userinfo where id_email = ?";
				pstmt = con.prepareStatement(sql);
				
				// 4. ???
				pstmt.setString(1, id);
				// 5. SQL 실행
				rs = pstmt.executeQuery();
				// 6. 데이터 처리
				if(rs.next()) { // 데이터 있을 때
					// 조회된 회원 정보를 저장
					dto.setId_email(rs.getString("id_email"));
					dto.setPassword(rs.getString("password"));
					dto.setName(rs.getString("name"));
					dto.setBirth(rs.getDate("birth"));
					dto.setPhone(rs.getString("phone"));
					dto.setAddress(rs.getString("address"));
					dto.setLicense_type(rs.getString("license_type"));
					dto.setLicense_number(rs.getString("license_number"));
					
					System.out.println("DAO : 회원정보 저장 완료");
				}
				System.out.println("DAO : 조회된 회원정보");
				System.out.println("DAO : " + dto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return dto;
		}
		// 회원정보 조회 끝 - getMember(id)
	
	
	
	
	// 로그인 - loginMember()
	public int loginMember(UserDTO dto) throws Exception {
		// 약속 : 1 - 로그인, 0 : 비밀번호 오류, -1 : 아이디 오류
		int result = -1;
		
		try {
			// 1.2. 디비 연결
			con = getConnect();
			// 3. SQL 작성 & pstmt 객체 생성
			sql = "select password from userinfo where id_email=?";
			pstmt = con.prepareStatement(sql);
			
			// 4. ???
			pstmt.setString(1, dto.getId_email());
			
			// 5. SQL 실행
			ResultSet rs = pstmt.executeQuery();
			
			// 6. 데이터 처리
			if(rs.next()) {
				// next() : 반복가능 객체의 다음 요소 반환
				// 아이디 있음, 회원
				if(dto.getPassword().equals(rs.getString("password"))) { // getString(key) : key에 해당하는 value 출력
					// 아이디 있음, 비밀번호 동일, 본인
					// => 로그인 성공
					result = 1;
				} else {
					// 아이디 있음, 비밀번호 다름, 비밀번호 오류
					result = 0;
				}
			} else {
				// 아이디 없음, 비회원
			}
			System.out.println("로그인 체크 완료 (" + result + ")");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("디비 연결 실패!");
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	} // 로그인  - loginMember()
		
	// 운전면허 입력 - updateMemberCarinfo()
	public UserDTO updateMemberCarinfo(String id_email, String license_type, String license_number) {
		
		UserDTO dto = new UserDTO();
		
		try {
			// 1. 2. 디비연결
			con = getConnect();
			
			// 3. SQL(select) & pstmt
			// 3-1. 수정하려는 정보가 회원, 본인여부 체크
			// 3-2. 본인일때만, 정보 수정
			sql = "select * from userinfo where id_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id_email);
			
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			// 3-2. 본인일때만, 정보 수정
			if(rs.next()) {
				// 조회된 회원 정보를 저장
				dto.setId_email(rs.getString("id_email"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth"));
				dto.setPhone(rs.getString("phone"));
				dto.setAddress(rs.getString("address"));
				
				// 3. SQL(update) & pstmt
				// 특정 사용자의 정보(이름, 나이, 성별, 이메일)을 수정
				sql = "update userinfo set license_type=?, license_number=? where id_email=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, license_type);
				pstmt.setString(2, license_number);
				pstmt.setString(3, id_email);
				
				// 4. SQL 실행
				pstmt.executeUpdate();
			}
			System.out.println(dto);
			System.out.println("DAO : 디비동작 처리 끝(수정처리)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	} // 운전면허 입력 - updateMemberCarinfo()
	
	// 회원정보 수정 - updateMember()
	public int updateMember(UserDTO dto) {
		// 수정 결과 : 1 수정성공, 0 수정실패(비밀번호), -1 수정실패(비회원)
		int result = -1;
		
		// 1. 2. 디비연결
		try {
			con = getConnect();
			
			// 3. SQL(select) & pstmt
			// 3-1. 수정하려는 정보가 회원, 본인여부 체크
			// 3-2. 본인일때만, 정보 수정
			sql = "select password from userinfo where id_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId_email());
			
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			// 3-2. 본인일때만, 정보 수정
			if(rs.next()) {
				// 3. SQL(update) & pstmt
				// 특정 사용자의 정보(이름, 나이, 성별, 이메일)을 수정
				sql = "update userinfo set password=?, name=?, phone=?, address=? where id_email=?";
				pstmt = con.prepareStatement(sql);
				
				// ???
				pstmt.setString(1, dto.getPassword());
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getPhone());
				pstmt.setString(4, dto.getAddress());
				pstmt.setString(5, dto.getId_email());
				
				// 4. SQL 실행
				pstmt.executeUpdate();
				
				result = 1;
				System.out.println("DAO : 정보 수정 완료!");
			} else {
				// 비회원
				result = -1;
			}
			
			System.out.println("DAO : 디비동작 처리 끝(수정처리)(" + result + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} // 회원정보 수정  - updateMember()
	
	// 회원정보 삭제 - deleteMember()
	public int deleteMember(String id, String pw) {
		int result = -1;
		
		try {
			// 1. 2. 디비 연결
			con = getConnect();
			
			// 3. sql 생성(select) & pstmt 생성
			sql = "select password from userinfo where id_email = ?";
			pstmt = con.prepareStatement(sql);
			// ???
			pstmt.setString(1, id);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()) {
				if(pw.equals(rs.getString("password"))){
					// 본인
					// 5-1. sql 생성(delete) & pstmt 생성
					sql = "delete from userinfo where id_email = ? and password = ?";
					pstmt = con.prepareStatement(sql);
					// ???
					pstmt.setString(1, id);
					pstmt.setString(2, pw);
					
					// 5-2. sql 실행
					result = pstmt.executeUpdate(); // == 1 
				 // result = 1;
					System.out.println("DAO : 회원삭제 완료!");
				} else {
					// 비밀번호 오류
					result = 0;
				}
			
			} else {
				// 비회원
				result = -1;
			}
			System.out.println("DAO : 삭제 정보 (" + result + ")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	} // 회원정보 삭제 - deleteMember()
	
	
	

}// class
