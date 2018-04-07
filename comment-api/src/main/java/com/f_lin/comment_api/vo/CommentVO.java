package com.f_lin.comment_api.vo;

import com.f_lin.comment_api.po.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author F_lin
 * @since 2018/4/7
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentVO {
    private String id;
    private String topCommentId;
    private String comment;
    private Date createDate;
    /**
     * 点赞数
     */
    private Integer likeCount;
    private String avatarNickName;
    private String avatarPath;

    public CommentVO switchVO(Comment comment, String name, String path) {
        this.id = comment.getId();
        this.topCommentId = comment.getTopCommentId();
        this.comment = comment.getComment();
        this.createDate = comment.getCreateDate();
        this.likeCount = comment.getLikeCount();
        this.avatarNickName = name;
        this.avatarPath = path;
        return this;
    }
}
