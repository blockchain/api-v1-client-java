/**
 * 
 */
package info.blockchain.api.exchangeRates;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import info.blockchain.api.APIException;
import info.blockchain.api.exchangerates.Currency;
import info.blockchain.api.exchangerates.ExchangeRates;

/**
 * @author Jay Bhosle
 *
 */
public class ExchangeRatesTest {

	private ExchangeRates exchange;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		exchange = new ExchangeRates();
		
	}

	/**
	 * Test that GetTicker response is not empty.
	 * @throws APIException
	 * @throws IOException
	 */
	@Test
	public void testGetTickerResponseIsNotEmpty() throws APIException, IOException {
		
		Map<String, Currency> ticker = exchange.getTicker();
		
		assertTrue(!ticker.isEmpty());
		
	}
	
	/**
	 * Test that the Currency response object is constructed properly with valid values from the response.
	 * @throws APIException
	 * @throws IOException
	 */
	@Test
	public void testGetTickerResponseCurrencyValueIsNotNull() throws APIException, IOException {
		
		Map<String, Currency> ticker = exchange.getTicker();
		
		final String currencyKey1 = "USD";
		final String currencyKey2 = "GBP";
		
		if(ticker.containsKey(currencyKey1)) {
			
			assertTrue(ticker.get(currencyKey1).getBuy() != null &&
					ticker.get(currencyKey1).getSell() != null &&
					ticker.get(currencyKey1).getPrice15m() != null &&
					ticker.get(currencyKey1).getSymbol() != null);
			
		}
		else 
		{
			
			if(ticker.containsKey(currencyKey2)) {
				
				assertTrue(ticker.get(currencyKey2).getBuy() != null &&
						ticker.get(currencyKey2).getSell() != null &&
						ticker.get(currencyKey2).getPrice15m() != null &&
						ticker.get(currencyKey2).getSymbol() != null);
			
			}
		}

	}
	
	/**
	 * Test that the function ToBTC is operational.
	 * @throws APIException
	 * @throws IOException
	 */
	@Test
	public void testToBTCisOperational() throws APIException, IOException {
		
		assertNotNull(exchange.toBTC("USD", new BigDecimal(8512.76)));
		
	}

}
