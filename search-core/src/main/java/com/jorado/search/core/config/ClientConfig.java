package com.jorado.search.core.config;

import com.jorado.zkconfig.ZKPSettings;
import com.jorado.zkconfig.ConfigFactory;
import com.jorado.zkconfig.ZKPConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端配置
 */
public class ClientConfig extends ZKPConfig {

    final static String ZKP_PATH = ZKPSettings.ZOOKEEPER_PATH + "/" + ClientConfig.class.getName();

    static ClientConfig settings;

    public synchronized static ClientConfig getInstance() {
        if (settings == null) {
            settings = ConfigFactory.get(ZKP_PATH);
        }
        return settings;
    }

    private int verifyClient;

    private int maxStart;

    private int maxRows;

    private String field;

    @Override
    public void adjust() {
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
