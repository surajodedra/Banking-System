package model;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		//initialize scanner
		Scanner s = new Scanner(System.in);
		
		//initialize bank
		Bank b = new Bank("Bank of A-Town");
		
		//add a user, which also creates a savings account
		User u = b.createNewUser("Suraj", "Odedra", "2000");
		
		//add a checking account
		Account newA = new Account("Checking", u, b);
		u.addAccount(newA);
		b.addAccount(newA);
		
		User currentUser;
		while(true) {
			
			//stay in the login prompt until successful login
			currentUser = ATM.mainMenu(b, s);
			
			//stay in the main menu until user quits
			ATM.printUserMenu(currentUser, s);
			
		}
		
	}
	
	/**
	 * Print the ATM login menu
	 * @param bank bank object
	 * @param s scanner
	 * @return authenticated user object
	 */
	public static User mainMenu(Bank bank, Scanner s) {
		
		//initializes
		String UserID;
		String pin;
		User authUser;
		
		//ask for userId and pin until a correct combination is reached
		do {
			
			System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
			System.out.print("Enter user ID: ");
			UserID = s.nextLine();
			System.out.printf("Enter Pin: ");
			pin = s.nextLine();
			
			//try to get the user object corresponding to thr id and pin combo
			authUser = bank.Login(UserID, pin);
			if(authUser == null) {
				System.out.println("Incorrect userID/pin combination, " + "please try again. ");
			}
			
		} while (authUser == null);//continue looping until successful login 
	
		return authUser;
		
	}
	
	public static void printUserMenu(User user, Scanner s) {
		
		//print a summary of the user's accounts
		user.printAccountsSummary();
		
		//initialize
		int choice;
		
		//user menu
		do {
			
			System.out.printf("Welcome %s, what would you like to do?\n ", user.getFirstName());
			
			System.out.println(" 1) show account transaction history");
			System.out.println(" 2) Withdraw");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.println("Enter choice: ");
			choice = s.nextInt();
			
			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice, PLease choose between 1 and 5");
				
			}
			
		} while (choice < 1 || choice > 5);
		
		//process the choice 
		switch (choice) {
			
		case 1:
			ATM.showTransHistory(user, s);
			break;
		case 2:
			ATM.withdrawFunds(user, s);
			break;
		case 3:
			ATM.depositFunds(user, s);
			break;
		case 4:
			ATM.transferFunds(user, s);
			break;
		case 5:
			//gobble up the rest of previous input
			s.nextLine();
			break;
		}
		
		//redisplay the menu unless the user wants to quit
		if (choice != 5) {
			ATM.printUserMenu(user, s);			
		}
	}
	
	public static void showTransHistory(User user, Scanner s) {
		
		int theAccount;
		
		//get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account" + " whose transactions you wanna see", user.numAccounts());
			
			theAccount = s.nextInt() - 1;
			
			if (theAccount < 0 || theAccount >= user.numAccounts()) {
				System.out.println("Invalid Account please try again");
			}
			
		} while (theAccount < 0 || theAccount >= user.numAccounts());
		
		//print the transaction history
		user.printAccountTransactionHistory(theAccount);
		
	}
	
	/**
	 * Process transferring funds from one account to another
	 * @param user user logged in
	 * @param s scanner object used for user input
	 */
	public static void transferFunds(User user, Scanner s) {
		
		//initialize
		int fromAccount;
		int toAccount;
		double amount;
		double accountBalance;
		
		//get the account to transfer from 
		do {
			System.out.printf("Enter thr number (1-%d) of the account\n" + "to transfer from: ", user.numAccounts());
			fromAccount = s.nextInt()-1;
			if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
				System.out.println("Invalid Account. PLease try again");
				
			}
		} while (fromAccount < 0 || fromAccount >= user.numAccounts());
		
		accountBalance = user.getAccountBalance(fromAccount);
		
		//get the account to transfer to
		do {
			System.out.printf("Enter thr number (1-%d) of the account\n" + "to transfer to: ", user.numAccounts());
			toAccount = s.nextInt()-1;
			if (toAccount < 0 || toAccount >= user.numAccounts()) {
				System.out.println("Invalid Account. PLease try again");
				
			}
		} while (toAccount < 0 || toAccount >= user.numAccounts());
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $", accountBalance);
			
			amount = s.nextDouble();
			
			if (amount < 0) {
				
				System.out.println("Amount must be greater than zero.");
			} else if (amount > accountBalance) {
				System.out.printf("Amount must not be greater than\n" + "balance of $%0.2f.\n", accountBalance);
				
			} 
		} while (amount < 0 || amount > accountBalance);
		
		//finally do the transfer 
		user.addAccountTrans(fromAccount, -1*amount, String.format("Transfer to account %s", user.getAccountUniqueId(toAccount)));
		user.addAccountTrans(toAccount, amount, String.format("Transfer to account %s", user.getAccountUniqueId(fromAccount)));
		
		}
	
	/**
	 * process a withdraw
	 * @param user logged in user
	 * @param s scanner object for user input
	 */
	public static void withdrawFunds(User user, Scanner s) {

		//initialize
		int fromAccount;
		double amount;
		double accountBalance;
		String note;

		//get the account to transfer from 
		do {
			System.out.printf("Enter thr number (1-%d) of the account\n" + "to transfer from: ", user.numAccounts());
			fromAccount = s.nextInt()-1;
			if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
				System.out.println("Invalid Account. PLease try again");

			}
		} while (fromAccount < 0 || fromAccount >= user.numAccounts());

		accountBalance = user.getAccountBalance(fromAccount);
		
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max $%.02f): $", accountBalance);

			amount = s.nextDouble();

			if (amount < 0) {

				System.out.println("Amount must be greater than zero.");
			} else if (amount > accountBalance) {
				System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", accountBalance);

			} 
		} while (amount < 0 || amount > accountBalance);
		
		//gobble up the rest of previous input
		s.nextLine();
		
		//get a note
		System.out.println("Enter a note: ");
		note = s.nextLine();
		
		//do withdraw
		user.addAccountTrans(fromAccount, -1*amount, note);
		
	}
	
	/**
	 * deposit funds into account
	 * @param user logged in user
	 * @param s scanner object for user input
	 */
	public static void depositFunds(User user, Scanner s) {
		
		//initialize
		int toAccount;
		double amount;
		double accountBalance;
		String note;

		//get the account to transfer from 
		do {
			System.out.printf("Enter thr number (1-%d) of the account\n" + "to deposit in: ", user.numAccounts());
			toAccount = s.nextInt()-1;
			if (toAccount < 0 || toAccount >= user.numAccounts()) {
				System.out.println("Invalid Account. PLease try again");

			}
		} while (toAccount < 0 || toAccount >= user.numAccounts());

		accountBalance = user.getAccountBalance(toAccount);

		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to deposit (max $%.02f): $", accountBalance);

			amount = s.nextDouble();

			if (amount < 0) {

				System.out.println("Amount must be greater than zero.");
			} 

		} while (amount < 0 );

		//gobble up the rest of previous input
		s.nextLine();

		//get a note
		System.out.println("Enter a note: ");
		note = s.nextLine();

		//do withdraw
		user.addAccountTrans(toAccount, amount, note);
		
	}

}
