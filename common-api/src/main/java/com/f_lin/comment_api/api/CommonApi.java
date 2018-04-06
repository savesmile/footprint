package com.f_lin.comment_api.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@FeignClient(value = "common")
public interface CommonApi {
    @RequestMapping(value = "/api/common/auth-code/verify?auth_code={auth_code}", method = RequestMethod.GET)
    boolean verifyAuthCode(@RequestHeader("x-auth-uid") String userId,
                           @PathVariable("auth_code") String authCode);
}
