package com.cxp.rabbitConfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 *重写￿MessageListenerAdapter的onMessage方法，把调用自定义监听器的方法加两个参数
 * @author cheng
 * @Date 2019/9/14 16:43
 */
public class CustomMessageListenerAdapter extends MessageListenerAdapter {

    public CustomMessageListenerAdapter(){
        super();
    }

    public CustomMessageListenerAdapter(Object delegate){
        super(delegate);
    }

    public CustomMessageListenerAdapter(Object delegate, MessageConverter messageConverter){
        super(delegate,messageConverter);
    }

    public CustomMessageListenerAdapter(Object delegate, String defaultListenerMethodr){
        super(delegate,defaultListenerMethodr);
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Object delegate = getDelegate();
        if (delegate != this) {
            if (delegate instanceof ChannelAwareMessageListener) {
                if (channel != null) {
                    ((ChannelAwareMessageListener) delegate).onMessage(message, channel);
                    return;
                }
                else if (!(delegate instanceof MessageListener)) {
                    throw new AmqpIllegalStateException("MessageListenerAdapter cannot handle a "
                            + "ChannelAwareMessageListener delegate if it hasn't been invoked with a Channel itself");
                }
            }
            if (delegate instanceof MessageListener) {
                ((MessageListener) delegate).onMessage(message);
                return;
            }
        }

        // Regular case: find a handler method reflectively.
        Object convertedMessage = extractMessage(message);
        String methodName = getListenerMethodName(message, convertedMessage);
        if (methodName == null) {
            throw new AmqpIllegalStateException("No default listener method specified: "
                    + "Either specify a non-null value for the 'defaultListenerMethod' property or "
                    + "override the 'getListenerMethodName' method.");
        }

        // Invoke the handler method with appropriate arguments.
        Object[] listenerArguments = buildListenerArguments(convertedMessage,message,channel);
        Object result = invokeListenerMethod(methodName, listenerArguments, message);
        if (result != null) {
            handleResult(result, message, channel);
        }
        else {
            logger.trace("No result object given - no result to handle");
        }
    }

    protected Object[] buildListenerArguments(Object extractedMessage,Message message,Channel channel) {
        return new Object[] {extractedMessage,message,channel};
    }
}
