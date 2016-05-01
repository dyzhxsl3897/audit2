package com.dyzhxsl.audit.bl.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forwardPage = "/signIn.jsp";

		req.getSession(true).removeAttribute("currentUser");

		req.getRequestDispatcher(forwardPage).forward(req, resp);
	}

}
