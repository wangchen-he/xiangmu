package com.pmcc.core.security.configuration;

import com.pmcc.core.security.handlers.HttpStatusLoginFailureHandler;
import com.pmcc.core.security.filters.JwtAuthenticationFilter;
import com.pmcc.core.security.token.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @ClassName: JwtLoginConfigurer <br>
 * @Description: TODO 初始化和配置JWTFilter
 * @Date: 2019/12/15 13:54 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JwtLoginConfigurer <T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;

    private JwtAuthenticationFilter authFilter;

    public JwtLoginConfigurer() {
        this.authFilter = new JwtAuthenticationFilter();
    }

    @Override
    public void configure(B http) throws Exception {
        /**
         * 自定义用户认证处理逻辑时，需要指定AuthenticationManager，否则无法认证
         */
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        /**
         * 指定自定义的失败的处理器
         */
        authFilter.setAuthenticationFailureHandler(new HttpStatusLoginFailureHandler());

        //将filter放到logoutFilter之前
        JwtAuthenticationFilter filter = postProcess(authFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }

    //设置匿名用户可访问url
    public JwtLoginConfigurer<T, B> permissiveRequestUrls(String ... urls){
        authFilter.setPermissiveUrl(urls);
        return this;
    }

    public JwtLoginConfigurer<T, B> tokenValidSuccessHandler(AuthenticationSuccessHandler successHandler){
        authFilter.setAuthenticationSuccessHandler(successHandler);
        return this;
    }

}
