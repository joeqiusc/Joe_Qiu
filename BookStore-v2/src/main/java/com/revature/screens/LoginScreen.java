package com.revature.screens;

import static com.revature.AppDriver.*;

import java.io.BufferedReader;
import java.sql.Connection;

import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;
import com.revature.util.UserSession;

public class LoginScreen extends Screen {
		
	private UserService userService = new UserService();
	
	public LoginScreen() {
		super("/login");
		System.out.println("Initializing LoginScreen");
	}

	@Override
	public void start(BufferedReader br) {
		
		// Method-scope variables to store user credentials
		String username, password;
		
		try {
			
			// Get user credentials
			System.out.println("\n\n\n\n+---------------------------------------------------------------------+\n");
			System.out.println("Please provide your login credentials");
			System.out.print("Username: ");
			username = br.readLine();
			System.out.print("Password: ");
			password = br.readLine();
			
			User loggedUser = userService.login(username, password);
			
			// If the user is not null, then the login attempt was successful; navigate to dashboard
			if(loggedUser != null) {
				System.out.println("[LOG] - Login successful, navigating to dashboard");
				Connection conn = ConnectionFactory.getInstance().getConnection(loggedUser);
				currentSession = new UserSession(loggedUser, conn);
				router.navigate("/dashboard", br);
				return;
			}
			
			// If the user is null, then the login attempt was unsuccessful; inform the user and navigate to home
			else {
				currentSession = null;
				System.out.println("[LOG] - Invalid credentials provided, navigating to home screen");
				router.navigate("/home", br);
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] - Error reading input from console");
			appRunning = false;
		}
		
	}
	
	

}
