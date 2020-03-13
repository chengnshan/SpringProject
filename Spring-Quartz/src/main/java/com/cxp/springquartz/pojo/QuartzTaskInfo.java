package com.cxp.springquartz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : cheng
 * @date : 2020-03-10 19:55
 */
@Data
@TableName(value = "quartz_task_info")
public class QuartzTaskInfo implements Serializable {

    private static final long serialVersionUID = -4329216699702284532L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**任务名称*/
    private String jobName;

    /**任务分组*/
    private String jobGroup;

    /**任务类名*/
    private String jobClassName;

    /**任务描述*/
    private String jobDescription;

    /**任务状态*/
    private String jobStatus = "1";

    private String triggerName;
    private String triggerGroup;

    /**任务表达式*/
    private String cronExpression;

    /**时区*/
    @TableField(exist = false)
    private String zoneId;

    private String createTime;
}
