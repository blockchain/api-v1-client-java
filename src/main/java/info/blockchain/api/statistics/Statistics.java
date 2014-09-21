package info.blockchain.api.statistics;

import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/charts_api
 *
 */
public class Statistics
{
	/**
	 * Gets the network statistics.
	 * @return An instance of the StatisticsResponse class
	 * @throws APIException If the server returns an error
	 * @throws IOException
	 */
	public static StatisticsResponse getStats()
			throws APIException, IOException
	{
		return getStats(null);
	}
	
	/**
	 * Gets the network statistics.
	 * @param apiCode Blockchain.info API code (optional, nullable)
	 * @return An instance of the StatisticsResponse class
	 * @throws APIException If the server returns an error
	 * @throws IOException
	 */
	public static StatisticsResponse getStats(String apiCode)
			throws APIException, IOException
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("format", "json");
		if (apiCode != null)
			params.put("api_code", apiCode);
		
		String response = HttpClient.get("stats", params);
		return new StatisticsResponse(response);
	}
}
