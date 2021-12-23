package hamin.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hamin.dao.MemberDAO;

public class MdeleteCommand implements MCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글 처리
		String id = request.getParameter("id");
		
		MemberDAO dao = new MemberDAO();
		
		dao.delete(id);

 }
}
