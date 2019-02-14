package com.jorado.httpclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http请求简单封装
 */
public final class HttpRequest {

    private final MediaType mediaType;
    private final URI uri;
    private final String url;
    private final HttpMethod method;
    private final Map<String, String> getParams;
    private final Map<String, String> postParams;

    HttpRequest(Builder builder) {
        this.mediaType = builder.mediaType;
        this.uri = builder.uri;
        this.url = builder.url;
        this.method = builder.method;
        this.getParams = builder.getParams;
        this.postParams = builder.postParams;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public URI getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public String getHost() {
        return uri.getHost();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getGetParams() {
        return getParams;
    }

    public Map<String, String> getPostParams() {
        return postParams;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method=" + method +
                ", getParams=" + getParams +
                ", postParams=" + postParams +
                '}';
    }

    public static class Builder {

        private MediaType mediaType;
        private String url;
        private URI uri;
        private HttpMethod method;
        private Map<String, String> getParams;
        private Map<String, String> postParams;

        public Builder() {
            this.mediaType = MediaType.TEXT;
            this.method = HttpMethod.GET;
            this.getParams = new ConcurrentHashMap<>();
            this.postParams = new ConcurrentHashMap<>();
        }

        public Builder(String url) {
            this();
            this.url = url;
        }

        Builder(HttpRequest request) {
            this.mediaType = request.mediaType;
            this.uri = request.getUri();
            this.url = request.url;
            this.method = request.getMethod();
            this.getParams = request.getGetParams();
            this.postParams = request.getPostParams();
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder addParam(String name, String value) {
            this.getParams.put(name, value);
            return this;
        }

        public Builder removeParam(String name) {
            this.getParams.remove(name);
            return this;
        }

        public Builder addPostParam(String name, String value) {
            this.postParams.put(name, value);
            return this;
        }

        public Builder removePostParam(String name) {
            this.postParams.remove(name);
            return this;
        }

        public Builder get() {
            this.method = HttpMethod.GET;
            return this;
        }

        public Builder post() {
            this.method = HttpMethod.POST;
            return this;
        }

        public Builder delete() {
            this.method = HttpMethod.DELETE;
            return this;
        }

        public Builder put() {
            this.method = HttpMethod.PUT;
            return this;
        }

        public Builder withJsonResponse() {
            this.mediaType = MediaType.JSON;
            return this;
        }

        public Builder withXmlResponse() {
            this.mediaType = MediaType.XML;
            return this;
        }

        public HttpRequest build() throws URISyntaxException {
            if (url == null) throw new IllegalArgumentException("url == null");
            this.uri = new URI(url);
            return new HttpRequest(this);
        }
    }
}
