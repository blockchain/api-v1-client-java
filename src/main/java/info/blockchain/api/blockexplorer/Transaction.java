package info.blockchain.api.blockexplorer;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Represents a transaction.
 *
 */
public class Transaction
{
	private boolean doubleSpend;
	private long blockHeight;
	private long time;
	private String relayedBy;
	private String hash;
	private long txIndex;
	private int version;
	private long size;
	private List<Input> inputs;
	private List<Output> outputs;
	
	public Transaction(boolean doubleSpend, long blockHeight, long time,
			String relayedBy, String hash, long txIndex, int version,
			long size, List<Input> inputs, List<Output> outputs)
	{
		this.doubleSpend = doubleSpend;
		this.blockHeight = blockHeight;
		this.time = time;
		this.relayedBy = relayedBy;
		this.hash = hash;
		this.txIndex = txIndex;
		this.version = version;
		this.size = size;
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public Transaction(JsonObject t)
	{
		this(t, t.get("block_height").getAsLong(),
				t.get("double_spend").getAsBoolean());
	}
	
	public Transaction(JsonObject t, long blockHeight, boolean doubleSpend)
	{
		this(	doubleSpend,
				blockHeight,
				t.get("time").getAsLong(),
				t.get("relayed_by").getAsString(),
				t.get("hash").getAsString(),
				t.get("tx_index").getAsLong(),
				t.get("ver").getAsInt(),
				t.get("size").getAsLong(),
				null,
				null);
		
		inputs = new ArrayList<Input>();
		for (JsonElement inputElem : t.get("inputs").getAsJsonArray())
		{
			inputs.add(new Input(inputElem.getAsJsonObject()));
		}
		
		outputs = new ArrayList<Output>();
		for (JsonElement outputElem : t.get("out").getAsJsonArray())
		{
			outputs.add(new Output(outputElem.getAsJsonObject()));
		}
	}
	
	/**
	 * @return Whether the transaction is a double spend
	 */
	public boolean isDoubleSpend()
	{
		return doubleSpend;
	}
	/**
	 * @return Block height of the parent block
	 */
	public long getBlockHeight()
	{
		return blockHeight;
	}
	/**
	 * @return Timestamp of the transaction
	 */
	public long getTime()
	{
		return time;
	}
	/**
	 * @return the relayedBy
	 */
	public String getRelayedBy()
	{
		return relayedBy;
	}
	/**
	 * @return Transaction hash
	 */
	public String getHash()
	{
		return hash;
	}
	/**
	 * @return Transaction index
	 */
	public long getTxIndex()
	{
		return txIndex;
	}
	/**
	 * @return Transaction version //TODO
	 */
	public int getVersion()
	{
		return version;
	}
	/**
	 * @return Size of the transaction
	 */
	public long getSize()
	{
		return size;
	}
	/**
	 * @return List of inputs
	 */
	public List<Input> getInputs()
	{
		return inputs;
	}
	/**
	 * @return List of outputs
	 */
	public List<Output> getOutputs()
	{
		return outputs;
	}
}
