package info.blockchain.api.blockexplorer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;
import info.blockchain.api.blockexplorer.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * The BlockExplorer class reflects the functionality documented at
 * https://blockchain.info/api/blockchain_api. It can be used to query the block chain,
 * fetch block, transaction and address data, get unspent outputs for an address etc.
 */
public class BlockExplorer {
    private final String apiCode;

    public BlockExplorer () {
        this(null);
    }

    /**
     * @param apiCode Blockchain.info API code (optional, nullable)
     */
    public BlockExplorer (String apiCode) {
        this.apiCode = apiCode;
    }

    /**
     * Gets a single transaction based on a transaction index.
     *
     * @param txIndex Transaction index
     * @return An instance of the {@link Transaction} class
     * @throws APIException If the server returns an error
     * @deprecated As of 1.1.5, will be removed in future releases
     */
    @Deprecated
    public Transaction getTransaction (long txIndex) throws APIException, IOException {
        return getTransaction(String.valueOf(txIndex));
    }

    /**
     * Gets a single transaction based on a transaction hash.
     *
     * @param txHash Transaction hash
     * @return An instance of the {@link Transaction} class
     * @throws APIException If the server returns an error
     */
    public Transaction getTransaction (String txHash) throws APIException, IOException {
        String response = HttpClient.getInstance().get("rawtx/" + txHash, buildBasicRequest());
        JsonObject txJson = new JsonParser().parse(response).getAsJsonObject();
        return new Transaction(txJson);
    }

    /**
     * Gets a single block based on a block index.
     *
     * @param blockIndex Block index
     * @return An instance of the {@link Block} class
     * @throws APIException If the server returns an error
     * @deprecated As of 1.1.5, will be removed in future releases
     */
    @Deprecated
    public Block getBlock (long blockIndex) throws APIException, IOException {
        return getBlock(String.valueOf(blockIndex));
    }

    /**
     * Gets a single block based on a block hash.
     *
     * @param blockHash Block hash
     * @return An instance of the {@link Block} class
     * @throws APIException If the server returns an error
     */
    public Block getBlock (String blockHash) throws APIException, IOException {
        String response = HttpClient.getInstance().get("rawblock/" + blockHash, buildBasicRequest());
        JsonObject blockJson = new JsonParser().parse(response).getAsJsonObject();
        return new Block(blockJson);
    }

    /**
     * Gets data for a single address.
     *
     * @param address Base58check or hash160 address string
     * @param filter the filter for transactions selection, use null to indicate default
     * @param limit an integer to limit number of transactions to display, use null to indicate default
     * @param offset an integer to set number of transactions to skip when fetch, use null to indicate default
     * @return An instance of the {@link Address} class
     * @throws APIException If the server returns an error
     */
    public Address getAddress (String address, FilterType filter, Integer limit, Integer offset) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        if (filter != null) {
            params.put("filter", filter.getFilterInt().toString());
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        }
        if (offset != null) {
            params.put("offset", offset.toString());
        }
        String response = HttpClient.getInstance().get("rawaddr/" + address, params);
        JsonObject addrJson = new JsonParser().parse(response).getAsJsonObject();

        return new Address(addrJson);
    }

    /**
     * Gets data for a single address.
     *
     * @param address Base58check or hash160 address string
     * @return An instance of the {@link Address} class
     * @throws APIException If the server returns an error
     */
    public Address getAddress (String address) throws APIException, IOException {
        return getAddress(address, null, null, null);
    }

    /**
     * Gets a list of blocks at the specified height. Normally, only one block will be
     * returned, but in case of a chain fork, multiple blocks may be present.
     *
     * @param height Block height
     * @return A list of blocks at the specified height
     * @throws APIException If the server returns an error
     */
    public List<Block> getBlocksAtHeight (long height) throws APIException, IOException {
        List<Block> blocks = new ArrayList<Block>();

        String response = HttpClient.getInstance().get("block-height/" + height, buildBasicRequest());
        JsonObject blocksJson = new JsonParser().parse(response).getAsJsonObject();

        for (JsonElement blockElem : blocksJson.get("blocks").getAsJsonArray()) {
            blocks.add(new Block(blockElem.getAsJsonObject()));
        }

        return blocks;
    }

    /**
     * Returns list of unspent outputs.
     *
     * @param addressList a list of Base58 or xpub addresses
     * @param confirms an integer for minimum confirms of the outputs, use null to indicate default
     * @param limit an integer to limit number of transactions to display, use null to indicate default
     * @return A list of unspent outputs for the specified address
     * @throws APIException If the server returns an error
     */
    public List<UnspentOutput> getUnspentOutputs (List<String> addressList, Integer confirms, Integer limit) throws APIException, IOException {
        List<UnspentOutput> outputs = new ArrayList<UnspentOutput>();

        Map<String, String> params = buildBasicRequest();
        String pipedAddresses = StringUtils.join(addressList, "|");
        params.put("active", pipedAddresses);
        if (confirms != null) {
            params.put("confirmations", confirms.toString());
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        }

        String response = null;
        try {
            response = HttpClient.getInstance().get("unspent", params);
        } catch (APIException e) {
            // The server endpoint will return error if no output, however this should be a valid situation so we return an empty result.
            if (e.getMessage().equals("No free outputs to spend")) {
                return outputs;
            }
        }

        JsonObject outsJson = new JsonParser().parse(response).getAsJsonObject();
        for (JsonElement outElem : outsJson.get("unspent_outputs").getAsJsonArray()) {
            outputs.add(new UnspentOutput(outElem.getAsJsonObject()));
        }

        return outputs;
    }

    /**
     * Gets unspent outputs for a single address.
     *
     * @param address Base58check or hash160 address string
     * @return A list of unspent outputs for the specified address
     * @throws APIException If the server returns an error
     */
    public List<UnspentOutput> getUnspentOutputs (String address) throws APIException, IOException {
        return getUnspentOutputs(Arrays.asList(address), null, null);
    }

    /**
     * Gets the latest block on the main chain (simplified representation).
     *
     * @return An instance of the {@link LatestBlock} class
     * @throws APIException If the server returns an error
     */
    public LatestBlock getLatestBlock () throws APIException, IOException {
        String response = HttpClient.getInstance().get("latestblock", buildBasicRequest());
        JsonObject blockObj = new JsonParser().parse(response).getAsJsonObject();
        return new LatestBlock(blockObj);
    }

    /**
     * Gets a list of currently unconfirmed transactions.
     *
     * @return A list of unconfirmed {@link Transaction} objects
     * @throws APIException If the server returns an error
     */
    public List<Transaction> getUnconfirmedTransactions () throws APIException, IOException {
        List<Transaction> transactions = new ArrayList<Transaction>();

        String response = HttpClient.getInstance().get("unconfirmed-transactions", buildBasicRequest());
        JsonObject txList = new JsonParser().parse(response).getAsJsonObject();

        for (JsonElement txElem : txList.get("txs").getAsJsonArray()) {
            JsonObject txObj = txElem.getAsJsonObject();
            transactions.add(new Transaction(txObj, -1, txObj.get("double_spend").getAsBoolean()));
        }

        return transactions;
    }

    /**
     * Gets a list of blocks mined today by all pools since 00:00 UTC.
     *
     * @return A list of {@link SimpleBlock} objects
     * @throws APIException APIException If the server returns an error
     */
    public List<SimpleBlock> getBlocks () throws APIException, IOException {
        return getBlocks(null);
    }

    /**
     * Gets a list of blocks mined on a specific day.
     *
     * @param timestamp Unix timestamp (without milliseconds) that falls between
     *                  00:00 UTC and 23:59 UTC of the desired day.
     * @return A list of {@link SimpleBlock} objects
     */
    public List<SimpleBlock> getBlocks (long timestamp) throws APIException, IOException {
        return getBlocks(String.valueOf(timestamp * 1000));
    }

    /**
     * Gets a list of recent blocks by a specific mining pool.
     *
     * @param poolName Name of the mining pool
     * @return A list of {@link SimpleBlock} objects
     * @throws APIException If the server returns an error
     */
    public List<SimpleBlock> getBlocks (String poolName) throws APIException, IOException {
        List<SimpleBlock> blocks = new ArrayList<SimpleBlock>();
        poolName = poolName == null ? "" : poolName;

        String response = HttpClient.getInstance().get("blocks/" + poolName, buildBasicRequest());
        JsonObject blockList = new JsonParser().parse(response).getAsJsonObject();

        for (JsonElement blockElem : blockList.get("blocks").getAsJsonArray()) {
            blocks.add(new SimpleBlock(blockElem.getAsJsonObject()));
        }

        return blocks;
    }

    /**
     * Returns the address balance summary for each address provided
     *
     * @param addressList base58 or xpub addresses
     * @param filter the filter for transactions selection, use null to indicate default
     * @return a map of (address, {@link Balance})
     */
    public Map<String, Balance> getBalance(List<String> addressList, FilterType filter) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        String pipedAddresses = StringUtils.join(addressList, "|");
        params.put("active", pipedAddresses);
        if (filter != null) {
            params.put("filter", filter.getFilterInt().toString());
        }

        String response = HttpClient.getInstance().get("balance", params);
        JsonObject balanceMap = new JsonParser().parse(response).getAsJsonObject();

        Map<String, Balance> balances = new HashMap<String, Balance>();
        for (String address : addressList) {
            JsonObject balance = balanceMap.getAsJsonObject(address);
            balances.put(address, new Balance(balance));
        }

        return balances;
    }

    /**
     * Returns an aggregated summary on all addresses provided.
     *
     * @param addressList a list of Base58 or xpub addresses
     * @param filter the filter for transactions selection, use null to indicate default
     * @param limit an integer to limit number of transactions to display, use null to indicate default
     * @param offset an integer to set number of transactions to skip when fetch, use null to indicate default
     * @return An instance of the {@link MultiAddress} class
     * @throws APIException If the server returns an error
     */
    public MultiAddress getMultiAddress(List<String> addressList, FilterType filter, Integer limit, Integer offset) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        String pipedAddresses = StringUtils.join(addressList, "|");
        params.put("active", pipedAddresses);
        if (filter != null) {
            params.put("filter", filter.getFilterInt().toString());
        }
        if (limit != null) {
            params.put("n", limit.toString());
        }
        if (offset != null) {
            params.put("offset", offset.toString());
        }

        String response = HttpClient.getInstance().get("multiaddr", params);
        JsonObject addrJson = new JsonParser().parse(response).getAsJsonObject();

        return new MultiAddress(addrJson);
    }

    /**
     * Returns xpub summary on a xpub provided, with its overall balance and its transactions.
     *
     * @param xpub a xpub address
     * @param filter the filter for transactions selection, use null to indicate default
     * @param limit an integer to limit number of transactions to display, use null to indicate default
     * @param offset an integer to set number of transactions to skip when fetch
     * @return {@link XpubFull} an object to represent the xpub summary
     */
    public XpubFull getXpub(String xpub, FilterType filter, Integer limit, Integer offset) throws APIException, IOException {
        MultiAddress multiAddress = getMultiAddress(Arrays.asList(xpub), filter, limit, offset);

        return new XpubFull(multiAddress.getAddresses().get(0), multiAddress.getTxs());
    }

    private Map<String, String> buildBasicRequest () {
        Map<String, String> params = new HashMap<String, String>();

        params.put("format", "json");
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        return params;
  }
}
