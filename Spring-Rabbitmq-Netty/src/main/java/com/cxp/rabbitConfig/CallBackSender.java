package com.cxp.rabbitConfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @author cheng
 * @Date 2019/9/14 12:35
 */
public class CallBackSender implements RabbitTemplate.ConfirmCallback {

    /**
     * CorrelationData 是在发送消息时传入回调方法的参数，可以用于区分消息对象。 CorrelationData对象中只有一个属性 String id。
     * 通过这个参数，我们可以区分当前是发送哪一条消息时的回调，并通过ack参数来进行失败重发功能
     *
     * @param correlationData 回调的相关数据.
     * @param ack true for ack, false for nack
     * @param cause 专门给NACK准备的一个可选的原因，其他情况为null。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("callbakck confirm: " + correlationData.getId() + " ACK : " + ack+", cause  "+cause);
        if(ack){
            System.out.println("ConfirmCallback -> 消息发布到交换器成功，id：" + correlationData);
        }else {
            System.out.println("ConfirmCallback -> 消息发布到交换器失败，错误原因为："+ cause);
        }
    }
}
