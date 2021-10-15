package model;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String BankName;
	
	private ArrayList<User> users;
	
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new bank with empty list of users and accounts
	 * @param bankName name of the bank
	 */
	public Bank(String bankName) {
		
		this.BankName = bankName;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Generate a new unique ud for a user
	 * @return the unique id
	 */
	public String getNewUserUniqueId() {
		
		String uniqueId;
		Random string = new Random();
		int length = 6;
		boolean notUnique;
		
		//continue looping until we get a unique id (notUnique = true)
		do {
			
			//generate the unique number
			uniqueId = "";
			for(int i = 0; i < length; i++) {
				uniqueId += ((Integer)string.nextInt(10)).toString();
			}
			
			//check to make sure it is unique
			notUnique = false;
			for (User u : this.users) {
				if(uniqueId.compareTo(u.getUniqueId()) == 0) {
					notUnique = true;
					break;
				}
			}
			
		}while(notUnique);
		
		return uniqueId;
	}

	/**
	 *  Generate a new unique id for an account
	 * @return the unique id
	 */
	public String getNewAccountId() {
		
		String uniqueId;
		Random string = new Random();
		int length = 10;
		boolean notUnique;
		
		//continue looping until we get a unique id (notUnique = true)
		do {
			
			//generate the unique number
			uniqueId = "";
			for(int i = 0; i < length; i++) {
				uniqueId += ((Integer)string.nextInt(10)).toString();
			}
			
			//check to make sure it is unique
			notUnique = false;
			for (Account account : this.accounts) {
				if(uniqueId.compareTo(account.getUniqueAccountId()) == 0) {
					notUnique = true;
					break;
				}
			}   
			
		}while(notUnique);
		
		return uniqueId;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Create a new user 
	 * @param firstName the users first name
	 * @param lastName the users last name
	 * @param pin the users pin
	 * @return the new user object
	 */
	public User createNewUser(String firstName, String lastName, String pin) {
		
		//creates a new user
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//create a savings account for the user and add to user and bank
		//accounts lists
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
		
	}
	
	/**
	 * Check if the userId and pin are associated with a user and if they are valid
	 * @param UserId the uniqueId for the user to login 
	 * @param pin the pin of the user 
	 * @return the user object of the login is successful, or null, if it is not
	 */
	public User Login(String UserId, String pin) {
		
		//search list of users
		for(User u: this.users) {
			
			//check user Id is correct
			if(u.getUniqueId().compareTo(UserId) == 0 && u.validatePin(pin)) {
				return u;
			}
			
		}
		
		//if we havent found the user or have an incorrect pin
		return null;
	}
	
	public String getName() {
		return this.BankName;
	}
	
	public ArrayList<Account> getAccounts() {
		return this.accounts;
	}
	
}
