package com.jorado.httpclient;

import com.jorado.logger.util.JsonUtils;
import com.jorado.core.Result;
import org.junit.Assert;
import org.junit.Test;

public class DefaultTest {

    /**
     * 异步请求
     *
     * @throws Exception
     */
    @Test
    public void asyncHttpClient() throws Exception {

        HttpClient httpClient = new HttpClient();

        HttpRequest request = new HttpRequest.Builder()
                .setUrl("https://www.baidu.com1/s")
                .addParam("wd", "java")
                .build();

        Call call = httpClient.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, HttpResponse result) {
                System.out.println(JsonUtils.toJson(result));
            }
        });

        //等待，否则无法看到控制台输出
        Thread.sleep(5000 * 10);
    }


    /**
     * 同步请求
     *
     * @throws Exception
     */
    @Test
    public void httpClient() throws Exception {

        HttpClient httpClient = new HttpClient();

        HttpRequest request = new HttpRequest.Builder("https://www.baidu.com/s")
                .addParam("wd", "java")
                .build();

        Call call = httpClient.newCall(request);
        Result<HttpResponse> result = call.request();
        System.out.println(JsonUtils.toJson(result));
        Result<HttpResponse> result1 = call.clone().request();


        System.out.println(JsonUtils.toJson(result1));
        if (result.getCode() == 200) {
            System.out.println(result.getData().getBody());
        }

        Assert.assertTrue(result.getCode() == 200);
    }
}