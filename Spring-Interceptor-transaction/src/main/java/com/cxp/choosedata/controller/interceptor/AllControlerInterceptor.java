package com.cxp.choosedata.controller.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: Spring-Project
 * @description:
 * @author: cheng
 * @create: 2019-08-26 10:55
 */
public class AllControlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AllControlerInterceptor preHandle run!");
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request,
                           javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("AllControlerInterceptor postHandle run!");
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request,
                                javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("AllControlerInterceptor afterCompletion run!");
    }
}
