package com.r00ta.telematics.platform.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHelper {

    private static final int maxRetries = 10;
    private static final CloseableHttpClient httpclient = createHttpClient();

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

    private ObjectMapper mapper = new ObjectMapper();

    private String baseHost;

    public HttpHelper(String baseHost) {
        this.baseHost = baseHost;
    }

    public String doGet(String path) {
        HttpGet request = new HttpGet(baseHost + path);
        HttpResponse response = null;
        try {
            response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                LOGGER.debug("Get request returned " + result);
                return result;
            }
        } catch (IOException e) {
            LOGGER.error("IO Exception", e);
            e.printStackTrace();
        }
        return null;
    }

    public String doPost(String path, String params) {

        HttpPost post = new HttpPost(baseHost + path);
        LOGGER.debug("Going to post to: " + path + "\n with: " + params);
        try {
            post.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            LOGGER.debug("I've got " + result);
            return result;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Encoding exception", e);
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            LOGGER.error("Client protocol exception", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("IO exception", e);
            e.printStackTrace();
        }
        return null;
    }

    private static CloseableHttpClient createHttpClient() {

        return HttpClientBuilder.create().setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                return executionCount <= maxRetries;
            }
        }).setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {
            int waitPeriod = 100;

            @Override
            public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
                waitPeriod *= 2;
                return executionCount <= maxRetries &&
                        response.getStatusLine().getStatusCode() >= 500; //important!
            }

            @Override
            public long getRetryInterval() {
                return waitPeriod;
            }
        })
                .build();
    }
}
