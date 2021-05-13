package com.pmcc.core.security.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.pmcc.core.utils.JwtUtils;
import com.pmcc.system.dao.SysRoleUserDao;
import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @ClassName: JwtUserService <br>
 * @Description: 查询用户信息
 * @Date: 2019/12/15 13:43 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class JwtUserService implements UserDetailsService {

    @Autowired
    SysUserDao sysUserDao;  //注入用户查询操作类
    @Autowired
    SysRoleUserDao sysRoleUserDao;

    private PasswordEncoder passwordEncoder;

    public JwtUserService() {
//        this.passwordEncoder = new BCryptPasswordEncoder(); //Spring默认使用 bcrypt
        //默认使用 bcrypt， strength=10
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 根据用户名获取用户 - 用户的角色、权限等信息
     * 用户明是英文名 全系统唯一
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        //连接数据库，根据账号查询用户信息
        SysUser user = sysUserDao.getUserByName(username);
        if(user == null){
            return null;
        }
        /**
         * 用户权限信息
         * 返回用户角色 暂时该处控制到菜单级别
         */
//        String[] authList = getAuthorities(user.getId());  //权限列表
        String authList = String.valueOf(Arrays.asList("user", "admin"));
        return User.builder()
                .username(user.getUserEName())  //用户英文名  英文名默认未账号  唯一值
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(authList)    //权限信息
                .build();
    }

    /**
     * 获取用户的角色权限
     *
     * @param
     * @return
     */
    private String[] getAuthorities(String userID) {
        List<String> authList = sysRoleUserDao.userRole(userID);
        String[] str = new String[authList.size()];
        authList.toArray(str);
        return str;
    }

    public UserDetails getUserLoginInfo(String username) {
        String salt = "123456ef";
        /**
         * @todo 从数据库或者缓存中取出jwt token生成时用的salt
         * salt = redisTemplate.opsForValue().get("token:"+username);
         */
        UserDetails user = loadUserByUsername(username);
        //将salt放到password字段返回
        return User.builder()
                .username(user.getUsername())
                .password(salt)
                .authorities(user.getAuthorities())
                .build();
    }

    /**
     * 登录成功后，生成token，并把token加密相关信息缓存
     * JsonLoginSuccessHandler 调用该方法
     *
     * @param user
     * @return
     */
    public String saveUserLoginInfo(UserDetails user) {
        /**
         * 将salt保存到数据库或者缓存中 包含用户名  密钥  过期时间
         * 设置变量值的过期时间
         * redisTemplate.opsForValue().set("timeOutValue","timeOut",5,TimeUnit.SECONDS);
         */
        return JwtUtils.createToken(user);
    }

    /**
     * 验证密码
     *
     * @param oldPwd   未加密的密码 登录密码
     * @param password 加密后的密码 数据库保存密码
     */
    public boolean matchesPwd(String oldPwd, String password) {
        return passwordEncoder.matches(oldPwd, password);
    }

    public void createUser(String username, String password) {
        String encryptPwd = passwordEncoder.encode(password);
        /**
         * @todo 保存用户名和加密后密码到数据库
         */
    }

    public void deleteUserLoginInfo(String username) {
        /**
         * @todo 清除数据库或者缓存中登录salt
         */
    }

    /**
     * 返回当前活动用户信息
     */
    public SysUser onLineUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //连接数据库，根据账号查询用户信息
        SysUser user = sysUserDao.getUserByName(name);
        return user;
    }

}
