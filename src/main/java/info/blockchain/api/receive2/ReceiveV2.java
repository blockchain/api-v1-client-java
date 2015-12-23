package info.blockchain.api.receive2;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class reflects the functionality necessary for using the receive-payments-api v2.
 * Passing on a xPUB, callbackUrl and the apiCode will return an address for receiving a payment.
 * <p>
 * Upon receiving a payment on this address, the merchant will be notified using the callback URL.
 */
public class ReceiveV2 {

    /**
     * Calls the receive-payments-api v2 and returns an address for the payment.
     *
     * @param xPUB        Destination address where the payment should be sent
     * @param callbackUrl Callback URI that will be called upon payment
     * @param apiCode     Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     * @return An instance of the ReceiveV2Response class
     * @throws APIException If the server returns an error
     */
    public static ReceiveV2Response receive (String xPUB, String callbackUrl, String apiCode) throws APIException, IOException {
        if (apiCode == null || Objects.equals(apiCode, "")) {
            throw new APIException("No API Code provided..");
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("xpub", xPUB);
        params.put("callback", callbackUrl);
        params.put("key", apiCode);

        String response = HttpClient.getInstance().get("https://api.blockchain.info/", "v2/receive", params);
        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(response).getAsJsonObject();

        return new ReceiveV2Response(obj.get("index").getAsInt(), obj.get("address").getAsString(), obj.get("callback").getAsString());
    }
}
