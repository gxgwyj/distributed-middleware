package com.xyz.rocketmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务监听
 */
public class TransactionListenerImpl implements TransactionListener {

    /**
     * 发送预消息成功时，执行本地事务，本地执行成功返回commit，本地事务失败返回rollback
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 执行数据库操作
        return null;
    }

    /**
     * 检查本地事务状态，并且相应MQ检查请求
     *
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        return null;
    }
}
