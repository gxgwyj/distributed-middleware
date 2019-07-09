package com.xyz.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步向中间件发送消息
 */
public class SyncProducer {
    /**
     * rocketmq 中间件地址
     */
    public static final String NAME_SRV_ADDR = "192.168.202.128:9876";


    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer");
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.start();

        for (int i = 0; i < 100; i++) {
            Message msg = new Message();
            // 要使得客户段能够发送任意的topic 需要启动broker使添加 autoCreateTopicEnable=true 参数，如下：
            // nohup sh mqbroker -n localhost:9876 autoCreateTopicEnable=true &
            msg.setTopic("Topic_gxg");
            msg.setTags("Tag_gxg");
            msg.setBody(("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n",sendResult);
        }
        producer.shutdown();
    }
}
