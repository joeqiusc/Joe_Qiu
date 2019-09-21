package com.revature.screens;

import static com.revature.AppDriver.currentSession;

import java.io.BufferedReader;
import java.sql.Connection;

import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;
import com.revature.util.UserSession;
/**
 * @author Joe
 * LoginScreen implements the Screen, provide user login function
 * 
 * */
public class LoginScreen implements Screen {

	private UserService userService = new UserService();

	@Override
	public Screen start(BufferedReader br) {
		String username, password;

		try {

			// get user credential
			System.out.println("\n\n\n\n-------------------------------------------------------------------\n");
			System.out.println("Please provide your login credential!");
			System.out.println("Username: ");
			username = br.readLine();
			System.out.println("Password: ");
			password = br.readLine();

			User loggedUser = userService.login(username, password);

			// if the user is not null then the login attempt was successful; navigate to
			// dashboard

			if (loggedUser != null) {
				System.out.println("\n\n------------------------------------------");
				System.out.println("[LOG]- login successful! ");
				Connection conn = ConnectionFactory.getInstance().getConnection(loggedUser);
				currentSession  = new UserSession(loggedUser, conn);
				System.out.println("Welcome: "+loggedUser.getFirstName() + " " +loggedUser.getLastName());
				return new BankScreen().start(br);
				

			} else {
				currentSession = null;

				System.out.println("[LOG]- Invalid credential, navigate to home screen ");

				return new HomeScreen().start(br);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error- error reading input from console");
		}

		return null;

	}

}
