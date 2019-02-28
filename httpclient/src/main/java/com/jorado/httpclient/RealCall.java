package com.jorado.httpclient;

import com.jorado.logger.EventClient;
import com.jorado.logger.exception.CoreException;
import com.jorado.logger.util.JsonUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * http请求实现
 */
public class RealCall implements Call {

    private HttpClient client;
    private final HttpRequest httpRequest;
    private boolean executed;

    private RealCall(HttpClient client, HttpRequest httpRequest) {
        this.client = client;
        this.httpRequest = httpRequest;
    }

    static RealCall newRealCall(HttpClient client, HttpRequest request) {
        return new RealCall(client, request);
    }

    @Override
    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    @Override
    public <T> Result<T> request(Class<T> type) {

        Result<HttpResponse> result = request();
        if (result.getCode() > 200) {
            return new Result<>(result.getCode(), result.getMessage());
        }
        T t = convert(result.getData(), type);

        return new Result<>(t);
    }

    @Override
    public Result<HttpResponse> request() {

        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("Already executed");
            }
            executed = true;
        }

        try {

            client.getDispatcher().executed(this);

            HttpResponse response = getResponse();

            if (response.getStatusCode() != 200) {
                throw new CoreException(String.format("Http status code=[%d]", response.getStatusCode()));
            }
            return new Result<>(response);

        } catch (TimeoutException e) {
            EventClient.getDefault().createException("Http request timeout", e).addData("httpRequest", httpRequest).asyncSubmit();
            return new Result<>(ResultStatus.ERROR, "Http request timeout");
        } catch (Exception e) {
            EventClient.getDefault().createException("Http request error", e).addData("httpRequest", httpRequest).asyncSubmit();
            return new Result<>(ResultStatus.ERROR, e.getMessage());
        } finally {
            client.getDispatcher().finished(this);
        }
    }

    @Override
    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("Already executed");
            }
            executed = true;
        }
        client.getDispatcher().enqueue(new AsyncCall(responseCallback));
    }

    @Override
    public boolean isExecuted() {
        return executed;
    }

    @Override
    public Call clone() {
        return RealCall.newRealCall(client, httpRequest);
    }

    private HttpResponse getResponse() throws Exception {

        int statusCode = 500;
        String queryString;
        String responseBody = "";
        NameValuePair contentTypeHeader = null;

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse httpResponse = null;

        try {

            httpclient = HttpClients.createDefault();

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(client.getReadTimeout())
                    .setConnectTimeout(client.getConnectTimeout())
                    .build();

            StringBuilder urlBuilder = new StringBuilder(httpRequest.getUrl());
            //拼接url
            if (!httpRequest.getGetParams().isEmpty()) {
                if (httpRequest.getUrl().lastIndexOf('?') < 0) {
                    urlBuilder.append("?");
                }

                urlBuilder.append("&");

                int index = 0;
                for (String key : httpRequest.getGetParams().keySet()) {
                    if (index > 0) {
                        urlBuilder.append("&");
                    }
                    urlBuilder.append(String.format("%s=%s", key, URLEncoder.encode(httpRequest.getGetParams().get(key), "utf-8")));
                    index++;
                }
            }

            queryString = urlBuilder.toString();

            if (httpRequest.getMethod() == HttpMethod.GET) {
                HttpGet httpGet = new HttpGet(queryString);

                httpGet.setConfig(requestConfig);
                httpResponse = httpclient.execute(httpGet);
                int scode = httpResponse.getStatusLine().getStatusCode();
                if (scode == HttpStatus.SC_OK) {
                    HttpEntity entity = httpResponse.getEntity();
                    responseBody = EntityUtils.toString(entity);
                    EntityUtils.consume(entity);
                    contentTypeHeader = httpResponse.getFirstHeader("Content-Type");
                }
                statusCode = httpResponse.getStatusLine().getStatusCode();

            } else if (httpRequest.getMethod() == HttpMethod.POST) {
                List<NameValuePair> nvps = new ArrayList<>();
                for (String key : httpRequest.getPostParams().keySet()) {
                    nvps.add(new BasicNameValuePair(key, httpRequest.getPostParams().get(key)));
                }
                HttpPost httpPost = new HttpPost(urlBuilder.toString());
                httpPost.setConfig(requestConfig);
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                httpResponse = httpclient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = httpResponse.getEntity();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    responseBody = sb.toString();
                    contentTypeHeader = httpResponse.getFirstHeader("Content-Type");
                }

                statusCode = httpResponse.getStatusLine().getStatusCode();
            }

        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpclient != null) {
                httpclient.close();
            }
        }

        return new HttpResponse(statusCode, queryString, contentTypeHeader == null ? "" : contentTypeHeader.getValue(), responseBody);
    }

    private <T> T convert(HttpResponse response, Class<T> t) {
        String body = response.getBody();

        //确认输出类型
        MediaType mediaType = MediaType.parse(response.getContentType());

        //如果没有识别出来用用户自定义的
        if (mediaType == MediaType.TEXT) {
            mediaType = httpRequest.getMediaType();
        }

        if (mediaType == MediaType.JSON) {
            return JsonUtils.fromJson(response.getBody(), t);
        }

        if (mediaType == MediaType.XML) {
            ByteArrayInputStream in = new ByteArrayInputStream(body.getBytes());
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(in));
            decoder.close();
            return (T) decoder.readObject();
        }

        return (T) body;

    }

    final class AsyncCall extends NamedRunnable {
        private final Callback responseCallback;

        AsyncCall(Callback responseCallback) {
            super("Http Request %s", httpRequest.getUrl());
            this.responseCallback = responseCallback;
        }

        String getHost() {
            return httpRequest.getHost();
        }

        HttpRequest getHttpRequest() {
            return httpRequest;
        }

        RealCall get() {
            return RealCall.this;
        }

        @Override
        protected void execute() {
            try {

                HttpResponse response = getResponse();

                if (response.getStatusCode() != 200) {
                    throw new RuntimeException(String.format("Http status code=[%d]", response.getStatusCode()));
                }
                responseCallback.onResponse(RealCall.this, response);

            } catch (Exception e) {

                responseCallback.onFailure(RealCall.this, e);

            } finally {
                client.getDispatcher().finished(this);
            }
        }
    }
}
