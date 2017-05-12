package info.blockchain.api.statistics;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/charts_api
 */
public class Statistics {
    private final String apiCode;

    public Statistics () {
        this(null);
    }

    public Statistics (String apiCode) {
        this.apiCode = apiCode;
    }

    /**
     * Gets the network statistics.
     *
     * @return An instance of the StatisticsResponse class
     * @throws APIException If the server returns an error
     */
    public StatisticsResponse getStats () throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("format", "json");
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        String response = HttpClient.getInstance().get("stats", params);
        return new StatisticsResponse(response);
    }

    /**
     * This method can be used to get and manipulate data behind all Blockchain.info's charts.
     *
     * @param type of chart (Example: transactions-per-second, total-bitcoins)
     * @param timeSpan (Example: 5weeks)
     * @param rollingAverage (Example: 8hours)
     * @return {@code Chart} represents the series of data of the chart
     * @throws APIException If the server returns an error
     */
    public Chart getChart(String type, String timeSpan, String rollingAverage) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("format", "json");
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }
        if (timeSpan != null) {
            params.put("timespan", timeSpan);
        }
        if (rollingAverage != null) {
            params.put("rollingAverage", rollingAverage);
        }

        String response = HttpClient.getInstance().get("charts/" + type, params);
        JsonObject chartJson = new JsonParser().parse(response).getAsJsonObject();

        return new Chart(chartJson);
    }

    /**
     * This method can be used to get the data behind Blockchain.info's pools information.
     *
     * @param timeSpan (Example: 5weeks)
     * @return a map of pool name and the number of blocks it mined
     * @throws APIException If the server returns an error
     */
    public Map<String, Integer> getPools(String timeSpan) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("format", "json");
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }
        if (timeSpan != null) {
            params.put("timespan", timeSpan);
        }

        String response = HttpClient.getInstance().get("pools", params);
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Gson gson = new Gson();
        Map<String, Integer> pools = gson.fromJson(response, type);

        return pools;
    }

}
