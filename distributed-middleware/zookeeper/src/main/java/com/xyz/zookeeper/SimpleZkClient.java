package com.xyz.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author gaoxugang
 * @data 2021/10/23 17:53
 * @description
 */
public class SimpleZkClient {

    private ZooKeeper zooKeeper = null;


    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(ZookeeperClient.CONNECT_INFO, ZookeeperClient.TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    zooKeeper.getChildren("/", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("watchedEvent:" + watchedEvent.getType());
            }
        });
    }

    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        /**
         * 1、节点路径
         * 2、节点内容
         * 3、节点权限
         * 4、节点类型 临时、持久等等
         */
        String result = zooKeeper.create("/SimpleZkClient", "SimpleZkClient".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(result);
    }

    @Test
    public void testGetChildren() throws KeeperException, InterruptedException {

        /**
         * 打印根节点下所有的子节点
         */
        List<String> childrenList = zooKeeper.getChildren("/", true);
        for (String children : childrenList) {
            System.out.println(children);
        }

        Thread.sleep(Integer.MAX_VALUE);

    }
}
