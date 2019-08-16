package com.xyz.rocketmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务监听
 */
public class TransactionListenerImpl implements TransactionListener {

    /**
     * 发送预消息成功时，执行本地事务，本地执行成功返回commit，本地事务失败返回rollback
     *
     * 消息预发送成功后会有回调操作，就是操作该方法
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 执行数据库操作
        return null;
    }

    /**【rocketMq如果扫描到未提交的事务消息，通过回调该方法，检查事务的最终状态】
     * 检查本地事务状态，并且相应MQ检查请求
     * MQ内部提供一个名为“事务状态服务”的服务，
     * 此服务会检查事务消息的状态，如果发现消息未COMMIT，
     * 则通过Producer启动时注册的TransactionCheckListener来回调业务系统，
     * 业务系统在checkLocalTransactionState方法中检查DB事务状态，
     * 如果成功，则回复COMMIT_MESSAGE，否则回复ROLLBACK_MESSAGE。
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        return null;
    }
}
