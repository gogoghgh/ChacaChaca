package com.itwillbs.chaca.db;

import java.sql.Date;


public class ContactDTO {
	//자바빈 == DTO(Data Transfer Object) : 데이터 전송 객체 
	private int bno; //게시판 번호
	private String name;//게시판 글쓴이
	private String id; //게시판 글 아이디
	private String password; //게시판 글 비밀번호
	private String subject; //게시판 제목
	private String content; //게시판 내용 
	//============================

	
	//============================
	
	private int readcount; //조회수
	private int re_ref; //답글 - 그룹번호 
	private int re_lev; //답글 - 그룹 레벨값(들여쓰기) 
	private int re_seq; //답글 - 그룹순서 
	
	private Date date; //작성일 
	private String ip; //작성자IP
	private String file; //첨부파일 
	          
	
	@Override
	public String toString() {
		return "BoardDTO [bno=" + bno + ", name=" + name + ", password=" + password + ", subject=" + subject
				+ ", content=" + content + ", readcount=" + readcount + ", re_ref=" + re_ref + ", re_lev=" + re_lev
				+ ", re_seq=" + re_seq + ", date=" + date + ", ip=" + ip + ", file=" + file + "]";
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
}
