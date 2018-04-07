package com.f_lin.gateway.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
public class UserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(UserIdMethodArgumentResolver.class);
    public static final String HEADER_X_AUTH_UID = "X-Auth-Uid";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String userId = webRequest.getHeader(HEADER_X_AUTH_UID);
        logger.info("userId 为 {}", userId);
        if (userId == null && parameter.getParameterAnnotation(UserId.class).required()) {
            throw new RuntimeException("UserId不能为空");
        }
        return userId;
    }
}