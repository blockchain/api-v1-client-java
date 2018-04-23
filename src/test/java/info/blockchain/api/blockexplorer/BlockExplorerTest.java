package info.blockchain.api.blockexplorer;

import info.blockchain.api.blockexplorer.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ray on 10/05/2017.
 */
public class BlockExplorerTest {

    BlockExplorer client;
    private static final String address1 = "1jH7K4RJrQBXijtLj1JpzqPRhR7MdFtaW";
    private static final String address2 = "xpub6CmZamQcHw2TPtbGmJNEvRgfhLwitarvzFn3fBYEEkFTqztus7W7CNbf48Kxuj1bRRBmZPzQocB6qar9ay6buVkQk73ftKE1z4tt9cPHWRn";

    @Before
    public void setUp () throws Exception {
        client = new BlockExplorer();
    }

    @Test
    public void getAddress () throws Exception {
        Address address = client.getAddress(address1, FilterType.All, 10, null);

        assertEquals(address1, address.getAddress());
        assertEquals("07feead7f9fb7d16a0251421ac9fa090169cc169",
                address.getHash160());
        assertEquals(137887, address.getFinalBalance());
        assertEquals(15, address.getTxCount());
        assertEquals(558123, address.getTotalReceived());
        assertEquals(420236, address.getTotalSent());
        assertEquals(10, address.getTransactions().size());
    }

    @Test
    public void getUnspentOutputs () throws Exception {
        List<UnspentOutput> unspentOutputs = client.getUnspentOutputs(Arrays.asList(address1, address2), 6, 10);

        assertTrue(unspentOutputs != null && unspentOutputs.size() != 0);
        assertEquals("2e7ab41818ee0ab987d393d4c8bf5e436b6e8c15ef3535a2b3eac581e33c7472", unspentOutputs.get(0).getTransactionHash());
        assertEquals(20000, unspentOutputs.get(0).getValue());
    }

    @Test
    public void getBalance () throws Exception {
        List<String> list = Arrays.asList(address1, address2);

        Map<String, Balance> balances = client.getBalance(list, FilterType.All);

        assertEquals(137887, balances.get(address1).getFinalBalance());
        assertEquals(15, balances.get(address1).getTxCount());
        assertEquals(558123, balances.get(address1).getTotalReceived());
        assertEquals(20000, balances.get(address2).getFinalBalance());
        assertEquals(1, balances.get(address2).getTxCount());
        assertEquals(20000, balances.get(address2).getTotalReceived());
    }

    @Test
    public void getMultiAddress () throws Exception {
        List<String> list = Arrays.asList(address1, address2);
        MultiAddress multiAddress = client.getMultiAddress(list, FilterType.All, null, null);

        //Addresses
        assertEquals(address1, multiAddress.getAddresses().get(0).getAddress());
        assertEquals(15, multiAddress.getAddresses().get(0).getTxCount());
        assertEquals(558123, multiAddress.getAddresses().get(0).getTotalReceived());
        assertEquals(420236, multiAddress.getAddresses().get(0).getTotalSent());
        assertEquals(137887, multiAddress.getAddresses().get(0).getFinalBalance());

        assertEquals(address2, multiAddress
                        .getAddresses().get(1).getAddress());
        assertEquals(1, multiAddress.getAddresses().get(1).getTxCount());
        assertEquals(20000, multiAddress.getAddresses().get(1).getTotalReceived());
        assertEquals(0, multiAddress.getAddresses().get(1).getTotalSent());
        assertEquals(20000, multiAddress.getAddresses().get(1).getFinalBalance());
        assertEquals(0, multiAddress.getAddresses().get(1).getChangeIndex());
        assertEquals(1, multiAddress.getAddresses().get(1).getAccountIndex());
        assertEquals(20, multiAddress.getAddresses().get(1).getGapLimit());
    }

    @Test
    public void getXpub () throws Exception {
        XpubFull xpub = client.getXpub(address2, null, null, null);

        assertEquals(xpub.getAddress(), address2);
        assertEquals(1, xpub.getTxCount());
        assertEquals(20000, xpub.getTotalReceived());
        assertEquals(0, xpub.getTotalSent());
        assertEquals(20000, xpub.getFinalBalance());
        assertEquals(0, xpub.getChangeIndex());
        assertEquals(1, xpub.getAccountIndex());
        assertEquals(20, xpub.getGapLimit());
    }

}