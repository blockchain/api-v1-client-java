package info.blockchain.api.blockexplorer;

import com.google.gson.JsonObject;

/**
 * Represents a transaction input. If the `previousOutput` object is null, this is a
 * coinbase input.
 */
public class Input {
    private Output previousOutput;
    private long sequence;
    private String scriptSignature;

    public Input (Output previousOutput, long sequence, String scriptSignature) {
        this.previousOutput = previousOutput;
        this.sequence = sequence;
        this.scriptSignature = scriptSignature;
    }

    public Input (JsonObject i) {
        if (i.has("prev_out")) {
            this.previousOutput = new Output(i.get("prev_out").getAsJsonObject(), true);
        }

        this.sequence = i.get("sequence").getAsLong();
        this.scriptSignature = i.get("script").getAsString();
    }

    /**
     * @return Previous output. If null, this is a coinbase input.
     */
    public Output getPreviousOutput () {
        return previousOutput;
    }

    /**
     * @return Sequence number of the input
     */
    public long getSequence () {
        return sequence;
    }

    /**
     * @return Script signature
     */
    public String getScriptSignature () {
        return scriptSignature;
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Input) {
            Input that = (Input) o;
            return (this.previousOutput.equals(that.previousOutput) && this.sequence == that.sequence && this.scriptSignature.equals(that.scriptSignature));
        }
        return false;
    }
}
