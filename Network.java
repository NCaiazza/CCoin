package CCoin;

import java.util.ArrayList;

public class Network {
	
	private ArrayList<User> users;
	private ArrayList<NetworkNode> networkNodes;
	private static Network network = new Network();
	
	private Network() {
		users = new ArrayList<>();
		networkNodes = new ArrayList<>();
	}
	
	public static Network getInstance() {
		return network;
	}
	
	protected void addUser(User u) {
		users.add(u);
	}
	
	protected void addNode(NetworkNode n) {
		networkNodes.add(n);
	}
	
	protected double getBlockReward() {
		return 50;
	}
	
	protected boolean verifyTransaction(CCoinTransaction t) {
		for (int i = 0; i < networkNodes.size(); i++) {
			if (!(networkNodes.get(i).verifyTransaction(t))) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean verifyBlock(Block b, Miner m) {
		boolean verified = true;
		for (int i = 0; i < networkNodes.size(); i++) {
			if (!(networkNodes.get(i).verifyBlock(b))) {
				verified = false;
			}
		}
		
		if (verified) {
			for (int i = 0; i < networkNodes.size(); i++) {
				networkNodes.get(i).getBlockchain().addBlock(b);
			}
			
			m.getMiner().deposit(b.getReward());
			
			for (int i = 0; i < b.getTransactions().size(); i++) {
				m.getMiner().deposit(b.getTransactions().get(i).getTransactionFee());
			}
		}
		
		return verified;
		
	}
	
	protected boolean containsUser(User u) {
		return users.contains(u);
	}
	
	protected BlockChain getABlockchain() {
		if (networkNodes.size() > 0) {
			return networkNodes.get(0).getBlockchain();
		} else {
			return new BlockChain();
		}
	}
}
