package com.f_lin.user.controller;

import com.f_lin.user_api.vo.SignPosts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author F_lin
 * @since 2018/3/17
 **/
@RestController
@RequestMapping("/user/sign-in")
public class SignInController {
    private final Logger log = LoggerFactory.getLogger(SignInController.class);

    @PostMapping
    public Object signIn(@RequestBody SignPosts signInPosts){



        return null;
    }
}
