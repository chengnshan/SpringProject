package com.cxp.springquartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 具体的Job类
 * @author : cheng
 * @date : 2020-03-09 19:31
 */
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(String.format("MyJob  %s  job执行. *****************************", new Date() ));
        // 获取参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        // 业务逻辑 ...
    }
}
