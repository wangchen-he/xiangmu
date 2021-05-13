package com.pmcc.core.security.token;

import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.core.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @ClassName: JwtAuthenticationProvider <br>
 * @Description: TODO 自定义用户认证处理器
 *   接收jwt的token，从数据库或者缓存中取出salt，对token做验证
 * @Date: 2019/12/15 14:03 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JwtAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private JwtUserService userService;

    public JwtAuthenticationProvider(JwtUserService userService) {
        this.userService = userService;
    }

    /**
     * 实现具体的认证逻辑
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /** 将未认证的Authentication转换成自定义的用户认证Token */
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String token = authenticationToken.getToken();
        UserDetails user;

        /**
         * todo  这里判断 authentication 是包含token 还是用户密码
         *  然后根据值做不同的验证
         *  如果是用户名密码则取查询库 如果是token 则去查询缓存(暂时跳过)
         */
        if(token!= null){
            /**
             * token认证
             */
            user = userService.loadUserByUsername(JwtUtils.getUserName(token));
            if (user == null || user.getPassword() == null) {
                throw new InternalAuthenticationServiceException("获取认证用户信息失败");
            }
        }else{
            /**
             * 账号密码认证
             * 根据用户名获取用户 - 用户的角色、权限等信息
             * 用户明是英文名 全系统唯一
             */
            user = userService.loadUserByUsername((String) (authenticationToken.getPrincipal()));

            if (user == null || user.getPassword() == null) {
                throw new InternalAuthenticationServiceException("获取认证用户信息失败");
            } else if (!userService.matchesPwd(authenticationToken.getCredentials().toString(), user.getPassword())) {
                throw new BadCredentialsException("用户名或密码不正确");
            }
            /**
             * 认证成功则创建一个已认证的用户认证Token
             */
            token = JwtUtils.createToken(user);
        }
        JwtAuthenticationToken authenticationResult  = new JwtAuthenticationToken(user, user.getPassword(), token,user.getAuthorities());
        /**
         * 设置一些详情信息
         */
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;

    }

    /**
     * 指定认证处理器处理当前JwtAuthenticationToken对象类似
     */
    @Override
    public boolean supports(Class<?> authentication) {
        /**
         * 指定该认证处理器能对JwtAuthenticationToken对象进行认证
         */
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
