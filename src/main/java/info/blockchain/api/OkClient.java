package info.blockchain.api;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the {@link HttpClientInterface} using OkHttp.
 * OkHttp handles Gzip, connection pooling, caching and retries out of the box.
 * The library also provides a set of fluent builders for creating requests and responses.
 * For more information about OkHttp visit http://square.github.io/okhttp/
 */
public class OkClient implements HttpClientInterface {

    public static final String URL_SCHEME = "https";
    public static final String URL_HOST = "blockchain.info";
    public static final int TIMEOUT_MS = 10000;

    private static OkHttpClient okHttpClientInstance = null;
    private static OkClient okClientInstance = null;

    /**
     * Private constructor to prevent instantiation.
     */
    private OkClient() {
        //prevent instantiation through reflection.
        if (okClientInstance != null) {
            throw new IllegalStateException("Already initialized.");
        }
    }

    /**
     * OkClient singleton
     */
    public synchronized static OkClient getOkClientInstance() {
        if (okClientInstance == null) {
            okClientInstance = new OkClient();
        }
        return okClientInstance;
    }

    /**
     * OkHttpClient singleton
     */
    private synchronized static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            okHttpClientInstance = new OkHttpClient();
            okHttpClientInstance.setConnectTimeout(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        }
        return okHttpClientInstance;
    }

    /**
     * This method makes a get request to a specific API resource.
     * Request is built using the OkHttp {@link HttpUrl} builder.
     *
     * @param baseURL  The API base URL
     * @param resource The API resource being requested.
     * @param params   A set of params sent to the resource
     * @return Returns a response as a {@link String}
     * @throws IOException Thrown if the request was unsuccessful.
     */
    @Override
    public String get(String baseURL, String resource, Map<String, String> params) throws IOException {
        HttpUrl.Builder url = getHttpUrlBuilder(baseURL, resource);

        for (String paramName : params.keySet()) {
            url.addEncodedQueryParameter(paramName, params.get(paramName));
        }

        return getInternal(url.build());
    }

    /**
     * This method makes a get request to a specific API resource.
     * Request is built using the OkHttp {@link HttpUrl} builder.
     *
     * @param resource The API resource being requested.
     * @param params   A set of params sent to the resource
     * @return Returns a response as a {@link String}
     * @throws IOException Thrown if the request was unsuccessful.
     */
    @Override
    public String get(String resource, Map<String, String> params) throws IOException {
        HttpUrl.Builder url = getHttpUrlBuilder(resource);

        for (String paramName : params.keySet()) {
            url.addEncodedQueryParameter(paramName, params.get(paramName));
        }

        return getInternal(url.build());
    }

    private String getInternal(HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return makeRequest(request);
    }

    /**
     * This method makes a post request to a specific API resource.
     * Request is built using the OkHttp {@link HttpUrl} builder.
     * Request body is built using the OkHttp {@link FormEncodingBuilder} builder .
     *
     * @param baseURL  The API base URL
     * @param resource The API resource being requested.
     * @param params   A set of params sent to the resource
     * @return Returns a response as a {@link String}
     * @throws IOException Thrown if the request was unsuccessful.
     */
    @Override
    public String post(String baseURL, String resource, Map<String, String> params) throws IOException {
          HttpUrl.Builder url = getHttpUrlBuilder(baseURL, resource);

          return postInternal(url.build(), params);
    }

    /**
     * This method makes a post request to a specific API resource.
     * Request is built using the OkHttp {@link HttpUrl} builder.
     * Request body is built using the OkHttp {@link FormEncodingBuilder} builder .
     *
     * @param resource The API resource being requested.
     * @param params   A set of params sent to the resource
     * @return Returns a response as a {@link String}
     * @throws IOException Thrown if the request was unsuccessful.
     */
    @Override
    public String post(String resource, Map<String, String> params) throws IOException {
        HttpUrl.Builder url = getHttpUrlBuilder(resource);

        return postInternal(url.build(), params);
    }

    private String postInternal(HttpUrl url, Map<String, String> params) throws IOException {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String paramName : params.keySet()) {
            formEncodingBuilder.addEncoded(paramName, params.get(paramName));
        }

        Request request = new Request.Builder()
                .url(url)
                .post(formEncodingBuilder.build())
                .build();

        return makeRequest(request);
  }

    private String makeRequest(Request request) throws IOException {
      Response response = getOkHttpClientInstance().newCall(request).execute();
      if (isNotSuccessfulResponse(response))
          throw new IOException(String.format("Unsuccessful call to %s Response: %s",
                  request.urlString(),
                  response));

      return response.body().string();
    }

    private HttpUrl.Builder getHttpUrlBuilder(String resource) {
        HttpUrl.Builder url = new HttpUrl.Builder();
        url.scheme(URL_SCHEME)
                .host(URL_HOST)
                .addPathSegment(resource);

        return url;
    }

    private HttpUrl.Builder getHttpUrlBuilder(String baseURL, String resource) throws MalformedURLException {
        HttpUrl url = HttpUrl.parse(baseURL);
        if (url == null) {
          throw new MalformedURLException();
        }

        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addPathSegment(resource);

        return urlBuilder;
    }

    private boolean isNotSuccessfulResponse(Response response) {
        return !response.isSuccessful();
    }
}
