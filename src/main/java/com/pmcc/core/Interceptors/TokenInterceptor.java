//package com.darkbf.core.Interceptors;
//
//import com.darkbf.core.utils.JwtUtils;
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @ClassName: TokenInterceptor <br>
// * @Description: TODO Token验证拦截器
// * @Date: 2019/12/8 13:57 <br>
// * @Author: DarkBF <br>
// * @Version: 1.0 <br>
// */
//public class TokenInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private JwtUtils jwtUtil;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //获取请求头（如果有此请求头，表示token已经签发）
//        String header = request.getHeader("tokenHeader");
//        if (header != null || !"".equals(header)) {
//            //解析请求头（防止伪造token，token内容以"token "作为开头）
//            if (header.startsWith("token ")) {
//                try {
//                    Claims claims = jwtUtil.checkToken(header.substring(6));
//                    String role = (String) claims.get("role");
//                    //为具有相关权限的用户添加权限到request域中
//                    if ("admin".equals(role)) {
//                        //拿到"admin_token"头信息，表示当前角色是admin
//                        request.setAttribute("admin_token", header.substring(6));
//                    }
//                    if ("user".equals(role)) {
//                        //拿到"user_token"头信息，表示当前角色是user
//                        request.setAttribute("user_token", header.substring(6));
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException("令牌不正确");
//                }
//            }
//        }
//        //所有请求都通过，具体权限在service层判断
//        return true;
//    }
//}
