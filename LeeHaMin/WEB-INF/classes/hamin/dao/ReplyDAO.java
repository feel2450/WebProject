package hamin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ReplyDAO {
	private DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ReplyDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/HAMIN");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {if(con !=null) { con.close(); con=null;}
	}catch(SQLException e) {
		e.printStackTrace();
	}
  }
	
	public void register(String id, String reply) {
		CallableStatement cstmt = null;
		String sql = "{call pro_reply2(?,?)}"; // 저장 프로시저 호출
		try {
			con = ds.getConnection(); // Connection 객체 CP에서 얻어오기
			cstmt = con.prepareCall(sql); // Connection 객체를 통해 SQL문 준비
			
			cstmt.setString(1, id); // SQL문과 데이터 바인딩
			cstmt.setString(2, reply);
			cstmt.execute(); 
			
			cstmt.close();
		}catch(SQLException ex) {
		   System.out.println("register : SQL 오류 " + ex.getLocalizedMessage());
	    }finally {
	    	close();
	    }
	}
	
	public JSONArray rlist(String id) {
		String sql = "select id, num, reply from reply2 where id = ? order by num desc";
		JSONArray list = new JSONArray();
		
		try {
			con = ds.getConnection(); // Connection객체 CP에서 얻어오기
			
			pstmt = con.prepareStatement(sql); // Connection 객체를 통해 SQL문 준비
			pstmt.setString(1, id); // SQL문과 데이터 바인팅
			
			rs = pstmt.executeQuery(); // SQL을 수행하고 결과 반환 : 결과를 입력이 된 행 갯수
			
			while (rs.next()) { // DB결과를 ResultSet에서 한행씩 추출하여 REPLY DTO로 만든다.
				JSONObject json = new JSONObject();
				json.put("num", rs.getInt("num"));
				json.put("reply", rs.getString("reply"));
				
				list.add(json);
				
			}
			rs.close();
			pstmt.close();
		}catch(SQLException ex) {
			System.out.println("rlist : SQL insert 오류 : " + ex.getLocalizedMessage());
		}finally {
			close();
		}
		return list;
 }
}
