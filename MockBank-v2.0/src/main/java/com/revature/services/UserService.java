package com.revature.services;

import static com.revature.AppDriver.currentSession;

import java.util.ArrayList;
import java.util.List;

import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;

public class UserService {

	private UserDAO userDao = new UserDAO();
	private AccountDAO accountDao = new AccountDAO();
	public List<User> getAllUsers() {

		User currentUser = currentSession.getSessionUser();	
		// Verify that the current user logged in is authorized to perform this
		// operation
		if (currentUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
			return userDao.getAll();
		}
		return new ArrayList<>();

	}

	public User getUserById(int userId) {
		
		User currentUser = currentSession.getSessionUser();
		if (currentUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
			return userDao.getById(userId);
		}

		return null;

	}

	public User getUserByUsername(String username) {
		
		User currentUser = currentSession.getSessionUser();

		if (username.equals("") || username == null) {
			return null;
		}
		if (currentUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) {
			return userDao.getUserByUsername(username);
		}

		return null;
	}

	public User login(String username, String password) {
		if ((username.equals("") || username == null) || password.equals("") || password == null) {
			return null;
		}
		return userDao.getUserByCredentials(username, password);

	}

	public User register(User newUser) {
		String username = newUser.getUsername();
//		validation for
		if(!validateUserFields(newUser)) return null;
//		validation for username available
		boolean usernameAvailable = userDao.getUserByUsername(username) == null;
		if (!usernameAvailable) return null;
	
		newUser  = userDao.add(newUser);

// Once the newUser register, the accountDao will create a new account for new user!		
		Account newAccount = new Account();
		newAccount.setUserId(newUser.getId());
		accountDao.add(newAccount);
		
		return newUser;

	}
	
	public User updateUser(User updatedUser) {
		
		User currentUser = currentSession.getSessionUser();		
		if(validateUserFields(updatedUser)) return null;		
		if(currentUser.getId() != updatedUser.getId() ||currentUser.getRole().getRoleName().equalsIgnoreCase("ADMIN")) return null;		
		return userDao.update(updatedUser);
	}
	
	public boolean deleteUser (int userId) {
		
		User currentUser = currentSession.getSessionUser();		
		if(currentUser.getId() != userId || currentUser.getRole().getRoleName().equalsIgnoreCase("USER")) return false;
		return userDao.delete(userId);	
	}
	
	private boolean validateUserFields(User user) {
		if (user.getUsername().trim().equals("") || user.getUsername() == null)
			return false;
		if (user.getPassword().trim().equals("") || user.getPassword() == null)
			return false;
		if (user.getFirstName().trim().equals("") || user.getFirstName() == null)
			return false;
		if (user.getLastName().trim().equals("") || user.getLastName() == null)
			return false;
			
		return true;
			
	}
	
}