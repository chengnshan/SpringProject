package com.cxp.controller;

import com.cxp.pojo.QuartzTaskInfo;
import com.cxp.service.QuartzTaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : cheng
 * @date : 2020-07-18 16:36
 */
@Controller
public class QuartzTaskInfoController {

    @Autowired
    private QuartzTaskInfoService quartzTaskInfoService;

    @RequestMapping(value = "getTaskByJobName" ,method = RequestMethod.POST)
    @ResponseBody
    public QuartzTaskInfo getTaskByJobName(@RequestBody QuartzTaskInfo taskInfo){
        if ( !StringUtils.hasLength(StringUtils.trimAllWhitespace(taskInfo.getJobName()))){
            return null;
        }
        QuartzTaskInfo quartzTaskInfo = quartzTaskInfoService.getQuartzTaskInfo(taskInfo);
        return quartzTaskInfo;
    }
}
