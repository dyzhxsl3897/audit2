package com.dyzhxsl.audit.dao.engine;

import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.dyzhxsl.audit.bl.beans.User;

@Repository
public class UserDAO extends HibernateDaoSupport {

	@Autowired
	private Properties userSqls;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public User getUser(long userId) {
		User user = null;

		String sql = userSqls.getProperty("user.id.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setLong("userId", userId);

		try {
			@SuppressWarnings("unchecked")
			List<User> userList = (List<User>) query.list();
			if (0 < userList.size()) {
				user = userList.get(0);
			}
		} catch (Exception e) {
			user = null;
			throw e;
		}

		return user;
	}

	public User getUser(String username) {
		User user = null;

		String sql = userSqls.getProperty("user.username.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setString("username", username);

		try {
			@SuppressWarnings("unchecked")
			List<User> userList = (List<User>) query.list();
			if (0 < userList.size()) {
				user = userList.get(0);
			}
		} catch (Exception e) {
			user = null;
			throw e;
		}

		return user;
	}

	public User getUser(String username, String password) {
		User user = null;

		String sql = userSqls.getProperty("user.username.password.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);
		query.setString("username", username);
		query.setString("password", password);

		try {
			@SuppressWarnings("unchecked")
			List<User> userList = (List<User>) query.list();
			if (0 < userList.size()) {
				user = userList.get(0);
			}
		} catch (Exception e) {
			user = null;
			throw e;
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUser() {
		List<User> userList = null;

		String sql = userSqls.getProperty("user.all.select");
		Query query = getSessionFactory().getCurrentSession().createQuery(sql);

		userList = (List<User>) query.list();

		return userList;
	}

	public void save(User user) {
		getSessionFactory().getCurrentSession().saveOrUpdate(user);
	}

}
