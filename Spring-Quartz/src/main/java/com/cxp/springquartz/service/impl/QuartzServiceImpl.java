package com.cxp.springquartz.service.impl;

import com.cxp.springquartz.pojo.QuartzTaskInfo;
import com.cxp.springquartz.service.QuartzService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author : cheng
 * @date : 2020-03-10 19:59
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    private static Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @PostConstruct
    public void init(){
    }

    /**Quartz定时任务核心的功能实现类*/
    private Scheduler scheduler;

    /**构造器注入*/
    public QuartzServiceImpl(@Autowired SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        scheduler = schedulerFactoryBean.getScheduler();
        System.out.println(schedulerFactoryBean.getScheduler().getSchedulerName());
    }

    @Override
    public List<QuartzTaskInfo> list() {
        List<QuartzTaskInfo> list = new ArrayList<>();
        QuartzTaskInfo info ;

        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);

            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    String cronExpression = "", createTime = "";
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        cronExpression = cronTrigger.getCronExpression();
                        createTime = cronTrigger.getDescription();
                    }

                    info = new QuartzTaskInfo();
                    info.setJobName(jobKey.getName());
                    info.setJobGroup(jobKey.getGroup());
                    info.setJobClassName(jobKey.getClass().getName());
                    info.setJobDescription(jobDetail.getDescription());
                    info.setJobStatus(triggerState.name());
                    info.setCronExpression(cronExpression);
                    info.setCreateTime(createTime);
                    list.add(info);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 保存定时任务
     * @param info 定时任务信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addJob(QuartzTaskInfo info) throws SchedulerException, ClassNotFoundException {
        String jobName = info.getJobName(),
                jobClassName = info.getJobClassName(),
                jobGroup = info.getJobGroup(),
                triggerName = info.getTriggerName(),
                triggerGroup = info.getTriggerGroup(),
                cronExpression = info.getCronExpression(),
                jobDescription = info.getJobDescription(),
                createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (checkExists(jobName,jobGroup,triggerName,triggerGroup)){
                logger.info(String.format("add job fail, job already exist, jobGroup: %s, jobName: %s", jobGroup, jobName));
                throw new SchedulerException("定时任务或触发器已经存在!");
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                    .inTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                    .withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime)
                    .withSchedule(scheduleBuilder).build();

            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobClassName);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
            //返回值是最近一次任务执行的开始时间
        scheduler.scheduleJob(jobDetail,trigger);
    }

    /**
     * 验证是否存在
     * @param jobName 任务名
     * @param jobGroup 任务组
     * @throws SchedulerException
     */
    @Override
    public boolean checkExists(String jobName, String jobGroup, String triggerName, String triggerGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey) && scheduler.checkExists(jobKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(QuartzTaskInfo info) throws SchedulerException {
        String cronExpression = info.getCronExpression();

        TriggerKey triggerKey = TriggerKey.triggerKey(info.getTriggerName(),info.getTriggerGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);
        //如果是cronTrigger的实例
        if (trigger instanceof CronTrigger){
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String oldTime = cronTrigger.getCronExpression();
            if ( ! oldTime.equalsIgnoreCase(cronExpression)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerKey).withDescription(info.getCreateTime());
                // 立即执行
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束  */
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(QuartzTaskInfo info) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(info.getTriggerName(),info.getTriggerGroup());
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(info.getJobName(),info.getJobGroup()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(QuartzTaskInfo info) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(info.getTriggerName(),info.getTriggerGroup()));
        scheduler.pauseJob(JobKey.jobKey(info.getJobName(), info.getJobGroup()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(QuartzTaskInfo info) throws SchedulerException {
        scheduler.resumeTrigger(TriggerKey.triggerKey(info.getTriggerName(),info.getTriggerGroup()));
        scheduler.resumeJob(JobKey.jobKey(info.getJobName(), info.getJobGroup()));
    }

}
