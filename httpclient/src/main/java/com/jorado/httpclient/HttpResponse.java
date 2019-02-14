package com.jorado.httpclient;

/**
 * http输出结果包装
 */
public class HttpResponse {

    private int statusCode;
    private String queryString;
    private String contentType;
    private String body;


    public HttpResponse() {

    }

    public HttpResponse(int statusCode, String queryString, String contentType, String body) {
        this.statusCode = statusCode;
        this.queryString = queryString;
        this.contentType = contentType;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
