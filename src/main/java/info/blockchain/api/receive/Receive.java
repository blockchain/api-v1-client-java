package info.blockchain.api.receive;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reflects the functionality necessary for using the receive-payments-api v2.
 * Passing on a xPUB, callbackUrl and the apiCode will return an address for receiving a payment.
 * <p>
 * Upon receiving a payment on this address, the merchant will be notified using the callback URL.
 */
public class Receive {
    private final String apiCode;

    /**
     * @param apiCode     Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     */
    public Receive (String apiCode) {
        this.apiCode = apiCode;
    }

    /**
     * Calls the receive-payments-api v2 and returns an address for the payment.
     *
     * @param xPUB        Destination address where the payment should be sent
     * @param callbackUrl Callback URI that will be called upon payment
     * @return An instance of the ReceiveV2Response class
     * @throws APIException If the server returns an error
     */
    public ReceiveResponse receive (String xPUB, String callbackUrl) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("xpub", xPUB);
        params.put("callback", callbackUrl);
        params.put("key", apiCode);

        String response = HttpClient.getInstance().get("https://api.blockchain.info/", "v2/receive", params);
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(response).getAsJsonObject();

        return new ReceiveResponse(obj.get("index").getAsInt(), obj.get("address").getAsString(), obj.get("callback").getAsString());
    }

    /**
     * Calls the receive-payments-api v2 and returns the xpub gap of an xpub.
     *
     * @param xPUB        Destination address where the payment should be sent
     * @return An instance of the ReceiveV2Response class
     * @throws APIException If the server returns an error
     */
    public int checkGap (String xPUB) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("xpub", xPUB);
        params.put("key", apiCode);

        String response = HttpClient.getInstance().get("https://api.blockchain.info/", "v2/receive/checkgap", params);
        JsonObject obj = new JsonParser().parse(response).getAsJsonObject();

        return obj.get("gap").getAsInt();
    }

    /**
     * Calls the receive-payments-api v2 and returns the callback log based on url.
     *
     * @param callbackUrl Callback URI that will be called upon payment
     * @return An instance of the ReceiveV2Response class
     * @throws APIException If the server returns an error
     */
    public CallbackLog getCallbackLog (String callbackUrl) throws APIException, IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("callback", callbackUrl);
        params.put("key", apiCode);

        String response = HttpClient.getInstance().get("https://api.blockchain.info/", "v2/receive/callback", params);
        JsonObject obj = new JsonParser().parse(response).getAsJsonObject();

        return new CallbackLog(obj);
    }
}
