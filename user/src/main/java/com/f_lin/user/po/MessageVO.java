package com.f_lin.user.po;

import com.f_lin.comment_api.po.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author F_lin
 * @since 2018/4/9
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MessageVO {
    private String articleId;
    /**
     * 发出者
     */
    private String fromUserId;
    /**
     * 上级评论
     */
    private String topCommentId;
    private String comment;
    private Date createDate;
    private Integer likeCount;
    private String authorName;
    private String avatar;

    public MessageVO switchVO(Comment comment, String authorName, String avatar) {
        this.articleId = comment.getArticleId();
        this.createDate = comment.getCreateDate();
        this.fromUserId = comment.getUserId();
        this.topCommentId = comment.getTopCommentId();
        this.comment = comment.getComment();
        this.likeCount = comment.getLikeCount();
        this.authorName = authorName;
        this.avatar = avatar;
        return this;
    }
}
