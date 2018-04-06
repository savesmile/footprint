package com.f_lin.comment_api.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Article {
    private String id;
    private String title;
    private String userId;
    private Date createTime;
    private String summary;
    private String location;
    private String secret;
    private String imgPath;
    private Integer likeCount;
    private List<String> likeUserId;
}
