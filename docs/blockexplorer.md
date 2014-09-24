##`info.blockchain.api.blockexplorer` package

The `blockexplorer` package contains the `BlockExplorer` class that reflects the functionality documented at  https://blockchain.info/api/blockchain_api. It can be used to query the block chain, fetch block, transaction and address data, get unspent outputs for an address etc.

Example usage:

```java

package test;
import info.blockchain.api.blockexplorer.*;

public class App 
{
    public static void main(String[] args) throws Exception
    {
    	// instantiate a block explorer
    	BlockExplorer blockExplorer = new BlockExplorer();
    	
    	// get a transaction by hash and list the value of all its inputs
    	Transaction tx = blockExplorer.getTransaction("df67414652722d38b43dcbcac6927c97626a65bd4e76a2e2787e22948a7c5c47");
    	for (Input i : tx.getInputs())
    	{
    		System.out.println(i.getPreviousOutput().getValue());
    	}
    	
    	// get a block by hash and read the number of transactions in the block
    	Block block = blockExplorer.getBlock("0000000000000000050fe18c9b961fc7c275f02630309226b15625276c714bf1");
    	int numberOfTxsInBlock = block.getTransactions().size();
    	
    	// get an address and read its final balance
    	Address address = blockExplorer.getAddress("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw");
    	long finalBalance = address.getFinalBalance();
    	
    	// get a list of currently unconfirmed transactions and print the relay IP address for each
    	List<Transaction> unconfirmedTxs = blockExplorer.getUnconfirmedTransactions();
    	for (Transaction unconfTx : unconfirmedTxs)
    		System.out.println(tx.getRelayedBy());
    	
    	// calculate the balanace of an address by fetching a list of all its unspent outputs
    	List<UnspentOutput> outs = blockExplorer.getUnspentOutputs("1EjmmDULiZT2GCbJSeXRbjbJVvAPYkSDBw");
    	long totalUnspentValue = 0;
    	for (UnspentOutput out : outs)
    		totalUnspentValue += out.getValue();
    	
    	// get inventory data for a recent transaction (valid up to one hour)
    	InventoryData inv = blockExplorer.getInventoryData("f23ee3525f78df032b47c3c9be6cd0d930f38c32674e861c1e41c6558b32ee97");
    	
    	// get the latest block on the main chain and read its height
    	LatestBlock latestBlock = blockExplorer.getLatestBlock();
    	long latestBlockHeight = latestBlock.getHeight();
    	
    	// use the previous block height to get a list of blocks at that height
		// and detect a potential chain fork
    	List<Block> blocksAtHeight = blockExplorer.getBlocksAtHeight(latestBlockHeight);
    	if (blocksAtHeight.size() > 1)
    		System.out.println("The chain has forked!");
    	else
    		System.out.println("The chain is still in one piece :)");
    	
    	// get a list of all blocks that were mined today since 00:00 UTC
    	List<SimpleBlock> todaysBlocks = blockExplorer.getBlocks();
    	System.out.println(todaysBlocks.size());
    }
}

```