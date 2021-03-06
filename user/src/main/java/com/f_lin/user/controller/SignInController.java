package com.f_lin.user.controller;

import com.f_lin.comment_api.api.CommonApi;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.po.Token;
import com.f_lin.gateway.support.UserId;
import com.f_lin.gateway.utils.TokenUtils;
import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.User;
import com.f_lin.user_api.vo.SignPosts;
import com.f_lin.utils.MD5Util;
import com.f_lin.utils.MapBuilder;
import com.f_lin.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

/**
 * @author F_lin
 * @since 2018/3/17
 **/
@RestController
@RequestMapping("/api/user")
public class SignInController {
    private final Logger log = LoggerFactory.getLogger(SignInController.class);

    @Autowired
    UserApi userApi;
    @Autowired
    CommonApi commonApi;
    @Autowired
    MongoOperations mongoOperations;


    /**
     * 登陆并返回token
     *
     * @param signInPosts
     * @return
     */
    @PostMapping("/sign-in")
    public Object signIn(@RequestBody SignPosts signInPosts) {
        if (StringUtils.isEmpty(signInPosts.getPassword()) || StringUtils.isEmpty(signInPosts.getPhone()))
            return JsonResult.error("用户名或密码不能为空");
        User user = userApi.getUserByPhone(signInPosts.getPhone());
        String pwd = MD5Util.getMD5(signInPosts.getPassword());
        if (user == null || !pwd.equals(user.getPassword()))
            return JsonResult.error("用户名或密码错误");
        log.info("用户 {} 登录成功", signInPosts.getPhone());
        return JsonResult.success(MapBuilder
                .forTypeSO("token", TokenUtils.encryptionToken(new Token(user.getId())))
                .with("userInfo", user.setPassword(null)).build());
    }

    @PostMapping("/reset-pwd")
    public Object resetPwd(@UserId String userId,
                           @RequestBody SignPosts resetPosts) {
        //if (commonApi.verifyAuthCode(userId, resetPosts.getAuthCode())) {
        mongoOperations.updateFirst(Query.query(Criteria.where("_id").is(userId)),
                Update.update("password", MD5Util.getMD5(resetPosts.getPassword())), User.class);
        return JsonResult.success(MapBuilder.of("result", true));
        // }
        // return JsonResult.success(MapBuilder.of("result", false));
    }
}
