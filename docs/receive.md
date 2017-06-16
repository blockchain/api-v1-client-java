## `info.blockchain.api.receive` package

The `receive` package contains the `Receive` class that reflects the functionality documented at https://blockchain.info/api/api_receive. It allows merchants to derive addresses from their HD wallets and be notified upon payment.

Example usage:

```java

package test;
import info.blockchain.api.receive.*;

public class App
{
    public static void main(String[] args) throws Exception
    {
        Receive receive = new Receive("YOUR_API_CODE");
        
    	ReceiveResponse response = receive.receive(
    	        "xpub661MyMwAqRbcFtXgS5sYJABqqG9YLmC4Q1Rdap9gSE8NqtwybGhePY2gZ29ESFjqJoCu1Rupje8YtGqsefD265TMg7usUDFdp6W1EGMcet8",
    			URLEncoder.encode("https://your.url.com?secret=foo", "UTF-8"));

    	System.out.println(String.format("The receiving address is %s. "
    			+ "The address index is %d",
    			response.getReceivingAddress(),
    			response.getIndex()));
    			
    	int xpubGap = receive.checkGap("xpub661MyMwAqRbcFtXgS5sYJABqqG9YLmC4Q1Rdap9gSE8NqtwybGhePY2gZ29ESFjqJoCu1Rupje8YtGqsefD265TMg7usUDFdp6W1EGMcet8");
    	CallbackLog cbLog = receive.getCallbackLog(URLEncoder.encode("https://your.url.com?secret=foo", "UTF-8"));
    }
}

```