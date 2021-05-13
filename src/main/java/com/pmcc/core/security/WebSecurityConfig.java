package com.pmcc.core.security;

import com.pmcc.core.security.configuration.JsonLoginConfigurer;
import com.pmcc.core.security.configuration.JwtLoginConfigurer;
import com.pmcc.core.security.filters.OptionsRequestFilter;
import com.pmcc.core.security.handlers.JsonLoginSuccessHandler;
import com.pmcc.core.security.handlers.JwtRefreshSuccessHandler;
import com.pmcc.core.security.handlers.TokenClearLogoutHandler;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.core.security.token.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @ClassName: WebSecurityConfig <br>
 * @Description: TODO Security配置
 * https://www.jianshu.com/p/d5ce890c67f7
 * https://www.cnblogs.com/wutongshu-master/p/10927870.html
 * https://www.cnblogs.com/loveer/p/11424821.html
 * https://blog.csdn.net/zimou5581/article/details/89511381
 *
 * 访问权限配置，使用url匹配是放过还是需要角色和认证
 * 跨域支持
 * 禁用csrf，csrf攻击是针对使用session的情况
 * 禁用默认的form登录支持
 * logout支持，spring security已经默认支持logout filter，会拦截/logout请求，
 * 交给logoutHandler处理，同时在logout成功后调用LogoutSuccessHandler。
 * 对于logout，我们需要清除保存的token salt信息，这样再拿logout之前的token访问就会失败。
 *
 * @Date: 2019/12/15 9:43 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)   //prePostEnabled 注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 授权器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制授权规则
        http.authorizeRequests()
                .antMatchers("/hello/**").permitAll() //访问无需认证
                  .antMatchers("/ureport/**").anonymous() //TODO 测试 20201113 报表工具UREPORT  放行/ureport/**路径
//                .antMatchers("/**").permitAll() // TODO 测试阶段所有访问无需认证
//                .antMatchers("/admin/**").hasAnyRole("ADMIN")   //访问admin下的请求拥有 USER角色
//                .antMatchers("/article/**").hasRole("USER")  //需登陆才能访问的url
//                .antMatchers("/").permitAll()     //访问首页的请求
//                .anyRequest().authenticated() //默认其它的请求都需要认证
//                //登录接口权限 允许
//                .regexMatchers("/user" + "/login.*")
                .and()
                .csrf().disable()  // CRSF禁用，因为不使用session  屏蔽跨站拦截
//                .formLogin().disable() //禁用自带登录页
                .sessionManagement().disable() //禁用session
                .cors() //支持跨域
                .and()
                //添加header设置，支持跨域和ajax请求
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                //支持所有源的访问
                new Header("Access-control-Allow-Origin","*"),
                //使ajax请求能够取到header中的jwt token信息
                new Header("Access-Control-Expose-Headers","Authorization"))))
                .and() //拦截OPTIONS请求，直接返回header
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
                //　这里指向了登录验证入口 添加登录filter 自定义的登录验证成功或失败后的去向
                .apply(new JsonLoginConfigurer<>()).loginSuccessHandler(jsonLoginSuccessHandler())
                .and()
//              //添加token的filter
                .apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jwtRefreshSuccessHandler()).permissiveRequestUrls("/logout")
                .and()
                //开启自动配置的注销功能 使用默认的logoutFilter
                .logout()
                //注销成功以后跳转的页面
//		        .logoutUrl("/logout")   //这里和自定义清理操作冲突 可以删除
                //退出后的自定义清理操作
                .addLogoutHandler(tokenClearLogoutHandler()) //logout时清除token
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()) //logout成功后返回200
                .and()
                .sessionManagement().disable()
                .headers().frameOptions().disable();

    }

    /**
     * 配置provider授权器(用户认证配置)
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //定义认证规则 附加访问权限
        auth.authenticationProvider(daoAuthenticationProvider())
          .authenticationProvider(jwtAuthenticationProvider()); //登录认证的自定义处理器
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("jwtAuthenticationProvider")
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtUserService());
    }

    /**
     * 设置通过什么方式进行校验
     * BCryptPasswordEncoder：相同的密码明文每次生成的密文都不同，安全性更高
     * @return
     * @throws Exception
     */
    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        //这里会默认使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        /**指定用户认证时，默认从哪里获取认证用户信息*/
        daoProvider.setUserDetailsService(userDetailsService());
        return daoProvider;
    }

    /**
     * 身份认证信息，登录后用户信息查询入口
     * @return
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return new JwtUserService();
    }

    @Bean("jwtUserService")
    protected JwtUserService jwtUserService() {
        return new JwtUserService();
    }

    @Bean
    protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
        return new JsonLoginSuccessHandler(jwtUserService());
    }

    @Bean
    protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
        return new JwtRefreshSuccessHandler(jwtUserService());
    }

//    /**
//     * 密码加密器
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        /**
//         * BCryptPasswordEncoder：相同的密码明文每次生成的密文都不同，安全性更高
//         */
//        return new BCryptPasswordEncoder();
//    }

    /**
     * 退出成功后的清理工作
     * @return
     */
    @Bean
    protected TokenClearLogoutHandler tokenClearLogoutHandler() {
        return new TokenClearLogoutHandler(jwtUserService());
    }

    /**
     * 跨域配置   添加头拦截配置参看 OncePerRequestFilter
     * @return
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        // 当前跨域请求最大有效时长。这里默认30天
        long maxAge = 30 * 24 * 60 * 60;

        CorsConfiguration configuration = new CorsConfiguration();
        // 支持证书，默认为true 用于 token 跨域
        configuration.setAllowCredentials(true);
        // 1 设置访问源地址
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // 2 设置访问源请求头
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 3 设置访问源请求方法
        configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD", "PUT", "OPTIONS", "DELETE"));
        configuration.setMaxAge(maxAge); // 预检请求有效时间 准备响应前的 缓存持续的 最大时间 或 3600L

        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 4 对接口配置跨域设置
        return source;
    }
}
