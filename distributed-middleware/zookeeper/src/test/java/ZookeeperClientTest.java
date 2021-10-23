import com.xyz.zookeeper.ZookeeperClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

/**
 * Created by Lenovo on 2018/5/5.
 */
public class ZookeeperClientTest {

    private ZookeeperClient zkCient = ZookeeperClient.getZkInstance();

    /**
     * 创建持久节点
     * @throws Exception
     */
    @Test
    public void createPersistentNodeTest() throws Exception {
        zkCient.addDigestAuth("foo","true");
        String path = "/gaoxugang";
        String context = "高旭刚";
        String result = zkCient.createPersistentNode(path,context);
        System.out.println(result);
        while (true){

        }
    }

    /**
     * 获取节点的数据
     * @throws Exception
     */
    @Test
    public void getNodeDataTest() throws Exception{
        String path = "/gaoxugang";
        String result = zkCient.getNodeData(path);
        System.out.println(result);
    }

    /**
     * 删除节点
     * @throws Exception
     */
    @Test
    public void deleteNodeTest() throws Exception {
        String path = "/gaoxugang";
        zkCient.deleteNode(path);
    }

    /**
     * 更新数据节点
     * @throws Exception
     */
    @Test
    public void updateNodeDataTest() throws Exception {
        String path = "/gaoxugang";
        zkCient.updateNodeData(path, "张三");
    }

    /**
     * 权限验证
     * @throws Exception
     */
    @Test
    public void addDigestAuthTest() throws Exception{
        String user = "root";
        String pwd = "root";
        zkCient.addDigestAuth(user, pwd);
    }

    /**
     * 权限验证示例
     * @throws Exception
     */
    @Test
    public void authSample() throws Exception{
        String path = "/zk_test";
        ZooKeeper zooKeeper = new ZooKeeper(ZookeeperClient.CONNECT_INFO,ZookeeperClient.TIME_OUT,null);
        zooKeeper.addAuthInfo("digest","foo:true".getBytes("utf-8"));
        zooKeeper.create(path,"init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zooKeeper1 = new ZooKeeper(ZookeeperClient.CONNECT_INFO,ZookeeperClient.TIME_OUT,null);
        zooKeeper1.getData(path,false,null);


    }




}
