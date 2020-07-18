package com.cxp.ibatis.controller;

import com.cxp.ibatis.dao.UserInfoDao;
import com.cxp.ibatis.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-06-06 16:25
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(value = "/getUserInfoList", produces = "application/json")
    @ResponseBody
    public List<UserInfo> getUserInfoList(){
        return userInfoDao.queryAllUserInfo();
    }

    @RequestMapping(value = "/queryAllMap", produces = "application/json")
    @ResponseBody
    public List queryAllMap( UserInfo userInfo){
        return userInfoDao.queryAllMap(userInfo);
    }

    @RequestMapping(value = "/insert")
    public Object insert( UserInfo userInfo){
        Object obj = userInfoDao.insert(userInfo);
        return "forward: /getUserInfoList";
    }

    @RequestMapping(value = "/deleteById")
    public String deleteById( Integer id ){
        userInfoDao.deleteById(id);
        return "forward: /getUserInfoList";
    }

    @RequestMapping(value = "/getByAddress")
    @ResponseBody
    public List<UserInfo> getByAddress( String  address ){
        List<UserInfo> userInfos = userInfoDao.getByAddress(address);
        return userInfos;
    }
}
