package com.f_lin.gateway.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author F_lin
 * @since 2018/4/5
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JsonResult<R> {
    private Integer code;
    private String message;
    private R data;

    public static JsonResult ofErrorResult(Integer errorCode, String errorMessages) {
        return new JsonResult<>(errorCode, errorMessages, null);
    }
}
