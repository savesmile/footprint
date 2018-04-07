package com.f_lin.gateway.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author F_lin
 * @since 2018/4/5
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Token {
    public static final String DEFAULT_SALT = "bishezhengtamananzuo";
    private String userId;
}
