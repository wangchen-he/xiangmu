package com.pmcc.core.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
/**
 * @ClassName: HttpStatusLoginFailureHandler <br>
 * @Description: TODO 登录失败/token验证失败
 * @Date: 2019/12/15 14:01 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class HttpStatusLoginFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        //登录失败 返回401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(exception.getMessage());
    }
}
