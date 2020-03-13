package com.cxp.springquartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author : cheng
 * @date : 2020-03-11 10:25
 */
@DisallowConcurrentExecution
public class MyJobTwo implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(String.format("MyJobTwo  %s  job执行.============", new Date() ));
    }
}
