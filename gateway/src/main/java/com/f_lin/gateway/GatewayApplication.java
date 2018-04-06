package com.f_lin.gateway;

import com.f_lin.gateway.filter.TokenVerificationSetting;
import com.f_lin.gateway.support.SimpleSupport;
import com.f_lin.gateway.support.UserIdMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.f_lin.*.api"})
@EnableZuulProxy
@Import(TokenVerificationSetting.class)
public class GatewayApplication extends SimpleSupport {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


}
