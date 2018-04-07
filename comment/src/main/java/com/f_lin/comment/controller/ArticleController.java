package com.f_lin.comment.controller;

import com.f_lin.comment_api.po.Article;
import com.f_lin.comment_api.vo.ArticlesVO;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.api.UserApi;
import com.f_lin.user_api.po.User;
import com.f_lin.utils.MapBuilder;
import com.f_lin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
                              @RequestParam(defaultValue = "default") String click_type) {
        Query query = Query.query(Criteria.where("secret").is(false)).with(new Sort(Sort.Direction.DESC, "createTime"));
        if (TYPE_FOCUS.equals(click_type) && !StringUtils.isEmpty(userId)) {
            List<String> focusIds = userApi.getFocusUserList(userId);
            query.addCriteria(Criteria.where("userId").in(focusIds));
        }
        query.fields().exclude("location");

        List<Article> articles = mongoOperations.find(query, Article.class);
        if (articles.isEmpty()) return JsonResult.success();

        return JsonResult.success(articles.stream().map(a -> toVO(a, userId)).collect(Collectors.toList()));
    }

    @GetMapping("/time-line")
    public Object getTimeLine(@UserId String userId) {
        Query query = Query.query(Criteria.where("userId").is(userId)).with(new Sort(Sort.Direction.DESC, "_id"));
        List<Article> articles = mongoOperations.find(query, Article.class);
        return JsonResult.success(articles);
    }

    @GetMapping("/other-time-line")
    public Object getOtherTimeLine(@UserId(required = false) String userId,
                                   @RequestParam("other-user-id") String otherUserId) {
        Query query = Query.query(Criteria.where("userId").is(otherUserId)
                .and("secret").is(false))
                .with(new Sort(Sort.Direction.DESC, "_id"));
        List<Article> articles = mongoOperations.find(query, Article.class);
        MapBuilder mp = MapBuilder.forTypeSO("article", articles);
        if (StringUtils.isEmpty(userId)) {
            mp.with("focus", userApi.isFocus(userId, otherUserId));
        }
        User user = mongoOperations.findOne(Query.query(Criteria.where("_id").is(otherUserId)), User.class);
        if (user != null) {
            mp.with("avatar", user.getAvatar()).with("nickname", user.getNickName());
        }
        return JsonResult.success(mp.build());
    }

    @GetMapping("/detail")
    public Object getArticleOne(@UserId(required = false) String userId,
                                @RequestParam("article-id") String articleId) {
        Article article = mongoOperations.findOne(Query.query(Criteria.where("_id").is(articleId)), Article.class);
        User user = mongoOperations.findOne(Query.query(Criteria.where("_id").is(article.getUserId())), User.class);
        boolean focus = false;
        boolean like = false;
        if (StringUtils.isEmpty(userId)) {
            like = article.getLikeUserId().contains(userId);
            focus = userApi.isFocus(userId, article.getUserId());
        }
        MapBuilder mp = MapBuilder.forTypeSO("article", new ArticlesVO().switchVO(article,
                user.getNickName(),
                user.getAvatar(),
                like)).with("focus", focus);
        /**
         * 加载点赞人的头像地址 6个
         */
        List<String> userIds = article.getLikeUserId();
        if (!userIds.isEmpty()) {
            mp.with("likeCount", userIds.size());
            if (userIds.size() > 6) userIds = userIds.subList(0, 5);
            Query query = Query.query(Criteria.where("").is(""));
            query.fields().include("avatar");
            List<String> avatars = new ArrayList<>();
            for (String id : userIds) {
                query.addCriteria(Criteria.where("_id").is(id));
                avatars.add(mongoOperations.findOne(query, User.class).getAvatar());
            }
            mp.with("likeAvatar", avatars);
        }
        return JsonResult.success(mp.build());
    }

    private ArticlesVO toVO(Article article, String userId) {
        User user = mongoOperations.findOne(Query.query(Criteria.where("_id").is(article.getUserId())), User.class);
        if (user == null) return new ArticlesVO();
        return new ArticlesVO().switchVO(article, user.getNickName(),
                user.getAvatar(), !StringUtils.isEmpty(userId) && article.getLikeUserId().contains(userId));
    }
}
