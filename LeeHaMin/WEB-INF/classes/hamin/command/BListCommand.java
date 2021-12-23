package hamin.command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hamin.dao.BoardDAO;
import hamin.dto.BoardDTO;



public class BListCommand implements BCommand{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. DB 작업을 위해 DB처리를 하고 있는 BoardDAO 객체를 생성한다.
		BoardDAO dao = new BoardDAO();
		
		//2. DB에서 가져온 ArrayList 내의 BoardDTO 객체들을 dots 변수에 저장한다.
		ArrayList<BoardDTO> dtos = dao.list();
		
		//3. dtos를 request scope에 저장하여 View가 화면출력할 수 있도록 준비한다.
		request.setAttribute("dtos", dtos);

 }
}
