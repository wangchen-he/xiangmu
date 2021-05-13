package com.pmcc.core.security.filters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pmcc.core.security.token.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @ClassName: LoginAuthenticationFilter <br>
 * @Description: TODO 登录过滤器
 * @Date: 2019/12/15 11:20 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private boolean postOnly = true;

    public LoginAuthenticationFilter() {
        /**
         * 1.匹配URL和Method
         * 设置该过滤器对POST请求/login进行拦截
         */
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
        Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
        Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            //啥？你没有用POST方法，给你一个异常，自己反思去
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
             /**
             * 2、从http请求中获取用户登录方式、用户名、密码
             * 登录方式   0：账号密码  1:手机登录   (登陆类型：user:用户密码登陆；phone:手机验证码登陆；qr:二维码扫码登陆)
             */
            String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
            String username = null, password = null, type = null;
            //获取前台传入的登录方式、账号、用户名
            if (StringUtils.hasText(body)) {
                JSONObject jsonObj = JSON.parseObject(body);
                type = jsonObj.getString("type");  //登录方式
                username = jsonObj.getString("userName");
                //TODO 此处密码应该进性加密后验证
                password = jsonObj.getString("password");
            }
            if(StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
                throw new UsernameNotFoundException("LoginAuthenticationFilter获取用户认证信息失败");
            }
            username = username.trim();
            /**
             *
             * 使用用户输入的用户名和密码信息创建一个未认证的用户认证Token
             */
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username, password, null);
            /**
             * 顺便把请求和Token存起来
             * 设置一些详情信息
             */
            this.setDetails(request, authRequest);
            /**
             * Token给谁处理呢？当然是给当前的AuthenticationManager喽
             * 通过AuthenticationManager调用相应的AuthenticationProvider进行用户认证
             */
            return this.getAuthenticationManager().authenticate(authRequest);
        }

    }

    protected void setDetails(HttpServletRequest request, JwtAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
