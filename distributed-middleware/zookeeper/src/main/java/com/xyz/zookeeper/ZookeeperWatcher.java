package com.xyz.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * 类: ZookeeperWatcher <br>
 * 描述: zookeeper 事件监听器<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年04月28日 17:55
 */
public class ZookeeperWatcher implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {

        System.out.println(watchedEvent.getPath()+":"+watchedEvent.getType());

    }
}
