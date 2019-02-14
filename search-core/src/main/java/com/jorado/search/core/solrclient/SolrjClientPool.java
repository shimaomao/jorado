package com.jorado.search.core.solrclient;

import com.jorado.logger.EventClient;
import com.jorado.logger.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Solr连接池
 */
public final class SolrjClientPool implements Closeable {

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * solr client 连接池数量
     */
    private int capacity;

    /**
     * solr client连接池
     */
    private volatile Map<Integer, SolrjClient> clientPool;

    public SolrjClientPool() {
        this(DEFAULT_CAPACITY);
    }

    public SolrjClientPool(int initialCapacity) {
        this.capacity = initialCapacity;
        this.clientPool = new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * 获取solr客户端
     *
     * @param isFromPool 如果为false 则创建新的客户端，否则初始化连接池并从连接池随机获取一个
     * @return
     */
    public SolrjClient getClient(boolean isFromPool, List<String> host, boolean isCloud) {
        if (isFromPool) {
            this.initPool(host, isCloud);
            return this.clientPool.get(ThreadLocalRandom.current().nextInt(this.getPoolSize()));
        } else {
            this.destroyPool();
            return newClient(host, isCloud);
        }
    }

    /**
     * 获取solr客户端
     *
     * @param isFromPool 如果为false 则创建新的客户端，否则初始化连接池并从连接池随机获取一个
     * @return
     */
    public SolrjClient getClient(boolean isFromPool, String host, boolean isCloud) {
        return this.getClient(isFromPool, StringUtils.split(host, ","), isCloud);
    }

    /**
     * 从连接池获取solr客户端
     *
     * @return
     */
    public SolrjClient getClient(List<String> host, boolean isCloud) {
        return getClient(true, host, isCloud);
    }

    /**
     * 从连接池获取solr客户端
     *
     * @return
     */
    public SolrjClient getClient(String host, boolean isCloud) {
        return this.getClient(StringUtils.split(host, ","), isCloud);
    }

    /**
     * 返回连接池容量
     *
     * @return
     */
    public int getPoolSize() {
        return this.clientPool.size();
    }

    /**
     * 销毁连接池中所有连接
     *
     * @throws IOException
     */
    @Override
    public void close() {
        this.destroyPool();
    }

    public static SolrjClient newClient(List<String> host, boolean isCloud) {
        if (isCloud) {
            return new SolrjClient(host);
        } else {
            return new SolrjClient(host.get(ThreadLocalRandom.current().nextInt(host.size())));
        }
    }

    public static SolrjClient newClient(String host, boolean isCloud) {
        return newClient(StringUtils.split(host, ","), isCloud);
    }

    private boolean isReady() {
        return this.getPoolSize() == this.capacity;
    }

    /**
     * 初始化连接池
     *
     * @param host
     * @param isCloud
     */
    private void initPool(List<String> host, boolean isCloud) {

        if (this.isReady()) {
            return;
        }

        for (int i = 0, j = this.capacity; i < j; i++) {
            SolrjClient solrjClient = newClient(host, isCloud);
            solrjClient.setReusable();
            this.clientPool.put(i, solrjClient);
        }
    }

    /**
     * 销毁连接池
     */
    private void destroyPool() {
        try {
            for (Map.Entry<Integer, SolrjClient> entry : this.clientPool.entrySet()) {
                entry.getValue().close();
            }
        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException("Destroy solrj client pool error", ex);
        } finally {
            this.clientPool.clear();
        }
    }
}
