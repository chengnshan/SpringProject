package com.cxp.service.impl;

import com.cxp.dao.QuartzTaskInfoDao;
import com.cxp.pojo.QuartzTaskInfo;
import com.cxp.service.QuartzTaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-07-18 16:30
 */
@Service(value = "quartzTaskInfoService")
public class QuartzTaskInfoServiceImpl implements QuartzTaskInfoService {

    @Autowired
    private QuartzTaskInfoDao quartzTaskInfoDao;

    @Override
    public List<Map<String, Object>> listQuartzTaskInfoMap(QuartzTaskInfo quartzTaskInfo) {
        return quartzTaskInfoDao.listQuartzTaskInfoMap(quartzTaskInfo);
    }

    @Override
    public QuartzTaskInfo getByJobName(String jobName) {
        return quartzTaskInfoDao.getByJobName(jobName);
    }

    @Cacheable(value = "getQuartzTaskInfo",key="'quartz-' + #quartzTaskInfo.jobName",
            condition = "#quartzTaskInfo.jobName != null and #quartzTaskInfo.jobName != '' ")
    @Override
    public QuartzTaskInfo getQuartzTaskInfo(QuartzTaskInfo quartzTaskInfo) {
        QuartzTaskInfo taskInfo = this.getByJobName(quartzTaskInfo.getJobName());
        return taskInfo;
    }
}
