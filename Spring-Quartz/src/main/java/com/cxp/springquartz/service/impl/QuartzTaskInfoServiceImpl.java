package com.cxp.springquartz.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.springquartz.mapper.QuartzTaskInfoMapper;
import com.cxp.springquartz.pojo.QuartzTaskInfo;
import com.cxp.springquartz.service.QuartzService;
import com.cxp.springquartz.service.QuartzTaskInfoService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-03-12 21:58
 */
@Service
public class QuartzTaskInfoServiceImpl extends ServiceImpl<QuartzTaskInfoMapper, QuartzTaskInfo> implements QuartzTaskInfoService {

    private QuartzService quartzService;

    @Autowired
    public void setQuartzService(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @Override
    public boolean saveTaskInfo(QuartzTaskInfo taskInfo) throws ClassNotFoundException, SchedulerException {
        boolean save = save(taskInfo);
        if (save) {
            quartzService.addJob(taskInfo);
        }
        return save;
    }

    @Override
    public Page<QuartzTaskInfo> listTaskByCondition(QuartzTaskInfo taskInfo, Integer pageNum, Integer pageSize) {
        Page<QuartzTaskInfo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuartzTaskInfo> wrapper = new QueryWrapper<QuartzTaskInfo>();
        if (!StringUtils.isEmpty(taskInfo.getJobName())){
            wrapper.eq("JOB_NAME", taskInfo.getJobName());
        }
        if (!StringUtils.isEmpty(taskInfo.getJobGroup())){
            wrapper.eq("JOB_GROUP", taskInfo.getJobGroup());
        }
        if (!StringUtils.isEmpty(taskInfo.getJobClassName())){
            wrapper.eq("JOB_CLASS_NAME", taskInfo.getJobClassName());
        }
        Page<QuartzTaskInfo> pages = page(page, wrapper);
        return pages;
    }

    @Override
    public boolean pauseJob(QuartzTaskInfo info) throws SchedulerException {
        return false;
    }

    @Override
    public boolean resumeJob(QuartzTaskInfo info) throws SchedulerException {
        return false;
    }

    @Override
    public boolean deleteJob(QuartzTaskInfo info) throws SchedulerException {
        return false;
    }

    @Override
    public boolean updateJob(QuartzTaskInfo info) throws SchedulerException {
        return false;
    }
}
