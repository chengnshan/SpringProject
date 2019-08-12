package com.cxp.choosedata.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 11:08
 */
public class DataSourceHandler {
    public static final Logger log = LoggerFactory.getLogger(DataSourceHandler.class);

    // 数据源名称线程池,用来隔离每个线程所使用的数据源
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 在项目启动的时候将配置的读、写数据源加到holder中
     */
    public static void putDataSource(String datasource) {
        log.info("切换到{}数据源", datasource);
        contextHolder.set(datasource);
    }

    /**
     * 从holer中获取数据源字符串
     */
    public static String getDataSource() {
        return contextHolder.get();
    }
}
