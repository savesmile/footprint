package com.f_lin.user_api.api;

import com.f_lin.user_api.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@FeignClient(value = "user")
public interface UserApi {
    @RequestMapping(value = "/user/{phone}", method = RequestMethod.GET)
    User getUserByPhone(@PathVariable("phone") String phone);
}
