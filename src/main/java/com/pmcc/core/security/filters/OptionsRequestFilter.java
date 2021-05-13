package com.pmcc.core.security.filters;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @ClassName: OptionsRequestFilter <br>
 * @Description: TODO OPTIONS请求配置
 * ajax的跨域请求，浏览器在发送真实请求之前，会向服务端发送OPTIONS请求，看服务端是否支持。
 * 只需要返回header，不需要filter 配置项通webSecurityConfig   corsConfigurationSource()
 * @Date: 2019/12/15 14:31 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class OptionsRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //判断是OPTIONS请求配置请求 填充header后就直接返回
        if(request.getMethod().equals("OPTIONS")) {
            // 支持证书，默认为true 用于 token 跨域
            response.setHeader("Access-Control-Allow-Credentials", "true");
            // 1 设置访问源地址
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 2 设置访问源请求头
            response.setHeader("Access-Control-Allow-Headers", response.getHeader("Access-Control-Request-Headers"));
            // 3 设置访问源请求方法
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
            // 预检请求有效时间
            response.setHeader("Access-Control-Max-Age", "86400");
            //检测是options方法则直接返回200
            response.setStatus(HttpServletResponse.SC_OK);
//            response.setStatus(HttpStatus.OK.value());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
