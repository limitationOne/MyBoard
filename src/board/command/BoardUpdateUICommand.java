package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.DTO.BoardDTO;
import board.util.ActionForward;

public class BoardUpdateUICommand implements BoardCommand {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		ActionForward af = new ActionForward(false, "list.do");
		String num = request.getParameter("num");
		
		if( !(num != null && !"".equals(num)) ) return af;
		
		request.setAttribute("dto", BoardDAO.getDao().updateUI(Integer.parseInt(num)));
		
		af.setPath("update.jsp");
		
		return af;
	}
}