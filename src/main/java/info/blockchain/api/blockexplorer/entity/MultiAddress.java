package info.blockchain.api.blockexplorer.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class MultiAddress {

    private MultiAddressBalance multiAddressBalance;

    private List<AddressSummary> addresses;

    private List<Transaction> txs;

    public MultiAddress (MultiAddressBalance multiAddressBalance, List<AddressSummary> addresses, List<Transaction> txs) {
        this.multiAddressBalance = multiAddressBalance;
        this.addresses = addresses;
        this.txs = txs;
    }

    public MultiAddress (JsonObject json) {
        List<AddressSummary> addresses = new ArrayList<AddressSummary>();
        for (JsonElement txElem : json.get("addresses").getAsJsonArray()) {
            JsonObject addrObj = txElem.getAsJsonObject();
            addresses.add(new AddressSummary(addrObj));
        }
        this.addresses = addresses;

        List<Transaction> txs = new ArrayList<Transaction>();
        for (JsonElement txElem : json.get("txs").getAsJsonArray()) {
            JsonObject addrObj = txElem.getAsJsonObject();
            txs.add(new Transaction(addrObj));
        }
        this.txs = txs;

        this.multiAddressBalance = new MultiAddressBalance(json.getAsJsonObject("wallet"));
    }

    public MultiAddressBalance getMultiAddressBalance () {
        return multiAddressBalance;
    }

    public List<AddressSummary> getAddresses() {
        return addresses;
    }

    public List<Transaction> getTxs() {
        return txs;
    }

    public void setMultiAddressBalance (MultiAddressBalance multiAddressBalance) {
        this.multiAddressBalance = multiAddressBalance;
    }

    public void setAddresses(List<AddressSummary> addresses) {
        this.addresses = addresses;
    }

    public void setTxs(List<Transaction> txs) {
        this.txs = txs;
    }

}
