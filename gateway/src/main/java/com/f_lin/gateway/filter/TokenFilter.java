package com.f_lin.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.po.Token;
import com.f_lin.gateway.utils.TokenUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.f_lin.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author F_lin
 * @since 2018/3/18
 **/
@Component
public class TokenFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    TokenVerificationSetting tokenVerificationSetting;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10000;
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
            setOrigin(ctx.getResponse());
            logger.info("======================userID {}====================", token.getUserId());
        }
        return null;
    }

    private void setOrigin(HttpServletResponse httpServletResponse) {
        //这里填写你允许进行跨域的主机ip
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "18000L");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
    }

    private String setMessage(String Message) {
        return JSONObject.toJSONString(JsonResult.error(Message));
    }


    private String test(HttpServletRequest req) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String body = IOUtils.toString(reader);
        return body;
    }
}
