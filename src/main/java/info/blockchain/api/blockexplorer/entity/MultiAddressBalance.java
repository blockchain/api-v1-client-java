package info.blockchain.api.blockexplorer.entity;

import com.google.gson.JsonObject;

public class MultiAddressBalance {

    private int txCount;
    private int txCountFiltered;
    private long totalReceived;
    private long totalSent;
    private long finalBalance;

    public MultiAddressBalance (int txCount, int txCountFiltered, long totalReceived, long totalSent, long finalBalance) {
        this.txCount = txCount;
        this.txCountFiltered = txCountFiltered;
        this.totalReceived = totalReceived;
        this.totalSent = totalSent;
        this.finalBalance = finalBalance;
    }

    public MultiAddressBalance (JsonObject json) {
        this(
                json.has("n_tx") ? json.get("n_tx").getAsInt() : 0,
                json.has("n_tx_filtered") ? json.get("n_tx").getAsInt() : 0,
                json.has("total_received") ? json.get("total_received").getAsLong() : 0,
                json.has("total_sent") ? json.get("total_sent").getAsLong() : 0,
                json.has("final_balance") ? json.get("final_balance").getAsLong() : 0
        );
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

    public void setFinalBalance(long final_balance) {
        this.finalBalance = final_balance;
    }

    public void setTotalReceived(long total_received) {
        this.totalReceived = total_received;
    }

    public void setTotalSent(long total_sent) {
        this.totalSent = total_sent;
    }

    public int getTxCountFiltered () {
        return txCountFiltered;
    }

    public void setTxCountFiltered (int txCountFiltered) {
        this.txCountFiltered = txCountFiltered;
    }

}
