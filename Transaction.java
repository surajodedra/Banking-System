package model;

import java.util.Date;

public class Transaction {

	//amount of transaction
	private double amount;
	
	//time and date of transactions
	private Date timeOfTransaction;
	
	//note for this transaction
	private String note;
	
	//account in which the transaction was performed 
	private Account accountName;
	
	/**
	 * creates a new transaction 
	 * @param amount the amount transacted
	 * @param account the account the transaction belongs to
	 */
	public Transaction (double amount, Account account) {
		
		this.amount = amount;
		this.accountName = account;
		this.timeOfTransaction = new Date();
		this.note = "";
		
	}
	
	/**
	 * create a new transaction
	 * @param amount the amount transacted
	 * @param note the note for thr transaction
	 * @param account thr account the transaction belongs to
	 */
	public Transaction(double amount, String note, Account account) {
		
		//call the two argument constructor first
		//this basically meand if there is a change in the constructor
		//above for amount and account it automatically changes for this constructor too.
		this(amount, account);
		
		//set the note
		this.note = note;
		
	}

	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Get a string summarizing the transaction
	 * @return the summary
	 */
	public String getSummaryLine() {
		
		if (this.amount >=0) {
			return String.format("%s : $%.02f : %s", this.timeOfTransaction.toString(), this.amount, this.note);
		}else {
			return String.format("%s : $(%.02f) : %s", this.timeOfTransaction.toString(), -this.amount, this.note);
		}
		
	}
}
