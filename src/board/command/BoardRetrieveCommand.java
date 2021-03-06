package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.util.ActionForward;

public class BoardRetrieveCommand implements BoardCommand {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String num = request.getParameter("num");
		
		if ( num != null && !"".equals(num) ) {
			request.setAttribute("dto", BoardDAO.getDao().retrieve(Integer.parseInt(num)));
		}
		return new ActionForward(false, "retrieve.jsp");
	}
}