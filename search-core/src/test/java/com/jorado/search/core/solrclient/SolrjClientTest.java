package com.jorado.search.core.solrclient;

import org.junit.Test;

public class SolrjClientTest {

    @Test
    public void pop() {

        SolrjClientPool clientPool = new SolrjClientPool();
        SolrjClient client = clientPool.getClient("localhost:8983", false);

        SolrjClient client1 = clientPool.getClient("localhost:8983", false);

        System.out.println(client);
        System.out.println(client1);
    }

    @Test
    public void clientBuilder() {
        SolrjClient solrjClient = SolrjClient.newClient("localhost:8983,localhost:8983");
        SolrjClient solrjClient1 = solrjClient;
        System.out.println(solrjClient1);
    }

}