package com.f_lin.user.controller;

import com.f_lin.gateway.po.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author F_lin
 * @since 2018/3/17
 **/
@RestController
@RequestMapping("/user/sign-in")
public class SignInController {
    private final Logger log = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping
    public Test testZul() {
        return mongoOperations.findOne(Query.query(Criteria.where("").is("")), Test.class);
    }
}
