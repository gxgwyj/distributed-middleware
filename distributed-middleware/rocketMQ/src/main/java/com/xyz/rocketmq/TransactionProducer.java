package com.xyz.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 事务消息，通过rocketMq事务消息，保证分布式环境中事务的最终一致性
 * 以原子的方式执行本地事务和消息的发送，事务执行成功，消息发送成功，事务执行失败，消息发送失败来达到最终一直信
 *
 * 事务性消息有三种状态：
 * （1）TransactionStatus.CommitTransaction：提交事务，这意味着允许消费者使用此消息。
 * （2）TransactionStatus.RollbackTransaction：回滚事务，表示该消息将被删除而不允许使用。
 * （3）TransactionStatus.Unknown：中间状态，表示需要MQ检查以确定状态。
 */
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException {
        TransactionListener listener = new TransactionListenerImpl();
        // 事务性消息发送者
        TransactionMQProducer producer = new TransactionMQProducer("TransactionProducer_group");

        // 创建一个线程池
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        // 设置发送者的线程池
        producer.setExecutorService(executorService);
        producer.setTransactionListener(listener);
        producer.start();

        // 构建发送消息
        Message msg =
                new Message("TransactionProducerTopic", "TransactionProducerTag",
                        "payMsg".getBytes(RemotingHelper.DEFAULT_CHARSET));

        producer.sendMessageInTransaction(msg, null);
    }
}
