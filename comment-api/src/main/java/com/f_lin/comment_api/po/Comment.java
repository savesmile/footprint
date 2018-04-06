package com.f_lin.comment_api.po;

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
public class Comment {
    private String id;
    private String articleId;
    /**
     * 发出者
     */
    private String userId;
    /**
     * 接受者
     */
    private String toUserId;
    /**
     * 上级评论
     */
    private String topCommentId;
    private String comment;
    private Date createDate;
    private Date readDate;
    /**
     * 点赞数
     */
    private Integer likeCount;
}
