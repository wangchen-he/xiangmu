package com.pmcc.core.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.core.security.token.JwtAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @ClassName: JsonLoginSuccessHandler <br>
 * @Description: TODO 登录成功
 * @Date: 2019/12/15 13:45 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler{

    private JwtUserService jwtUserService;

    public JsonLoginSuccessHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    /**
     * 认证成功
     * 认证成功后spring security默认会做处理 隐藏密码不返回给前台 所以这里密码为null
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //这里认证成功后考虑生成JwtAuthenticationToken类型的token
        //生成token，并把token加密相关信息缓存，具体请看实现类
//        String token = jwtUserService.saveUserLoginInfo((UserDetails)authentication.getPrincipal());
        String token = ((JwtAuthenticationToken)authentication).getToken();
        if(token != null){
            response.setHeader("Authorization", token);
            //TODO 登录成功 返回HttpStatus.OK 200
            response.setStatus(HttpStatus.OK.value());
        }else{
            //TOKEN 创建失败  返回HttpStatus.INTERNAL_SERVER_ERROR 500
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        //这里处理成功后返回的数据给前台
        //返回json数据
        ApiResult result = ApiResult.success();
        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));

    }

}
