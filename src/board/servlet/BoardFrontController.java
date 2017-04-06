package board.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.command.BoardCommand;
import board.command.BoardDeleteCommand;
import board.command.BoardListCommand;
import board.command.BoardReplyCommand;
import board.command.BoardReplyUICommand;
import board.command.BoardRetrieveCommand;
import board.command.BoardSearchCommand;
import board.command.BoardUpdateCommand;
import board.command.BoardUpdateUICommand;
import board.command.BoardWriteCommand;
import board.command.BoardWriteUICommand;
import board.util.ActionForward;

@WebServlet("*.do")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardFrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		String path = request.getContextPath();
		String what = uri.substring(path.length());

		System.out.println(what);

		BoardCommand com = null;
		ActionForward af = null;

		if ("/list.do".equalsIgnoreCase(what)) {
			com = new BoardListCommand();
		} else if ("/write.do".equalsIgnoreCase(what)) {
			com = new BoardWriteCommand();
		} else if ("/writeUI.do".equalsIgnoreCase(what)) {
			com = new BoardWriteUICommand();
		} else if ("/retrieve.do".equalsIgnoreCase(what)) {
			com = new BoardRetrieveCommand();
		} else if ("/updateUI.do".equalsIgnoreCase(what)) {
			com = new BoardUpdateUICommand();
		} else if ("/update.do".equalsIgnoreCase(what)) {
			com = new BoardUpdateCommand();
		} else if ("/delete.do".equalsIgnoreCase(what)) {
			com = new BoardDeleteCommand();
		} else if ("/search.do".equalsIgnoreCase(what)) {
			com = new BoardSearchCommand();
		} else if ("/replyUI.do".equalsIgnoreCase(what)) {
			com = new BoardReplyUICommand();
		} else if ("/reply.do".equalsIgnoreCase(what)) {
			com = new BoardReplyCommand();
		}

		if (com != null)
			af = com.execute(request, response);

		if (af.isRedirect()) {
			response.sendRedirect(af.getPath());
		} else {
			RequestDispatcher dis = request.getRequestDispatcher(af.getPath());
			dis.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}