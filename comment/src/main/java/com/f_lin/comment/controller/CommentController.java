package com.f_lin.comment.controller;

import com.f_lin.comment_api.api.CommentApi;
import com.f_lin.comment_api.po.Comment;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
import java.util.Date;
import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@RestController
@RequestMapping("/api/comment")
public class CommentController implements CommentApi {

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping("/{article_id}")
    public Object getCommentByArticleId(@PathVariable("article_id") String articleId) {
        List<Comment> comments = mongoOperations.find(Query.query(Criteria.where("articleId").is(articleId)), Comment.class);
        if (comments.isEmpty()) {
            return JsonResult.error("没有评论");
        }
        return JsonResult.success(comments);
    }

    @Override
    @GetMapping
    public List<Comment> getCommentByUserId(@UserId String userId) {
        return mongoOperations.find(Query.query(Criteria.where("userId").is(userId)), Comment.class);
    }

    @PostMapping
    public Object postComment(@UserId String userId,
                              @RequestBody Comment comment) {
        comment.setCreateDate(new Date());
        comment.setLikeCount(0);
        comment.setUserId(userId);
        mongoOperations.save(comment);
        return JsonResult.success(comment);
    }
}
