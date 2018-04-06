package com.f_lin.common.po;

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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AuthCode {
    private String id;
    private String phone;
    private String authCode;
    private Integer aliveCount;
    private Date createTime;
}
