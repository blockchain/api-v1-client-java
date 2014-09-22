package info.blockchain.api.blockexplorer;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Used as a response to the `getLatestBlock` method in the `BlockExplorer` class.
 *
 */
public class LatestBlock extends SimpleBlock
{
	private long index;
	private List<Long> transactionIndexes;
	
	public LatestBlock(long height, String hash, long time, boolean mainChain, long index,
			List<Long> transactionIndexes)
	{
		super(height, hash, time, mainChain);
		this.index = index;
		this.transactionIndexes = transactionIndexes;
	}
	
	public LatestBlock(JsonObject b)
	{
		this(	b.get("height").getAsLong(),
				b.get("hash").getAsString(),
				b.get("time").getAsLong(),
				true,
				b.get("block_index").getAsLong(),
				null);
		
		transactionIndexes = new ArrayList<Long>();
		for (JsonElement idxElem : b.get("txIndexes").getAsJsonArray())
		{
			transactionIndexes.add(idxElem.getAsLong());
		}
	}

	/**
	 * @return Transaction indexes included in this block
	 */
	public List<Long> getTransactionIndexes()
	{
		return transactionIndexes;
	}

	/**
	 * @return Block index
	 */
	public long getIndex()
	{
		return index;
	}
}
