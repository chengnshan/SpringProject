package com.cxp.rabbitConfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author 程
 * @date 2019/7/3 下午2:50
 */
public class DirectListener {

    public String onMessage(String msg,Message message,Channel channel){
        try{
            System.out.println("DirectListener onMessage 消费.");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("DirectListener onMessage 消费成功!");
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
