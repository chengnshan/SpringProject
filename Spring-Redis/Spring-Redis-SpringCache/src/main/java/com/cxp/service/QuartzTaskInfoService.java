package com.cxp.service;

import com.cxp.pojo.QuartzTaskInfo;

import java.util.List;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-07-18 16:29
 */
public interface QuartzTaskInfoService {

    List<Map<String,Object>> listQuartzTaskInfoMap(QuartzTaskInfo quartzTaskInfo);

    QuartzTaskInfo getByJobName(String jobName);

    QuartzTaskInfo getQuartzTaskInfo(QuartzTaskInfo quartzTaskInfo);
}
