package com.cxp.choosedata.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 11:21
 */
@Component
@Aspect
public class DataSourceAspect {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(* com.cxp.choosedata.service.*.*(..))")
    public void aspect(){}

    /**
     * 配置前置通知,使用在方法aspect()上注册的切入点
     */
    @Around(value = "aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String className = point.getTarget().getClass().getName();
        String method = point.getSignature().getName();
        logger.info(className + "." + method + "(" + StringUtils.join(point.getArgs(), ",") + ")");
        for (String key:DynamicDataSource.METHOD_TYPE_MAP.keySet()){
            for (String type:DynamicDataSource.METHOD_TYPE_MAP.get(key)){
                if (method.startsWith(type)) {
                    DataSourceHandler.putDataSource(key);
                }
            }
        }

        try {
            return point.proceed();
        } finally {
            DataSourceHandler.removeDataSource();
        }
    }
}
