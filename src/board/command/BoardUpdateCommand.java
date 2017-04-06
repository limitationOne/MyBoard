package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.DAO.BoardDAO;
import board.util.ActionForward;

public class BoardUpdateCommand implements BoardCommand {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("BoardUpdateCommand");
		
		String num = request.getParameter("num");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		System.out.println(num + title + content);
		
		ActionForward af = new ActionForward(false, "retrieve.do?num=" + num);
		
		if( !(num != null && !"".equals(num) && title != null && !"".equals(title) 
				&& content != null && !"".equals(content)) ){
			af.setPath("list.do");
			return af;
		}
		BoardDAO.getDao().update(Integer.parseInt(num), title, content);
		
		return af;
	}
}