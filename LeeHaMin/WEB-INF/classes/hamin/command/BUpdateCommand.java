package hamin.command;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hamin.dao.BoardDAO;
import hamin.dto.BoardDTO;



public class BUpdateCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글 처리
		
		BoardDTO dto = new BoardDTO();
		
		dto.setNumber(Integer.parseInt(request.getParameter("number")));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setName(request.getParameter("name"));

		
		BoardDAO dao = new BoardDAO();
		
		dao.update(dto); // DB에 변경된 데이터 업데이트
	
  }
}
