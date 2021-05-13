//package com.darkbf.core.configuration;
//
//import com.darkbf.core.Interceptors.TokenInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * @ClassName: TokenInterceptorConfig <br>
// * @Description: TODO 注册Token拦截器
// * @Date: 2019/12/15 11:11 <br>
// * @Author: DarkBF <br>
// * @Version: 1.0 <br>
// */
//public class TokenInterceptorConfig extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login/**");
//    }
//}
