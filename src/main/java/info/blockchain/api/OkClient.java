package info.blockchain.api;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the {@link HttpClientInterface} using OkHttp. For more information about
 * OkHttp visit http://square.github.io/okhttp/
 */
public class OkClient implements HttpClientInterface {

    public static final String URL_SCHEME = "https";
    public static final String URL_HOST = "blockchain.info";

    private static OkHttpClient okHttpClientInstance = null;
    private static OkClient okClientInstance = null;

    private OkClient() {
    }

    private static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            okHttpClientInstance = new OkHttpClient();
            okHttpClientInstance.setConnectTimeout(10000, TimeUnit.MILLISECONDS);
        }
        return okHttpClientInstance;
    }

    public static OkClient getOkClientInstance() {
        if (okClientInstance == null) {
            okClientInstance = new OkClient();
        }
        return okClientInstance;
    }


    @Override
    public String get(String resource, Map<String, String> params) throws APIException, IOException {

        HttpUrl.Builder url = getHttpUrlBuilder(resource);

        for (String paramName : params.keySet()) {
            url.addQueryParameter(paramName, params.get(paramName));
        }

        Request request = new Request.Builder()
                .url(url.build())
                .build();

        Response response = getOkHttpClientInstance().newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    @Override
    public String post(String resource, Map<String, String> params) throws APIException, IOException {

        HttpUrl.Builder url = getHttpUrlBuilder(resource);

        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (String paramName : params.keySet()) {
            formEncodingBuilder.addEncoded(paramName, params.get(paramName));
        }

        Request request = new Request.Builder()
                .url(url.build())
                .post(formEncodingBuilder.build())
                .build();

        Response response = getOkHttpClientInstance().newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();

    }

    private HttpUrl.Builder getHttpUrlBuilder(String resource) {
        HttpUrl.Builder url = new HttpUrl.Builder();
        url.scheme(URL_SCHEME)
                .host(URL_HOST)
                .addPathSegment(resource);
        return url;
    }
}
