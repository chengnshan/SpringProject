package com.cxp.controller;

import com.cxp.pojo.User;
import com.cxp.utils.JackJsonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author cheng
 * @Date 2019/9/13 19:40
 */
@Controller
public class RabbitController {

    @Autowired
    @Qualifier(value = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(value = "jsonMessageConverter")
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    @RequestMapping(value = "rabbitSendMsg")
    @ResponseBody
    public String rabbitSendMsg(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        messageProperties.setContentEncoding("UTF-8");
        Message message = new Message("这是一条String类型消息!".getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("directExchange","directBindings",
                message);

        User user = new User();
        user.setName("张三");
        user.setPassword("123456");
        messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.getHeaders().put("__TypeId__","User");
        //在发送端，可通过如下方式设置消息过期时间
        messageProperties.setExpiration("30000");
        message = new Message(JackJsonUtil.objTojson(user).getBytes(),messageProperties);
        rabbitTemplate.convertAndSend("directExchange","directObjectBindings",message);


        return null;
    }

    @RequestMapping(value = "rabbitSendList")
    @ResponseBody
    public String rabbitSendList(){
        List<User> list = new ArrayList<>();
        list.add(new User("张三丰","123456"));
        list.add(new User("张无忌","123456"));
        //list转换为json字符串
        String strJsonList = JackJsonUtil.objectToString(list);

        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setContentType("application/json");
        messageProperties2.getHeaders().put("__TypeId__","java.util.List");
        messageProperties2.getHeaders().put("__ContentTypeId__","User");
        Message message2 = new Message(strJsonList.getBytes(),messageProperties2);

        rabbitTemplate.convertAndSend("directExchange", "directObjectBindings", message2,
                new CorrelationData(UUID.randomUUID().toString()));

    //    rabbitTemplate.convertAndSend("directExchange","1234","654321");
        return "success";
    }
}
