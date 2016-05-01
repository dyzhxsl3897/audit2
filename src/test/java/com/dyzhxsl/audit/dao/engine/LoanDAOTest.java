package com.dyzhxsl.audit.dao.engine;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dyzhxsl.audit.bl.beans.Loan;
import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.dao.GenericDAOTest;

public class LoanDAOTest extends GenericDAOTest {

	@Autowired
	private LoanDAO loanDao;

	@Autowired
	private UserDAO userDao;

	@Transactional
	@Test
	public void testGetLoansIntInt() {
		User test1 = userDao.getUser("test1");
		User test2 = userDao.getUser("test2");

		List<Loan> loans = loanDao.getLoans(test1, test2);

		Assert.assertNotNull(loans);
	}

	@Transactional
	@Test
	public void testSaveLoan() {
		User test1 = userDao.getUser("test1");
		User test2 = userDao.getUser("test2");
		User auditUser = userDao.getUser("test3");

		int loanSize = loanDao.getLoans(test1, test2).size();
		loanDao.saveLoan(test1, test2, 99.99, "Costco", auditUser);

		Assert.assertEquals(loanSize + 1, loanDao.getLoans(test1, test2).size());
	}

	@Transactional
	@Test
	public void testSaveLoanWithLoanTime() throws ParseException {
		User test1 = userDao.getUser("test1");
		User test2 = userDao.getUser("test2");
		User auditUser = userDao.getUser("test3");

		int loanSize = loanDao.getLoans(test1, test2).size();
		loanDao.saveLoan(test1, test2, 88.88, DateUtils.parseDate("2014-09-25", "yyyy-MM-dd"), "Costco", auditUser);

		Assert.assertEquals(loanSize + 1, loanDao.getLoans(test1, test2).size());
	}

	@Transactional
	@Test
	public void testDeleteLoan() {
		User test1 = userDao.getUser("test1");

		int expectedLoanListSize = loanDao.getAllLoans().size() - 1;

		List<Loan> loanList = loanDao.getFromUserLoans(test1);
		Loan loanDeleted = loanList.get(loanList.size() - 1);

		loanDao.deleteLoan(loanDeleted);

		Assert.assertEquals(expectedLoanListSize, loanDao.getAllLoans().size());
	}

}
