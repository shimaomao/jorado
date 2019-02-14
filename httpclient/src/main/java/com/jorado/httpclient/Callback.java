package com.jorado.httpclient;

/**
 * 异步回调接口
 */
public interface Callback {

    void onFailure(Call call, Exception e);

    void onResponse(Call call, HttpResponse result);
}
