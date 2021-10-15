package model;

import java.util.ArrayList;

public class Account {

	//name of account
	private String name;
	
	//id number of account
	private String Id;
	
	//user that holds this account
	private User accountHolder;
	
	//transactions of this account
	private ArrayList<Transaction> transactions;
	
	/**
	 * Create new account
	 * @param name the name of the account
	 * @param accountHolder the user that holds thid account
	 * @param bank the bank the issues this account
	 */
	public Account(String name, User accountHolder, Bank bank) {
		
		//set the account name and holder
		this.name = name;
		this.accountHolder = accountHolder;
		
		//get new unique account Id
		this.Id = bank.getNewAccountId();
		
		//initialize transactions into an array list
		this.transactions = new ArrayList<Transaction>();
		
	}
	
	public String getUniqueAccountId() {
		return this.Id;
	}
	
	/**
	 * Get summary line for the account
	 * @return the string summary
	 */
	public String getSummaryLines() {
		
		//get the account's balance
		double balance = this.getBalance();
		
		//format the summary line depending on whether the balance is negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.Id, balance, this.name );
		}else {
			return String.format("%s : $(%.02f) : %s", this.Id, balance, this.name );
		}
		
	}
	
	public double getBalance() {
		double balance = 0;
		
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		
		return balance;
	}

	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.Id);
		for (int trans = this.transactions.size()-1; trans >= 0; trans--) {
			System.out.println(this.transactions.get(trans).getSummaryLine());
		}
		
		System.out.println();
		
	}

	public void addTransaction(double amount, String note) {
		//create  anew transaction object and add it to our list
		Transaction newTransaction = new Transaction(amount, note, this);
		this.transactions.add(newTransaction);
	}
	
}
