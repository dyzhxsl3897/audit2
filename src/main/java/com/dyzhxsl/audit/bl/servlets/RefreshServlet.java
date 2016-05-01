package com.dyzhxsl.audit.bl.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.bl.services.LoanService;
import com.dyzhxsl.audit.bl.services.UserService;

public class RefreshServlet extends HttpServlet {

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

		User currentUser = (User) req.getSession(true).getAttribute("currentUser");
		if (null == currentUser) {
			forwardPage = "/signIn.jsp";
		} else {
			List<Loan> loanList = loanService.getLoans(currentUser);
			req.getSession().setAttribute("loans", loanList);

			currentUser = userService.getUser(currentUser.getUserId());
			req.getSession(true).setAttribute("currentUser", currentUser);

			forwardPage = "/index.jsp";
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
		dispatcher.forward(req, resp);
	}

}
