package com.cxp.kafkaconfig;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;


/**
 * @program: SpringProject
 * @description:
 * @author: cheng
 * @create: 2019-07-31 18:08
 */
public class KafkaProducerListener implements ProducerListener {

    protected final Logger LOG = LoggerFactory.getLogger(KafkaProducerListener.class);

    private static Logger MSG = LoggerFactory.getLogger("operation");

    /**
     * 发送消息成功后调用
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key,
                          Object value, RecordMetadata recordMetadata) {
        LOG.info("==========kafka发送数据成功（日志开始）==========");
        LOG.info("----------topic:"+topic);
        LOG.info("----------partition:"+partition);
        LOG.info("----------key:"+key);
        LOG.info("----------value:"+value);
        LOG.info("----------RecordMetadata:"+recordMetadata);
        LOG.info("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");

        MSG.info("send success topic:" + topic + ",value:" + value);
    }

    /**
     * 发送消息错误后调用
     */
    @Override
    public void onError(String topic, Integer partition, Object key,
                        Object value, Exception exception) {
        LOG.info("==========kafka发送数据错误（日志开始）==========");
        LOG.info("----------topic:"+topic);
        LOG.info("----------partition:"+partition);
        LOG.info("----------key:"+key);
        LOG.info("----------value:"+value);
        LOG.error("----------Exception:",exception);
        LOG.info("~~~~~~~~~~kafka发送数据错误（日志结束）~~~~~~~~~~");

        MSG.info("send error topic:" + topic + ",value:" + value);
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    @Override
    public boolean isInterestedInSuccess() {
        LOG.info("///kafkaProducer监听器启动///");
        return true;
    }
}
