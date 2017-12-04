package CCoin;

import java.util.LinkedList;

/**
 * This driver is currently being used for testing.
 */

public class Driver {

	public static void main(String[] args) {
		// This class is used to test the CCoin
		
		BlockChain b = new BlockChain();
		
		System.out.println("No Blocks Added To Chain Yet (Just Genesis):\n");
		
		System.out.println(b.toString());
		
		b.addBlock(new Block(new LinkedList<CCoinTransaction>(), new Timestamp(2, 22, 2017, 4, 30, true), b.lastBlockAdded(), 5, new Miner(new User("John Doe", 0), "0")));
		
		System.out.println("One Block Has Been Added To The Chain:\n");
		
		System.out.println(b.toString());
		
		System.out.println("Is The Chain Valid?");
		
		System.out.println(b.isChainValid());
	}

}
