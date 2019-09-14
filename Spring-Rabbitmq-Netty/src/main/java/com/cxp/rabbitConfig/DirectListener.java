package com.cxp.rabbitConfig;

import com.cxp.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 程
 * @date 2019/7/3 下午2:50
 */
public class DirectListener {

    private static final Logger logger = LoggerFactory.getLogger(DirectListener.class);

    public void directString(String msg){
        try{
            System.out.println("DirectListener directString 消费."+msg);

            System.out.println("DirectListener directString 消费成功!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void directObject(User user){
        System.out.println("DirectListener directObject"+user);
    }

    public void directObjectList(List<User> userList){

    }
}
