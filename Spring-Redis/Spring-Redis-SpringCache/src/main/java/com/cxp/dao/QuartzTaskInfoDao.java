package com.cxp.dao;

import com.cxp.pojo.QuartzTaskInfo;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-07-18 16:21
 */
public interface QuartzTaskInfoDao {

    /**
     * 插入数据
     * @param quartzTaskInfo
     * @return
     */
    int insertQuartzTaskInfo(QuartzTaskInfo quartzTaskInfo);

    int deleteById(Integer id);

    /**
     *
     * @param quartzTaskInfo
     * @return
     */
    List<Map<String,Object>> listQuartzTaskInfoMap(QuartzTaskInfo quartzTaskInfo);

    QuartzTaskInfo getByJobName(String jobName);
}
