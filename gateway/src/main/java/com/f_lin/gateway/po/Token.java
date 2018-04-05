package com.f_lin.gateway.po;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author F_lin
 * @since 2018/4/5
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Token {
    public static final String DEFAULT_SALT = "bishezhengtamananzuo";
    private String userId;
}
