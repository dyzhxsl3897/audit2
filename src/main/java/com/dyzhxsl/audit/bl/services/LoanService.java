package com.dyzhxsl.audit.bl.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.dao.engine.LoanDAO;

@Service
public class LoanService {

	@Autowired
	private LoanDAO loanDao;

	@Transactional
	public List<Loan> getLoans(User user) {
		List<Loan> loans = loanDao.getUserLoans(user);

		return loans;
	}

	@Transactional
	public Loan getLoan(int loanId) {
		Loan loan = loanDao.getLoan(loanId);

		return loan;
	}

	@Transactional
	public void createLoan(User fromUser, User toUser, double money, Date loanTime, String notes, User auditUser) {
		loanDao.saveLoan(fromUser, toUser, money, loanTime, notes, auditUser);
	}

	@Transactional
	public void deleteLoan(Loan loan) {
		loanDao.deleteLoan(loan);
	}

}
