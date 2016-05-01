package com.dyzhxsl.audit.dao.engine;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.bl.utils.EncryptUtil;
import com.dyzhxsl.audit.dao.GenericDAOTest;

public class UserDAOTest extends GenericDAOTest {

	@Autowired
	private UserDAO userDao;

	@Transactional
	@Test
	public void testGetUser() {
		User user = userDao.getUser(8);
		Assert.assertEquals("denglufei", String.valueOf(user.getUsername()));
	}

	@Transactional
	@Test
	public void testGetUserUsername() throws Exception {
		User user = userDao.getUser("test1");
		Assert.assertEquals("Test1", String.valueOf(user.getFirstName()));
	}

	@Transactional
	@Test
	public void testGetUserUsernamePassword() throws Exception {
		User user = userDao.getUser("test2", EncryptUtil.encode("12345"));
		Assert.assertEquals("Test2", String.valueOf(user.getFirstName()));
	}

}
