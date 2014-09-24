##`info.blockchain.api.exchangerates` package

The `exchangerates` package contains the `CreateWallet` class that reflects the functionality documented at https://blockchain.info/api/create_wallet. It allows users to create new wallets as long as their API code has the 'generate wallet' permission.

Example usage:

```java

package test;
import info.blockchain.api.exchangerates.*;

public class App 
{
    public static void main(String[] args) throws Exception
    {	
		// get the ticker map
    	Map<String, Currency> ticker = ExchangeRates.getTicker();
    	double BTCUSDsell = ticker.get("USD").getSell();
    	
		// convert 5362 EUR to BTC
    	double EURToBTC = ExchangeRates.toBTC("EUR", 5362);
    }
}

```