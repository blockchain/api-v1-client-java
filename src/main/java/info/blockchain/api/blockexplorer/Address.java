package info.blockchain.api.blockexplorer;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Representation of an address.
 *
 */
public class Address
{
	private String hash160;
	private String address;
	private long totalReceived;
	private long totalSent;
	private long finalBalance;
	private List<Transaction> transactions;
	
	public Address(String hash160, String address, long totalReceived,
			long totalSent, long finalBalance, List<Transaction> transactions)
	{
		this.hash160 = hash160;
		this.address = address;
		this.totalReceived = totalReceived;
		this.totalSent = totalSent;
		this.finalBalance = finalBalance;
		this.transactions = transactions;
	}
	
	public Address(JsonObject a)
	{
		this(	a.get("hash160").getAsString(),
				a.get("address").getAsString(),
				a.get("total_received").getAsLong(),
				a.get("total_sent").getAsLong(),
				a.get("final_balance").getAsLong(),
				null);
		
		transactions = new ArrayList<Transaction>();
		for (JsonElement txElem : a.get("txs").getAsJsonArray())
		{
			JsonObject addrObj = txElem.getAsJsonObject();
			transactions.add(new Transaction(addrObj, -1, false));
		}
	}
	
	/**
	 * @return Hash160 representation of the address
	 */
	public String getHash160()
	{
		return hash160;
	}
	/**
	 * @return Base58Check representation of the address
	 */
	public String getAddress()
	{
		return address;
	}
	/**
	 * @return Total amount received (in satoshi)
	 */
	public long getTotalReceived()
	{
		return totalReceived;
	}
	/**
	 * @return Total amount sent (in satoshi)
	 */
	public long getTotalSent()
	{
		return totalSent;
	}
	/**
	 * @return Final balance of the address (in satoshi)
	 */
	public long getFinalBalance()
	{
		return finalBalance;
	}
	/**
	 * @return List of transactions associated with this address
	 */
	public List<Transaction> getTransactions()
	{
		return transactions;
	}
}
