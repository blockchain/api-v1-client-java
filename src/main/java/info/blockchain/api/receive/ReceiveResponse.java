package info.blockchain.api.receive;

/**
 * This class is used as a response object to the `ReceiveV2.receive` method.
 */
public class ReceiveResponse {
    private int index;
    private String receivingAddress;
    private String callbackUrl;

    public ReceiveResponse (int index, String receivingAddress, String callbackUrl) {
        this.index = index;
        this.receivingAddress = receivingAddress;
        this.callbackUrl = callbackUrl;
    }

    /**
     * @return Index of the address in the account
     */
    public int getIndex () {
        return index;
    }

    /**
     * @return Address to be displayed for the customer at checkout.
     */
    public String getReceivingAddress () {
        return receivingAddress;
    }

    /**
     * @return Callback URI that will be called upon payment
     */
    public String getCallbackUrl () {
        return callbackUrl;
    }
}