package com.f_lin.user_api.api;

import com.f_lin.user_api.po.User;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@FeignClient(value = "user")
public interface UserApi {
    @RequestMapping(value = "/api/user/{phone}", method = RequestMethod.GET)
    User getUserByPhone(@PathVariable("phone") String phone);


    @RequestMapping(value = "/api/user/focus", method = RequestMethod.GET)
    List<String> getFocusUserList(@RequestHeader("x-auth-uid") String userId);

    @RequestMapping(value = "/api/user/focus/focused", method = RequestMethod.GET)
    boolean isFocus(@RequestHeader("x-auth-uid") String userId,
                    @RequestParam("with-user-id") String withUserId);

}
