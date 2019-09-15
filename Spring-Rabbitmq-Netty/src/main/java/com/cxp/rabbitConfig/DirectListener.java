package com.cxp.rabbitConfig;

import com.cxp.pojo.User;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 程
 * @date 2019/7/3 下午2:50
 */
public class DirectListener {

    private static final Logger logger = LoggerFactory.getLogger(DirectListener.class);

    private static AtomicInteger atomicInteger = new AtomicInteger();

     public void handleMessage(byte[] msg, Message message, Channel channel){
         System.out.println("handleMessage -------------byte-----------");
         System.out.println(new String(msg));
     }

   public void directString(String msg,Message message,Channel channel){
        try{
            System.out.println("DirectListener directString 消费."+msg);

            System.out.println("DirectListener directString 消费成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleMessage(User user,Message message,Channel channel){
        System.out.println("handleMessage User : "+user);
    }

    public void handleMessage(List<User> userList,Message message,Channel channel){
         long deliveryTag = message != null ? message.getMessageProperties().getDeliveryTag() : 0L;
         try{
             System.out.println("handleMessage userList :" +userList);
             if (atomicInteger.get() == 2){
                 int i  =10 /0 ;
             }
             atomicInteger.addAndGet(1);
            channel.basicAck(deliveryTag,false);
         }catch (Exception e){
             logger.error("handleMessage userList exception : "+e.getMessage(),e);
             try {
                 channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                         false,false);
             } catch (IOException e1) {
                 e1.printStackTrace();
             }
         }
        //return "success";
    }
}
