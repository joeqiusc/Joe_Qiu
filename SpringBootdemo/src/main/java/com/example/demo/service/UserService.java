package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

@Service
public class UserService {

	private UserDao userDao;	
	
		@Autowired
		public UserService(@Qualifier("fakeDao") UserDao userDao) {
			this.userDao = userDao;
		}
		public int addUser(User user) {
			return userDao.insertUser(user);
	}
		
		public List<User> getAllUser(){
			return userDao.selectAllUser();
		}
		
		public Optional<User> getUserById(UUID id) {
			return userDao.selectUserById(id);
		}
		
		public int deleteUser(UUID id) {
			return userDao.deletUserById(id);
		}
		
		public int updateUser(UUID id, User newUser) {
			return userDao.updateUserById(id, newUser);
		}
}
