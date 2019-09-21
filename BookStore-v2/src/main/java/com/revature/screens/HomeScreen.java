package com.revature.screens;

import static com.revature.AppDriver.*;

import java.io.BufferedReader;

public class HomeScreen extends Screen {
	
	public HomeScreen() {
		super("/home");
		System.out.println("Initializing HomeScreen");
	}

	@Override
	public void start(BufferedReader br) {
		
		System.out.println("+------------------------------------------+");
		System.out.println("|                                          |");
		System.out.println("|          REVATURE BOOK STORE             |");
		System.out.println("|                                          |");
		System.out.println("+------------------------------------------+");
		
		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Exit Application");
		
		try {
			
			// Get the user's selection
			System.out.print("> ");
			String userSelection = br.readLine();
			
			switch(userSelection) {
			
			case "1":
				router.navigate("/login", br);
				return;
			case "2":
				router.navigate("/register", br);
				return;
			case "3":
				System.out.println("Exiting application.");
				appRunning = false;
				return;
			default:
				System.out.println("[LOG] - Invalid selection!");
				router.navigate("/home", br);
				return;
			}
			
		} catch (Exception e) {
			
			System.out.println("[ERROR] - Error reading input fom console");
			System.out.println("[LOG] - Shutting down application");
			appRunning = false;
		}
		
	}

}
