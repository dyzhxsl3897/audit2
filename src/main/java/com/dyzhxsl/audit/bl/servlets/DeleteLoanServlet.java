package com.dyzhxsl.audit.bl.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.bl.services.LoanService;
import com.dyzhxsl.audit.bl.utils.SMSSender;

public class DeleteLoanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger auditLogger = Logger.getLogger("auditInfo");

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
		if (null != currentUser) {
			String[] loanCheckbox = req.getParameterValues("loanCheckbox");
			if (null != loanCheckbox) {
				for (String loanId : loanCheckbox) {
					Loan loan = loanService.getLoan(Integer.valueOf(loanId));

					StringBuilder logMsg = new StringBuilder();
					logMsg.append("Loan (");
					logMsg.append(loan.getFromUser().getFirstName()).append(" ").append(loan.getFromUser().getLastName());
					logMsg.append(" lent $").append(loan.getMoney()).append(" to ");
					logMsg.append(loan.getToUser().getFirstName()).append(" ").append(loan.getToUser().getLastName());
					logMsg.append(" for \"").append(loan.getNotes()).append("\"");
					logMsg.append(" on ").append(loan.getLoanTime());
					logMsg.append(" ) is deleted by ");
					logMsg.append(currentUser.getFirstName()).append(" ").append(currentUser.getLastName());
					auditLogger.info(logMsg.toString());

					String[] phoneNumbers = new String[2];
					phoneNumbers[0] = loan.getFromUser().getPhone();
					phoneNumbers[1] = loan.getToUser().getPhone();
					try {
						SMSSender.sendSMS(phoneNumbers, logMsg.toString());
						loanService.deleteLoan(loan);
					} catch (Exception e) {
						auditLogger.error("Error on sending SMS message to " + loan.getFromUser() + "(" + phoneNumbers[0] + ") and "
								+ loan.getToUser() + "(" + phoneNumbers[1] + ")");
					}
				}
			}

			List<Loan> loanList = loanService.getLoans(currentUser);
			req.getSession().setAttribute("loans", loanList);

			forwardPage = "/index.jsp";
		} else {
			forwardPage = "/signIn.jsp";
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPage);
		dispatcher.forward(req, resp);
	}

}
