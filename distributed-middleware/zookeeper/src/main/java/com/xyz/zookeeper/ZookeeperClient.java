package com.xyz.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * 类: ZookeeperClient <br>
 * 描述: zookeeper客户端<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年04月28日 17:57
 */
public class ZookeeperClient {

    public static final String CONNECT_INFO = "192.168.202.128:2181,192.168.202.129:2181,192.168.202.130:2181";      //家里的zookeeper地址
    public static final int TIME_OUT = 5000;
    public static String CHAR_SET = "UTF-8";
    private ZooKeeper zooKeeper;
    private ZookeeperWatcher zookeeperWatcher = new ZookeeperWatcher();

    /**
     * 构造方法私有化
     */
    private ZookeeperClient(){
        zookeeperInit();
    }

    private void zookeeperInit(){
        try {
            zooKeeper = new ZooKeeper(CONNECT_INFO,TIME_OUT,zookeeperWatcher);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ZookeeperClientHolder{
        private static ZookeeperClient zookeeperClient = new ZookeeperClient();
    }

    public static ZookeeperClient getZkInstance(){
        return ZookeeperClientHolder.zookeeperClient;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    /**
     * 对节点添加监听
     * @param path
     */
    public void watchChanged(String path){
        try {
            zooKeeper.getChildren(path,zookeeperWatcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建持久的节点
     */
    public String createPersistentNode(String path,String context) throws Exception{
        return zooKeeper.create(path, context.getBytes(CHAR_SET), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
    }

    /**
     * 创建顺序化的持久节点
     * @param path
     * @param context
     */
    public String createPersistentSequentialNode(String path,String context) throws Exception {
        return zooKeeper.create(path, context.getBytes(CHAR_SET), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    /**
     * 创建临时节点
     * @param path
     * @param context
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createEphemeralNode(String path,String context) throws Exception {
        return zooKeeper.create(path, context.getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
    }

    /**
     * 创建临时顺序节点
     * @param path
     * @param context
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createEphemeralSequentialNode(String path,String context) throws Exception {
        return zooKeeper.create(path, context.getBytes(CHAR_SET), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * 删除节点
     * @param path
     */
    public void deleteNode(String path,int... version) throws Exception {
        if (version.length==0){
            zooKeeper.delete(path,-1);
        }else{
            for (int i =0;i<version.length;i++){
                zooKeeper.delete(path, version[i]);
            }
        }
    }

    /**
     * 更新节点数据
     * @param path
     * @param context
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void updateNodeData(String path,String context) throws Exception {
        zooKeeper.setData(path,context.getBytes(CHAR_SET),-1);
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     * @throws Exception
     */
    public String getNodeData(String path) throws Exception{
       return new String(zooKeeper.getData(path,false,null));
    }

    /**
     * zookeeper 内置ACL Schemes
     * （1）word:anyone任何人都有访问权限
     * （2）auth 只要通过auth的user都有权限
     * （3）digest 使用用户名/密码
     * （4）使用ip地址段的方式验证权限
     */
    public void addDigestAuth(String user,String pwd) throws Exception{
        String info = MessageFormat.format("{0}:{1}",user,pwd);
        zooKeeper.addAuthInfo("digest",info.getBytes(CHAR_SET));
    }

}
