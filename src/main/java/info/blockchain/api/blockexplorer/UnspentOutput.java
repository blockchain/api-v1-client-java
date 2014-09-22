package info.blockchain.api.blockexplorer;

import com.google.gson.JsonObject;

/**
 * Represents an unspent transaction output.
 *
 */
public class UnspentOutput
{
	private int n;
	private String transactionHash;
	private long transactionIndex;
	private String script;
	private long value;
	private long confirmations;


	public UnspentOutput(int n, String transactionHash, long transactionIndex,
			String script, long value, long confirmations)
	{
		this.n = n;
		this.transactionHash = transactionHash;
		this.transactionIndex = transactionIndex;
		this.script = script;
		this.value = value;
		this.confirmations = confirmations;
	}

	public UnspentOutput(JsonObject o)
	{
		this(	o.get("tx_output_n").getAsInt(),
				o.get("tx_hash").getAsString(),
				o.get("tx_index").getAsLong(),
				o.get("script").getAsString(),
				o.get("value").getAsLong(),
				o.get("confirmations").getAsLong());
	}

	/**
	 * @return Index of the output in a transaction
	 */
	public int getN()
	{
		return n;
	}

	/**
	 * @return Transaction hash
	 */
	public String getTransactionHash()
	{
		return transactionHash;
	}

	/**
	 * @return Transaction index
	 */
	public long getTransactionIndex()
	{
		return transactionIndex;
	}

	/**
	 * @return Output script
	 */
	public String getScript()
	{
		return script;
	}

	/**
	 * @return Value of the output (in satoshi)
	 */
	public long getValue()
	{
		return value;
	}

	/**
	 * @return Number of confirmations
	 */
	public long getConfirmations()
	{
		return confirmations;
	}
}
