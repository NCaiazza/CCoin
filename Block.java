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
	
	public Block(LinkedList<CCoinTransaction> transactions, Timestamp t, Block previousBlock, int d, Miner miner) {
		this.transactions = new LinkedList<>();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = previousBlock.hashify(); // Get string version of previous block
			md.update(temp.getBytes()); // Update the Digest of md to be the hash of the previous block
			this.previousHash = Arrays.toString(md.digest()); // Set the previous hash to be the digest of md
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		
		if (this.previousHash == null) { // If for some reason the previous hash is not set by this point, there is an error
			System.out.println("ERROR CREATING BLOCK"); // This code should never run
			System.exit(1); // This code should never run
		}
		
		this.timestamp = t;
		
		this.difficulty = d;
		
		this.miner = miner;
	}
	
	/**
	 * This method sets the miner of the block to m. 
	 */
	public void setMiner(Miner m) {
		this.miner = m;
	}
	
	/**
	 * This method returns the reward for mining the block.
	 */
	public double getReward() {
		return this.reward;
	}
	
	/**
	 * This method returns a linked list containing the transactions in the block.
	 */
	public LinkedList<CCoinTransaction> getTransactions() {
		return transactions;
	}
	
	/**
	 * This method returns the miner of the block.
	 */
	public Miner getMiner() {
		return this.miner;
	}
	
	/**
	 * This method returns the difficulty of the block.
	 */
	public int getDifficulty() {
		return this.difficulty;
	}
	
	/**
	 * This method adds the transaction t to the linked list of transactions in the block.
	 */
	public void addTransaction(CCoinTransaction t) {
		transactions.push(t);
	}
	
	/**
	 * This method returns the previous hash of the block.
	 */
	public String getPreviousHash() {
		return previousHash.toString();
	}
	
	/**
	 * This method returns the Merkle root of the block.  The Merkle root is the hash of all transactions in the block.
	 */
	private String getMerkleRoot() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = "";
			for (int i = 0; i < transactions.size(); i++) {
				temp += transactions.get(i).hash(); // Get string version of previous block
			}
			md.update(temp.getBytes()); // Update the Digest of md to be the hash of all transactions in the block
			return Arrays.toString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		return null;
	}
	
	/**
	 * This method sets the Merkle root of the block.
	 */
	private void setMerkleRoot() {
		this.merkleRoot = getMerkleRoot();
	}
	
	/**
	 * This method returns a String of elements of the block.  This String is used in hashing the block.
	 */
	public String hashify() {
		return this.previousHash + this.timestamp.toString() + this.transactions.toString();
	}
	
	/**
	 * This method returns the hash of this block.
	 */
	public String getThisHash() {
		String temp = this.previousHash + this.timestamp.toString() + this.transactions.toString();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			md.update(temp.getBytes()); // Update the Digest of md to be the hash of this block
			temp = Arrays.toString(md.digest());
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
