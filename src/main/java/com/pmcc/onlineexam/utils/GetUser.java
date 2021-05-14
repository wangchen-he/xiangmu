package com.pmcc.onlineexam.utils;

import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetUser {
    @Autowired
     SysUserDao sysUserDao;
    public  SysUser getUsername(){
        SysUser user= null;
        //当前认证通过的用户身份
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        //用户信息
        Object principal = authenticator.getPrincipal();
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            System.out.println(userDetails.getUsername());
            user = sysUserDao.getUserByName(userDetails.getUsername());
        }
        return user;
    }
}
