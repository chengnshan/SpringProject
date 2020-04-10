package com.cxp.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-04-01 20:19
 */
@Controller
@MessageMapping(value = "/wss")
public class WebSocketStompController {

    /**
     * 使用Restful风格
     * @param message
     * @param desVariable
     * @param headerMap
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello/{variable}")
    //@SendTo广播消息出去
    @SendTo(value = {"/topic/greetings","/queue/greetings"})
    public String greeting(String message, @DestinationVariable(value = "variable") String desVariable,
                           @Headers Map<String,Object> headerMap) throws Exception {
        System.out.println("WebSocketStompController == "+message+" , desValriable : "+ desVariable);
        System.out.println( headerMap);
        return "message";
    }

    /**
     * 这里没用@SendTo注解指明消息目标接收者，消息将默认通过@SendTo("/topic/wss/twoWays")交给Broker进行处理
     * 不推荐不使用@SendTo注解指明目标接受者
     */
    @MessageMapping("/twoWays")
    @ResponseBody
    public String twoWays(String message, StompHeaderAccessor headerAccessor) {
        System.out.println( "twoWays : " + message);
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        return "这是没有指明目标接受者的消息:"+ message;
    }


    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template){
        this.template = template;
    }

    /**
     * 使用SimpMessagingTemplate发送消息给客户端
     * @param greeting
     */
    @RequestMapping(path = "/templateSend",method = RequestMethod.GET)
    @ResponseBody
    public void templateSend(String greeting){
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings",text);
    }

    /**
     *  1、spring webscoket能识别带”/user”的订阅路径并做出处理，例如，如果浏览器客户端，订阅了’/user/topic/greetings’这条路径
     *  就会被spring websocket利用UserDestinationMessageHandler进行转化成”/topic/greetings-usererbgz2rq”,”usererbgz2rq”中，
     *  user是关键字，erbgz2rq是sessionid，这样子就把用户和订阅路径唯一的匹配起来了
     *  2、如果一个帐号打开了多个浏览器窗口，也就是打开了多个websocket session通道，这时，
     *  spring webscoket默认会把消息推送到同一个帐号不同的session，你可以利用broadcast = false把避免推送到所有的session中
     * @param sendUser
     * @return
     */
    @SendToUser(destinations = "/queue/sendUser",broadcast = false)
    @MessageMapping(value = "/sendUser")
    public String sendUser(String sendUser){
        System.out.println("sendUser : " + sendUser);
        return sendUser;
    }
}
