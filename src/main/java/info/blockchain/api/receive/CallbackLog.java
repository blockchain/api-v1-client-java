package info.blockchain.api.receive;

import com.google.gson.JsonObject;

public class CallbackLog {
    private String callback;
    private String callbackTime;
    private String response;
    private int responseCode;

    public CallbackLog (JsonObject callbackJson) {
        this(
                callbackJson.has("callback") ? callbackJson.get("callback").getAsString() : "",
                callbackJson.has("called_at") ? callbackJson.get("called_at").getAsString() : "",
                callbackJson.has("raw_response") ? callbackJson.get("raw_response").getAsString() : "",
                callbackJson.has("response_code") ? callbackJson.get("response_code").getAsInt() : 0
        );
    }

    public CallbackLog (String callback, String callbackTime, String response, int responseCode) {
        this.callback = callback;
        this.callbackTime = callbackTime;
        this.response = response;
        this.responseCode = responseCode;
    }

    public String getCallback () {
        return callback;
    }

    public void setCallback (String callback) {
        this.callback = callback;
    }

    public String getCallbackTime () {
        return callbackTime;
    }

    public void setCallbackTime (String callbackTime) {
        this.callbackTime = callbackTime;
    }

    public String getResponse () {
        return response;
    }

    public void setResponse (String response) {
        this.response = response;
    }

    public int getResponseCode () {
        return responseCode;
    }

    public void setResponseCode (int responseCode) {
        this.responseCode = responseCode;
    }

}
