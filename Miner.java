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
	
	public void addTransactionToPool(CCoinTransaction t) {
		transactions.add(t);
	}
	
	public void setCurrentBlockToEmptyBlock(int difficulty) {
		this.currentBlock = new Block(difficulty, Network.getInstance().getBlockReward());
	}
	
	public User getMiner() {
		return name;
	}
	
	public void addTransactionToBlock() {
		CCoinTransaction t = transactions.get(0);
		transactions.remove(0);
		this.currentBlock.addTransaction(t);
	}
	
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
