package com.f_lin.user;

import com.f_lin.gateway.support.SimpleSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.f_lin.*.api"})
public class UserApplication extends SimpleSupport {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
