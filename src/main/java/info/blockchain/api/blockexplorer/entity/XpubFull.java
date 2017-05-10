package info.blockchain.api.blockexplorer.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class XpubFull {

    private String address;
    private int txCount;
    private long totalReceived;
    private long totalSent;
    private long finalBalance;
    private int changeIndex;
    private int accountIndex;
    private int gapLimit;
    private List<Transaction> txs;

    public XpubFull (String address, int txCount, long totalReceived, long totalSent, long finalBalance, int changeIndex, int accountIndex, int gapLimit,
                     List<Transaction> txs) {
        this.address = address;
        this.txCount = txCount;
        this.totalReceived = totalReceived;
        this.totalSent = totalSent;
        this.finalBalance = finalBalance;
        this.changeIndex = changeIndex;
        this.accountIndex = accountIndex;
        this.gapLimit = gapLimit;
        this.txs = txs;
    }

    public XpubFull (AddressSummary addressSummary, List<Transaction> txs) {
        this.address = addressSummary.getAddress();
        this.txCount = addressSummary.getTxCount();
        this.accountIndex = addressSummary.getAccountIndex();
        this.changeIndex = addressSummary.getChangeIndex();
        this.finalBalance = addressSummary.getFinalBalance();
        this.gapLimit = addressSummary.getGapLimit();
        this.totalReceived = addressSummary.getTotalReceived();
        this.totalSent = addressSummary.getTotalSent();
        this.txs = txs;
    }

    public XpubFull (JsonObject json) {
        this(
                json.has("address") ? json.get("address").getAsString() : "",
                json.has("n_tx") ? json.get("n_tx").getAsInt() : 0,
                json.has("total_received") ? json.get("total_received").getAsLong() : 0,
                json.has("total_sent") ? json.get("total_sent").getAsLong() : 0,
                json.has("final_balance") ? json.get("final_balance").getAsLong() : 0,
                json.has("change_index") ? json.get("change_index").getAsInt() : 0,
                json.has("account_index") ? json.get("account_index").getAsInt() : 0,
                json.has("gap_limit") ? json.get("gap_limit").getAsInt() : 0,
                null
        );

        txs = new ArrayList<Transaction>();
        for (JsonElement txElem : json.get("txs").getAsJsonArray()) {
            JsonObject addrObj = txElem.getAsJsonObject();
            txs.add(new Transaction(addrObj));
        }
    }

    public String getAddress() {
        return address;
    }

    public int getTxCount() {
        return txCount;
    }

    public void setTxCount(int txCount) {
        this.txCount = txCount;
    }

    public long getTotalReceived() {
        return totalReceived;
    }

    public long getTotalSent() {
        return totalSent;
    }

    public long getFinalBalance() {
        return finalBalance;
    }

    public int getChangeIndex() {
        return changeIndex;
    }

    public int getAccountIndex() {
        return accountIndex;
    }

    public void setFinalBalance(long final_balance) {
        this.finalBalance = final_balance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalReceived(long total_received) {
        this.totalReceived = total_received;
    }

    public void setTotalSent(long total_sent) {
        this.totalSent = total_sent;
    }

    public void setChangeIndex(int change_index) {
        this.changeIndex = change_index;
    }

    public void setAccountIndex(int account_index) {
        this.accountIndex = account_index;
    }

    public int getGapLimit () {
        return gapLimit;
    }

    public void setGapLimit (int gapLimit) {
        this.gapLimit = gapLimit;
    }

    public List<Transaction> getTxs () {
        return txs;
    }

    public void setTxs (List<Transaction> txs) {
        this.txs = txs;
    }
}
