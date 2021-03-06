package com.f_lin.comment_api.api;

import com.f_lin.comment_api.po.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@FeignClient(value = "comment")
public interface CommentApi {
    @RequestMapping(value = "/api/comment", method = RequestMethod.GET)
    List<Comment> getCommentByUserId(@RequestParam("user-id") String userId);
}
