package com.cxp.rabbitConfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cheng
 * @Date ${date} ${time}
 */
//@RabbitListener(queues = {"directQueue"})
//@Component
public class RabbitConsumer {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @RabbitHandler
    public void consumeMsg(Channel channel, Message message){
        System.out.println(message);
        System.out.println(cachingConnectionFactory);
    }

}


