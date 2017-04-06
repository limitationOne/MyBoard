package board.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.util.ActionForward;

public interface BoardCommand {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException;
}