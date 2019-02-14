package com.jorado.ik.util;

import com.jorado.ik.Logger;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static String post(String url, Map<String, String> data) {
        return post(url, data, 20000);
    }

    public static String post(String url, Map<String, String> data, int timeout) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = null;
        List<NameValuePair> nvps = new ArrayList<>();
        for (String key : data.keySet()) {
            nvps.add(new BasicNameValuePair(key, data.get(key)));
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(entity.getContent()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return sb.toString();
            }
        } catch (Exception e) {
            Logger.error("http post error,url=" + url, e);
        } finally {
            try {
                if (response != null)
                    response.close();
                if (httpclient != null)
                    httpclient.close();
            } catch (Exception e) {
                Logger.error("http post error,url=" + url, e);
            }
        }
        return null;
    }

    public static String get(String url) {
        return get(url, 2000);
    }

    public static String get(String url, int timeout) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            httpGet.setConfig(requestConfig);
            response = httpclient.execute(httpGet);
            int scode = response.getStatusLine().getStatusCode();
            if (scode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            Logger.error("http get error,url=" + url, e);
        } finally {
            try {
                if (response != null)
                    response.close();
                if (httpclient != null)
                    httpclient.close();
            } catch (Exception e) {
                Logger.error("http get error,url=" + url, e);
            }
        }
        return null;
    }
}
