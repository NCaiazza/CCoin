package CCoin;

import java.util.LinkedList;

/**
 * This driver is currently being used for testing.
 */

public class Driver {

	public static void main(String[] args) {
		// This class is used to test the CCoin
		
		User u1 = new User("John Doe", 100);
		User u2 = new User("Jane Doe", 100);
		User u3 = new User("Other Guy", 150);
		
		Miner m1 = new Miner(u3, "Miner1");
		Miner m2 = new Miner(u2, "Miner2");
		
		NetworkNode n1 = new NetworkNode();
		NetworkNode n2 = new NetworkNode();
		NetworkNode n3 = new NetworkNode();
		
		n1.initBlockchain();
		n2.initBlockchain();
		
		int testDifficulty = 8;
		
		long counter = 0;
		boolean mined = false;
		
		System.out.println("Node 1's BC\n" + n1.getBlockchain());
		System.out.println("Node 2's BC\n" + n2.getBlockchain());
		
		System.out.println("User1: " + u1);
		System.out.println("User2: " + u2);
		System.out.println("User3: " + u3);
		
		while(!mined) {	
			
			m1.setCurrentBlockToEmptyBlock(testDifficulty);
			if (counter % 30 == 0) {
				m2.setCurrentBlockToEmptyBlock(testDifficulty);
			}
			
			Integer m1Salt = m1.mine();
			
			if (m1Salt != null) {
				Network.getInstance().verifyBlock(m1.getCurrentBlock(), m1);
				mined = true;
			}
			
			counter++;
		}
		
		System.out.println("Node 2's BC\n" + n2.getBlockchain());
		
		n3.initBlockchain();
		System.out.println("Node 3's BC\n" + n3.getBlockchain());
		
		/*BlockChain b = new BlockChain();
		
		System.out.println("No Blocks Added To Chain Yet (Just Genesis):\n");
		
		System.out.println(b.toString());
		
		b.addBlock(new Block(new LinkedList<CCoinTransaction>(), new Timestamp(2, 22, 2017, 4, 30, true), b.lastBlockAdded(), 5, new Miner(new User("John Doe", 0), "0")));
		
		System.out.println("One Block Has Been Added To The Chain:\n");
		
		System.out.println(b.toString());
		
		System.out.println("Is The Chain Valid?");
		
		System.out.println(b.isChainValid());
		
		System.out.println("\n\n\n\nThis is the order of G:");
		
		DigitalSignature ds = new DigitalSignature(new Miner(new User("",0),""), new Block(0, 0.5), "");
		
		System.out.print(ds.findTheOrder());*/
	}

}
