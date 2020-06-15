package com.example.democurator.infrastructure.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkClient {

    private final String zookeeperConnectionString;
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3) ;
    private CuratorFramework client;

    public ZkClient(String zookeeperConnectionString){
        this.zookeeperConnectionString = zookeeperConnectionString;
    }

    public boolean init(){
        this.client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        return true;
    }

    public void start(){
        client.start();
    }

    public CuratorFramework getClient(){
        return client;
    }
}
