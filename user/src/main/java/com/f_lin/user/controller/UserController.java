package com.f_lin.user.controller;

import com.f_lin.comment_api.api.CommentApi;
import com.f_lin.comment_api.po.Comment;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.User;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

/**
 * @author F_lin fengjunlin@23mofang.com
 * @since 2018/3/16
 **/
@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {

    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    CommentApi commentApi;

    @Override
    @GetMapping("/{phone}")
    public User getUserByPhone(@PathVariable String phone) {
        return mongoOperations.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
    }

    @GetMapping("/messages")
    public Object getMessages(@UserId String userId) {
        List<Comment> comments = commentApi.getCommentByUserId(userId);
        if (!comments.isEmpty()) {
            comments.sort(Comparator.comparing(Comment::getCreateDate));
        }
        return JsonResult.success(comments);
    }
}
