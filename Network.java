package CCoin;

import java.util.ArrayList;

public class Network {
	
	private ArrayList<User> users;
	private ArrayList<NetworkNode> networkNodes;
	private int numberOfCCoin = 0;
	private static Network network = new Network();
	
	private static int maxCCoin = 10000000;
	
	private Network() {
		users = new ArrayList<>();
		networkNodes = new ArrayList<>();
	}
	
	/**
	 * Network is a singleton class, so there can only be one network.  This method returns that single network.
	 */
	public static Network getInstance() {
		return network;
	}
	
	/**
	 * This method sets the total number of CCoin in circulation.  There will only ever be 10 million CCoin in circulation, this method is used to affect the block
	 * reward.
	 */
	protected void setNumberCCoin() {
		int newCCoinTotal = 0;
		
		for (User u : users) {
			newCCoinTotal += u.getBalance();
		}
		
		this.numberOfCCoin = newCCoinTotal;
	}
	
	/**
	 * This method adds the user u to the network's list of users.
	 */
	protected void addUser(User u) {
		users.add(u);
	}
	
	/**
	 * This method adds the node n to the network's list of nodes.
	 */
	protected void addNode(NetworkNode n) {
		networkNodes.add(n);
	}
	
	/**
	 * This method returns the current block reward for mining a block.
	 */
	protected double getBlockReward() {
		return 50; // This will result in 200,000 blocks mined before there will no longer be a reward
	}
	
	/**
	 * This method has every node of the network verify the transaction t.  If each node agrees that t is a valid transaction, the method returns true, otherwise,
	 * the method returns false.
	 */
	protected boolean verifyTransaction(CCoinTransaction t) {
		for (int i = 0; i < networkNodes.size(); i++) {
			if (!(networkNodes.get(i).verifyTransaction(t))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * This method has every node of the network verify the block b.  If each node agrees that b is a valid block, the method returns true, otherwise,
	 * the method returns false.
	 */
	public void verifyBlock(Block b, Miner m, DigitalSignature ds) {
		boolean verified = true;
		for (int i = 0; i < networkNodes.size(); i++) {
			if (!(networkNodes.get(i).verifyBlock(b))) {
				verified = false;
			}
		}
		
		if (verified && ds.verifySignature()) {
			for (int i = 0; i < networkNodes.size(); i++) {
				networkNodes.get(i).getBlockchain().addBlock(b);
			}
			
			setNumberCCoin();
			
			if (this.numberOfCCoin < maxCCoin) {
				m.getMiner().deposit(b.getReward());
			}
			
			for (int i = 0; i < b.getTransactions().size(); i++) {
				m.getMiner().deposit(b.getTransactions().get(i).getTransactionFee());
			}
		}
		
	}
	
	/**
	 * This method checks if the user u is in the network's list of users.
	 */
	protected boolean containsUser(User u) {
		return users.contains(u);
	}
	
	/**
	 * This method returns a blockchain from one of the nodes on the network.
	 */
	protected BlockChain getABlockchain() {
		if (networkNodes.size() > 0) {
			return networkNodes.get(0).getBlockchain();
		} else {
			return new BlockChain();
		}
	}
}
