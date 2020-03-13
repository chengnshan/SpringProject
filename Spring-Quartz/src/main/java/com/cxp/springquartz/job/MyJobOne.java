package com.cxp.springquartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : cheng
 * @date : 2020-03-12 20:35
 */
@DisallowConcurrentExecution
public class MyJobOne implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(String.format("%s MyJobOne printMessage !",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }
}
