package com.f_lin.user_api.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    /**
     * 默认男性
     */
    public static final int SEX_MALE = 0;
    /**
     * 默认女性
     */
    public static final int SEX_FEMALE = 1;

    private String id;
    private int sex;
    private String nickName;
    private String phone;
    private String password;
    private String avatar;

}
