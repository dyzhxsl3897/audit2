package com.dyzhxsl.audit.dao.engine;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;

@Repository
public class LoanDAO extends HibernateDaoSupport {

	@Autowired
	private Properties loanSqls;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public Loan getLoan(int loanId) {
		Loan loan = null;

		String sql = loanSqls.getProperty("loan.id.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("loanId", loanId);

		try {
			List<Loan> loanList = (List<Loan>) query.list();
			if (0 < loanList.size()) {
				loan = loanList.get(0);
			}
		} catch (Exception e) {
			loan = null;
			throw e;
		}

		return loan;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getAllLoans() {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("loan.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getUserLoans(User user) {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("user.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("fromUser", user.getUserId());
		query.setLong("toUser", user.getUserId());

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getFromUserLoans(User fromUser) {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("from.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("fromUser", fromUser.getUserId());

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getToUserLoans(User toUser) {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("to.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("toUser", toUser.getUserId());

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getLoans(User fromUser, User toUser) {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("from.to.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("fromUser", fromUser.getUserId());
		query.setLong("toUser", toUser.getUserId());

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getLoans(User fromUser, User toUser, Date fromDate) {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("from.to.fromdate.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("fromUser", fromUser.getUserId());
		query.setLong("toUser", toUser.getUserId());
		query.setTimestamp("fromDate", fromDate);

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	@SuppressWarnings("unchecked")
	public List<Loan> getLoans(User fromUser, User toUser, Date fromDate, Date toDate) {
		List<Loan> loanList = null;

		String sql = loanSqls.getProperty("from.to.fromdate.todate.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("fromUser", fromUser.getUserId());
		query.setLong("toUser", toUser.getUserId());
		query.setTimestamp("fromDate", fromDate);
		query.setTimestamp("toDate", toDate);

		loanList = (List<Loan>) query.list();

		return loanList;
	}

	public void saveLoan(User fromUser, User toUser, double money, String notes, User auditUser) {
		saveLoan(fromUser, toUser, money, new Date(), notes, auditUser);
	}

	public void saveLoan(User fromUser, User toUser, double money, Date loanTime, String notes, User auditUser) {
		Loan newLoan = new Loan();

		newLoan.setFromUser(fromUser);
		newLoan.setToUser(toUser);
		newLoan.setMoney(money);
		newLoan.setLoanTime(loanTime);
		newLoan.setNotes(notes);
		newLoan.setAuditUser(auditUser);
		newLoan.setAuditTime(new Date());

		getSessionFactory().getCurrentSession().save(newLoan);
	}

	public void deleteLoan(Loan loan) {
		getSessionFactory().getCurrentSession().delete(loan);
	}

}
