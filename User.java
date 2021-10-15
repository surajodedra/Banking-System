package model;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

	//users first name
	private String firstName;
	
	//users last name
	private String lastName;
	
	//users ID number
	private String uniqueId;
	
	//users pin number (MD5)
	private byte pin[];
	
	//list of accounts of the user
	private ArrayList<Account> accounts;
	
	/**
	 * Create user
	 * @param firstName of user
	 * @param lastName of user
	 * @param pin users pin number
	 * @param bank what bank they have an account in
	 */
	public User (String firstName, String lastName, String pin, Bank bank) {
		
		//set users name
		this.firstName = firstName;
		this.lastName = lastName;
		
		//hashing the pin using MD5
		try {
			MessageDigest message = MessageDigest.getInstance("MD5");
			this.pin = message.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error, no such algorithm");
			e.printStackTrace();
			System.exit(1);
		}
		
		//generating a unique Id
		this.uniqueId = bank.getNewUserUniqueId();
		
		//List of accounts
		this.accounts = new ArrayList<Account>();
		
		//log message
		System.out.printf("New User %s, %s with ID %s created.\n", lastName, firstName, this.uniqueId);
	}

	/**
	 * Add an account for the user 
	 * @param account the account to add to the list 
	 */
	public void addAccount(Account account) {
		
		this.accounts.add(account);
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	
	/**
	 * Check whether the given pin matches the true user pin
	 * @param pin the pin to check
	 * @return whether the pin is valid or not
	 */
	public boolean validatePin(String pin) {
		
		try {
			MessageDigest md =  MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pin);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error, no such algorithm");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
		
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Prints summaries for the accounts of this user 
	 */
	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int i = 0; i < this.accounts.size(); i++) {
			System.out.printf("%d) %s\n", i +1 , this.accounts.get(i).getSummaryLines());
		}
		System.out.println();
		
	}
	
	public int numAccounts() {
		return this.accounts.size();
	}

	public void printAccountTransactionHistory(int accountIndex) {
		
		this.accounts.get(accountIndex).printTransHistory();
		
	}

	public double getAccountBalance(int accountIndex) {
		
		return this.accounts.get(accountIndex).getBalance();
		
	}

	public String getAccountUniqueId(int accountIndex) {
		return this.accounts.get(accountIndex).getUniqueAccountId();
	}

	public void addAccountTrans(int accountIndex, double amount, String note) {
		
		this.accounts.get(accountIndex).addTransaction(amount, note);
		
	}
	
}
