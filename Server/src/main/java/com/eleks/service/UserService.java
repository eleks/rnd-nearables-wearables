package com.eleks.service;

import com.eleks.model.db.User;

public interface UserService {
	User getUserByName(String userName);
	void saveUser(User user);
}
