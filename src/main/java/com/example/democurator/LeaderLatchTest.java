package com.example.democurator;

import com.example.democurator.infrastructure.zookeeper.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import java.util.ArrayList;
import java.util.List;

public class LeaderLatchTest {
    public static void main(String[] args) {
        List<ZkClient> clients = new ArrayList<>();
        List<LeaderLatch> latches = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                ZkClient zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183");
                zkClient.init();
                zkClient.start();
                clients.add(zkClient);
                CuratorFramework client = zkClient.getClient();
                LeaderLatch leaderLatch = new LeaderLatch(client, "/demo/leader");
                DemoLeaderLatchListener demoLeaderLatchListener = new DemoLeaderLatchListener(i);
                leaderLatch.addListener(demoLeaderLatchListener);
                leaderLatch.start();
                latches.add(leaderLatch);
            }
            Thread.sleep(Integer.MAX_VALUE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            latches.forEach(f->{
                try{
                    f.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            clients.forEach(f->{
                try{
                    f.getClient().close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }



    }

    static class DemoLeaderLatchListener implements LeaderLatchListener{

        private Integer id;

        public DemoLeaderLatchListener(Integer id){
            this.id = id;
        }

        @Override
        public void isLeader() {
            System.out.println("isLeader... " + id);
        }

        @Override
        public void notLeader() {
            System.out.println("notLeader... " + id);
        }
    }
}
