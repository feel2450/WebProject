package hamin.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hamin.dao.BoardDAO;
import hamin.dto.BoardDTO;

public class BdeleteCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글 처리
		String number = request.getParameter("number");
		BoardDTO dto = new BoardDTO();
		dto.setNumber(Integer.parseInt(number));
		
		BoardDAO dao = new BoardDAO();
		
		dao.delete(dto.getNumber());

 }
}
