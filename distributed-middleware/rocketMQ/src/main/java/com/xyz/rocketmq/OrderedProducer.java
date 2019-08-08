package com.xyz.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * 有序消息
 */
public class OrderedProducer {
    public static void main(String[] args) throws Exception {
        MQProducer producer = new DefaultMQProducer("OrderedProducer_group");
        ((DefaultMQProducer) producer).setNamesrvAddr(MqConstants.NAME_SRV_ADDR);
        producer.start();
        // 定义5个tag
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            // 构造消息
            Message msg = new Message("OrderedProducerTag", tags[i % tags.length], "KEY" + i, ("hello " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            },orderId);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
