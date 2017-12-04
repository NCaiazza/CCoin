package CCoin;

public class NetworkNode {
	
	private BlockChain blockchain = null;
	
	public NetworkNode() {}
	
	public boolean verifyTransaction(CCoinTransaction t) {
		
		if (!Network.getInstance().containsUser(t.getRecipient()) ||
				!Network.getInstance().containsUser(t.getSender()) ||
				t.getTransactionFee() < 0 ||
				t.getAmount() <= 0 ||
				t.getSender().getBalance() < t.getAmount()) {
			return false;
		}
		
		return true;
	}
	
	public boolean verifyBlock(Block b) {
		
		
		return false;
	}
	
	public BlockChain getBlockchain() {
		if (!blockchain.equals(null))
			return blockchain;
		return new BlockChain();
	}
	
	public void initBlockchain() {
		this.blockchain = Network.getInstance().getABlockchain();
	}
}
