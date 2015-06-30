package com.eleks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eleks.model.db.User;
import com.eleks.repository.UserRepository;
import com.eleks.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	@Override
	public User getUserByName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Transactional
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}
}
