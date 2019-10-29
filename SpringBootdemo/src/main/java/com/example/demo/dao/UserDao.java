package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.User;

public interface UserDao {

	int insertUser(User user);
	
	default int addUser(User user){

		return insertUser(user);
	}

	List<User> selectAllUser();
	
	Optional<User> selectUserById(UUID id);
	
	int deletUserById(UUID id);
	
	int updateUserById(UUID id, User user);
	
	
}
