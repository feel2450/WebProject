package hamin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hamin.command.BCommand;
import hamin.command.BInsertCommand;
import hamin.command.BListCommand;
import hamin.command.BUpdateCommand;
import hamin.command.BViewCommand;
import hamin.command.BdeleteCommand;





@WebServlet("*.ha")
public class BoardController extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String viewPage =null;
		BCommand command = null;
		
		String uri = request.getRequestURI(); 
		String com= uri.substring(uri.lastIndexOf("/")+ 1, uri.lastIndexOf(".ha"));
		
		if(com !=null && com.trim().equals("blist")) {
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/view/bList.jsp";
		}else if(com !=null && com.trim().equals("binsertForm")) {
			viewPage = "/WEB-INF/view/bInsertForm.jsp";
			
		}else if(com !=null && com.trim().equals("binsert")) {
			command = new BInsertCommand();
			command.execute(request, response);
			viewPage ="blist.ha";
		}else if(com !=null && com.trim().equals("bview")) {
			command = new BViewCommand();
			command.execute(request, response);
			viewPage = "/WEB-INF/view/bView.jsp";
		}else if(com !=null && com.trim().equals("bupdate")) {
			command = new BUpdateCommand();
			command.execute(request, response);
			viewPage = "blist.ha";
		}else if(com !=null && com.trim().equals("bdelete")) {
			command = new BdeleteCommand();
			command.execute(request, response);
			viewPage = "blist.ha";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

}