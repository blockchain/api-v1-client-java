package info.blockchain.api.wallet;

/**
 * Used in combination with the `Wallet` class
 *
 */
public class Address
{
	private long balance;
	private String address;
	private String label;
	private long totalReceived;
	
	public Address(long balance, String address, String label, long totalReceived)
	{
		this.balance = balance;
		this.address = address;
		this.label = label;
		this.totalReceived = totalReceived;
	}

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Address) {
            Address that = (Address) o;
            return (this.getAddress().equals(that.getAddress()) && this.getLabel().equals(that.getLabel()) && this.totalReceived == that.totalReceived &&
                this.balance == that.balance);
        }
        return false;
    }
	
	/**
	 * @return Balance in satoshi
	 */
	public long getBalance()
	{
		return balance;
	}
	
	/**
	 * @return String representation of the address
	 */
	public String getAddress()
	{
		return address;
	}
	
	/**
	 * @return Label attached to the address
	 */
	public String getLabel()
	{
		return label;
	}
	
	/**
	 * @return Total received amount in satoshi
	 */
	public long getTotalReceived()
	{
		return totalReceived;
	}
}
