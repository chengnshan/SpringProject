package com.cxp.netty.server;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-09-15 20:28
 */
public class OpenNettyServer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        NettyServer nettyServer = new NettyServer(8899);
        Thread thread = new Thread(nettyServer);
        thread.start();
    }

}
