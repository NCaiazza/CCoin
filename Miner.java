package CCoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class Miner {

	private User name;
	private String id;
	private Block currentBlock;
	private ArrayList<CCoinTransaction> transactions;
	private int salt;
	
	public Miner(User name, String id) {
		this.name = name;
		this.id = id;
		transactions = new ArrayList<>();
	}
	
	/**
	 * This method adds the transaction t to the miner's pool of transactions.
	 */
	public void addTransactionToPool(CCoinTransaction t) {
		transactions.add(t);
	}
	
	/**
	 * This method removes the block the miner was currently mining and replaces it with an empty block.
	 */
	public void setCurrentBlockToEmptyBlock(int difficulty) {
		this.currentBlock = new Block(difficulty, Network.getInstance().getBlockReward());
	}
	
	/**
	 * This method returns the miner of the block.
	 */
	public User getMiner() {
		return name;
	}
	
	/**
	 * This method removes a transaction from the miner's pool of transactions and adds it to the list of transactions in the block.
	 */
	public void addTransactionToBlock() {
		CCoinTransaction t = transactions.get(0);
		transactions.remove(0);
		this.currentBlock.addTransaction(t);
	}
	
	/**
	 * This method has the miner attempt to mine their block.
	 */
	public Integer mine() {
		
		String hash = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = currentBlock.hashify(); // Get string version of block
			md.update(temp.getBytes()); // Update the Digest of md to be the has of the previous block
			hash = Arrays.toString(md.digest()); // Set the previous hash to be the digest of md
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		
		System.out.println(hash); // For debugging
		char[] chars = hash.toCharArray();
		
		for (int i = 0; i < currentBlock.getDifficulty(); i++) {
			if (chars[i] != '0') {
				return null;
			}
		}
		
		return this.salt;
	}
}
