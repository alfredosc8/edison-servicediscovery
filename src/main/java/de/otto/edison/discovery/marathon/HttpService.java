package de.otto.edison.discovery.marathon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import de.otto.edison.annotations.Beta;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Beta
class HttpService {

    private static final long HTTP_CLIENT_TIMEOUT_IN_MS = 1000;

    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Response getJson(final String username,
                            final String password,
                            final String url) throws ExecutionException, InterruptedException, IOException, TimeoutException {
        final AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
                .prepareGet(url);

        if (null != username && null != password && !username.isEmpty() && !password.isEmpty()) {
            requestBuilder.setHeader("Authorization", encodeAuth(username, password));
        }

        return requestBuilder
                .setHeader("Accept", "application/json")
                .execute()
                .get(HTTP_CLIENT_TIMEOUT_IN_MS, MILLISECONDS);
    }

    public Response getJson(final String url) throws ExecutionException, InterruptedException, IOException, TimeoutException {
        return getJson(null, null, url);
    }

    public Response putJson(final String username,
                            final String password,
                            final String url,
                            final Map<String, Object> body) throws ExecutionException, InterruptedException, IOException, TimeoutException {
        return asyncHttpClient
                .preparePut(url)
                .setHeader("Authorization", encodeAuth(username, password))
                .setBody(objectMapper.writeValueAsString(body))
                .setHeader("Accept", "application/json")
                .execute()
                .get(HTTP_CLIENT_TIMEOUT_IN_MS, MILLISECONDS);
    }

    private String encodeAuth(final String username, final String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ':' + password).getBytes(UTF_8));
    }
}
