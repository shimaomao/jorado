package com.jorado.logger.dubbo.data;

public class ProviderInfo extends DubboInfo {
    private String clientIP;

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }
}
