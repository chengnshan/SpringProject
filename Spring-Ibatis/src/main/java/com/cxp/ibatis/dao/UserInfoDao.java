package com.cxp.ibatis.dao;

import com.cxp.ibatis.pojo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-06-06 16:27
 */
public interface UserInfoDao {

    public List<UserInfo> queryAllUserInfo();         //查询全部

    public List<Map<String,Object>> queryAllMap(UserInfo userInfo);

    Object insert(UserInfo userInfo);

    int deleteById(Integer id);

    List<UserInfo> getByAddress(String address);
}
