package com.jorado.httpclient;

import com.jorado.core.Result;

/**
 * 请求接口
 */
public interface Call extends Cloneable {

    HttpRequest getHttpRequest();

    <T> Result<T> request(Class<T> t);

    Result<HttpResponse> request();

    void enqueue(Callback responseCallback);

    boolean isExecuted();

    Call clone();

    interface Factory {
        Call newCall(HttpRequest request);
    }
}
