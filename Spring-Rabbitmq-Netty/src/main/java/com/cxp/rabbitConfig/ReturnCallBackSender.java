package com.cxp.rabbitConfig;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author cheng
 * @Date 2019/9/14 14:27
 */
public class ReturnCallBackSender implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId =message.getMessageProperties().getCorrelationIdString();
        System.out.println(String.format(
                "ReturnCallback -> 消息 %s 发送失败，应答码：%s，原因：%s，交换器: %s，路由键：%s",
                correlationId, replyCode, replyText, exchange, routingKey));
    }
}

