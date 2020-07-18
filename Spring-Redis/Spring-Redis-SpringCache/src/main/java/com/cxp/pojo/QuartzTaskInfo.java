package com.cxp.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : cheng
 * @date : 2020-07-18 15:54
 */
public class QuartzTaskInfo implements Serializable {

   /* `ID` int not null ,
    `JOB_NAME` VARCHAR(200) NOT NULL,
    `JOB_GROUP` VARCHAR(200) NOT NULL,
    `JOB_DESCRIPTION` VARCHAR(250) NULL,
    `JOB_CLASS_NAME` VARCHAR(250) NOT NULL,
    `JOB_STATUS` VARCHAR(250) NOT NULL,
    `CRON_EXPRESSION` VARCHAR(250) NOT NULL,
    `CREATE_TIME` timestamp not null,*/

   private Integer id;
   private String jobName;
   private String jobGroup;
   private String jobDescription;
   private String jobClassName;
   private String jobStatus;
   private String cronExpression;
   private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
