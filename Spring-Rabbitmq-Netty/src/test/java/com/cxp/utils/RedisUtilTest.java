package com.cxp.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author cheng
 * @Date 2019/9/14 9:11
 */
// 配置Spring中的测试环境
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件路径
@ContextConfiguration(locations = {"classpath:/spring/application.xml"})
public class RedisUtilTest {

    @Test
    public void testRedis(){
        String key ="username";
        RedisUtil.set(key,"张三",20000L);

        System.out.println(RedisUtil.get(key));
    }
}