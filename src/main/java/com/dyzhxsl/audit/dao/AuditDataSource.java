package com.dyzhxsl.audit.dao;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import com.dyzhxsl.audit.bl.utils.EncryptUtil;

public class AuditDataSource extends BasicDataSource {

	private static Logger logger = Logger.getLogger(AuditDataSource.class);

	public AuditDataSource() {
		super();
	}

	@PostConstruct
	public void initDataSource() {
		try {
			String password = EncryptUtil.decode(this.getPassword());
			this.setPassword(password);
		} catch (IOException e) {
			logger.error("Error on decoding database password");
		}
	}

}
