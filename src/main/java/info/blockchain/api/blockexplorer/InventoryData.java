package info.blockchain.api.blockexplorer;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * TODO 
 *
 */
public class InventoryData
{
	private String hash;
	private long initialTime;
	private long lastTime;
	private String initialIP;
	private int nConnected;
	private int relayedCount;
	private int relayedPercent;
	private List<Owner> probableOwners;
	private List<MiningNode> miningNodes;
	
	public InventoryData(String hash, long initialTime, long lastTime, String initialIP,
			int nConnected, int relayedCount, int relayedPercent,
			List<Owner> probableOwners, List<MiningNode> miningNodes)
	{
		this.hash = hash;
		this.initialTime = initialTime;
		this.lastTime = lastTime;
		this.initialIP = initialIP;
		this.nConnected = nConnected;
		this.relayedCount = relayedCount;
		this.relayedPercent = relayedPercent;
		this.probableOwners = probableOwners;
		this.miningNodes = miningNodes;
	}

	public InventoryData(JsonObject i)
	{
		this (	i.get("hash").getAsString(),
				i.get("initial_time").getAsLong(),
				i.get("last_time").getAsLong(),
				i.get("initial_ip").getAsString(),
				i.get("nconnected").getAsInt(),
				i.get("relayed_count").getAsInt(),
				i.get("relayed_percent").getAsInt(),
				new ArrayList<Owner>(),
				new ArrayList<MiningNode>());
		
		for (JsonElement jsonOwner : i.get("probable_owners").getAsJsonArray())
		{
			JsonObject ownerObj = jsonOwner.getAsJsonObject();
			probableOwners.add(new Owner(ownerObj.get("ip").getAsString(),
					ownerObj.get("confidence").getAsInt()));
		}
		
		for (JsonElement jsonNode : i.get("mining_nodes").getAsJsonArray())
		{
			JsonObject nodeObj = jsonNode.getAsJsonObject();
			miningNodes.add(new MiningNode(nodeObj.get("link").getAsString(),
					nodeObj.get("name").getAsString()));
		}
	}
	
	/**
	 * @return Transaction hash
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return the initialTime
	 */
	public long getInitialTime()
	{
		return initialTime;
	}

	/**
	 * @return
	 */
	public long getLastTime()
	{
		return lastTime;
	}

	/**
	 * @return the initialIP
	 */
	public String getInitialIP()
	{
		return initialIP;
	}

	/**
	 * @return the nConnected
	 */
	public int getnConnected()
	{
		return nConnected;
	}

	/**
	 * @return the relayedCount
	 */
	public int getRelayedCount()
	{
		return relayedCount;
	}

	/**
	 * @return the relayedPercent
	 */
	public int getRelayedPercent()
	{
		return relayedPercent;
	}

	/**
	 * @return the probableOwners
	 */
	public List<Owner> getProbableOwners()
	{
		return probableOwners;
	}

	/**
	 * @return the miningNodes
	 */
	public List<MiningNode> getMiningNodes()
	{
		return miningNodes;
	}

	public class Owner
	{
		private String ip;
		private int confidence;
		
		public Owner(String ip, int confidence)
		{
			this.ip = ip;
			this.confidence = confidence;
		}
		
		/**
		 * @return IP address of the potential owner
		 */
		public String getIp()
		{
			return ip;
		}
		/**
		 * @return Confidence that the transaction belongs to this owner
		 */
		public int getConfidence()
		{
			return confidence;
		}
	}
	
	public class MiningNode
	{
		private String link;
		private String name;

		public MiningNode(String link, String name)
		{
			this.link = link;
			this.name = name;
		}

		/**
		 * @return Link to the mining pool
		 */
		public String getLink()
		{
			return link;
		}

		/**
		 * @return Name of the mining pool
		 */
		public String getName()
		{
			return name;
		}
	}
}
