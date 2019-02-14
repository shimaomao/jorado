package com.jorado.httpclient;

/**
 * 轻量级http请求客户端封装
 */
public class HttpClient implements Cloneable, Call.Factory {

    private final Dispatcher dispatcher;
    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;

    public HttpClient() {
        this(new Builder());
    }

    HttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
    }

    @Override
    public Call newCall(HttpRequest request) {
        return RealCall.newRealCall(this, request);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        Dispatcher dispatcher;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;

        public Builder() {
            dispatcher = new Dispatcher();
            connectTimeout = 2000;
            readTimeout = 5000;
            writeTimeout = 2000;
        }

        public Builder(HttpClient httpClient) {
            this.dispatcher = httpClient.dispatcher;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }
    }
}
