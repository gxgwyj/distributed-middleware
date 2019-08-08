package com.xyz.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 异步发送mq消息（本质上是开一个线程池，使用多线程的方式去发送消息）
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer  producer = new DefaultMQProducer("asyncProducer_group");
        producer.setNamesrvAddr(MqConstants.NAME_SRV_ADDR);
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Message msg = new Message("AsyncProducerTopic",
                    "AsyncProducerTags",
                    "order111",
                    "order111".getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 消息发送
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });

        }

    }
}
