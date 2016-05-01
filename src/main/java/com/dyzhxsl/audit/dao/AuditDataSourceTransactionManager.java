package com.dyzhxsl.audit.dao;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class AuditDataSourceTransactionManager extends HibernateTransactionManager {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AuditDataSourceTransactionManager.class);

	@Override
	protected void doBegin(Object arg0, TransactionDefinition arg1) {
		super.doBegin(arg0, arg1);
		logger.debug("Transaction Manager doBegin()...");
	}

	@Override
	protected void doCommit(DefaultTransactionStatus arg0) {
		super.doCommit(arg0);
		logger.debug("Transaction Manager doCommit()...");
	}

	@Override
	protected void doRollback(DefaultTransactionStatus arg0) {
		super.doRollback(arg0);
		logger.debug("Transaction Manager doRollback()...");
	}
}
