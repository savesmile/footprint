package com.f_lin.user_api.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/7
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Focus {
    private String id;
    private String userId;
    private List<String> focusUserIds;
    private List<String> followUserIds;
}
