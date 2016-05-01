package com.dyzhxsl.audit.bl.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.bl.services.UserService;

public class ChangeUsernameServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forwardPage = "";

		User currentUser = (User) req.getSession(true).getAttribute("currentUser");
		if (null == currentUser) {
			forwardPage = "/signIn.jsp";
		} else {
			String usernameInput = req.getParameter("usernameInput");

			if (!StringUtils.isEmpty(usernameInput)) {
				userService.changeUsername(currentUser, usernameInput);
				forwardPage = "/index.jsp";
				req.getSession(true).setAttribute("currentUser", currentUser);
			}
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
		dispatcher.forward(req, resp);
	}

}
