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

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/exchange_rates_api. It allows users to fetch the latest
 * ticker data and convert amounts between BTC and fiat currencies.
 */
public class ExchangeRates {

    private final String apiCode;

    public ExchangeRates () {
        this(null);
    }

    public ExchangeRates (String apiCode) {
        this.apiCode = apiCode;
    }

    /**
     * Gets the price ticker from https://blockchain.info/ticker
     *
     * @return A map of currencies where the key is a 3-letter currency symbol and the
     * value is the `Currency` class
     * @throws APIException If the server returns an error
     */
    public Map<String, Currency> getTicker () throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        String response = HttpClient.getInstance().get("ticker", params);
        JsonObject ticker = new JsonParser().parse(response).getAsJsonObject();

        Map<String, Currency> resultMap = new HashMap<String, Currency>();
        for (Entry<String, JsonElement> ccyKVP : ticker.entrySet()) {
            JsonObject ccy = ccyKVP.getValue().getAsJsonObject();
            Currency currency = new Currency(ccy.get("buy").getAsDouble(), ccy.get("sell").getAsDouble(), ccy.get("last").getAsDouble(), ccy.get("15m").getAsDouble(), ccy.get("symbol").getAsString());

            resultMap.put(ccyKVP.getKey(), currency);
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
    public BigDecimal toBTC (String currency, BigDecimal value) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("currency", currency);
        params.put("value", String.valueOf(value));
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        String response = HttpClient.getInstance().get("tobtc", params);
        return new BigDecimal(response);
    }

    /**
     * Converts x value in BTC to the provided currency.
     *
     * @param currency Currency code
     * @param value    Value to convert
     * @return Converted value in the provided currency
     * @throws APIException If the server returns an error
     */
    public BigDecimal toFiat (String currency, BigDecimal value) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("currency", currency);
        params.put("value", String.valueOf(value.multiply(BigDecimal.valueOf(100000000L)))); // The endpoint is expecting satoshi
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        String response = HttpClient.getInstance().get("frombtc", params);
        return new BigDecimal(response);
    }

}
