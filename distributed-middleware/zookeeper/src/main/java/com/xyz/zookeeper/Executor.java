package com.xyz.zookeeper;


/**
 * Created by Lenovo on 2018/4/26.
 */
public class Executor {


    public static void main(String[] args) {
        ZookeeperClient zookeeperClient = ZookeeperClient.getZkInstance();
        zookeeperClient.watchChanged("/gaoxugang");
        while (true){
            zookeeperClient.watchChanged("/gaoxugang");
        }
    }

}
