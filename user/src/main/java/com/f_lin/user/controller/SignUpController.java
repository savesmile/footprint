package com.f_lin.user.controller;

import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.po.Token;
import com.f_lin.gateway.utils.TokenUtils;
import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.User;
import com.f_lin.user_api.vo.SignPosts;
import com.f_lin.utils.MD5Util;
import com.f_lin.utils.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author F_lin
 * @since 2018/3/17
 **/
@RestController
@RequestMapping("/api/user/sign-up")
public class SignUpController {

    private static final String DEFAULT_AVATAR = "http://47.95.121.41/image/default_avatar.png";

    @Autowired
    UserApi userApi;

    @Autowired
    MongoOperations mongoOperations;

    /**
     * 注册并返回token
     *
     * @param signUpPosts
     * @return
     */
    @PostMapping
    public Object signUp(@RequestBody SignPosts signUpPosts) {
        if (mongoOperations.exists(Query.query(Criteria.where("phone").is(signUpPosts.getPhone())), User.class)) {
            return JsonResult.error("该用户名已经存在!");
        }
        mongoOperations.save(new User()
                .setAvatar(DEFAULT_AVATAR)
                .setNickName("用户" + signUpPosts.getPhone())
                .setPhone(signUpPosts.getPhone())
                .setPassword(MD5Util.getMD5(signUpPosts.getPassword())));
        User user = userApi.getUserByPhone(signUpPosts.getPhone());
        if (user == null) {
            return JsonResult.error("user不存在");
        }
        return JsonResult.success(MapBuilder.of("token", TokenUtils.encryptionToken(new Token(user.getId()))));
    }
}
