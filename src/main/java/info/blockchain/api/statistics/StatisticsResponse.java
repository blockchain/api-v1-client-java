package info.blockchain.api.statistics;

import info.blockchain.api.Util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class is used as a response object to the 'getStats' method in the 'Statistics' class
 *
 */
public class StatisticsResponse
{
	private double tradeVolumeBTC;
	private double tradeVolumeUSD;
	private double electricityConsumption;
	private double electricityCostUSD;
	private double minersRevenueBTC;
	private double minersRevenueUSD;
	private double minersOperatingMargin;
	private double marketPriceUSD;
	private double estimatedTransactionVolumeUSD;
	private double totalFeesBTC;
	private long totalBTCSent;
	private double estimatedBTCSent;
	private double nBTCMined;
	private double difficulty;
	private double minutesBetweenBlocks;
	private double daysDestroyed;
	private long nTx;
	private double hashRate;
	private long timestamp;
	private int nBlocksMined;
	private long blocksSize;
	private int totalBTC;
	private long nBlocksTotal;
	private long nextRetarget;
	
	public StatisticsResponse(String jsonString)
	{
		JsonObject s = new JsonParser().parse(jsonString).getAsJsonObject();
		
		this.tradeVolumeBTC = s.get("trade_volume_btc").getAsDouble();
		this.tradeVolumeUSD = s.get("trade_volume_usd").getAsDouble();
		this.electricityConsumption = s.get("electricity_consumption").getAsDouble();
		this.electricityCostUSD = s.get("electricity_cost_usd").getAsDouble();
		this.minersRevenueBTC = s.get("miners_revenue_btc").getAsDouble();
		this.minersRevenueUSD = s.get("miners_revenue_usd").getAsDouble();
		this.minersOperatingMargin = s.get("miners_operating_margin").getAsDouble();
		this.marketPriceUSD = s.get("market_price_usd").getAsDouble();
		this.estimatedTransactionVolumeUSD = s.get("estimated_transaction_volume_usd").getAsDouble();
		this.totalFeesBTC = s.get("total_fees_btc").getAsDouble();
		this.totalBTCSent = s.get("total_btc_sent").getAsLong() / Util.SATOSHI_IN_BTC;
		this.estimatedBTCSent = s.get("estimated_btc_sent").getAsDouble();
		this.nBTCMined = s.get("n_btc_mined").getAsDouble();
		this.tradeVolumeUSD = s.get("trade_volume_usd").getAsDouble();
		this.difficulty = s.get("difficulty").getAsDouble();
		this.minutesBetweenBlocks = s.get("minutes_between_blocks").getAsDouble();
		this.daysDestroyed = s.get("days_destroyed").getAsDouble();
		this.nTx = s.get("n_tx").getAsLong();
		this.hashRate = s.get("hash_rate").getAsDouble();
		this.timestamp = s.get("timestamp").getAsLong();
		this.nBlocksMined = s.get("n_blocks_mined").getAsInt();
		this.blocksSize = s.get("blocks_size").getAsLong();
		this.totalBTC = (int) (s.get("totalbc").getAsLong() / Util.SATOSHI_IN_BTC);
		this.nBlocksTotal = s.get("n_blocks_total").getAsLong();
		this.nextRetarget = s.get("nextretarget").getAsLong();
	}
	
	/**
	 * @return the tradeVolumeBTC
	 */
	public double getTradeVolumeBTC()
	{
		return tradeVolumeBTC;
	}
	/**
	 * @return the tradeVolumeUSD
	 */
	public double getTradeVolumeUSD()
	{
		return tradeVolumeUSD;
	}
	/**
	 * @return the electricityConsumption
	 */
	public double getElectricityConsumption()
	{
		return electricityConsumption;
	}
	/**
	 * @return the electricityCostUSD
	 */
	public double getElectricityCostUSD()
	{
		return electricityCostUSD;
	}
	/**
	 * @return the minersRevenueBTC
	 */
	public double getMinersRevenueBTC()
	{
		return minersRevenueBTC;
	}
	/**
	 * @return the minersRevenueUSD
	 */
	public double getMinersRevenueUSD()
	{
		return minersRevenueUSD;
	}
	/**
	 * @return the minersOperatingMargin
	 */
	public double getMinersOperatingMargin()
	{
		return minersOperatingMargin;
	}
	/**
	 * @return the marketPriceUSD
	 */
	public double getMarketPriceUSD()
	{
		return marketPriceUSD;
	}
	/**
	 * @return the estimatedTransactionVolumeUSD
	 */
	public double getEstimatedTransactionVolumeUSD()
	{
		return estimatedTransactionVolumeUSD;
	}
	/**
	 * @return the totalFeesBTC
	 */
	public double getTotalFeesBTC()
	{
		return totalFeesBTC;
	}
	/**
	 * @return the totalBTCSent
	 */
	public long getTotalBTCSent()
	{
		return totalBTCSent;
	}
	/**
	 * @return the estimatedBTCSent
	 */
	public double getEstimatedBTCSent()
	{
		return estimatedBTCSent;
	}
	/**
	 * @return the nBTCMined
	 */
	public double getnBTCMined()
	{
		return nBTCMined;
	}
	/**
	 * @return the difficulty
	 */
	public double getDifficulty()
	{
		return difficulty;
	}
	/**
	 * @return the minutesBetweenBlocks
	 */
	public double getMinutesBetweenBlocks()
	{
		return minutesBetweenBlocks;
	}
	/**
	 * @return the daysDestroyed
	 */
	public double getDaysDestroyed()
	{
		return daysDestroyed;
	}
	/**
	 * @return the nTx
	 */
	public long getnTx()
	{
		return nTx;
	}
	/**
	 * @return the hashRate
	 */
	public double getHashRate()
	{
		return hashRate;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp()
	{
		return timestamp;
	}
	/**
	 * @return the nBlocksMined
	 */
	public int getnBlocksMined()
	{
		return nBlocksMined;
	}
	/**
	 * @return the blocksSize
	 */
	public long getBlocksSize()
	{
		return blocksSize;
	}
	/**
	 * @return the totalBTC
	 */
	public long getTotalBTC()
	{
		return totalBTC;
	}
	/**
	 * @return the nBlocksTotal
	 */
	public long getnBlocksTotal()
	{
		return nBlocksTotal;
	}
	/**
	 * @return the nextRetarget
	 */
	public long getNextRetarget()
	{
		return nextRetarget;
	}
}
