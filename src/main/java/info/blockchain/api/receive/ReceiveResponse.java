package info.blockchain.api.receive;

/**
 * This class is used as a response object to the Receive.receive call. 
 *
 */
public class ReceiveResponse
{
	private int feePercent;
	private String destinationAddress;
	private String inputAddress;
	private String callbackUrl;
	
	public ReceiveResponse(int feePercent, String destinationAddress,
			String inputAddress, String callbackUrl)
	{
		this.feePercent = feePercent;
		this.destinationAddress = destinationAddress;
		this.inputAddress = inputAddress;
		this.callbackUrl = callbackUrl;
	}
	
	public int getFeePercent()
	{
		return feePercent;
	}
	public String getDestinationAddress()
	{
		return destinationAddress;
	}
	public String getInputAddress()
	{
		return inputAddress;
	}
	public String getCallbackUrl()
	{
		return callbackUrl;
	}
}