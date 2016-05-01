package com.dyzhxsl.audit.bl.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.bl.services.LoanService;
import com.dyzhxsl.audit.bl.services.UserService;
import com.dyzhxsl.audit.bl.utils.EncryptUtil;

public class SignInServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	@Autowired
	private LoanService loanService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String forwardPage = "";

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		User currentUser = null;
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
			currentUser = (User) req.getSession(true).getAttribute("currentUser");
		}

		if (null == currentUser && !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			currentUser = userService.getUser(username, EncryptUtil.encode(password));
		}

		if (null != currentUser) {
			req.getSession(true).setAttribute("currentUser", currentUser);

			List<Loan> loanList = loanService.getLoans(currentUser);
			req.getSession().setAttribute("loans", loanList);

			forwardPage = "/index.jsp";
		} else {
			req.setAttribute("errMsg", "No user found!");
			forwardPage = "/signIn.jsp";
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
		dispatcher.forward(req, resp);
	}

}
