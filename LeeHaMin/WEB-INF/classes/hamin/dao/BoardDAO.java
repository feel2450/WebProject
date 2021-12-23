package hamin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import hamin.dto.BoardDTO;

public class BoardDAO {
	private DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	

// 생성자에서 jdbc/mvc 객체를 찾아 DataSource 로 받는다.
	public BoardDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/HAMIN");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

// Connection 해제를 위한 메소드
	public void close() {
		try {
			if(con !=null) {
				con.close();
				con=null;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
// 전체 게시판 목록보기
	public ArrayList<BoardDTO> list(){
		String sql = "SELECT * FROM Board1";
		
		ArrayList<BoardDTO> dtos = new ArrayList<BoardDTO>(); // DB처리 결과를 BoardDTO에 담아 ArrayList로 만들기 위해
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {		//DB결과를 ResultSet에서 한행씩 추출하여 BoardDTO로 만든다.
				BoardDTO dto = new BoardDTO();
				dto.setNumber(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setName(rs.getString("name"));
				dto.setJoinDate(rs.getDate("joinDate"));
				dtos.add(dto);	//BoardDTO객체를 ArrayList에 추가한다.
			}
			rs.close(); pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return dtos;
	}
	
// 게시글 추가하기
	public boolean insert(BoardDTO dto) {	
		String sql = "insert into Board1(num, title, content, name, joindate) values(NO_INCREASE.nextval,?,?,?, SYSDATE)";
		boolean check = false;
		try {  
			con = ds.getConnection();  //Connection 객체 CP에서 얻어오기
			pstmt =con.prepareStatement(sql);  	//Connection객체를 통해 SQL문 준비
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getName());
			
			int x = pstmt.executeUpdate(); // SQL을 수행하고 결과 반환 : 결과는 입력이 된 행 갯수
			
			if(x<1) { // 1보다 적으면
				System.out.println("정상적으로 저장되지 않았습니다.");
			}else { // 1이상인 경우는 저장이 된 경우
				check = true;
			}
			pstmt.close();
		}catch(SQLException ex) {
			System.out.println("SQL insert 오류: " + ex.getLocalizedMessage());
			check = false;
		}
		return check;
 }
	
	
// 게시판 상세보기
	public BoardDTO view(int num) {
		String sql ="select title,content,name,joindate from Board1 where num=?";
		BoardDTO dto = new BoardDTO();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 상세보기를 위한 한 레코드셋을 DTO에 저장
				dto.setNumber(num);
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setName(rs.getString("name"));
				dto.setJoinDate(rs.getDate("joinDate"));
			}
			
			rs.close();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return dto; // DTO객체에 데이터를 담아서 반환
	}
	
	
// 게시글 수정하기
	public boolean update(BoardDTO dto) {
		String sql = "update Board1 set title=?, content=?, name=? where num=?";
		boolean check = false;
		
		try {
			con = ds.getConnection();
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getName());
			pstmt.setInt(4, dto.getNumber());
			
			pstmt.executeUpdate();	

			pstmt.close();
			
		}catch(SQLException ex) {
			System.out.println("SQL update 오류 : " + ex.getLocalizedMessage());
		}finally {
			close();
		}
		return check;

	}
	
	// 게시글 삭제 하기		
		public void delete(int num) {
			String sql = "delete from Board1 where num=?";
			
			try {
				con = ds.getConnection();
				pstmt =con.prepareStatement(sql);
				pstmt.setInt(1, num);
				
				pstmt.executeUpdate();	


				pstmt.close();
				
			}catch(SQLException ex) {
				System.out.println("SQL delete 오류 : " + ex.getLocalizedMessage());
			}finally {
				close();
			}

		}	
	
}

