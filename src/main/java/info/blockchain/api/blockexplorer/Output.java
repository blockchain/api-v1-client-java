package info.blockchain.api.blockexplorer;

import com.google.gson.JsonObject;

/**
 * Represents a transaction output.
 *
 */
public class Output
{
	private int n;
	private long value;
	private String address;
	private long txIndex;
	private String script;
	private boolean spent;
	
	public Output(int n, long value, String address, long txIndex,
			String script, boolean spent)
	{
		this.n = n;
		this.value = value;
		this.address = address;
		this.txIndex = txIndex;
		this.script = script;
		this.spent = spent;
	}
	
	public Output(JsonObject o)
	{
		this(o, o.get("spent").getAsBoolean());
	}
	
	public Output(JsonObject o, boolean spent)
	{
		this(	o.get("n").getAsInt(),
				o.get("value").getAsLong(),
				o.get("addr").getAsString(),
				o.get("tx_index").getAsLong(),
				o.get("script").getAsString(),
				spent);
	}
	
	/**
	 * @return Index of the output in a transaction
	 */
	public int getN()
	{
		return n;
	}
	
	/**
	 * @return Value of the output (in satoshi)
	 */
	public long getValue()
	{
		return value;
	}
	
	/**
	 * @return Address that the output belongs to
	 */
	public String getAddress()
	{
		return address;
	}
	
	/**
	 * @return Transaction index
	 */
	public long getTxIndex()
	{
		return txIndex;
	}
	
	/**
	 * @return Output script
	 */
	public String getScript()
	{
		return script;
	}
	
	/**
	 * @return Whether the output is spent
	 */
	public boolean isSpent()
	{
		return spent;
	}
}
