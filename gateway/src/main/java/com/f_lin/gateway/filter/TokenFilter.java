package com.f_lin.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author F_lin
 * @since 2018/3/18
 **/
@Component
public class TokenFilter extends ZuulFilter{
    private static Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String userId = request.getHeader("x-auth-uid");
        if (userId == null){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
        }
        log.info("uid: {} ", userId);

        return null;
    }
}
