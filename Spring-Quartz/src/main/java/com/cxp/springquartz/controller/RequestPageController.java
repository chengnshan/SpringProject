package com.cxp.springquartz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxp.springquartz.job.ResponseDto;
import com.cxp.springquartz.pojo.QuartzTaskInfo;
import com.cxp.springquartz.service.QuartzTaskInfoService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : cheng
 * @date : 2020-03-13 12:24
 */
@Controller
@RequestMapping(value = "/job")
public class RequestPageController {

    private static Logger logger = LoggerFactory.getLogger(RequestPageController.class);

    private QuartzTaskInfoService quartzTaskInfoService;

    @Autowired
    public void setQuartzTaskInfoService(QuartzTaskInfoService quartzTaskInfoService) {
        this.quartzTaskInfoService = quartzTaskInfoService;
    }
    /**
     * 进入定时任务管理页面
     *
     * @return
     */
    @RequestMapping(value = {"/","/toQuartzVuePage"})
    public String toQuartzVuePage() {
        logger.info("进入quartz_vue页面!");
        return "quartz_vue";
    }

    /**
     * 查询所有job
     *
     * @param info
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "queryJob", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseDto queryJob(QuartzTaskInfo info,
                                @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        logger.info("页数: {}, 条数：{}.", pageNum, pageSize);
        Page<QuartzTaskInfo> taskInfoPages = quartzTaskInfoService.listTaskByCondition(info, pageNum, pageSize);
        return ResponseDto.success(taskInfoPages);
    }

    /**
     * 保存定时任务
     *
     * @param info
     */
    @ResponseBody
    @RequestMapping(value = "addJob", produces = "application/json; charset=UTF-8")
    public ResponseDto saveQuartzTaskInfo(QuartzTaskInfo info) {
        try {
            if (info == null) {
                return ResponseDto.fail(400, "参数不能为空!");
            }
            if (StringUtils.isEmpty(info.getJobName())) {
                return ResponseDto.fail(400, "任务名称需要填写!");
            }
            if (StringUtils.isEmpty(info.getJobGroup())) {
                return ResponseDto.fail(400, "任务分组需要填写!");
            }
            boolean b = quartzTaskInfoService.saveTaskInfo(info);
            return ResponseDto.success(null);
        } catch (ClassNotFoundException e) {
            logger.error("saveQuartzTaskInfo Exception : " + e.getMessage(), e);
            return ResponseDto.error(500, e.getMessage());
        }catch ( SchedulerException e){
            logger.error("saveQuartzTaskInfo Exception : " + e.getMessage(), e);
            return ResponseDto.error(500, e.getMessage());
        }
    }
}
