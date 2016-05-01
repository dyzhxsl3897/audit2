package com.dyzhxsl.audit.bl.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.bl.services.LoanService;
import com.dyzhxsl.audit.bl.services.UserService;
import com.dyzhxsl.audit.bl.utils.SMSSender;

public class CreateLoanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger auditLogger = Logger.getLogger("auditInfo");

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
			String moneyDirectionSelect = req.getParameter("moneyDirectionSelect");
			String friendSelect = req.getParameter("friendSelect");
			String moneyInput = req.getParameter("moneyInput");
			String loanTimeInput = req.getParameter("loanTimeInput");
			String notesInput = req.getParameter("notesInput");

			if (!StringUtils.isEmpty(moneyDirectionSelect) && !StringUtils.isEmpty(friendSelect) && !StringUtils.isEmpty(moneyInput)
					&& !StringUtils.isEmpty(loanTimeInput) && !StringUtils.isEmpty(notesInput)) {
				User friend = userService.getUser(Integer.valueOf(friendSelect));
				User fromUser = null;
				User toUser = null;
				if ("expense".equals(moneyDirectionSelect)) {
					fromUser = currentUser;
					toUser = friend;
				} else {
					fromUser = friend;
					toUser = currentUser;
				}

				double money = Double.valueOf(moneyInput);
				Date loanTime = new Date();
				try {
					loanTime = DateUtils.parseDate(loanTimeInput, "yyyy-MM-dd");
				} catch (ParseException e) {
					auditLogger.error("Error on parsing date: " + loanTimeInput, e);
				}

				StringBuilder logMsg = new StringBuilder();
				logMsg.append(fromUser.getFirstName()).append(" ").append(fromUser.getLastName());
				logMsg.append(" lend $").append(money).append(" to ");
				logMsg.append(toUser.getFirstName()).append(" ").append(toUser.getLastName());
				logMsg.append(" for \"").append(notesInput).append("\"");
				logMsg.append(" on ").append(loanTime).append(".");
				logMsg.append(" Audit by ").append(currentUser.getFirstName()).append(" ").append(currentUser.getLastName());
				auditLogger.info(logMsg.toString());

				String[] phoneNumbers = new String[2];
				phoneNumbers[0] = fromUser.getPhone();
				phoneNumbers[1] = toUser.getPhone();
				try {
					SMSSender.sendSMS(phoneNumbers, logMsg.toString());
					loanService.createLoan(fromUser, toUser, money, loanTime, notesInput, currentUser);
				} catch (Exception e) {
					auditLogger.error("Error on sending SMS message to " + fromUser + "(" + phoneNumbers[0] + ") and " + toUser + "("
							+ phoneNumbers[1] + ")");
				}

				List<Loan> loanList = loanService.getLoans(currentUser);
				req.getSession().setAttribute("loans", loanList);

				forwardPage = "/index.jsp";
			}
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
		dispatcher.forward(req, resp);
	}

}
