package com.revature.services;

import static com.revature.AppDriver.currentSession;
import com.revature.daos.AccountDAO;
import com.revature.models.Account;
import com.revature.models.User;

/**
 * @author Joe This class will handle the customer input requirement, such as
 *         view the balance, deposit money, withdraw money, and also handle the
 *         input string, will format it and change it into double value.
 */
public class AccountService {

	User loggedUser = currentSession.getSessionUser();
	private Account account;
	private AccountDAO accountDao = new AccountDAO();

	private double MAX_BALANCE = 100000000;

	/**
	 * @param user, once pass the user information in, it will talk to account dao
	 * @return void, the method just print the balance information on the console;
	 */
	public void viewBalance(User user) {
		account = accountDao.getById(loggedUser.getId());

		if (loggedUser != null)
		System.out.println("============================");
		System.out.println("Welcome " + loggedUser.getFirstName()+ " " +loggedUser.getLastName());
		System.out.println("Your balance is: $" + Double.valueOf(account.getBalance()));
		System.out.println("============================");
	}

	/**
	 * This method will realize the money deposit function
	 * 
	 * @param input string from the console
	 * @return void, it will print the information on the console;
	 * 
	 */
	public void deposit(String depositStr) {

		account = accountDao.getById(loggedUser.getId());
		if (loggedUser != null) {
			double deposit = Double.valueOf(depositStr);
			double balance = account.getBalance();
			if (deposit < 0 || deposit + balance > MAX_BALANCE) {
				System.out.println("Invalid amount for deposit. Transaction rejected");

			} else {
				balance = deposit + balance;
				account.setBalance(balance);

 				accountDao.update(account);
			}
			if (loggedUser != null) {
				System.out.println("===========================================");
				System.out.println("A deposit of $" + depositStr + " was made to your account.");
				System.out.println("===========================================");
				System.out.println("Your balance is: $" + Double.valueOf(account.getBalance()));
			} else
				System.out.println("Please check your input number. Try again");
		}
	}

	/**
	 * This method will realize the withdraw function for customer
	 * 
	 * @param input string from the console
	 * @return void, it will print the information on the console;
	 * 
	 */
	public void withdraw(String withdrawStr) {
		account = accountDao.getById(loggedUser.getId());
		if (loggedUser != null) {
			double withdraw = Double.valueOf(withdrawStr);
			double balance = account.getBalance();
			if (withdraw < 0 || balance < withdraw) {
				System.out.println("You cannot withdraw this amount. Transaction rejected");

			} else

			{
				balance -= withdraw;
				account.setBalance(balance);
 				accountDao.update(account);
			}
			if (loggedUser != null) {
				System.out.println("You made a withdraw of $" + withdrawStr);
				System.out.println("Your balance is: $" + Double.valueOf(account.getBalance()));
			}else
			System.out.println("Please check your input number. Try again");

		}
	}
	
}