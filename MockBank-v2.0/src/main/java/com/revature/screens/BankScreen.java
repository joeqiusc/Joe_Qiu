package com.revature.screens;

import static com.revature.AppDriver.currentSession;

import java.io.BufferedReader;

import com.revature.AppDriver;
import com.revature.models.User;
import com.revature.services.AccountService;
/**
 * @author Joe
 * The BankScreen implement the Screen, which will supply user for the 
 * collection to the account service class, and call the function from
 * account service, realize the money transfer function.
 * 
 * */
public class BankScreen implements Screen {

	User loggedUser = currentSession.getSessionUser();
	private AccountService accountService = new AccountService();

	@Override
	public Screen start(BufferedReader br) {

		System.out.println("+------------------------------------------+");
		System.out.println("|                                          |");
		System.out.println("|          REVATURE MOCK BANK              |");
		System.out.println("|                                          |");
		System.out.println("+------------------------------------------+");
		System.out.println("\n\n");

		System.out.println("1) View Balance");
		System.out.println("2) Deposit");
		System.out.println("3) Withdraw");
		System.out.println("4) Logout");

		try {

			System.out.print("> ");
			String userSelection = br.readLine();

			switch (userSelection) {

			case "1":
				accountService.viewBalance(loggedUser);
				return new BankScreen().start(br);

			case "2":
				System.out.println("Please input the number you want to deposit: ");
				String deposit = br.readLine();
				accountService.deposit(deposit);
				return new BankScreen().start(br);
			case "3":
				System.out.println("Please input the number you want to withdraw: ");
				String withdraw = br.readLine();
				accountService.withdraw(withdraw);
				return new BankScreen().start(br);			
			case "4":
				System.out.println("Welcome to HomeScreen!");
				loggedUser = null;
				return new HomeScreen().start(br);
			default:
				System.out.println("[LOG] - Invalid selection!");
				return this.start(br);

			}

		} catch (Exception e) {

			System.out.println("[ERROR] - Error reading input fom console");

			return new BankScreen().start(br);	

		}
	}

}
