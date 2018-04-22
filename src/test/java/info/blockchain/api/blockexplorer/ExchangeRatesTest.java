package info.blockchain.api.blockexplorer;

import info.blockchain.api.exchangerates.Currency;
import info.blockchain.api.exchangerates.ExchangeRates;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by mariosk on 04/23/2018.
 */
public class ExchangeRatesTest {

    ExchangeRates rates;

    @Before
    public void setUp () throws Exception {
        rates = new ExchangeRates();
    }

    @Test
    public void exchangeTest() throws Exception {
        // get the Exchange object
        ExchangeRates exchange = new ExchangeRates();
        
        // get the ticker map
        Map<String, Currency> ticker = exchange.getTicker();
        BigDecimal BTCUSDsell = ticker.get("USD").getSell();
        assertNotNull(BTCUSDsell);

        // convert 5362 EUR to BTC
        BigDecimal EURToBTC = exchange.toBTC("EUR", new BigDecimal(53620));
        assertNotNull(EURToBTC);

        // convert 100,000,000 satoshi to USD
        BigDecimal BTCToUSD = exchange.toFiat("USD", new BigDecimal(100000000));
        assertNotNull(BTCToUSD);

        // convert 1000 satoshi to USD
        BigDecimal SmallBTCToUSD = exchange.toFiat("USD", new BigDecimal(1000));
        assertNotNull(SmallBTCToUSD);
    }
}