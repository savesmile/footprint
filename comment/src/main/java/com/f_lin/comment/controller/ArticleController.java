package com.f_lin.comment.controller;

import com.f_lin.comment_api.po.Article;
import com.f_lin.comment_api.vo.ArticlesVO;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.User;
import com.f_lin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@RestController
@RequestMapping("/api/comment/article")
public class ArticleController {

    private static final String TYPE_FOCUS = "focus";

    @Autowired
    UserApi userApi;

    @Autowired
    MongoOperations mongoOperations;

    @PostMapping
    public Object postArticle(@UserId String userId,
                              @RequestBody Article article) {
        article.setContent(article.getContent().replace("。", "。<br/>"));
        return JsonResult.success(
                mongoOperations.findAndModify(
                        Query.query(Criteria.where("userId").is(userId).and("createTime").is(new Date())),
                        Update.update("likeCount", 0)
                                .set("location", article.getLocation().replace(" ", " # "))
                                .set("likeUserId", Collections.emptyList())
                                .set("title", article.getTitle())
                                .set("summary", article.getSummary())
                                .set("secret", article.isSecret())
                                .set("imgPath", article.getImgPath())
                                .set("content", article.getContent()),
                        FindAndModifyOptions.options().returnNew(true).upsert(true),
                        Article.class));
    }

    @GetMapping
    public Object getArticles(@UserId(required = false) String userId,
                              @RequestParam(defaultValue = "default") String type) {
        Query query = Query.query(Criteria.where("secret").is(false)).with(new Sort(Sort.Direction.DESC, "createTime"));
        if (TYPE_FOCUS.equals(type) && !StringUtils.isEmpty(userId)) {
            List<String> focusIds = userApi.getFocusUserList(userId);
            query.addCriteria(Criteria.where("userId").in(focusIds));
        }
        query.fields().exclude("location");

        List<Article> articles = mongoOperations.find(query, Article.class);
        if (articles.isEmpty()) return JsonResult.success();

        return JsonResult.success(articles.stream().map(a -> toVO(a, userId)).collect(Collectors.toList()));
    }


    private ArticlesVO toVO(Article article, String userId) {
        User user = mongoOperations.findOne(Query.query(Criteria.where("_id").is(article.getUserId())), User.class);
        if (user == null) return new ArticlesVO();
        return new ArticlesVO().switchVO(article, user.getNickName(),
                user.getAvatar(), !StringUtils.isEmpty(userId) && article.getLikeUserId().contains(userId));
    }
}
