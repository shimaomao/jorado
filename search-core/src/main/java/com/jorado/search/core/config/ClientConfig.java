package com.jorado.search.core.config;

import com.jorado.logger.util.JsonUtils;
import com.jorado.zookeeper.LoadConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端配置
 */
public class ClientConfig {

    private static LoadConfig remoteConfig = LoadConfig.newInstance("client", () -> {
        adjust();
        return null;
    });

    private static volatile ClientConfig config;

    private int verifyClient;

    private int maxStart;

    private int maxRows;

    private String field;

    public static ClientConfig getInstance() {
        if (null == config) {
            adjust();
        }
        return config;
    }

    private static void adjust() {
        config = JsonUtils.fromJson(remoteConfig.getBody(), ClientConfig.class);
        clientMap.clear();
        for (Client client : clientList) {
            clientMap.put(client.getName().toLowerCase(), client);
        }
    }

    public int getMaxStart() {
        return maxStart;
    }

    public void setMaxStart(int maxStart) {
        this.maxStart = maxStart;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    private static List<Client> clientList = new ArrayList<>();

    public List<Client> getClients() {
        return clientList;
    }

    public void setClients(List<Client> clients) {
        clientList = clients;
    }

    private static Map<String, Client> clientMap = new HashMap<>();

    public Client getClient(String clientName) {
        return clientMap.get(clientName.toLowerCase());
    }

    public int getVerifyClient() {
        return verifyClient;
    }

    public void setVerifyClient(int verifyClient) {
        this.verifyClient = verifyClient;
    }

    public boolean isVerifyClient() {
        return verifyClient == 1;
    }
}
