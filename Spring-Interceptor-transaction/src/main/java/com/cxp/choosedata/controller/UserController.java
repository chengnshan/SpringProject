package com.cxp.choosedata.controller;

import com.cxp.choosedata.pojo.User;
import com.cxp.choosedata.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 12:25
 */
@Controller
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
public class UserController {

    @Autowired
    private IUserService iUserService;

    //http://localhost:8080/user/select.do
    @ResponseBody
    @RequestMapping(value = "/select.do", method = RequestMethod.GET)
    public String select() {
        User user = new User("aaa","123");
        return user.toString();
    }

    //http://localhost:8080/user/add.do
    @ResponseBody
    @RequestMapping(value = "/add.do", method = RequestMethod.GET)
    public String add() {
        boolean isOk = iUserService.addUser(new User("333", "444"));
        return isOk == true ? "shibai" : "chenggong";
    }
}
