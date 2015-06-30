package com.eleks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eleks.model.db.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserName(String userName);
}
