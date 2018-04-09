package com.f_lin.user.po;

import com.f_lin.user_api.po.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author F_lin
 * @since 2018/4/7
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SimpleUserVO {
    private String nickName;
    private String introduction;
    private String avatar;
    private String userId;

    public SimpleUserVO(User user) {
        this.nickName = user.getNickName();
        this.introduction = user.getIntroduction();
        this.avatar = user.getAvatar();
        this.userId = user.getId();
    }
}
