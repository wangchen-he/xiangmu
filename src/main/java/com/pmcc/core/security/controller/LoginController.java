package com.pmcc.core.security.controller;

import com.pmcc.core.api.ApiResult;
import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: LoginController <br>
 * @Description: TODO 登录
 * @Date: 2019/12/15 21:19 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */

@RestController
public class LoginController {

    @Autowired
    SysUserDao sysUserDao;

    //    @PreAuthorize("hasAuthority('p1')")  //拥有P1权限才可以访问 等同配置授权 开启不设置则不拦截
    @RequestMapping("/login")
    public ApiResult loginSuccess(){
        return ApiResult.success(getUsername(),"登录成功");
    }

    private SysUser getUsername(){
        SysUser user= null;
        //当前认证通过的用户身份
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        //用户信息
        Object principal = authenticator.getPrincipal();
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            user = sysUserDao.getUserByName(userDetails.getUsername());
        }
        return user;
    }
}
