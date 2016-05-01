package com.dyzhxsl.audit.bl.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyzhxsl.audit.bl.beans.User;
import com.dyzhxsl.audit.dao.engine.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDao;

	@Transactional
	public User getUser(int userId) {
		User user = userDao.getUser(userId);
		return user;
	}

	@Transactional
	public User getUser(String username, String password) {
		User user = userDao.getUser(username, password);
		return user;
	}

	@Transactional
	public List<User> getAllUser() {
		List<User> userList = userDao.getAllUser();

		return userList;
	}

	@Transactional
	public void changePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		userDao.save(user);
	}

	@Transactional
	public void changeUsername(User user, String username) {
		user.setUsername(username);
		userDao.save(user);
	}

	@Transactional
	public void addFriend(User user, User friend) {
		if (!user.getFriends().contains(friend)) {
			user.getFriends().add(friend);
			userDao.save(user);
		}
	}

	@Transactional
	public void removeFriend(User user, User friend) {
		user.getFriends().remove(friend);
		userDao.save(user);
	}

}
