package info.blockchain.api.wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;
import info.blockchain.api.wallet.entity.Address;
import info.blockchain.api.wallet.entity.CreateWalletResponse;
import info.blockchain.api.wallet.entity.PaymentResponse;

/**
 * This class reflects the functionality documented
 * at https://blockchain.info/api/blockchain_wallet_api. It allows users to interact
 * with their Blockchain.info wallet. If you have an API code, please set it via the
 * `setApiCode` method.
 */
public class Wallet {
    private JsonParser jsonParser;

    private String serviceURL;
    private String identifier;
    private String password;
    private String secondPassword;
    private String apiCode;

    /**
     * Creates a new Blockchain.info wallet.
     *
     * @param serviceURL URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param password Password for the new wallet. At least 10 characters.
     * @param apiCode  API code with create wallets permission
     * @return An instance of the CreateWalletResponse class
     * @throws APIException If the server returns an error
     */
    public static CreateWalletResponse create (String serviceURL, String password,
                                               String apiCode) throws IOException, APIException {
        return create(serviceURL, password, apiCode, null, null, null);
    }

    /**
     * Creates a new Blockchain.info wallet. It can be created containing a pre-generated
     * private key or will otherwise generate a new private key.
     *
     * @param serviceURL URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param password   Password for the new wallet. At least 10 characters.
     * @param apiCode    API code with create wallets permission
     * @param privateKey Private key to add to the wallet (optional, nullable)
     * @param label      Label for the first address in the wallet (optional, nullable)
     * @param email      Email to associate with the new wallet (optional, nullable)
     * @return An instance of the CreateWalletResponse class
     * @throws APIException If the server returns an error
     */
    public static CreateWalletResponse create (String serviceURL, String password,String apiCode,
                                               String privateKey, String label, String email) throws IOException, APIException {
        Map<String, String> params = new HashMap<String, String>();

        params.put("password", password);
        params.put("api_code", apiCode);
        if (privateKey != null) {
            params.put("priv", privateKey);
        }
        if (label != null) {
            params.put("label", label);
        }
        if (email != null) {
            params.put("email", email);
        }

        String response = HttpClient.getInstance().post(serviceURL, "api/v2/create", params);
        JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();

        return new CreateWalletResponse(
                jsonObj.get("guid").getAsString(),
                jsonObj.get("address").getAsString(),
                jsonObj.get("label").getAsString());
    }

    /**
     * @param serviceURL URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param apiCode    API Code
     * @param identifier Wallet identifier (GUID)
     * @param password   Decryption password
     */
    public Wallet (String serviceURL, String apiCode, String identifier, String password) {
        this(serviceURL, apiCode, identifier, password, null);
    }

    /**
     * @param serviceURL URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param apiCode    API Code
     * @param identifier     Wallet identifier (GUID)
     * @param password       Decryption password
     * @param secondPassword Second password
     */
    public Wallet (String serviceURL, String apiCode, String identifier, String password, String secondPassword) {
        this.serviceURL = serviceURL;
        this.apiCode = apiCode;
        this.identifier = identifier;
        this.password = password;
        this.secondPassword = secondPassword;
        this.jsonParser = new JsonParser();
    }

    /**
     * Sends bitcoin from your wallet to a single address.
     *
     * @param toAddress   Recipient bitcoin address
     * @param amount      Amount to send (in satoshi)
     * @param fromAddress Specific address to send from (optional, nullable)
     * @param fee         Transaction fee in satoshi. Must be greater than the default fee (optional, nullable).
     * @return An instance of the PaymentResponse class
     * @throws APIException If the server returns an error
     */
    public PaymentResponse send (String toAddress, long amount, String fromAddress, Long fee) throws APIException, IOException {
        Map<String, Long> recipient = new HashMap<String, Long>();
        recipient.put(toAddress, amount);

        return sendMany(recipient, fromAddress, fee);
    }

    /**
     * Sends bitcoin from your wallet to multiple addresses.
     *
     * @param recipients  Map with the structure of 'address':amount in satoshi (String:long)
     * @param fromAddress Specific address to send from (optional, nullable)
     * @param fee         Transaction fee in satoshi. Must be greater than the default fee (optional, nullable).
     * @return An instance of the PaymentResponse class
     * @throws APIException If the server returns an error
     * @throws IllegalArgumentException If no recipients is null or empty
     */
    public PaymentResponse sendMany (Map<String, Long> recipients, String fromAddress, Long fee) throws APIException, IOException {
        Map<String, String> params = buildBasicRequestWithSecondPassword();
        String method = null;

       if (recipients.size() == 1) {
            method = "payment";
            Entry<String, Long> e = recipients.entrySet().iterator().next();
            params.put("to", e.getKey());
            params.put("amount", e.getValue().toString());
        } else {
            method = "sendmany";
            params.put("recipients", new Gson().toJson(recipients));
        }

        if (fromAddress != null) {
            params.put("from", fromAddress);
        }
        if (fee != null) {
            params.put("fee", fee.toString());
        }

        String response = HttpClient.getInstance().post(serviceURL, String.format("merchant/%s/%s", identifier, method), params);
        JsonObject topElem = parseResponse(response);

        return new PaymentResponse(topElem.get("message").getAsString(), topElem.get("tx_hash").getAsString(), topElem.has("notice") ? topElem.get("notice").getAsString() : null);
    }

    /**
     * Fetches the wallet balance. Includes unconfirmed transactions and
     * possibly double spends.
     *
     * @return Wallet balance in satoshi
     * @throws APIException If the server returns an error
     */
    public long getBalance () throws APIException, IOException {
        String response = HttpClient.getInstance().get(serviceURL, String.format("merchant/%s/balance", identifier), buildBasicRequest());
        JsonObject topElem = parseResponse(response);

        return topElem.get("balance").getAsLong();
    }

    /**
     * Lists all active addresses in the wallet.
     *
     * @return A list of Address objects
     * @throws APIException If the server returns an error
     */
    public List<Address> listAddresses () throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();

        String response = HttpClient.getInstance().get(serviceURL, String.format("merchant/%s/list", identifier), params);
        JsonObject topElem = parseResponse(response);

        List<Address> addresses = new ArrayList<Address>();
        for (JsonElement jAddr : topElem.get("addresses").getAsJsonArray()) {
            JsonObject a = jAddr.getAsJsonObject();
            Address address = new Address(a.get("balance").getAsLong(), a.get("address").getAsString(), a.has("label") && !a.get("label").isJsonNull() ? a.get("label").getAsString() : null, a.get("total_received").getAsLong());

            addresses.add(address);
        }

        return addresses;
    }

    /**
     * Retrieves an address from the wallet.
     *
     * @param address       Address in the wallet to look up
     * @return An instance of the Address class
     * @throws APIException If the server returns an error
     */
    public Address getAddress (String address) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        params.put("address", address);

        String response = HttpClient.getInstance().get(serviceURL, String.format("merchant/%s/address_balance", identifier), params);
        JsonObject topElem = parseResponse(response);

        return new Address(topElem.get("balance").getAsLong(), topElem.get("address").getAsString(), topElem.has("label") && !topElem.get("label").isJsonNull() ? topElem.get("label").getAsString() : null, topElem.get("total_received").getAsLong());
    }

    /**
     * Generates a new address and adds it to the wallet.
     *
     * @param label Label to attach to this address (optional, nullable)
     * @return An instance of the Address class
     * @throws APIException If the server returns an error
     */
    public Address newAddress (String label) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        if (label != null) {
            params.put("label", label);
        }

        String response = HttpClient.getInstance().post(serviceURL, String.format("merchant/%s/new_address", identifier), params);
        JsonObject topElem = parseResponse(response);

        return new Address(0L, topElem.get("address").getAsString(), topElem.has("label") && !topElem.get("label").isJsonNull() ? topElem.get("label").getAsString() : null, 0L);
    }

    /**
     * Archives an address.
     *
     * @param address Address to archive
     * @return String representation of the archived address
     * @throws APIException If the server returns an error
     */
    public String archiveAddress (String address) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        params.put("address", address);

        String response = HttpClient.getInstance().post(serviceURL, String.format("merchant/%s/archive_address", identifier), params);
        JsonObject topElem = parseResponse(response);

        return topElem.get("archived").getAsString();
    }

    /**
     * Unarchives an address.
     *
     * @param address Address to unarchive
     * @return String representation of the unarchived address
     * @throws APIException If the server returns an error
     */
    public String unarchiveAddress (String address) throws APIException, IOException {
        Map<String, String> params = buildBasicRequest();
        params.put("address", address);

        String response = HttpClient.getInstance().post(serviceURL, String.format("merchant/%s/unarchive_address", identifier), params);
        JsonObject topElem = parseResponse(response);

        return topElem.get("active").getAsString();
    }

    private Map<String, String> buildBasicRequest () {
        Map<String, String> params = new HashMap<String, String>();

        params.put("password", password);
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        return params;
    }

    private Map<String, String> buildBasicRequestWithSecondPassword () {
        Map<String, String> params = buildBasicRequest();
        if (secondPassword != null) {
            params.put("second_password", secondPassword);
        }

        return params;
    }

    private JsonObject parseResponse (String response) throws APIException {
        JsonObject topElem = jsonParser.parse(response).getAsJsonObject();
        if (topElem.has("error")) {
            throw new APIException(topElem.get("error").getAsString());
        }

        return topElem;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Wallet wallet = (Wallet) o;

        return identifier == null ? wallet.identifier == null : identifier.equals(wallet.identifier);
    }

    @Override
    public int hashCode () {
        return identifier != null ? identifier.hashCode() : 0;
    }

}
