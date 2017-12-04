package CCoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CCoinTransaction {
	
	private User sender;
	private User recipient;
	private double amount;
	private double transactionFee;
	
	public User getSender() {
		return sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public CCoinTransaction(User sender, User recipient, double amount, double transactionFee) {
		this.sender = sender;
		this.recipient = recipient;
		this.amount = amount;
		this.transactionFee = transactionFee;
	}
	
	public String hash() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = this.recipient.toString() + this.transactionFee + this.sender.toString() + this.amount;
			md.update(temp.getBytes()); // Update the Digest of md to be the has of the previous block
			return Arrays.toString(md.digest()); // Set the previous hash to be the digest of md
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		
		return null;
	}
}
