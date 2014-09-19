package info.blockchain.api.receive;

import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/api_receive
 *
 */
public class Receive
{
	/**
	 * Calls the 'api/receive' endpoint and creates a forwarding address.
	 * 
	 * @param receiving_address Destination address where the payment should be sent
	 * @param callback_url Callback URI that will be called upon payment
	 * @return An instance of the ReceiveResponse class
	 * @throws APIException If the server returns an error
	 * @throws IOException
	 */
	public static ReceiveResponse receive(String receiving_address, String callback_url)
			throws APIException, IOException
	{
		return receive(receiving_address, callback_url, null);
	}
	
	/**
	 * Calls the 'api/receive' endpoint and creates a forwarding address.
	 * 
	 * @param receiving_address Destination address where the payment should be sent
	 * @param callback_url Callback URI that will be called upon payment
	 * @param apiCode Blockchain.info API code
	 * @return An instance of the ReceiveResponse class
	 * @throws APIException If the server returns an error
	 * @throws IOException
	 */
	public static ReceiveResponse receive(String receiving_address, String callback_url,
			String apiCode) throws APIException, IOException
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("address", receiving_address);
		params.put("callback", callback_url);
		params.put("method", "create");
		
		if (apiCode != null)
			params.put("api_code", apiCode);
		
		String response = HttpClient.post("api/receive", params);
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(response).getAsJsonObject();
		
		return new ReceiveResponse(
				obj.get("fee_percent").getAsInt(),
				obj.get("destination").getAsString(),
				obj.get("input_address").getAsString(),
				obj.get("callback_url").getAsString());
	}
}
