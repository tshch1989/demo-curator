package com.example.democurator;

import com.example.democurator.infrastructure.zookeeper.ZkClient;

import java.util.List;

public class TestMain {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183");
        zkClient.init();
        zkClient.start();
        String s = zkClient.getClient().create().orSetData().forPath("/test","hello".getBytes());
        System.out.println(s);
        byte[] bytes = zkClient.getClient().getData().forPath("/test");
        System.out.println(new String(bytes));
        List<String> childrens = zkClient.getClient().getChildren().forPath("/");
        System.out.println(childrens);
    }
}
