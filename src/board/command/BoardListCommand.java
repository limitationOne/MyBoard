package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.util.ActionForward;

public class BoardListCommand implements BoardCommand{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("BoardListCommand");
		
		request.setAttribute("list", BoardDAO.getDao().list());
		
		return new ActionForward(false, "list.jsp");
	}
}