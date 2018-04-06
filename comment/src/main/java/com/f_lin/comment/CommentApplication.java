package com.f_lin.comment;

import com.f_lin.gateway.support.UserIdMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.f_lin.*.api"})
public class CommentApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class, args);
    }


    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册UserIdMethodArgumentResolver的参数分解器
        argumentResolvers.add(new UserIdMethodArgumentResolver());
    }
}
