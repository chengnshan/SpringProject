package com.cxp.choosedata.controller.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-26 10:48
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle run!");
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request,
                           javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle run!");
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request,
                                javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion run!");
    }
}
