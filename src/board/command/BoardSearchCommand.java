package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.util.ActionForward;

public class BoardSearchCommand implements BoardCommand {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String type = request.getParameter("searchType");
		String value = request.getParameter("searchValue");
		
		request.setAttribute("to", BoardDAO.getDao().search(type, value));
		
		return new ActionForward(false, "list.jsp");
	}
}