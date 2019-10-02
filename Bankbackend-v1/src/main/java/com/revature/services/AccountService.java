package com.revature.services;

import static com.revature.AppDriver.currentSession;
import com.revature.daos.AccountDAO;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	// I am not sure this is the rigth way to get the current user and account
	
	User loggedUser = currentSession.getSessionUser();
	private Account account;
	private AccountDAO accountDao = new AccountDAO();
		
	private double MAX_BALANCE = 100000000; 
	
	public void viewBalance(User user) {
		account = accountDao.getById(loggedUser.getId());
		if (loggedUser != null)
		System.out.println("Your balance is: $" + this.convertMoneyToString(account.getBalance()));
	}
	
	public void deposit(String depositStr) {
		account = accountDao.getById(loggedUser.getId());		
		account = accountDao.update(account);
		double deposit = this.convertMoneyToDouble(depositStr);
		double balance = account.getBalance();
		if(deposit < 0 || deposit + balance > MAX_BALANCE) {
			System.out.println("Invalid amount for deposit. Transaction rejected");
			
		}
		
		balance = deposit + balance;		
		account.setBalance(balance);
			
		if (loggedUser != null)  {
			System.out.println("A deposit of $" + depositStr + " was made to your account.");
		};
		System.out.println("Please check your input number. Try again");
	}
	
	public void withdraw(String withdrawStr) {
		account = accountDao.getById(loggedUser.getId());		
		account = accountDao.update(account);
		double withdraw = this.convertMoneyToDouble(withdrawStr);
		double balance = account.getBalance();
		if(withdraw < 0 || balance < withdraw) {
			System.out.println("You cannot withdraw this amount. Transaction rejected");
			
		}
		balance -= withdraw;
		account.setBalance(balance);
		if(loggedUser != null) {
			System.out.println("You made a withdraw of $" + withdrawStr);
			
		};
		System.out.println("Please check your input number. Try again");
		
	}

	private double convertMoneyToDouble(String money) {
		if(money.contains(".")) {
			if(money.substring(money.indexOf(".")).length() > 2) money = money.replace(".", "");
			else money = money.replace(".", "") + "0";
			return Double.parseDouble(money);
		}
		else {
			return Double.parseDouble(money)*100;
		}
	}
	
	private String convertMoneyToString(double d) {
		String str = Double.toString(d);
		if(d > 99) return str.substring(0, str.length()-2) + "." + str.substring(str.length()-2);
		if(d < 100 && d > 9) return "0." + str;
		else return "0.0" + str;
	}
	
}
