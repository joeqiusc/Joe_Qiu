package com.revature.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.models.User;

public class UserSession {

	private User sessionUser;
	private Connection connection;
	
	public UserSession (User user, Connection conn) {
		sessionUser =  user;
		connection = conn;
		
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void finalize() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
