package com.revature.screens;

import java.io.BufferedReader;

import com.revature.AppDriver;
/**
 * @author Joe
 * HomeScreen class will guide you to login screen, register screen or exit the bank
 * and also will hand the wrong import exceptions.
 * */
public class HomeScreen implements Screen {

	@Override
	public Screen start(BufferedReader br) {
		
		System.out.println("+------------------------------------------+");
		System.out.println("|                                          |");
		System.out.println("|          REVATURE MOCK BANK              |");
		System.out.println("|                                          |");
		System.out.println("+------------------------------------------+");
		
		System.out.println("1) Login");
		System.out.println("2) Register");
		System.out.println("3) Exit Bank");
		
		try {
			
			// Get the user's selection
			System.out.print("> ");
			String userSelection = br.readLine();
			
			switch(userSelection) {
			
			case "1":
				return new LoginScreen().start(br);
			
			case "2":
				System.out.println("Register option selected.");
				return new RegisterScreen().start(br);
			case "3":
				System.out.println("Thank you for choose revature Bank, Bye Bye!");
				AppDriver.appRunning = false;
				break;
			default:
				System.out.println("[LOG] - Invalid selection!");
				return this.start(br);
			
			}
			
		} catch (Exception e) {
			
			System.out.println("[ERROR] - Error reading input fom console");
			System.out.println("[LOG] - Restarting application");
			return null;
		}
		
		return this;
		
	}

}
