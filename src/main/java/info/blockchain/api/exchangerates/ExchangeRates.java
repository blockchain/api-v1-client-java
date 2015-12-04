package info.blockchain.api.exchangerates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import static com.google.common.base.Preconditions.*;

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/exchange_rates_api. It allows users to fetch the latest
 * ticker data and convert amounts between BTC and fiat currencies.
 */
public class ExchangeRates {
    /**
     * Gets the price ticker from https://blockchain.info/ticker
     *
     * @return A map of currencies where the key is a 3-letter currency symbol and the
     * value is the `Currency` class
     * @throws APIException If the server returns an error
     */
    public static Map<String, Currency> getTicker () throws APIException, IOException {
        return getTicker(null);
    }

    /**
     * Gets the price ticker from https://blockchain.info/ticker
     *
     * @param apiCode Blockchain.info API code (optional, nullable)
     * @return A map of currencies where the key is a 3-letter currency symbol and the
     * value is the `Currency` class
     * @throws APIException If the server returns an error
     */
    public static Map<String, Currency> getTicker (String apiCode) throws APIException, IOException {
        Map<String, String> params = createDefaultParams(apiCode);

        String response = HttpClient.getInstance().get("ticker", params);
        JsonObject ticker = new JsonParser().parse(response).getAsJsonObject();

        Map<String, Currency> resultMap = new HashMap<String, Currency>();
        for (Entry<String, JsonElement> currencyPair : ticker.entrySet()) {
            JsonObject ccy = currencyPair.getValue().getAsJsonObject();
            Currency currency = new Currency(ccy.get("buy").getAsDouble(), ccy.get("sell").getAsDouble(), ccy.get("last").getAsDouble(), ccy.get("15m").getAsDouble(), ccy.get("symbol").getAsString());

            resultMap.put(currencyPair.getKey(), currency);
        }

        return resultMap;
    }

    /**
     * Converts x value in the provided currency to BTC.
     *
     * @param currency Currency code
     * @param value    Value to convert
     * @return Converted value in BTC
     * @throws APIException If the server returns an error
     */
    public static BigDecimal toBTC (String currency, BigDecimal value) throws APIException, IOException {
        return toBTC(currency, value, null);
    }

    /**
     * Converts x value in the provided currency to BTC.
     *
     * @param currency Currency code
     * @param value    Value to convert
     * @param apiCode  Blockchain.info API code (optional, nullable)
     * @return Converted value in BTC
     * @throws APIException If the server returns an error
     */
    public static BigDecimal toBTC (String currency, BigDecimal value, String apiCode) throws APIException, IOException {
        Map<String, String> params = createDefaultParams(apiCode);
        addToParams (params , "currency", currency);
        addToParams (params , "value", String.valueOf(value));  
      
        String response = HttpClient.getInstance().get("tobtc", params);
        return new BigDecimal(response);
    }

    private static  Map<String, String> createDefaultParams (String apiCode) {
      Map<String, String> params = new HashMap<String, String>();
      if (apiCode != null) {
        params.put("api_code", apiCode);
      }
      return params; 
    }
    
    private static void addToParams (Map<String, String> params , String key, String value) {
      checkArgument(params != null, "Params list should be initialised before adding new pairs.");
      params.put(key, value);
      
    }
}
