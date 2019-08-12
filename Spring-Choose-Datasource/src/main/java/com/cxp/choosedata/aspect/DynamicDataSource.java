package com.cxp.choosedata.aspect;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-12 11:04
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    public static Map<String, List<String>> METHOD_TYPE_MAP = new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("数据源为:===={}", DataSourceHandler.getDataSource());
        return DataSourceHandler.getDataSource();
    }

    // 设置方法名前缀对应的数据源
    // 这个是用过set方法为methodType属性注入值，注入的配置是在spring-db.xml文件中配置的
    public void setMethodType(Map<String, String> map) {
        for (String key : map.keySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = map.get(key).split(",");
            for (String type : types) {
                if (StringUtils.isNotBlank(type)) {
                    v.add(type);
                }
            }
            METHOD_TYPE_MAP.put(key, v);
        }
    }
}
