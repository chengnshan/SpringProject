package com.cxp.dao.impl;

import com.cxp.dao.QuartzTaskInfoDao;
import com.cxp.pojo.QuartzTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-07-18 16:21
 */
public class QuartzTaskInfoDaoImpl extends SqlMapClientDaoSupport implements QuartzTaskInfoDao {

    @Autowired
    private SqlMapClientFactoryBean sqlMapClient;

    @Override
    public int insertQuartzTaskInfo(QuartzTaskInfo quartzTaskInfo) {
        Object insert = super.getSqlMapClientTemplate().insert("insertQuartzTaskInfo", quartzTaskInfo);
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return getSqlMapClientTemplate().delete("deleteById", id);
    }

    @Override
    public List<Map<String, Object>> listQuartzTaskInfoMap(QuartzTaskInfo quartzTaskInfo) {
        List queryForList = getSqlMapClientTemplate().queryForList("listQuartzTaskInfoMap", quartzTaskInfo);
        return queryForList;
    }

    @Override
    public QuartzTaskInfo getByJobName(String jobName) {
        QuartzTaskInfo getByJobName = (QuartzTaskInfo) getSqlMapClientTemplate().queryForObject("getByJobName", jobName);
        return getByJobName;
    }
}
