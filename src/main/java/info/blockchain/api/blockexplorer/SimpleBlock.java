package info.blockchain.api.blockexplorer;

/**
 * Simple representation of a block
 *
 */
public class SimpleBlock
{
	private long height;
	private String hash;
	private long time;
	private boolean mainChain;

	public SimpleBlock(long height, String hash, long time, boolean mainChain)
	{
		this.height = height;
		this.hash = hash;
		this.time = time;
		this.mainChain = mainChain;
	}

	/**
	 * @return Block height
	 */
	public long getHeight()
	{
		return height;
	}
	
	/**
	 * @return Block hash
	 */
	public String getHash()
	{
		return hash;
	}
	
	/**
	 * @return Timestamp
	 */
	public long getTime()
	{
		return time;
	}
	
	/**
	 * @return Whether the block is on the main chain
	 */
	public boolean isMainChain()
	{
		return mainChain;
	}
}
