package com.cxp.springquartz.service;

import com.cxp.springquartz.pojo.QuartzTaskInfo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-03-10 14:28
 */
public interface QuartzService {


    List<QuartzTaskInfo> list();

    void addJob(QuartzTaskInfo info) throws SchedulerException, ClassNotFoundException;

    void edit(QuartzTaskInfo info) throws SchedulerException;

    void delete(QuartzTaskInfo info) throws SchedulerException;

    void pause(QuartzTaskInfo info) throws SchedulerException;

    void resume(QuartzTaskInfo info) throws SchedulerException;

    boolean checkExists(String jobName, String jobGroup, String triggerName, String triggerGroup)throws SchedulerException;

}
