package CCoin;

import java.util.LinkedList;

public class BlockChain {
	private LinkedList<Block> blockChain;
	
	public BlockChain() {
		this.blockChain = new LinkedList<Block>(); //Create the block chain
		this.blockChain.add(new Block(new LinkedList<CCoinTransaction>(),
				new Timestamp(),
				new Block(0, Network.getInstance().getBlockReward()),
				0,
				new Miner(new User("Nick Caiazza", 0), ""))); //Genesis Block
	}
	
	/**
	 * This method adds the block to the blockchain.
	 */
	
	public void addBlock(Block block) {
			this.blockChain.add(block);
	}
	
	/**
	 * This method outputs the blockchain in an easy to read format.
	**/
	
	public void outputChain() {
		for (Block b : blockChain) {
			System.out.println(b.toString());
		}
	}
	
	/**
	 * This method verifies if the blockchain is valid.
	 */
	
	public boolean isChainValid() {
		String previous = blockChain.getFirst().getPreviousHash();
		
		for (int i = 0; i < blockChain.size(); i++) {
			Block currentBlock = blockChain.get(i);
			
			if (!(currentBlock.getPreviousHash()).equals(previous)) {
				return false;
			}
			
			previous = currentBlock.getThisHash();
		}
		
		return true;
	}
	
	/**
	 * This method returns the last block added to the blockchain.
	 */
	
	public Block lastBlockAdded() {
		return blockChain.get(blockChain.size() - 1);
	}
	
	@Override
	public String toString() {
		String output = "";
		int counter = 0;
		
		for (Block b : blockChain) {
			output = output + "Block: " + counter + "\n";
			counter++;
			output += b.toString();
			output += "\n\n";
		}
		
		return output;
	}
}
