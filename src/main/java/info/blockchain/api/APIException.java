package info.blockchain.api;

public class APIException extends Exception
{
	private static final long serialVersionUID = -7731961787745059713L;
	
	public APIException(String message)
	{
		super(message);
	}
}
