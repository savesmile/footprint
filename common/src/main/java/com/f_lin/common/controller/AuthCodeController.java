package com.f_lin.common.controller;

import com.f_lin.comment_api.api.CommonApi;
import com.f_lin.common.po.AuthCode;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.po.User;
import com.f_lin.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Updates.inc;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@RestController
@RequestMapping("/api/common/auth-code")
public class AuthCodeController implements CommonApi {

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping
    public Object getAuthCode(@RequestParam String phone) {
        User user = mongoOperations.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
        if (user == null) {
            return JsonResult.error("该用户不存在");
        }
        String authCode = randomAuthCode();
        mongoOperations.upsert(
                Query.query(Criteria.where("phone").is(user.getPhone())),
                Update.update("aliveCount", 5).set("createTime", new Date()).set("authCode", authCode),
                AuthCode.class);
        return JsonResult.success(MapBuilder.of("authCode", authCode));
    }

    @Override
    @GetMapping("/verify")
    public boolean verifyAuthCode(@RequestParam String phone,
                                  @RequestParam("auth_code") String authCode) {
        AuthCode ac = mongoOperations.findAndModify(
                Query.query(Criteria.where("phone").is(phone)),
                new Update().inc("aliveCount", -1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                AuthCode.class);
        return ac.getAliveCount() > 1 && authCode.equals(ac.getAuthCode());
    }


    private static String randomAuthCode() {
        Random random = new Random();
        String temp = "";
        for (int i = 0; i < 6; i++) {
            temp += Math.abs(random.nextInt()) % 10;
        }
        return temp;
    }

}
