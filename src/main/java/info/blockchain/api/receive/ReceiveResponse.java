package info.blockchain.api.receive;

/**
 * This class is used as a response object to the `Receive.receive` method.
 *
 * @deprecated Since there is a new Receive Payments API, this class is deprecated. Use ReceiveV2Response instead.
 */
@Deprecated
public class ReceiveResponse {
    private int feePercent;
    private String destinationAddress;
    private String inputAddress;
    private String callbackUrl;

    @Deprecated
    public ReceiveResponse (int feePercent, String destinationAddress, String inputAddress, String callbackUrl) {
        this.feePercent = feePercent;
        this.destinationAddress = destinationAddress;
        this.inputAddress = inputAddress;
        this.callbackUrl = callbackUrl;
    }

    /**
     * @return Forwarding fee
     * @deprecated Use ReceiveV2Response instead..
     */
    @Deprecated
    public int getFeePercent () {
        return feePercent;
    }

    /**
     * @return Destination address where the funds will be forwarded
     * @deprecated Use ReceiveV2Response instead..
     */
    @Deprecated
    public String getDestinationAddress () {
        return destinationAddress;
    }

    /**
     * @return Input address where the funds should be sent
     * @deprecated Use ReceiveV2Response instead..
     */
    @Deprecated
    public String getInputAddress () {
        return inputAddress;
    }

    /**
     * @return Callback URI that will be called upon payment
     * @deprecated Use ReceiveV2Response instead..
     */
    @Deprecated
    public String getCallbackUrl () {
        return callbackUrl;
    }
}