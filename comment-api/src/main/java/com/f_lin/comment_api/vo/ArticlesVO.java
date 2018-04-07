package com.f_lin.comment_api.vo;

import com.f_lin.comment_api.po.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticlesVO {
    private String id;
    private String title;
    private Date createTime;
    private String summary;
    private String imgPath;
    private Integer likeCount;
    private String authorName;
    private String avatarPath;
    private String location;
    private boolean isLike;
    private String content;
    private String authorId;

    public ArticlesVO switchVO(Article article,
                               String authorName,
                               String avatarPath,
                               boolean isLike) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.createTime = article.getCreateTime();
        this.summary = article.getSummary();
        this.imgPath = article.getImgPath();
        this.likeCount = article.getLikeCount();
        this.authorName = authorName;
        this.avatarPath = avatarPath;
        this.isLike = isLike;
        this.content = article.getContent();
        this.location = article.getLocation();
        this.authorId = article.getUserId();
        return this;
    }
}
