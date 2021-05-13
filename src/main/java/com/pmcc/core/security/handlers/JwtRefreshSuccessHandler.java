package com.pmcc.core.security.handlers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.core.security.token.JwtAuthenticationToken;
import com.pmcc.core.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

/**
 * @ClassName: JwtRefreshSuccessHandler <br>
 * @Description: TODO token认证成功
 * @Date: 2019/12/15 13:46 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler{

    private static final Logger logger = LoggerFactory.getLogger(JwtRefreshSuccessHandler.class);

    private static final int tokenRefreshInterval = 300;  //刷新间隔5分钟

    private JwtUserService jwtUserService;

    private RequestCache requestCache = new HttpSessionRequestCache();

    public JwtRefreshSuccessHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功！");
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        /**
         * 认证成功后获取传入的token 验证是否需要刷新token
         */
        DecodedJWT jwt = JwtUtils.deToken(((JwtAuthenticationToken)authentication).getToken());
        //验证token是否需要刷新
        boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
        if(shouldRefresh) {
            //生成新的token
            String newToken = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
            response.setHeader("Authorization", newToken);
        }
    }

    //根据时间验证token是否需要刷新
    protected boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

}
