##`info.blockchain.api.receive` package

The `receive` package contains the `Receive` class that reflects the functionality documented at at https://blockchain.info/api/api_receive. It allows merchants to create forwarding addresses and be notified upon payment.

Example usage:

```java

package test;
import info.blockchain.api.receive.*;

public class App 
{
    public static void main(String[] args) throws Exception
    {	
    	ReceiveResponse response = Receive.receive("1KZoUuPWFAeyVySHAGqvTUDoX6P3ntuLNF",
    			"http://your.url.com");
    	
    	System.out.println(String.format("The receiving address is %s. "
    			+ "The payment will be forwarded to %s", 
    			response.getInputAddress(),
    			response.getDestinationAddress()));
    }
}

```