package com.f_lin.gateway.filter;

import com.f_lin.gateway.po.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author F_lin fengjunlin@23mofang.com
 * @since 2018/3/16
 **/
@FeignClient(value = "gateway", primary = false)
public interface TestApi {

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    Test testFeign();
}
