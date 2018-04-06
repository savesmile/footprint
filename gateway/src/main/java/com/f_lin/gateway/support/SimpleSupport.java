package com.f_lin.gateway.support;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/6
 **/

public class SimpleSupport extends WebMvcConfigurationSupport {
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册UserIdMethodArgumentResolver的参数分解器
        argumentResolvers.add(new UserIdMethodArgumentResolver());
    }
}
