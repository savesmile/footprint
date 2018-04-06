package com.f_lin.common.controller;

import com.f_lin.common.po.AuthCode;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.po.User;
import com.f_lin.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@RestController
@RequestMapping("/api/common/auth-code")
public class AuthCodeController {

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping
    public Object getAuthCode(@UserId String userId) {
        User user = mongoOperations.findOne(Query.query(Criteria.where("_id").is(userId)), User.class);
        if (user == null) {
            return JsonResult.error("该用户不存在");
        }
        String authCode = randomAuthCode().toString();
        mongoOperations.upsert(
                Query.query(Criteria.where("phone").is(user.getPhone())),
                Update.update("aliveCount", 5).set("createTime", new Date()).set("authCode", authCode),
                AuthCode.class);
        return JsonResult.success(MapBuilder.of("authCode", authCode));
    }

    @GetMapping("/verify")
    public Object verifyAuthCode() {
        return JsonResult.success();
    }


    private static List<Integer> randomAuthCode() {
        Random random = new Random();
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            arr.add(Math.abs(random.nextInt()) % 10);
        }
        return arr;
    }

}
