package com.cxp.choosedata.service;

import com.cxp.choosedata.pojo.User;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 12:14
 */
public interface IUserService {

    public User selectUserById(int id);

    public boolean addUser(User user);
}
