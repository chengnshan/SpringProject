package com.cxp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-09-15 22:54
 */
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "rightPop")
    public String rightPop(String key, long timeout, TimeUnit unit) {
        return (String) redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    @RequestMapping(value = "sendMsg")
    public String sendMsg(){
    //    redisTemplate.convertAndSend("topic.channel","{\"name\":\"channel\"}");
        redisTemplate.convertAndSend("topic.name","{\"name\":\"张三丰\"}");

        return null;
    }
}
