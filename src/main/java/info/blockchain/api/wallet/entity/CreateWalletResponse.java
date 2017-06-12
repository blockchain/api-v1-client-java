package info.blockchain.api.wallet.entity;

/**
 * Used in response to the `create` method in the `CreateWallet` class.
 */
public class CreateWalletResponse {
    private String identifier;
    private String address;
    private String label;

    public CreateWalletResponse (String identifier, String address, String label) {
        this.identifier = identifier;
        this.address = address;
        this.label = label;
    }

    /**
     * @return Wallet identifier (GUID)
     */
    public String getIdentifier () {
        return identifier;
    }

    /**
     * @return First address in the wallet
     */
    public String getAddress () {
        return address;
    }

    /**
     * @return Label of first address in the wallet
     */
    public String getLable () {
        return label;
    }
}
