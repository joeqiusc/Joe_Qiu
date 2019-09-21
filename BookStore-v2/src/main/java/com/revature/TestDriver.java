package com.revature;

import java.sql.Connection;

import com.revature.daos.BookDAO;
import com.revature.models.Book;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;
import com.revature.util.UserSession;

public class TestDriver {
	
	public static UserSession currentSession;
	
	public static void main(String[] args) {
		
		User user = new User(42, "rconnell", "forwardspace", "Robert", "Connell", new Role("USER"));
		Connection conn = ConnectionFactory.getInstance().getConnection(user);
		currentSession = new UserSession(user, conn);
		BookDAO dao = new BookDAO();
		
		for(Book b : dao.getAll()) {
			System.out.println(b);
		}
		
		
	}

}
