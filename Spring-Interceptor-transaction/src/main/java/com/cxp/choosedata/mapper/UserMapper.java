package com.cxp.choosedata.mapper;

import com.cxp.choosedata.pojo.User;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 12:09
 */
public interface UserMapper {

    //访问读数据库
    User selectUserById(int id);

    //访问写数据库
    int addUser(User user);
}
