package com.cxp.ibatis.dao.impl;

import com.cxp.ibatis.dao.UserInfoDao;
import com.cxp.ibatis.pojo.UserInfo;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-06-06 16:28
 */
public class UserInfoDaoImpl extends SqlMapClientDaoSupport implements UserInfoDao {

    @Override
    public List<UserInfo> queryAllUserInfo() {
        List<UserInfo> list = getSqlMapClientTemplate().queryForList("listUserInfo");
        return list;
    }

    @Override
    public List<Map<String, Object>> queryAllMap(UserInfo userInfo) {
        List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList("listUserInfoMap", userInfo);
        return list;
    }

    @Override
    public Object insert(UserInfo userInfo) {
        return getSqlMapClientTemplate().insert("insert", userInfo);
    }

    @Override
    public int deleteById(Integer id) {
        return getSqlMapClientTemplate().delete("deleteById", id);
    }

    @Override
    public List<UserInfo> getByAddress(String address) {
        return getSqlMapClientTemplate().queryForList("getByAddress",address);
    }
}
