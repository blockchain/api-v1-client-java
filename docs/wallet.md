## `info.blockchain.api.wallet` package

The `wallet` package contains the `Wallet` class that reflects the functionality documented at at https://blockchain.info/api/blockchain_wallet_api. It allows users to directly interact with their existing Blockchain.info wallet, send funds, manage addresses etc.

Example usage:

```java

package test;
import info.blockchain.api.wallet.*;

public class App
{
    public static void main(String[] args) throws Exception
    {
    
        CreateWalletResponse wallet = Wallet.create(
        	    "http://localhost:3000/",
        	    "YOUR_SUPER_SECURE_PASSWORD",
        	    "YOUR_API_CODE");
    
        System.out.println(wallet.getIdentifier());
        	
    	Wallet wallet = new Wallet(
    	        "http://localhost:3000/",
    	        "YOU_API_CODE",
    	        "YOUR_GUID",
    	        "YOUR_SUPER_SECURE_PASSWORD");

    	// get an address from your wallet and include only transactions with up to 3
    	// confirmations in the balance
    	Address addr = wallet.getAddress("1JzSZFs2DQke2B3S4pBxaNaMzzVZaG4Cqh", 3);
    	System.out.println(String.format("The balance is %s", addr.getBalance()));

    	// send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
    	PaymentResponse payment = wallet.send("1dice6YgEVBf88erBFra9BHf6ZMoyvG88", 20000000L, null, 1000000L);
    	System.out.println(String.format("The payment TX hash is %s", payment.getTxHash()));

    	long totalBalance = wallet.getBalance();
    	System.out.println(String.format("The total wallet balance is %s", totalBalance));

    	// list all addresses and their balances 
    	List<Address> addresses = wallet.listAddresses();
    	for (Address a : addresses)
    	{
    		System.out.println(String.format("The address %s has a balance of %s satoshi",
    				a.getAddress(), a.getBalance()));
    	}

    	// archive an old address
    	wallet.archiveAddress("1JzSZFs2DQke2B3S4pBxaNaMzzVZaG4Cqh");

    	// create a new address and attach a label to it
    	Address newAddr = wallet.newAddress("test label 123");
    	System.out.println(String.format("The new address is %s", newAddr.getAddress()));
    }
}

```