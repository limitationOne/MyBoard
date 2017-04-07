package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.util.ActionForward;

public class BoardWriteCommand implements BoardCommand {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		BoardDAO.getDao().write(
				request.getParameter("author"), 
				request.getParameter("title"), 
				request.getParameter("content"));
		
		return new ActionForward(true, "list.do?num=1");
	}
}