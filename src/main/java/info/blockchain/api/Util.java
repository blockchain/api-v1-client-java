package info.blockchain.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

public class Util
{
	private static final String BASE_URL = "https://blockchain.info/";
	
	public static String get(String resource, Map<String, String> params)
			throws IOException, APIException
	{
		return openURL(resource, params, "GET");
	}

	public static String post(String resource, Map<String, String> params)
			throws IOException, APIException
	{
		return openURL(resource, params, "POST");
	}
	
	private static String openURL(String resource, Map<String, String> params,
			String requestMethod) throws IOException, APIException
	{
		String encodedParams = urlEncodeParams(params);
		URL url = null;
		
		if (requestMethod.equals("GET"))
			url = new URL(BASE_URL + resource + '?' + encodedParams); 
		else if (requestMethod.equals("POST"))
			url = new URL(BASE_URL + resource);
		
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		
		conn.setRequestMethod(requestMethod);
		if (requestMethod.equals("POST"))
		{
			byte[] postBytes = encodedParams.getBytes("UTF-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postBytes);
		}
        conn.setDoInput(true);
        
        if (conn.getResponseCode() != 200)
        {
        	throw new APIException(inputStreamToString(conn.getErrorStream()));
        }
        
        return inputStreamToString(conn.getInputStream());
	}
	
	private static String urlEncodeParams(Map<String, String> params)
	{
		String result = "";
		
		if (params != null && params.size() > 0)
		{
			try
			{
				StringBuilder postData = new StringBuilder();
				for (Entry<String, String> kvp : params.entrySet())
				{
					if (postData.length() > 0)
					{
						postData.append('&');
						postData.append(URLEncoder.encode(kvp.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(kvp.getValue(), "UTF-8"));
					}
				}
				result = postData.toString();
			}
			catch (UnsupportedEncodingException e) { }
		}
		
		return result;
	}
	
	private static String inputStreamToString(InputStream is) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        StringBuilder responseStringBuilder = new StringBuilder();
        String line = "";

        while ((line = reader.readLine()) != null)
        	responseStringBuilder.append(line);
        
		return responseStringBuilder.toString();
	}
}
