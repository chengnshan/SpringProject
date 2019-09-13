package com.cxp.choosedata.service.impl;

import com.cxp.choosedata.mapper.UserMapper;
import com.cxp.choosedata.pojo.User;
import com.cxp.choosedata.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 12:15
 */
@Service
public class IUserServiceImpl implements IUserService {

    //@Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user) == 1 ? true : false;
    }
}
