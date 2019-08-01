package com.cxp.kafkaconfig;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;

import java.util.Arrays;
import java.util.List;

/**
 * @program: SpringProject
 * @description:
 * @author: cheng
 * @create: 2019-07-31 18:10
 */
public class KafkaConsumerServer implements MessageListener<String, String> {

    protected final Logger LOG = LoggerFactory.getLogger(KafkaConsumerServer.class);

    private static Logger MSG = LoggerFactory.getLogger("operation");

    private String sendTopics;

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaConsumerServer(){

    }

    public KafkaConsumerServer(String sendTopics){
        this.sendTopics = sendTopics;
    }

    public KafkaConsumerServer(String sendTopics,KafkaTemplate<String, String> kafkaTemplate){
        this.sendTopics = sendTopics;
        this.kafkaTemplate = kafkaTemplate;
    }

    private List<String> list = null;

    private List<String> getTopicList() {
        if (list == null) {
            list = Arrays.asList(sendTopics.split("\\,"));
        }
        return list;
    }

    /**
     * 监听器自动执行该方法 消费消息 自动提交offset 执行业务代码 （high level api
     * 不提供offset管理，不能指定offset进行消费）
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        List<String> list = getTopicList();
        LOG.info("=============kafkaConsumer开始消费=============");
        String topic = record.topic();
        String key = record.key();
        String value = record.value();
        long offset = record.offset();
        int partition = record.partition();
        LOG.info("-------------topic:" + topic);
        LOG.info("-------------value:" + value);
        LOG.info("-------------key:" + key);
        LOG.info("-------------offset:" + offset);
        LOG.info("-------------partition:" + partition);
        LOG.info("~~~~~~~~~~~~~kafkaConsumer消费结束~~~~~~~~~~~~~");

        MSG.info("consumer topic:" + topic + ",value:" + value);
        if (list != null && !list.contains(topic)) {
            LOG.info("-------------config sendTopics:" + sendTopics + " is not contain the topic:" + topic);
            return;
        }

    }

}
