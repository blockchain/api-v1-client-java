package info.blockchain.api.blockexplorer;

import java.util.List;

import com.google.gson.JsonObject;

/**
 * TODO 
 *
 */
public class InventoryData
{
	private String hash;
	private String type;
	private long initialTime;
	private String initialIP;
	private int nConnected;
	private int relayedCount;
	private int relayedPercent;
	private List<Owner> probableOwners;
	private List<MiningNode> miningNodes;
	
	public InventoryData(String hash, String type, long initialTime, String initialIP,
			int nConnected, int relayedCount, int relayedPercent,
			List<Owner> probableOwners, List<MiningNode> miningNodes)
	{
		this.hash = hash;
		this.type = type;
		this.initialTime = initialTime;
		this.initialIP = initialIP;
		this.nConnected = nConnected;
		this.relayedCount = relayedCount;
		this.relayedPercent = relayedPercent;
		this.probableOwners = probableOwners;
		this.miningNodes = miningNodes;
	}

	public InventoryData(JsonObject i)
	{
		
	}
	
	/**
	 * @return the hash
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return the initialTime
	 */
	public long getInitialTime()
	{
		return initialTime;
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
		 * @return the ip
		 */
		public String getIp()
		{
			return ip;
		}
		/**
		 * @return the confidence
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
		 * @return the link
		 */
		public String getLink()
		{
			return link;
		}

		/**
		 * @return the name
		 */
		public String getName()
		{
			return name;
		}
	}
}
