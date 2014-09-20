package info.blockchain.api.exchangerates;

/**
 *  Used in response to the `getTicker` method in the `ExchangeRates` class.
 *
 */
public class Currency
{
	private double buy;
	private double sell;
	private double last;
	private double price15m;
	private String symbol;
	
	public Currency(double buy, double sell, double last, double price15m, String symbol)
	{
		this.buy = buy;
		this.sell = sell;
		this.last = last;
		this.price15m = price15m;
		this.symbol = symbol;
	}

	/**
	 * @return Current buy price
	 */
	public double getBuy()
	{
		return buy;
	}

	/**
	 * @return Current sell price
	 */
	public double getSell()
	{
		return sell;
	}

	/**
	 * @return Most recent market price
	 */
	public double getLast()
	{
		return last;
	}

	/**
	 * @return 15 minutes delayed market price
	 */
	public double getPrice15m()
	{
		return price15m;
	}

	/**
	 * @return Currency symbol
	 */
	public String getSymbol()
	{
		return symbol;
	}
}
