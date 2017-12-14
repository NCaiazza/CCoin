package CCoin;

public class NetworkNode {
	
	private BlockChain blockchain = null;
	
	public NetworkNode() {}
	
	/**
	 * This method checks if the transaction t is a valid transaction.
	 */
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
	
	/**
	 * This method checks if the block b is a valid block.
	 */
	public boolean verifyBlock(Block b) {
		
		if (b.getDifficulty() > 0) {
			return false;
		}
		
		if (!Network.getInstance().containsUser(b.getMiner().getMiner())) {
			return false;
		}
		
		if (Network.getInstance().getABlockchain().lastBlockAdded().getThisHash() != b.getPreviousHash()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method returns the node's blockchain.  If the node does not have a blockchain, a new blockchain is returned.
	 */
	public BlockChain getBlockchain() {
		if (!blockchain.equals(null))
			return blockchain;
		return new BlockChain();
	}
	
	/**
	 * This method initializes the node's blockchain.
	 */
	public void initBlockchain() {
		this.blockchain = Network.getInstance().getABlockchain();
	}
}
