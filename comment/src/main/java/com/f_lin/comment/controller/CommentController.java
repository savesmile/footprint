package com.f_lin.comment.controller;

import com.f_lin.comment_api.api.CommentApi;
import com.f_lin.comment_api.po.Comment;
import com.f_lin.comment_api.vo.CommentVO;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@RestController
@RequestMapping("/api/comment")
public class CommentController implements CommentApi {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping("/list")
    public Object getCommentByArticleId(@RequestParam("article_id") String articleId) {
        Query query = Query.query(Criteria.where("articleId").is(articleId));
        query.with(new Sort(Sort.Direction.DESC, "_id"));
        List<Comment> comments = mongoOperations.find(, Comment.class);
        if (comments.isEmpty()) {
            return JsonResult.error("没有评论");
        }

        List<CommentVO> VOs = comments.stream().map(c -> toVO(c)).collect(Collectors.toList());
        return JsonResult.success(VOs);
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

    private CommentVO toVO(Comment comment) {
        User user = mongoOperations.findOne(Query.query(Criteria.where("_id").is(comment.getUserId())), User.class);
        return new CommentVO().switchVO(comment, user.getNickName(), user.getAvatar());
    }
}
