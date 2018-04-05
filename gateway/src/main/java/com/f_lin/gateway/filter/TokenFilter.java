package com.f_lin.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.po.Token;
import com.f_lin.gateway.utils.TokenUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author F_lin
 * @since 2018/3/18
 **/
@Component
public class TokenFilter extends ZuulFilter {

    @Autowired
    TokenVerificationSetting tokenVerificationSetting;

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
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().setCharacterEncoding("utf-8");
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        List<String> tokenVerification = tokenVerificationSetting.getNo_token_verification();
        if (!tokenVerification.contains(requestURI)) {
            String tokenStr = request.getParameter("token");
            if (tokenStr == null || "".equals(tokenStr)) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(setMessage("请上传token信息!"));
                return null;
            }
            Token token = TokenUtils.decryptionToken(tokenStr);
            if (token.getUserId() == null) {
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(setMessage("错误的token信息!"));
                return null;
            }
            ctx.addZuulRequestHeader("x-auth-uid", token.getUserId());
        }
        return null;
    }


    private String setMessage(String Message) {
        return JSONObject.toJSONString(JsonResult.ofErrorResult(401, Message));
    }
}
