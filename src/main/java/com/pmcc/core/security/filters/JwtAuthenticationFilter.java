package com.pmcc.core.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.core.security.token.JwtAuthenticationProvider;
import com.pmcc.core.security.token.JwtAuthenticationToken;
import com.pmcc.core.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @ClassName: JwtAuthenticationFilter <br>
 * @Description: TODO 拦截除登录之外的所有请求，提取header中的token
 * @Date: 2019/12/15 13:55 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserService userService;
//    @Autowired
//    RedisTemplate<String, String> redisTemplate;

    private RequestMatcher requiresAuthenticationRequestMatcher;
    private List<RequestMatcher> permissiveRequestMatchers;
    private AuthenticationManager authenticationManager;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    public JwtAuthenticationFilter() {
        //拦截header中带Authorization的请求
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
        Assert.notNull(successHandler, "AuthenticationSuccessHandler must be specified");
        Assert.notNull(failureHandler, "AuthenticationFailureHandler must be specified");
    }

    protected String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        //从字符串中移除匹配的字符或字符序列
//        return StringUtils.removeStart(authInfo, "Bearer ");
        return authInfo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //header没带token的，直接放过，因为部分url匿名用户也可以访问
        //如果需要不支持匿名用户的请求没带token，这里放过也没问题，
        // 因为SecurityContext中没有认证信息，后面会被权限控制模块拦截
        if (!requiresAuthentication(request, response)) {
            //将请求转发给过滤器链下一个filter
            filterChain.doFilter(request, response);
            return;
        }
        //认证信息
        Authentication authResult = null;
        //失败异常
        AuthenticationException failed = null;
        try {
            //从头中获取token并封装后提交给AuthenticationManager
            String token = getJwtToken(request);
//            String authHeader = request.getHeader(this.tokenHeader);
//            if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(tokenHead)) {
//                //如果header中存在token，则覆盖掉url中的token
//                authToken = authHeader.substring(tokenHead.length()); // "Bearer "之后的内容
//            }

            //判断token是否为null
            if (StringUtils.isNotBlank(token)) {
                //不为空且长度不为0 调用自定义JWT身份信息
                //TODO 需要在 JwtAuthenticationProvider 的authenticate方法中验证TOKEN
                // 考虑生成token后保存在数据库或者内存中待用
                if (!JwtUtils.verify(token))
                    throw new NonceExpiredException("Token授权已过期");
                //这里仅把获取到的TOKEN发送到authenticate方法中验证TOKEN
                JwtAuthenticationToken authToken = new JwtAuthenticationToken(token);
                //TOKEN认证
                authResult = this.getAuthenticationManager().authenticate(authToken);

            } else {
                //如果token长度为0
                failed = new InsufficientAuthenticationException("JWT is Empty");
            }
        } catch (JWTDecodeException e) {
            logger.error("JWT format error", e);
            failed = new InsufficientAuthenticationException("JWT format error", failed);
        } catch (InternalAuthenticationServiceException e) {
            logger.error(
                    "An internal error occurred while trying to authenticate the user.",
                    failed);
            failed = e;
        } catch (AuthenticationException e) {
            // Authentication failed
            failed = e;
        }
        if (authResult != null) {
            //token认证成功
            successfulAuthentication(request, response, filterChain, authResult);
        } else if (!permissiveRequest(request)) {
            //token认证失败，并且这个request不在例外列表里，才会返回错误
            unsuccessfulAuthentication(request, response, failed);
            return;
        }

        //将请求转发给过滤器链下一个filter
        filterChain.doFilter(request, response);
    }

    //token认证失败
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    //token认证成功
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // 将当前的认证信息放到SecurityContextHolder中 包含用户在线信息
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // 调用成功处理器，可以自己实现AuthenticationSuccessHandler接口重写方法写自己的逻辑
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    protected boolean permissiveRequest(HttpServletRequest request) {
        if (permissiveRequestMatchers == null)
            return false;
        for (RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if (permissiveMatcher.matches(request))
                return true;
        }
        return false;
    }

    public void setPermissiveUrl(String... urls) {
        if (permissiveRequestMatchers == null)
            permissiveRequestMatchers = new ArrayList<>();
        for (String url : urls)
            permissiveRequestMatchers.add(new AntPathRequestMatcher(url));
    }

    public void setAuthenticationSuccessHandler(
            AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(
            AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    protected AuthenticationSuccessHandler getSuccessHandler() {
        return successHandler;
    }

    protected AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }
}
