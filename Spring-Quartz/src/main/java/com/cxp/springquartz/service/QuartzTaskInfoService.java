package com.cxp.springquartz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxp.springquartz.pojo.QuartzTaskInfo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-03-12 21:57
 */
public interface QuartzTaskInfoService extends IService<QuartzTaskInfo> {

    /**
     * 保存定时任务
     * @param taskInfo
     * @return
     */
    boolean saveTaskInfo(QuartzTaskInfo taskInfo) throws ClassNotFoundException, SchedulerException;

    /**
     * 查询定时任务
     * @param taskInfo
     */
    Page<QuartzTaskInfo> listTaskByCondition(QuartzTaskInfo taskInfo, Integer pageNum, Integer pageSize);

    /**
     * 暂停定时任务
     * @param info
     * @return
     */
    boolean pauseJob(QuartzTaskInfo info) throws SchedulerException;

    /**
     * 恢复任务
     * @param info
     * @return
     * @throws SchedulerException
     */
    boolean resumeJob(QuartzTaskInfo info) throws SchedulerException;

    /**
     * 删除定时任务
     * @param info
     * @return
     * @throws SchedulerException
     */
    boolean deleteJob(QuartzTaskInfo info) throws SchedulerException;

    boolean updateJob(QuartzTaskInfo info) throws SchedulerException;
}
