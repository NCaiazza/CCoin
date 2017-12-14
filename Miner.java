package CCoin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class Miner {

	private User name;
	private String id;
	private Block currentBlock = null;
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
	
	public Block getCurrentBlock() {
		return this.currentBlock;
	}
	
	/**
	 * This method is used to get the bit at the index: index of a byte.
	 */
	private Byte getBit(Byte b, int index) {
		if (index < 8 && index > -1) {
			Byte newByte = b; // Set a new byte to this.b
			
			for (int i = 0; i < 8; i++) {
				if (i != index) {
					newByte = (byte) (newByte & ~(1 << i)); // Set every bit in newByte but the one we want to 0
				}
			}
			
			if (index != 0) {
				return (byte) (newByte >> index);
			}
			
			return newByte;
		}
		
		return 0;
	}
	
	/**
	 * This method has the miner attempt to mine their block.
	 */
	public Integer mine() {
		
		if (this.currentBlock == null) {
			
		}
		
		String hash = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // Set hash algorithm to SHA-256
			String temp = currentBlock.hashify(); // Get string version of block
			String temp2 = temp + this.salt;
			md.update(temp2.getBytes()); // Update the Digest of md to be the hash that hopefully satisfies the difficulty
			hash = Arrays.toString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); // If SHA-256 is not found (It should be): error
		}
		
		System.out.println(hash);
		
		byte[] ba = hash.getBytes();
		int checked = 0;
		int i = 0;
		int checkedCount = 0;
		
		while (checked < currentBlock.getDifficulty()) {
			if (!(checkedCount < 8)) {
				i++;
				checkedCount = 0;
			}
			
			if (getBit(ba[i], (checked % 8)) != 0) {
				this.salt++;
				return null;
			} else {
				checked++;
				checkedCount++;
			}
		}
		
		return salt;
	}
}
