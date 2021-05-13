package com.pmcc.core.security.configuration;

import com.pmcc.core.security.handlers.HttpStatusLoginFailureHandler;
import com.pmcc.core.security.filters.LoginAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 * @ClassName: JsonLoginConfigurer <br>
 * @Description: TODO 登录配置
 * @Date: 2019/12/15 14:29 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B>  {

    private LoginAuthenticationFilter authFilter;

    public JsonLoginConfigurer() {
        this.authFilter = new LoginAuthenticationFilter();
    }

    @Override
    public void configure(B http) throws Exception {
        //自定义用户认证处理逻辑时，需要指定AuthenticationManager，否则无法认证
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置失败的Handler
        authFilter.setAuthenticationFailureHandler(new HttpStatusLoginFailureHandler());
        //不将认证后的context放入session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        LoginAuthenticationFilter filter = postProcess(authFilter);
        //指定Filter的位置
        http.addFilterAfter(filter, LogoutFilter.class);
    }

    //设置成功的Handler，这个handler定义成Bean，所以从外面set进来
    public JsonLoginConfigurer<T,B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }
}
