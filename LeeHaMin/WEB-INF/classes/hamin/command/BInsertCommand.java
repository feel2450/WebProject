package hamin.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hamin.dao.BoardDAO;
import hamin.dto.BoardDTO;


public class BInsertCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardDTO dto = new BoardDTO(); // DB에 데이터를 저장하기 위해 DTO 객체 생성
		

		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setName(request.getParameter("name"));
		
		BoardDAO dao = new BoardDAO();
		dao.insert(dto); //DB에 DTO객체를 저장하기 위한 메소드 insert 호출

 }
}
