package com.f_lin.user.controller;

import com.f_lin.gateway.filter.TestApi;
import com.f_lin.gateway.po.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author F_lin fengjunlin@23mofang.com
 * @since 2018/3/16
 **/
@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    TestApi testApi;

    @GetMapping
    public Test getTest() {
        return testApi.testFeign();
    }
}
