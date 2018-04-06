package com.f_lin.user.controller;

import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author F_lin fengjunlin@23mofang.com
 * @since 2018/3/16
 **/
@RestController
@RequestMapping("/user")
public class UserController implements UserApi {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    @GetMapping("/{phone}")
    public User getUserByPhone(@PathVariable String phone) {
        return mongoOperations.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
    }
}
