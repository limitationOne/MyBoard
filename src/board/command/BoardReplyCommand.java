package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.util.ActionForward;

public class BoardReplyCommand implements BoardCommand {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String author = request.getParameter("author");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String repRoot = request.getParameter("repRoot");
		String repStep = request.getParameter("repStep");
		String repIndent = request.getParameter("repIndent");
		
		if(author != null  && !"".equals(author) && title != null  && !"".equals(title) 
				&& content != null && !"".equals(content) && repRoot != null && !"".equals(repRoot) 
				&& repStep != null && !"".equals(repStep) && repIndent != null && !"".equals(repIndent)){
			System.out.println("BoardReplyCommand if()");
			
			int num = BoardDAO.getDao().reply(author, title, content, 
					Integer.parseInt(repRoot), Integer.parseInt(repStep), Integer.parseInt(repIndent));
			
			request.setAttribute("dto", BoardDAO.getDao().retrieve(num));
		}
		return new ActionForward(false, "retrieve.jsp");
	}
}