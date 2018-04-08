package com.f_lin.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.f_lin.comment_api.api.CommentApi;
import com.f_lin.comment_api.po.Comment;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user.po.SimpleUserVO;
import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.Focus;
import com.f_lin.user_api.po.User;
import com.f_lin.utils.MapBuilder;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author F_lin fengjunlin@23mofang.com
 * @since 2018/3/16
 **/
@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    CommentApi commentApi;

    @Override
    @GetMapping("/{phone}")
    public User getUserByPhone(@PathVariable String phone) {
        return mongoOperations.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
    }

    @Override
    @GetMapping("/focus")
    public List<String> getFocusUserList(@RequestParam("user-id") String userId) {
        Focus focus = mongoOperations.findOne(Query.query(Criteria.where("userId").is(userId)), Focus.class);
        if (focus == null || focus.getFocusUserIds() == null || focus.getFocusUserIds().isEmpty())
            return Collections.emptyList();
        return focus.getFocusUserIds();
    }

    @Override
    @GetMapping("/focus/focused")
    public boolean isFocus(@RequestParam("user-id") String userId,
                           @RequestParam("with-user-id") String withUserId) {
        logger.info("======= {} {} =========", userId, withUserId);
        Focus focus = mongoOperations.findOne(Query.query(Criteria.where("userId").is(userId)), Focus.class);
        if (focus == null
                || focus.getFocusUserIds() == null
                || focus.getFocusUserIds().isEmpty()
                || !focus.getFocusUserIds().contains(withUserId))
            return false;
        return true;
    }


    @PostMapping("/focus")
    public Object postFocus(@UserId String userId,
                            @RequestBody JSONObject jsonObject) {
        String focusUserId = jsonObject.getString("focusUserId");
        if (mongoOperations.exists(Query.query(Criteria.where("userId").is(userId)
                .and("focusUserIds").is(focusUserId)), Focus.class)) {
            return JsonResult.error("你已经关注该用户,请勿重复关注.");
        }
        mongoOperations.upsert(Query.query(Criteria.where("userId").is(userId)),
                new Update().push("focusUserIds", focusUserId), Focus.class);
        mongoOperations.upsert(Query.query(Criteria.where("userId").is(focusUserId)),
                new Update().push("followUserIds", userId), Focus.class);
        return JsonResult.success(MapBuilder.of("result", true));
    }

    @PostMapping("/un-focus")
    public Object postUnFocus(@UserId String userId,
                              @RequestBody JSONObject jsonObject) {
        String focusUserId = jsonObject.getString("focusUserId");
        if (!mongoOperations.exists(Query.query(Criteria.where("userId").is(userId)
                .and("focusUserIds").is(focusUserId)), Focus.class)) {
            return JsonResult.error("你并没有关注该用户。");
        }
        mongoOperations.upsert(Query.query(Criteria.where("userId").is(userId)),
                new Update().pull("focusUserIds", focusUserId), Focus.class);
        mongoOperations.upsert(Query.query(Criteria.where("userId").is(focusUserId)),
                new Update().pull("focusUserIds", userId), Focus.class);
        return JsonResult.success(MapBuilder.of("result", true));
    }

    @GetMapping("/my-focus")
    public Object getMyFocus(@UserId String userId) {
        Focus focus = mongoOperations.findOne(Query.query(Criteria.where("userId").is(userId)), Focus.class);
        if (focus == null || focus.getFocusUserIds().isEmpty()) return JsonResult.error("该用户没有关注");
        List<User> users = mongoOperations.find(Query.query(Criteria.where("_id").in(focus.getFocusUserIds())), User.class);
        List<SimpleUserVO> results = users.stream().map(f -> new SimpleUserVO(f)).collect(Collectors.toList());
        return JsonResult.success(results);
    }

    @GetMapping("/my-follow")
    public Object getMyFollow(@UserId String userId) {
        Focus focus = mongoOperations.findOne(Query.query(Criteria.where("userId").is(userId)), Focus.class);
        if (focus == null || focus.getFollowUserIds() == null || focus.getFollowUserIds().isEmpty())
            return JsonResult.error("该用户没有粉丝");
        List<User> users = mongoOperations.find(Query.query(Criteria.where("_id").in(focus.getFollowUserIds())), User.class);
        List<SimpleUserVO> results = users.stream().map(f -> new SimpleUserVO(f)).collect(Collectors.toList());
        return JsonResult.success(results);
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
