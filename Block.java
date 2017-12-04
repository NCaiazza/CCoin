package CCoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;

public class Block {
	
	private String previousHash = null;
	private LinkedList<CCoinTransaction> transactions;
	private Timestamp timestamp;
	private int difficulty;
	private Miner miner;
	private String merkleRoot;
	private double reward;
	
	public Block(int difficulty, double reward) { // Previous Block for Genesis Block
		this.previousHash = "";
		this.transactions = new LinkedList<CCoinTransaction>();
		this.timestamp = new Timestamp();
		this.difficulty = difficulty;
		this.miner = new Miner(new User("Nick Caiazza", 0), "");
		this.reward = reward;
	}
	
	public void setMiner(Miner name) {
		this.miner = name;
	}
	
	public double getReward() {
		return this.reward;
	}
	
	public LinkedList<CCoinTransaction> getTransactions() {
		return transactions;
	}
	
	public Miner getMiner() {
		return this.miner;
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}
	
	public Block(LinkedList<CCoinTransaction> transactions, Timestamp t, Block previousBlock, int d, Miner miner) {
		this.transactions = new LinkedList<>();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = previousBlock.hashify(); // Get string version of previous block
			md.update(temp.getBytes()); // Update the Digest of md to be the has of the previous block
			this.previousHash = Arrays.toString(md.digest()); // Set the previous hash to be the digest of md
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		
		if (this.previousHash == null) { // If for some reason the previous hash is not set by this point, there is an error
			System.out.println("ERROR CREATING BLOCK"); // This code should never run
			System.exit(1); 
		}
		
		this.timestamp = t;
		
		this.difficulty = d;
		
		this.miner = miner;
	}
	
	public void addTransaction(CCoinTransaction t) {
		transactions.push(t);
	}
	
	public String getPreviousHash() {
		return previousHash.toString();
	}
	
	private String getMerkleRoot() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = "";
			for (int i = 0; i < transactions.size(); i++) {
				temp += transactions.get(i).hash(); // Get string version of previous block
			}
			md.update(temp.getBytes()); // Update the Digest of md to be the has of the previous block
			return Arrays.toString(md.digest()); // Set the previous hash to be the digest of md
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		return null;
	}
	
	private void setMerkleRoot() {
		this.merkleRoot = getMerkleRoot();
	}
	
	public String hashify() {
		return this.previousHash + this.timestamp.toString() + this.transactions.toString();
	}
	
	public String getThisHash() {
		String temp = this.previousHash + this.timestamp.toString() + this.transactions.toString();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			md.update(temp.getBytes()); // Update the Digest of md to be the has of the previous block
			temp = Arrays.toString(md.digest()); // Set the previous hash to be the digest of md
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		
		return temp;
	}
	
	@Override
	public String toString() {
		return "Transactions: " + this.transactions.toString() + "\nPrevious Block's Hash: " + this.previousHash + "\nTime: " + this.timestamp.toString()
			+ "\nMiner: " + this.miner;
	}
}
