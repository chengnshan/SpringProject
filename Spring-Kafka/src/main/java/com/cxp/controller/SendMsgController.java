package com.cxp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 程
 * @date 2019/7/29 下午6:28
 */
@RestController
public class SendMsgController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /***
     * 发送消息体为基本类型的消息
     */
    @GetMapping("sendSimple")
    public void sendSimple() {
        kafkaTemplate.send("spring.boot.kafka.simple","hello spring kafka !");
    }

}
